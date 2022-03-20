import java.math.BigDecimal;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

public class Checkout implements ElectronicScaleObserver

{
    private int paymentType = 0;
    private double totalToBePaid;
    private double paid = 0;
    private boolean sucessfulTransaction = false;

    private PayBanknote payB = new PayBanknote();
    private coinUSE payC = new coinUSE();
    private ScanItem scanned = new ScanItem();

    public void setPaymentType(int a)
    {
        paymentType = a;
    }

    public void setTotalToBePaid(int a)
    {
        totalToBePaid = a;
    }    

    public boolean getSuccessfulTransaction()
    {
        return sucessfulTransaction;
    }

    public double calcPaidCB()
    {
        paid = payB.getTotalBanknotes() + (payC.coinTotal).doubleValue();
        return paid;
    }

    public void calcTotalToBePaid()
    {
        int j = scanned.barcodesScanned.size();
        int k = 0;
        for(int i = 0; i < j; i++);
        {
            scanned.barcodesScanned.get(k);
            k++;
        }
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
                calcPaidCB();
                if(paid >= totalToBePaid)
                {
                    sucessfulTransaction = true;
                    /*if(paid > totalToBePaid)
                    {
                        returnChange();
                    }*/
                }
                else
                {

                }
                
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
