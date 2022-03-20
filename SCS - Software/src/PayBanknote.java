import java.util.ArrayList;
import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteSlotObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

public class PayBanknote implements BanknoteSlotObserver, BanknoteValidatorObserver, BanknoteStorageUnitObserver, BanknoteDispenserObserver{

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}
	ArrayList<Banknote> validBanknotes = new ArrayList<Banknote>();
	private boolean banknoteInserted;	
	private boolean banknoteEjected;
	private boolean banknoteRemoved;
	private boolean banknoteValidity;
	private boolean storageFull;
	private boolean banknoteAdded;
	private boolean banknotesLoadedStorage;
	private boolean banknotesUnloadedStorage;
	private int banknoteCount;
	private int storageCapacityMax;
	private boolean dispenserEmpty;
	private boolean dispenserFull;
	private boolean banknotesUnloadedDispenser;
	private boolean banknotesLoadedDispenser;
	private boolean billAdded;
	private boolean billRemoved;
	private double totalCost;
	/**
	 * PayBanknote is a class that takes a cost as a formal parameter and compares this total cost owed to the total cost paid. Only valid
	 * banknotes can contribute to the total cost paid, thus the deduction of totalCost happens inside validBanknoteDetected.
	 * @param cost
	 */
	public PayBanknote(double cost)
	{
		totalCost = cost;
	}
	/**
	 * Sets banknoteInserted boolean to true, which should be the expected reaction if this method is called.
	 * banknoteInserted and banknoteRemoved set to false as they cannot be true at the same time as banknoteRemoved,
	 * i.e., if a banknote is ejected it is not the case that it is inserted and/or removed.
	 */
	@Override
	public void banknoteInserted(BanknoteSlot slot) {
		banknoteInserted = true;
		banknoteEjected = false;
		banknoteRemoved = false;
		// do a plus equals for the global integer
	}
	/**
	 * Sets banknoteEjected boolean to true, which should be the expected reaction if this method is called.
	 * banknoteInserted and banknoteRemoved set to false as they cannot be true at the same time as banknoteRemoved,
	 * i.e., if a banknote is ejected it is not the case that it is inserted and/or removed.
	 */
	@Override
	public void banknoteEjected(BanknoteSlot slot) {
		banknoteInserted = false;
		banknoteEjected = true;
		banknoteRemoved = false;	}
	/**
	 * Sets banknoteRemoved boolean to true, which should be the expected reaction if this method is called
	 * banknoteInserted and banknoteEjected set to false as they cannot be true at the same time as banknoteRemoved,
	 * i.e., if a banknote is removed it is not the case that it is inserted and/or ejected.
	 */
	@Override
	public void banknoteRemoved(BanknoteSlot slot) {
		banknoteInserted = false;
		banknoteEjected = false;
		banknoteRemoved = true;	}
	/**
	 * Sets the boolean representing the banknote's validity to true; indicative of the validator's state when a valid banknote is passed.
	 * Creates a banknote using the passed in currency and value and appends it to an ArrayList (a field of this class) containing
	 * all valid banknotes.
	 * Deducts the value of the most recently validated banknote from the totalCost. totalCost is the total price of checkout that is passed to
	 * the constructor of PayBanknote as a formal parameter.
	 */
	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		banknoteValidity = true;
		Banknote b = new Banknote(currency, value);
		validBanknotes.add(b);
		totalCost -= validBanknotes.get(validBanknotes.size()-1).getValue();
		}
	/**
	 * Sets the boolean representing the banknote's validity to false; indicative of the validator's state when an invalid banknote is passed.
	 *
	 */
	@Override
	public void invalidBanknoteDetected(BanknoteValidator validator) {
		banknoteValidity = false;
	}
	/**
	 * Announces that the indicated banknote storage unit is full of banknotes. How?
	 * Sets the flag storageFull to true which is indicative of the current unit's state.
	 * Also provides print statement containing the capacity of the storage unit passed in as a formal parameter.
	 */
	@Override
	public void banknotesFull(BanknoteStorageUnit unit) {
		storageFull = true;
		storageCapacityMax = unit.getBanknoteCount();
		System.out.println("The banknote storage unit is currently full and holds " + storageCapacityMax + " banknotes");
	}
	/**
	 * Announces that a banknote has been added to the indicated storage unit. How?
	 * Sets the banknoteAdded flag to true.
	 * Provides print statement notifying that a banknote has been added as well as the current number of banknotes/maximum capacity of the unit
	 * passed in as a formal parameter.
	 */
	@Override
	public void banknoteAdded(BanknoteStorageUnit unit) {
		banknoteCount = unit.getBanknoteCount();
		banknoteAdded = true;
		System.out.println("A banknote has been added and the storage currently has " + banknoteCount +
		" banknotes. The storage unit can carry up to" + unit.getCapacity() + " banknotes.");
	}
	/**
	 * Announces that the indicated storage unit has been loaded with banknotes. Used to simulate direct, physical loading of the unit.
	 * Sets the status flag banknotesUnloadedStorage to false and the status flag banknotesLoadedStorage to true. This is indicative of the
	 * storage unit's state when this method is called.
	 */
	@Override
	public void banknotesLoaded(BanknoteStorageUnit unit) {
		banknotesLoadedStorage = true;
		banknotesUnloadedStorage = false;
	}
	/**
	 * Announces that the storage unit has been emptied of banknotes. Used to simulate direct, physical unloading of the unit.
	 * Sets the status flag banknotesUnloadedStorage to true and the status flag banknotesLoadedStorage to false. This is indicative of the
	 * storage unit's state when this method is called.
	 */
	@Override
	public void banknotesUnloaded(BanknoteStorageUnit unit) {
		banknotesUnloadedStorage = true;
		banknotesLoadedStorage = false;
	}
	/**
	 * Announces that the indicated banknote dispenser is full of banknotes.
	 * Does this by setting the dispenserFull flag to true and the dispenserEmpty flag to false.
	 * Provides print statement stating that the dispenser is full as well as the capacity of the dispenser.
	 */
	@Override
	public void moneyFull(BanknoteDispenser dispenser) {
		dispenserFull = true;
		dispenserEmpty = false;
		System.out.println("Dispenser is currently full. Capacity: " + dispenser.getCapacity());
	}
	/**
	 * Announces that the indicated banknote dispenser is empty of banknotes.
	 * Does this by setting the dispenserFull flag to false and the dispenserEmpty flag to true.
	 * Provides print statement stating that the dispenser is empty as well as the capacity of the dispenser.
	 */
	@Override
	public void banknotesEmpty(BanknoteDispenser dispenser) {
		dispenserFull = false;
		dispenserEmpty = true;
		System.out.println("Dispenser is currently empty. Capacity: " + dispenser.getCapacity());
	}
	/**
	 * Announces that the indicated banknote has been added to the indicated banknote dispenser by setting the billAdded flag to true and 
	 * the billRemoved flag to false (these cannot be true at the same time).
	 * Method also provides print statement to announce the addition of the banknote as well as the banknote's currency and value.
	 */
	@Override
	public void billAdded(BanknoteDispenser dispenser, Banknote banknote) {
		billAdded = true;
		billRemoved = false;
		System.out.println("Bill has been added to the dispenser.\n Currency: " + banknote.getCurrency() + "\nValue: " + banknote.getValue());
	}
	/**
	 * Announces that the indicated banknote has been removed  from the indicated banknote dispenser by setting the billAdded flag to false and 
	 * the billRemoved flag to true (these cannot be true at the same time).
	 * Method also provides print statement to announce the removal of the banknote as well as the banknote's currency and value.
	 */
	@Override
	public void banknoteRemoved(BanknoteDispenser dispenser, Banknote banknote) {
		billAdded = false;
		billRemoved = true;
		System.out.println("Bill has been removed from the dispenser.\n Currency: " + banknote.getCurrency() + "\nValue: " + banknote.getValue());
	}
	/**
	 * Announces that the indicated sequence of banknotes has been added to the indicated banknote dispenser. 
	 * Used to simulate direct, physical loading of the dispenser.
	 * Sets the flags banknotesLoadedDispenser to true and banknotesUnloadedDispenser to false. The state of these flags indicate the state of
	 * the dispenser and cannot be true at the same time (i.e., a single dispenser cannot be loading and unloading at the same time)
	 * Also prints out the sequence of the loaded banknotes as well as their corresponding Value and Currency.
	 */
	@Override
	public void banknotesLoaded(BanknoteDispenser dispenser, Banknote... banknotes) {
		banknotesLoadedDispenser = true;
		banknotesUnloadedDispenser = false;
		System.out.println("Banknotes Loaded:");
		for (int i = 0; i < banknotes.length; i++) {
			int value = banknotes[i].getValue();
			Currency c = banknotes[i].getCurrency();
			System.out.println("Banknote " + (i+1) + ":\tValue: " + value + "\tCurrency: " + c);
		}
	}
	/**
	 *Announces that the indicated sequence of banknotes has been removed to the indicated banknote dispenser. 
	 * Used to simulate direct, physical unloading of the dispenser. 
	 * Sets the flags banknotesLoadedDispenser to false and banknotesUnloadedDispenser to true. The state of these flags indicate the state of
	 * the dispenser and cannot be true at the same time (i.e., a single dispenser cannot be loading and unloading at the same time)
	 * Also prints out the sequence of the unloaded banknotes as well as their corresponding Value and Currency.
	 */
	@Override
	public void banknotesUnloaded(BanknoteDispenser dispenser, Banknote... banknotes) {
		banknotesLoadedDispenser = true;
		banknotesUnloadedDispenser = false;		
		System.out.println("Banknotes Unloaded:");
		for (int i = 0; i < banknotes.length; i++) {
			int value = banknotes[i].getValue();
			Currency c = banknotes[i].getCurrency();
			System.out.println("Banknote " + (i+1) + ":\tValue: " + value + "\tCurrency: " + c);
		}
	}

}
