// imports
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.observers.*;


// potential returns
// return totalCost
// return cartWeight


public class ScanItem {
    private SelfCheckoutStation checkoutStation;
    private Double cartWeight;
    private BigDecimal totalCost;   // amount to be paid/ total

    public ScanItem(//parameters for this constructor)
    {

    }
    
    // implementing observer
    private class SI implements BarcodeScannerObserver {
        @Override
        public void enabled(AbstractDevice<? extends AbstractDeviceObserver>)
        {

        }
        
        public void disabled(AbstractDevice<? extends AbstractDeviceObserver>)
        {

        }
    }
     
}
