import java.math.BigDecimal;
import java.util.ArrayList;

import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinSlot;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.CoinTray;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.observers.*;

public class coinUSE  implements CoinDispenserObserver,CoinSlotObserver,CoinStorageUnitObserver,CoinTrayObserver,CoinValidatorObserver{

    private ArrayList<CoinSlot> slots = new ArrayList<CoinSlot>();
    private boolean coinInserted;
	private boolean coinEjected;
	private boolean coinRemoved;
    private boolean validCoin;
    private boolean coinTrayNotFull;
    private boolean coinsLoaded;
    private boolean coinsUnloaded;
    private boolean coinsFull;
    private boolean coinsEmpty;
    private boolean coinsAdded;
    private boolean coinsRemoved;
    private boolean CoinDispenserLoaded;
    private boolean CoinDispenserUnloaded;

    
    @Override//not used but necessary to implement any observer
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // TODO Auto-generated method stub

        
    }

    @Override//not used but necessary to implement any observer
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void validCoinDetected(CoinValidator validator, BigDecimal value) {
        // TODO Auto-generated method stub
        validCoin = true;



        
    }

    @Override
    public void invalidCoinDetected(CoinValidator validator) {
        // TODO Auto-generated method stub
        validCoin = false;
        
    }

    @Override
    public void coinAdded(CoinTray tray) {
        // TODO Auto-generated method stub
        coinTrayNotFull = true;
        
    }

    @Override
    public void coinsFull(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        coinTrayNotFull = false;
        
    }

    @Override //not necessary for this use case 
    public void coinAdded(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinsLoaded(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        coinsLoaded = true;
        coinsUnloaded = false;

        
    }

    @Override
    public void coinsUnloaded(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        coinsUnloaded = true;
        coinsLoaded = false;
        
    }

    @Override
    public void coinInserted(CoinSlot slot) {
        // TODO Auto-generated method stub
        coinInserted = true;
        coinEjected = false;
        coinRemoved = false;
        slots.add(slot);
        
    }

    @Override
    public void coinsFull(CoinDispenser dispenser) {
        // TODO Auto-generated method stub
        coinsFull = true;
        
    }

    @Override
    public void coinsEmpty(CoinDispenser dispenser) {
        // TODO Auto-generated method stub
        coinsEmpty = false;
        
    }

    @Override
    public void coinAdded(CoinDispenser dispenser, Coin coin) {
        // TODO Auto-generated method stub
        coinsAdded = true;
        coinRemoved = false;
        
    }

    @Override
    public void coinRemoved(CoinDispenser dispenser, Coin coin) {
        // TODO Auto-generated method stub
        coinRemoved = true;
        coinsAdded =false;
    }

    @Override
    public void coinsLoaded(CoinDispenser dispenser, Coin... coins) {
        // TODO Auto-generated method stub
        CoinDispenserLoaded = true;
        
    }

    @Override
    public void coinsUnloaded(CoinDispenser dispenser, Coin... coins) {
        // TODO Auto-generated method stub
        CoinDispenserUnloaded = true;
        
    }

    
    

    
}
