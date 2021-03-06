import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import java.math.BigDecimal;
import java.util.Currency;

public class SoftwareTest
{
    // Declare the self checkout stations that will be initialized and used by the test cases
    SelfCheckoutStation selfCheckoutStation;

    // Declare the receipt printer hardware
    ReceiptPrinter receiptPrinter;

    // Declares all the software implementations
    BarcodedItemCollection itemLookup;
    ScanItem scanItemUseCase;
    BaggingArea baggingAreaUseCase;
    PayCoin coinUseCase;
    PayBanknote banknoteUseCase;
    Checkout checkoutUseCase;

    // Initialize static arrays to store the banknote and coin denominations (that will be fed to the self checkout station)
    final int[] banknoteDenominations = new int[] {5, 10, 20, 50};
    final BigDecimal[] coinDenominations = new BigDecimal[] {BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), BigDecimal.valueOf(1.00), BigDecimal.valueOf(2.00)};

    // Initialize static variables that will be used by the test cases
    final int scaleWeightLimit = 1000;
    final int scaleSensitivity = 10;

    // Setup performed before each test case
    @Before
    public void setup()
    {
        // Initializes the self checkout station
        selfCheckoutStation = new SelfCheckoutStation(Currency.getInstance("CAD"), banknoteDenominations, coinDenominations, scaleWeightLimit, scaleSensitivity);

        // Initialize the receipt printer hardware
        receiptPrinter = new ReceiptPrinter();

        // Initialize all the software implementations
        itemLookup = new BarcodedItemCollection();
        scanItemUseCase = new ScanItem();
        baggingAreaUseCase = new BaggingArea();
        coinUseCase = new PayCoin();
        banknoteUseCase = new PayBanknote(0);
        checkoutUseCase = new Checkout(banknoteUseCase, coinUseCase, scanItemUseCase, itemLookup, baggingAreaUseCase, receiptPrinter, 1000, 1000);
    }

    // Tests to see if an item is successfully scanned and stored (attempts scanning up to 5 times if necessary due to there being a chance for a scan to fail)
    @Test
    public void testScanningAnItemSuccessfully()
    {
        selfCheckoutStation.scanner.attach(scanItemUseCase);
        BarcodedItem testBarcodedItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), 150);

        for (int attemptCounter = 0; attemptCounter < 5; attemptCounter++)
            if (scanItemUseCase.barcodesScanned.isEmpty()) selfCheckoutStation.scanner.scan(testBarcodedItem);

        Assert.assertTrue(scanItemUseCase.barcodesScanned.contains(testBarcodedItem.getBarcode()));
    }

    // Tests to see if an item is successfully added to the bagging area
    @Test
    public void testAddingItemToBaggingSuccessfully()
    {
        selfCheckoutStation.scale.attach(baggingAreaUseCase);
        BarcodedItem testBarcodedItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), scaleSensitivity * 2);

        selfCheckoutStation.scale.add(testBarcodedItem);

        Assert.assertEquals(baggingAreaUseCase.getWeightInGrams(), testBarcodedItem.getWeight(), 0);
    }

    // Tests to see if an item is not successfully added to the bagging area due to being overweight
    @Test
    public void testAddingItemToBaggingUnsuccessfully()
    {
        selfCheckoutStation.scale.attach(baggingAreaUseCase);
        BarcodedItem testBarcodedItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), scaleWeightLimit + scaleSensitivity);

        selfCheckoutStation.scale.add(testBarcodedItem);

        Assert.assertEquals(baggingAreaUseCase.getWeightInGrams(), 0, 0);
    }

    // Tests to see if an item is successfully added to the collections class (used for item information lookup)
    @Test
    public void testAddingItemToCollectionSuccessfully()
    {
        BarcodedItem testBarcodedItem1 = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), scaleSensitivity);
        BarcodedProduct testBarcodedProduct1 = new BarcodedProduct(new Barcode(new Numeral[] {Numeral.one}), "N/A", BigDecimal.valueOf(10.00));
        BarcodedItem testBarcodedItem2 = new BarcodedItem(new Barcode(new Numeral[] {Numeral.two}), scaleSensitivity * 2);
        BarcodedProduct testBarcodedProduct2 = new BarcodedProduct(new Barcode(new Numeral[] {Numeral.two}), "N/A", BigDecimal.valueOf(20.00));

        itemLookup.addItem(testBarcodedItem1);
        itemLookup.addProduct(testBarcodedProduct1);
        itemLookup.addItem(testBarcodedItem2);
        itemLookup.addProduct(testBarcodedProduct2);

        Assert.assertEquals(itemLookup.getExpectedWeight(testBarcodedItem1.getBarcode()), testBarcodedItem1.getWeight(), 0);
        Assert.assertEquals(itemLookup.getPrice(testBarcodedItem1.getBarcode()), testBarcodedProduct1.getPrice());
        Assert.assertEquals(itemLookup.getExpectedWeight(testBarcodedItem2.getBarcode()), testBarcodedItem2.getWeight(), 0);
        Assert.assertEquals(itemLookup.getPrice(testBarcodedItem2.getBarcode()), testBarcodedProduct2.getPrice());
    }

    // Tests to see if a coin is successfully added and stored
    @Test
    public void testInsertedACoinSuccessfully() throws DisabledException
    {
        selfCheckoutStation.coinValidator.attach(coinUseCase);
        selfCheckoutStation.coinSlot.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(2.00)));

        Assert.assertEquals(coinUseCase.coinTotal, BigDecimal.valueOf(2.00));
    }

    // Tests to see if a coin is unsuccessfully added (provided an invalid denomination)
    @Test
    public void testInsertedACoinUnsuccessfully() throws DisabledException
    {
        selfCheckoutStation.coinValidator.attach(coinUseCase);
        selfCheckoutStation.coinSlot.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(10.00)));

        Assert.assertEquals(coinUseCase.coinTotal, BigDecimal.valueOf(0));
    }

    // Tests to see if a banknote is successfully added and stored
    @Test
    public void testInsertedABanknoteSuccessfully() throws DisabledException, OverloadException
    {
        selfCheckoutStation.banknoteValidator.attach(banknoteUseCase);
        selfCheckoutStation.banknoteInput.accept(new Banknote(Currency.getInstance("CAD"), 50));

        Assert.assertEquals(banknoteUseCase.getTotalBanknotes(), 50);
    }

    // Tests to see if a banknote is unsuccessfully added (provided an invalid denomination)
    @Test
    public void testInsertedABanknoteUnsuccessfully() throws DisabledException, OverloadException
    {
        selfCheckoutStation.banknoteValidator.attach(banknoteUseCase);
        selfCheckoutStation.banknoteInput.accept(new Banknote(Currency.getInstance("CAD"), 100));

        Assert.assertEquals(banknoteUseCase.getTotalBanknotes(), 0);
    }

    // Tests to see if the checkout process is successful
    @Test
    public void testSuccessfulCheckout() throws DisabledException, OverloadException
    {
        selfCheckoutStation.scanner.attach(scanItemUseCase);
        selfCheckoutStation.scale.attach(baggingAreaUseCase);
        selfCheckoutStation.coinValidator.attach(coinUseCase);
        selfCheckoutStation.banknoteValidator.attach(banknoteUseCase);

        BarcodedItem testBarcodedItem1 = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), scaleSensitivity * 2);
        BarcodedProduct testBarcodedProduct1 = new BarcodedProduct(new Barcode(new Numeral[] {Numeral.one}), "N/A", BigDecimal.valueOf(10.00));
        BarcodedItem testBarcodedItem2 = new BarcodedItem(new Barcode(new Numeral[] {Numeral.two}), scaleSensitivity * 3);
        BarcodedProduct testBarcodedProduct2 = new BarcodedProduct(new Barcode(new Numeral[] {Numeral.two}), "N/A", BigDecimal.valueOf(20.00));

        itemLookup.addItem(testBarcodedItem1);
        itemLookup.addProduct(testBarcodedProduct1);
        itemLookup.addItem(testBarcodedItem2);
        itemLookup.addProduct(testBarcodedProduct2);

        for (int attemptCounter = 0; attemptCounter < 10; attemptCounter++)
            if (scanItemUseCase.barcodesScanned.size() == 0) selfCheckoutStation.scanner.scan(testBarcodedItem1);
        for (int attemptCounter = 0; attemptCounter < 10; attemptCounter++)
            if (scanItemUseCase.barcodesScanned.size() == 1) selfCheckoutStation.scanner.scan(testBarcodedItem2);

        selfCheckoutStation.scale.add(testBarcodedItem1);
        selfCheckoutStation.scale.add(testBarcodedItem2);

        selfCheckoutStation.coinSlot.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(2.00)));
        selfCheckoutStation.coinSlot.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(1.00)));
        selfCheckoutStation.coinSlot.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(2.00)));

        selfCheckoutStation.banknoteInput.accept(new Banknote(Currency.getInstance("CAD"), 10));
        selfCheckoutStation.banknoteInput.accept(new Banknote(Currency.getInstance("CAD"), 5));
        selfCheckoutStation.banknoteInput.accept(new Banknote(Currency.getInstance("CAD"), 10));

        Assert.assertEquals(checkoutUseCase.checkoutMain(), 0);
    }

    // Tests to see if the checkout process is unsuccessful due to insufficient funds
    @Test
    public void testUnsuccessfulCheckout() throws DisabledException, OverloadException
    {
        selfCheckoutStation.scanner.attach(scanItemUseCase);
        selfCheckoutStation.scale.attach(baggingAreaUseCase);
        selfCheckoutStation.coinValidator.attach(coinUseCase);
        selfCheckoutStation.banknoteValidator.attach(banknoteUseCase);

        BarcodedItem testBarcodedItem1 = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), scaleSensitivity * 2);
        BarcodedProduct testBarcodedProduct1 = new BarcodedProduct(new Barcode(new Numeral[] {Numeral.one}), "N/A", BigDecimal.valueOf(10.00));
        BarcodedItem testBarcodedItem2 = new BarcodedItem(new Barcode(new Numeral[] {Numeral.two}), scaleSensitivity * 3);
        BarcodedProduct testBarcodedProduct2 = new BarcodedProduct(new Barcode(new Numeral[] {Numeral.two}), "N/A", BigDecimal.valueOf(20.00));

        itemLookup.addItem(testBarcodedItem1);
        itemLookup.addProduct(testBarcodedProduct1);
        itemLookup.addItem(testBarcodedItem2);
        itemLookup.addProduct(testBarcodedProduct2);

        for (int attemptCounter = 0; attemptCounter < 10; attemptCounter++)
            if (scanItemUseCase.barcodesScanned.size() == 0) selfCheckoutStation.scanner.scan(testBarcodedItem1);
        for (int attemptCounter = 0; attemptCounter < 10; attemptCounter++)
            if (scanItemUseCase.barcodesScanned.size() == 1) selfCheckoutStation.scanner.scan(testBarcodedItem2);

        selfCheckoutStation.scale.add(testBarcodedItem1);
        selfCheckoutStation.scale.add(testBarcodedItem2);

        selfCheckoutStation.coinSlot.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(2.00)));
        selfCheckoutStation.coinSlot.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(1.00)));

        selfCheckoutStation.banknoteInput.accept(new Banknote(Currency.getInstance("CAD"), 10));
        selfCheckoutStation.banknoteInput.accept(new Banknote(Currency.getInstance("CAD"), 5));

        Assert.assertEquals(checkoutUseCase.checkoutMain(), 2);
    }
}
