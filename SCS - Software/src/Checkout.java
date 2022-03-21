import java.math.BigDecimal;
import java.text.NumberFormat;
import java.lang.Math;

import org.lsmr.selfcheckout.devices.ReceiptPrinter;

public class Checkout 

{
    private int paymentType = 2;
    private BigDecimal totalToBePaid = BigDecimal.valueOf(0);
    private BigDecimal paid = BigDecimal.valueOf(0);
    private int sucessfulTransaction = 1;

    private  PayBanknote payB = new PayBanknote();
    private PayCoin payC = new PayCoin();
    private ScanItem scanned = new ScanItem();
    private BarcodedItemCollection collection = new BarcodedItemCollection();
    private double expectedWeightInGrams = 0.0;
    private ReceiptPrinter printer = new ReceiptPrinter();
    private BaggingArea bagging = new BaggingArea();

    /** 
	 * Constructor for checkout class
	 * 
	 * @param a 
	 *              Object dealing with banknote payments
	 * @param b
	 *              Object dealing with coin payments
	 * @param c
	 *              Object dealing with scannning items
     * @param d
     *              Collection of stores inventory
     * @param e 
     *              Object to make sure the correct items are placed in the bagging area
	 */
    public Checkout(PayBanknote a, PayCoin b, ScanItem c, BarcodedItemCollection d, BaggingArea e)
    {
        payB = a;
        payC = b;
        scanned = c;
        collection = d;
        bagging = e;
    }

    /** 
	 * Sets the payment type the customer choses
	 * 
	 * @param a 
	 *             Payment type where 1 is cash/coins and 2 is card(for later use)
     * 
	 */
    public void setPaymentType(int a)
    {
        paymentType = a;
    }

    /** 
	 * Sets the varible for the total to be paid by the customer
	 * 
	 * @param a
	 *             If the device is disabled.
     * 
	 */
    public void setTotalToBePaid(BigDecimal a)
    {
        totalToBePaid = a;
    }    

    /** 
	 * method to get the current value of sucessfullTransaction
	 * 
	 * @return what is the current value of sucessfulTransaction were 0 is true, 1 is false, and 2 is error
     * 
	 */
    public int getSuccessfulTransaction()
    {
        return sucessfulTransaction;
    }

    /** 
	 * Calculate the amount paid in banknotes and coins and converted to a BigDecimal
	 * 
     * @return the amount paid in banknotes and coins
     * 
	 */
    public BigDecimal calcPaidBC()
    {
        paid = BigDecimal.valueOf(payB.getTotalBanknotes()).add(payC.coinTotal);
        return paid;
    }

    /** 
	 * Going through the items scanned and calculating the price of each and adding to the total owed and storing the amount
	 * 
	 */
    public void calcTotalToBePaid()
    {        
        int i;
        int j = scanned.barcodesScanned.size();
        for(i = 0; i < j; i++)
        {
            BigDecimal temp = collection.getPrice(scanned.barcodesScanned.get(i));
            totalToBePaid = totalToBePaid.add(temp);
        }
    }

    /** 
	 * Calculating the expected weight based on the weight according to records associated with the barcode
     * 
	 */
    public void expectedWeight()
    {
        int j = scanned.barcodesScanned.size();
        int i;
        for(i = 0; i < j; i++)
        {
            expectedWeightInGrams += collection.getExpectedWeight(scanned.barcodesScanned.get(i));
        }
    }

    /** 
	 * Prints the amount owed and paid then cuts the receipt and removes it
	 * 
	 * @param paid 
	 *              The amount paid
	 * @param totalToBePaid
     *              The amount that was needing to be paid
     * 
	 */
    public void receipt(BigDecimal paid, BigDecimal totalToBePaid)
    {
        String tempPaid = NumberFormat.getCurrencyInstance().format(paid);
        String tempTotal = NumberFormat.getCurrencyInstance().format(totalToBePaid);
        String toPrint = "Total: " + tempTotal + "\n" + "Paid: " + tempPaid + "\n" + "Thank you for your purchase.";
        for(int i = 0; i < toPrint.length(); i++)
        {
            printer.print(toPrint.charAt(i));
        }
        printer.cutPaper();
        printer.removeReceipt();
    }

    /** 
	 * Sets sucessfullTransaction = 2 (error)
     * 
	 */
    public void cancelTransaction()
    {
        sucessfulTransaction = 2;
    }
    
    /** 
	 * Main driving code of the controll software
	 * 
	 * @return an int indicating how the transaction ended
	 *        
	 */
    public int checkoutMain()
    {
        calcTotalToBePaid();
        expectedWeight();
        if(Math.abs(bagging.getWeightInGrams()-expectedWeightInGrams) > bagging.getScaleSensitivity())
        {
            cancelTransaction();
            return sucessfulTransaction;
        }
        switch(paymentType)
        {
            /*case 1:
            {
                //pay with card (for later)
                break;
            }*/
            case 2:
            {
                //pay with cash
                calcPaidBC();
                if(paid.compareTo(totalToBePaid) >= 0)
                {
                    sucessfulTransaction = 0;
                    /*if(paid > totalToBePaid)
                    {
                        returnChange();
                    }*/
                }
                else
                {
                    cancelTransaction();
                    return sucessfulTransaction;
                }
                break;
            }

        }
        if(sucessfulTransaction == 0)
        {
            receipt(paid, totalToBePaid);
        }
        return sucessfulTransaction;
    }
}
