import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.w3c.dom.css.ElementCSSInlineStyle;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;

public class BaggingArea implements ElectronicScaleObserver
{
    private ScanItem scanned = new ScanItem();
    private BarcodedItemCollection collection = new BarcodedItemCollection();

    private double initialWeightInGrams;
    private double finalWeightInGrams;
    private double prevWeight;
    private double itemWeight;

    private int numberOfItems;

    public int numberOfItemsInBaggingArea()
    {
        return numberOfItems;
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device)
    {

    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) 
    {

    }

    public void changeWeight(ElectronicScale scale, double weight)
    {
        prevWeight = initialWeightInGrams;
        initialWeightInGrams = finalWeightInGrams;
        finalWeightInGrams = weight;
    }
    
    public double getWeight()
    {
        return finalWeightInGrams;
    }
    
    public double getItemWeight()
    {
        itemWeight = finalWeightInGrams - prevWeight;
        return itemWeight;
        }
   
    
}
