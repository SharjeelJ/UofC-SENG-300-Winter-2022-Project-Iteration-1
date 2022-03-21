import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;

public class BaggingArea implements ElectronicScaleObserver
{
    private ScanItem scanned = new ScanItem();
    private BarcodedItemCollection collection = new BarcodedItemCollection();

    private double initialWeightInGrams;
    private double finalWeightInGrams;

    private int numberOfItems;

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device)
    {

    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) 
    {

    }
}
