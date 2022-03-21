import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

import java.math.BigDecimal;
import java.util.Currency;

public class SoftwareTest
{
    // Declare the self checkout stations that will be initialized and used by the test cases
    SelfCheckoutStation selfCheckoutStation;

    // Declares all the software implementations
    Checkout checkoutUseCase = new Checkout();
    coinUSE coinUseCase = new coinUSE();
    PayBanknote banknoteUseCase = new PayBanknote(0);
    ScanItem scanItemUseCase = new ScanItem();

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

    // Testing template (can be used for all/most of the test cases)
    @Test
    public void testScanningAnItemSuccessfully()
    {
        selfCheckoutStation.scanner.attach(scanItemUseCase);
        selfCheckoutStation.scanner.scan(new BarcodedItem(null, 22));
    }
}
