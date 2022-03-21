import java.math.BigDecimal;
import java.text.NumberFormat;

import org.lsmr.selfcheckout.devices.ReceiptPrinter;

public class Checkout 

{
    private int paymentType = 0;
    private BigDecimal totalToBePaid;
    private BigDecimal paid;
    private boolean sucessfulTransaction = false;

    private PayBanknote payB = new PayBanknote();
    private PayCoin payC = new PayCoin();
    private ScanItem scanned = new ScanItem();
    private BarcodedItemCollection collection = new BarcodedItemCollection();
    private double expectedWeightInGrams = 0.0;
    private ReceiptPrinter printer = new ReceiptPrinter();

    public void setPaymentType(int a)
    {
        paymentType = a;
    }

    public void setTotalToBePaid(BigDecimal a)
    {
        totalToBePaid = a;
    }    

    public boolean getSuccessfulTransaction()
    {
        return sucessfulTransaction;
    }

    public BigDecimal calcPaidBC()
    {
        paid = BigDecimal.valueOf(payB.getTotalBanknotes()).add(payC.coinTotal);
        return paid;
    }

    public void calcTotalToBePaid()
    {
        int j = scanned.barcodesScanned.size();
        int i;
        for(i = 0; i < j; i++);
        {
            BigDecimal temp = collection.getPrice(scanned.barcodesScanned.get(i));
            totalToBePaid = totalToBePaid.add(temp);
        }
    }

    public void expectedWeight()
    {
        int j = scanned.barcodesScanned.size();
        int i;
        for(i = 0; i < j; i++);
        {
            expectedWeightInGrams = collection.getExpectedWeight(scanned.barcodesScanned.get(i));
        }
    }

    public void receipt(BigDecimal paid, BigDecimal totalToBePaid)
    {
        String tempPaid = NumberFormat.getCurrencyInstance().format(paid);
        String tempTotal = NumberFormat.getCurrencyInstance().format(totalToBePaid);
        String toPrint = "Total: " + tempTotal + "\n" + "Paid: " + tempPaid + "\n" + "Thank you for your purchase.";
        for(int i = 0; i < toPrint.length(); i++)
        {
            printer.print(toPrint.charAt(i));
        }

    }

    public void cancelTransaction()
    {
        
    }
    
    public void checkoutMain()
    {
        calcTotalToBePaid();
        switch(paymentType)
        {
            /*case 1:
            {
                //pay with card (for later)
                break;
            }*/
            case 2:
            {
                calcPaidBC();
                if(paid.compareTo(totalToBePaid) >= 0)
                {
                    sucessfulTransaction = true;
                    /*if(paid > totalToBePaid)
                    {
                        returnChange();
                    }*/
                }
                else
                {
                    cancelTransaction();
                }
                
                //pay with cash
                break;
            }

        }
        if(sucessfulTransaction)
        {
            //ReceiptPrinter
        }
    }
}
