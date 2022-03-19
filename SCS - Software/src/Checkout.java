public class Checkout 
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

    public void calcPaid()
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
                //pay with card
                break;
            }
            case 2:
            {
                //pay with cash
                break;
            }

        }
    }
}
