/**
 * AUTHOR: SAMI TAHA
 * UCID: 30124214
 * PURPOSE: This code is designed to implement all abstract methods in Observer interfaces related to paying with Banknotes.
 * This implementation consists of an ArrayList that keeps track of all validated banknotes, status flags that are indicative of the state of
 * the banknote slot, the banknote storage unit, the banknote dispenser, and the banknote validity. The constructor for this class takes a total
 * cost as a formal parameter, in order to communicate with the checkout use case. The deduction of the total price takes place in the validBanknoteDetected()
 * method (i.e., only deduct the cost once a banknote is validated). A get-method is also provided for getting the sum of all values of the valid
 * inserted banknotes, this can prove useful in checkout as well.
 */

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

import java.util.ArrayList;
import java.util.Currency;

public class PayBanknote implements BanknoteValidatorObserver
{
	@Override
	public void enabled(AbstractDevice <? extends AbstractDeviceObserver> device)
	{

	}

	@Override
	public void disabled(AbstractDevice <? extends AbstractDeviceObserver> device)
	{

	}

	ArrayList <Banknote> validBanknotes = new ArrayList <Banknote>();

	private int banknoteSum;

	public PayBanknote()
	{

	}

	/**
	 * Sets the boolean representing the banknote's validity to true; indicative of the validator's state when a valid banknote is passed.
	 * Creates a banknote using the passed in currency and value and appends it to an ArrayList (a field of this class) containing
	 * all valid banknotes.
	 * Deducts the value of the most recently validated banknote from the totalCost. totalCost is the total price of checkout that is passed to
	 * the constructor of PayBanknote as a formal parameter.
	 */
	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value)
	{
		Banknote b = new Banknote(currency, value);
		validBanknotes.add(b);
	}

	/**
	 * Sets the boolean representing the banknote's validity to false; indicative of the validator's state when an invalid banknote is passed.
	 */
	@Override
	public void invalidBanknoteDetected(BanknoteValidator validator)
	{

	}

	/**
	 * Returns the sum of all the valid banknotes inserted.
	 * iterates through ArrayList of valid banknotes and sums up all the values of the banknotes.
	 *
	 * @return banknoteSum
	 */
	public int getTotalBanknotes()
	{
		for (int i = 0; i < validBanknotes.size(); i++)
		{
			banknoteSum += validBanknotes.get(i).getValue();
		}
		return banknoteSum;
	}

}
