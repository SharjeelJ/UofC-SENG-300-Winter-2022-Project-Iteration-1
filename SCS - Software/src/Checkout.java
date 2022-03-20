import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

public class Checkout implements ElectronicScaleObserver

{
    int paymentType = 0;
    double totalToBePaid;
    double paid = 0;
    boolean sucessfulTransaction = false;

    PayBanknote payB = new PayBanknote();
    coinUSE payC = new coinUSE();

    public void setPaymentType(int a)
    {
        paymentType = a;
    }

    public void setTotalToBePaid(int a)
    {
        totalToBePaid = a;
    }    

    public double calcPaidCB()
    {
        paid += payB.getTotalBanknotes();
        //paid += payC.
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
                if(calcPaidCB() >= totalToBePaid)
                
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
