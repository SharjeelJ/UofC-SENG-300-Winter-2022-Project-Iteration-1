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

    private int numberOfItems = 0;

    // return number of items in bagging area
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

    public void changeWeight(ElectronicScale scale, double weightInGrams)
    {
        // change final weight
        initialWeightInGrams = finalWeightInGrams;
        finalWeightInGrams = weightInGrams;

        // change number of items in bagging area
        if(finalWeightInGrams > initialWeightInGrams)
        {
            // if final weight is greater than initial than an item was added
            numberOfItems = numberOfItems + 1;
        }else if(finalWeightInGrams < initialWeightInGrams) {
            // if final weight is less than initial than an item was removed
            numberOfItems = numberOfItems - 1;
        }
    }

    // return the final weight in bagging area
    public double getWeightInGrams()
    {
        return finalWeightInGrams;
    }
}
