import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

public class Checkout implements ElectronicScaleObserver
{
    int paymentType = 0;
    int totalToBePaid;
    int paid = 0;
    boolean sucessfulTransaction = false;

 

    public void setPaymentType(int a)
    {
        paymentType = a;
    }

    public void setTotalToBePaid(int a)
    {
        totalToBePaid = a;
    }    

    public void calcPaidCB()
    {

    }

    public void calcTotalToBePaid()
    {

    }

    public void cancelTransaction()
    {
        
    }
    
    public void checkoutMain()
    {
        switch(paymentType)
        {
            case 1:
            {
                //pay with card (for later)
                break;
            }
            case 2:
            {
                PayBanknote.PayBankote();
                //pay with cash
                break;
            }

        }
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void weightChanged(ElectronicScale scale, double weightInGrams) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void overload(ElectronicScale scale) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void outOfOverload(ElectronicScale scale) {
        // TODO Auto-generated method stub
        
    }
}
