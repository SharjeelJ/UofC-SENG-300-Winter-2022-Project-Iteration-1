import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

public class BaggingArea implements ElectronicScaleObserver
{
    private double initialWeightInGrams;
    private double finalWeightInGrams;
    private double prevWeight;
    private double itemWeight;
    private double scaleSensitivity = 0;

    private int numberOfItems = 0;

    @Override
    public void enabled(AbstractDevice <? extends AbstractDeviceObserver> device)
    {

    }

    @Override
    public void disabled(AbstractDevice <? extends AbstractDeviceObserver> device)
    {

    }

    @Override
    public void weightChanged(ElectronicScale scale, double weightInGrams)
    {
        // TODO Auto-generated method stub
        // change final weight
        prevWeight = initialWeightInGrams;
        initialWeightInGrams = finalWeightInGrams;
        finalWeightInGrams = weightInGrams;

        // change number of items in bagging area
        if (finalWeightInGrams > initialWeightInGrams)
        {
            // if final weight is greater than initial than an item was added
            numberOfItems = numberOfItems + 1;
        } else if (finalWeightInGrams < initialWeightInGrams)
        {
            // if final weight is less than initial than an item was removed
            numberOfItems = numberOfItems - 1;
        }
    }

    @Override
    public void overload(ElectronicScale scale)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void outOfOverload(ElectronicScale scale)
    {
        // TODO Auto-generated method stub

    }

    // return the final weight in bagging area
    public double getWeightInGrams()
    {
        return finalWeightInGrams;
    }


    //returns scale sensitivity factor
    public double getScaleSensitivity()
    {
        return scaleSensitivity;
    }
}
