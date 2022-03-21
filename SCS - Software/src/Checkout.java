import org.lsmr.selfcheckout.devices.ReceiptPrinter;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Checkout

{
    private BigDecimal totalToBePaid = BigDecimal.valueOf(0);
    private BigDecimal paid = BigDecimal.valueOf(0);
    private int successfulTransaction = 1;
    private PayBanknote payB;
    private PayCoin payC;
    private ScanItem scanned;
    private BarcodedItemCollection collection;
    private double expectedWeightInGrams = 0.0;
    private ReceiptPrinter printer;
    private BaggingArea bagging;


    /**
     * Constructor for checkout class
     *
     * @param a Object dealing with banknote payments
     * @param b Object dealing with coin payments
     * @param c Object dealing with scanning items
     * @param d Collection of stores inventory
     * @param e Object to make sure the correct items are placed in the bagging area
     * @param f Receipt printer
     * @param g Amount of ink for the printer
     * @param h Amount of paper for the printer
     */
    public Checkout(PayBanknote a, PayCoin b, ScanItem c, BarcodedItemCollection d, BaggingArea e, ReceiptPrinter f, int g, int h)
    {
        payB = a;
        payC = b;
        scanned = c;
        collection = d;
        bagging = e;
        printer = f;
        printer.addInk(g);
        printer.addPaper(h);
        printer.endConfigurationPhase();
    }

    /**
     * Calculate the amount paid in banknotes and coins and converted to a BigDecimal
     *
     * @return the amount paid in banknotes and coins
     */
    public BigDecimal calcPaidBC()
    {
        paid = BigDecimal.valueOf(payB.getTotalBanknotes()).add(payC.coinTotal);
        return paid;
    }

    /**
     * Going through the items scanned and calculating the price of each and adding to the total owed and storing the amount
     */
    public void calcTotalToBePaid()
    {
        int i;
        int j = scanned.barcodesScanned.size();
        for (i = 0; i < j; i++)
        {
            BigDecimal temp = collection.getPrice(scanned.barcodesScanned.get(i));
            totalToBePaid = totalToBePaid.add(temp);
        }
    }

    /**
     * Calculating the expected weight based on the weight according to records associated with the barcode
     */
    public void expectedWeight()
    {
        int j = scanned.barcodesScanned.size();
        int i;
        for (i = 0; i < j; i++)
        {
            expectedWeightInGrams += collection.getExpectedWeight(scanned.barcodesScanned.get(i));
        }
    }

    /**
     * Prints the amount owed and paid then cuts the receipt and removes it
     *
     * @param paid          The amount paid
     * @param totalToBePaid The amount that was needing to be paid
     */
    public void receipt(BigDecimal paid, BigDecimal totalToBePaid)
    {
        String tempPaid = NumberFormat.getCurrencyInstance().format(paid);
        String tempTotal = NumberFormat.getCurrencyInstance().format(totalToBePaid);
        String toPrint = "Total: " + tempTotal + "\n" + "Paid: " + tempPaid + "\n" + "Thank you for your purchase.";
        for (int i = 0; i < toPrint.length(); i++)
        {
            printer.print(toPrint.charAt(i));
        }
        printer.cutPaper();
        printer.removeReceipt();
    }

    /**
     * Sets successfulTransaction = 2 (error)
     */
    public void cancelTransaction()
    {
        successfulTransaction = 2;
    }

    /**
     * Main driving code of the control software
     *
     * @return an int indicating how the transaction ended
     */
    public int checkoutMain()
    {
        calcTotalToBePaid();
        expectedWeight();
        if (Math.abs(bagging.getWeightInGrams() - expectedWeightInGrams) > bagging.getScaleSensitivity())
        {
            cancelTransaction();
            return successfulTransaction;
        }

        calcPaidBC();
        if (paid.compareTo(totalToBePaid) >= 0)
        {
            successfulTransaction = 0;
        } else
        {
            cancelTransaction();
            return successfulTransaction;
        }

        if (successfulTransaction == 0)
        {
            receipt(paid, totalToBePaid);
        }
        return successfulTransaction;
    }
}
