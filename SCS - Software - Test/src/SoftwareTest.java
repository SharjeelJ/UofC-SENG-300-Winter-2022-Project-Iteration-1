import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import java.math.BigDecimal;
import java.util.Currency;

public class SoftwareTest
{
    // Declare the self checkout stations that will be initialized and used by the test cases
    SelfCheckoutStation selfCheckoutStation;

    // Declares all the software implementations
    BarcodedItemCollection itemLookup = new BarcodedItemCollection();
    Checkout checkoutUseCase = new Checkout();
    ScanItem scanItemUseCase = new ScanItem();
    // TODO: Adjust the class to be the bagging area use case's once implemented
    ElectronicScaleObserver baggingAreaUseCase = new ElectronicScaleObserver()
    {
        @Override
        public void weightChanged(ElectronicScale scale, double weightInGrams)
        {

        }

        @Override
        public void overload(ElectronicScale scale)
        {

        }

        @Override
        public void outOfOverload(ElectronicScale scale)
        {

        }

        @Override
        public void enabled(AbstractDevice <? extends AbstractDeviceObserver> device)
        {

        }

        @Override
        public void disabled(AbstractDevice <? extends AbstractDeviceObserver> device)
        {

        }
    };
    PayCoin coinUseCase = new PayCoin();
    PayBanknote banknoteUseCase = new PayBanknote(0);

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
        BarcodedItem testBarcodedItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), scaleSensitivity);
        selfCheckoutStation.scale.add(testBarcodedItem);
        // TODO: Check if the weight reported by the bagging area is equal to that of the item just added
        //        Assert.assertEquals(baggingAreaUseCase.totalWeight(), testItem.getWeight());
    }

    // Tests to see if an item is not successfully added to the bagging area due to being overweight
    @Test
    public void testAddingItemToBaggingUnsuccessfully()
    {
        selfCheckoutStation.scale.attach(baggingAreaUseCase);
        BarcodedItem testBarcodedItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), scaleSensitivity);
        selfCheckoutStation.scale.add(testBarcodedItem);
        // TODO: Check if the weight reported by the bagging area is equal to 0 (item was not added)
        //        Assert.assertNotEquals(baggingAreaUseCase.totalWeight(), 0);
    }

    // Tests to see if an item is successfully added to the collections class (used for item information lookup)
    @Test
    public void testAddingItemToCollectionSuccessfully()
    {
        BarcodedItem testBarcodedItem = new BarcodedItem(new Barcode(new Numeral[] {Numeral.one}), scaleSensitivity);
        BarcodedProduct testBarcodedProduct = new BarcodedProduct(new Barcode(new Numeral[] {Numeral.one}), "N/A", BigDecimal.valueOf(10.00));
        itemLookup.addItem(testBarcodedItem);
        itemLookup.addProduct(testBarcodedProduct);
        Assert.assertEquals(itemLookup.getExpectedWeight(testBarcodedItem.getBarcode()), testBarcodedItem.getWeight(), 0);
        Assert.assertEquals(itemLookup.getPrice(testBarcodedItem.getBarcode()), testBarcodedProduct.getPrice().multiply(BigDecimal.valueOf(testBarcodedItem.getWeight())));
    }
}
