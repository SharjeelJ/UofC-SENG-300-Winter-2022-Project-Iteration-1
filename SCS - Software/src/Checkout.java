import org.lsmr.selfcheckout.devices.ElectronicScale;
import java.util.Scanner;

import javax.smartcardio.Card;

public class Checkout 
{
    int paymentType = 0;
    int weightLimitInGrams;
    int sensitivity;
    int totalToBePaid;
    int paid = 0;
    ElectronicScale a = new ElectronicScale(weightLimitInGrams, sensitivity);

    public void disableScanner()
    {
        a.disable();
    }

    public void setWLIG(int a)
    {
        weightLimitInGrams = a;
    }

    public void setSensitivity(int a)
    {
        sensitivity = a;
    }
       
    public void getPayType()
    {   Scanner in = new Scanner(System.in);

        //broke up cash and coins for testsing each
        System.out.println("select payment type, 1 = card, 2 = cash, 3 = coins");

        paymentType = in.nextInt();
        in.close();
    }

    public void setTTBP(int a)
    {
        totalToBePaid = a;
    }

    public void checkoutMain()
    {
        disableScanner();
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
