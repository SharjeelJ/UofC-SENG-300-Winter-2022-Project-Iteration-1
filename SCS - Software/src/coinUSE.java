package org.lsmr.selfcheckout.devices.observers;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinSlot;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.CoinTray;
import org.lsmr.selfcheckout.devices.CoinValidator;

public class coinUSE  implements CoinDispenserObserver,CoinSlotObserver,CoinStorageUnitObserver,CoinTrayObserver,CoinValidatorObserver{

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void validCoinDetected(CoinValidator validator, BigDecimal value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void invalidCoinDetected(CoinValidator validator) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinAdded(CoinTray tray) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinsFull(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinAdded(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinsLoaded(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinsUnloaded(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinInserted(CoinSlot slot) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinsFull(CoinDispenser dispenser) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinsEmpty(CoinDispenser dispenser) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinAdded(CoinDispenser dispenser, Coin coin) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinRemoved(CoinDispenser dispenser, Coin coin) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinsLoaded(CoinDispenser dispenser, Coin... coins) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinsUnloaded(CoinDispenser dispenser, Coin... coins) {
        // TODO Auto-generated method stub
        
    }

    
    

    
}
