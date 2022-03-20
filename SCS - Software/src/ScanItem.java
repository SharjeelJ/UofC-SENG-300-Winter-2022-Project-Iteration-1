import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;

import java.util.ArrayList;

public class ScanItem implements BarcodeScannerObserver
{
    public ArrayList <Barcode> barcodesScanned = new ArrayList <>();

    /**
     * Announces that the indicated device has been enabled.
     *
     * @param device The device that has been enabled.
     */
    @Override
    public void enabled(AbstractDevice <? extends AbstractDeviceObserver> device)
    {

    }

    /**
     * Announces that the indicated device has been disabled.
     *
     * @param device The device that has been enabled.
     */
    @Override
    public void disabled(AbstractDevice <? extends AbstractDeviceObserver> device)
    {

    }

    /**
     * An event announcing that the indicated barcode has been successfully scanned.
     *
     * @param barcodeScanner The device on which the event occurred.
     * @param barcode
     */
    @Override
    public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode)
    {
        barcodesScanned.add(barcode);
    }
}
