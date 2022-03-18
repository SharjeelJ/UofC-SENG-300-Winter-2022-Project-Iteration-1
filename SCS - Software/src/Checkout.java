import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.Currency;

public class Checkout 
{
    int paymentType = 0;
    int weightLimitInGrams;
    int sensitivity;
    int totalToBePaid;
    int paid = 0;
    Currency currency;
    int[] banknoteDenominations;
    BigDecimal[] coinDenomination;
    SelfCheckoutStation test = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, weightLimitInGrams, sensitivity);

    public void setCurrency(Currency a)
    {
        currency = a;
    }

    public void setBD(int[] a)
    {
        banknoteDenominations = a;
    }

    public void setCD(BigDecimal[] a)
    {
        coinDenomination = a;
    }

    public void setWLIG(int a)
    {
        weightLimitInGrams = a;
    }

    public void setSensitivity(int a)
    {
        sensitivity = a;
    }
       
    public void setPayType(int a)
    {   
        paymentType = a;
    }

    public void setTTBP(int a)
    {
        totalToBePaid = a;
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
            case 3:
            {
                //pay with coins
                break;
            }
        }
    }

}
