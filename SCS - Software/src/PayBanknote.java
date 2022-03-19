import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
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

	private boolean banknoteInserted;
	private boolean banknoteEjected;
	private boolean banknoteRemoved;
	
	@Override
	public void banknoteInserted(BanknoteSlot slot) {
		banknoteInserted = true;
		banknoteEjected = false;
		banknoteRemoved = false;
	}

	@Override
	public void banknoteEjected(BanknoteSlot slot) {
		banknote
	}

	@Override
	public void banknoteRemoved(BanknoteSlot slot) {
		
	}

	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invalidBanknoteDetected(BanknoteValidator validator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknotesFull(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknoteAdded(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknotesLoaded(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknotesUnloaded(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moneyFull(BanknoteDispenser dispenser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknotesEmpty(BanknoteDispenser dispenser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void billAdded(BanknoteDispenser dispenser, Banknote banknote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknoteRemoved(BanknoteDispenser dispenser, Banknote banknote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknotesLoaded(BanknoteDispenser dispenser, Banknote... banknotes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknotesUnloaded(BanknoteDispenser dispenser, Banknote... banknotes) {
		// TODO Auto-generated method stub
		
	}

}
