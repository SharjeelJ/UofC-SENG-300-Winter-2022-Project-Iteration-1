import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;

import java.math.BigDecimal;

public class PayCoin implements CoinValidatorObserver
{
    public BigDecimal coinTotal = BigDecimal.valueOf(0);

    @Override
    public void enabled(AbstractDevice <? extends AbstractDeviceObserver> device)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void disabled(AbstractDevice <? extends AbstractDeviceObserver> device)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void validCoinDetected(CoinValidator validator, BigDecimal value)
    {
        // TODO Auto-generated method stub
        coinTotal = coinTotal.add(value);
    }

    @Override
    public void invalidCoinDetected(CoinValidator validator)
    {
        // TODO Auto-generated method stub
    }
}
