package org.lsmr.selfcheckout.devices;

import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.AbstractDevice.Phase;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

/**
 * Represents a device for optically and/or magnetically validating banknotes.
 * Banknotes deemed valid are moved to storage; banknotes deemed invalid are
 * ejected.
 */
public final class BanknoteValidator extends AbstractDevice<BanknoteValidatorObserver>
	implements Acceptor<Banknote>, Emitter<Banknote> {
	private final Currency currency;
	private final int[] denominations;
	private BidirectionalChannel<Banknote> source;
	private UnidirectionalChannel<Banknote> sink;

	/**
	 * Creates a banknote validator that recognizes banknotes of the specified
	 * denominations (i.e., values) and currency.
	 * 
	 * @param currency
	 *            The kind of currency to accept.
	 * @param denominations
	 *            An array of the valid banknote denominations (like $5, $10, etc.)
	 *            to accept. Each value must be &gt;0 and unique in this array.
	 * @throws SimulationException
	 *             If either argument is null.
	 * @throws SimulationException
	 *             If the denominations array does not contain at least one value.
	 * @throws SimulationException
	 *             If any value in the denominations array is non-positive.
	 * @throws SimulationException
	 *             If any value in the denominations array is non-unique.
	 */
	public BanknoteValidator(Currency currency, int[] denominations) {
		if(currency == null)
			throw new SimulationException(
				new NullPointerException("currency is null, which has no analogue in the real world."));

		if(denominations == null)
			throw new SimulationException(
				new NullPointerException("denominations is null, which has no analogue in the real world."));

		if(denominations.length < 1)
			throw new SimulationException(new IllegalArgumentException("There must be at least one denomination."));

		this.currency = currency;
		Arrays.sort(denominations);

		HashSet<Integer> set = new HashSet<>();

		for(int denomination : denominations) {
			if(denomination <= 0)
				throw new SimulationException(
					new IllegalArgumentException("Non-positive denomination detected: " + denomination + "."));

			if(set.contains(denomination))
				throw new SimulationException(new IllegalArgumentException(
					"Each denomination must be unique, but " + denomination + " is repeated."));

			set.add(denomination);
		}

		this.denominations = denominations;
	}

	/**
	 * Connects input and output channels to the banknote validator. Causes no events.
	 * <p>
	 * This operation is permissible only during the configuration phase.
	 * 
	 * @param source
	 *            The channel from which banknotes normally arrive for validation,
	 *            and to which invalid banknotes will be ejected.
	 * @param sink
	 *            The channel to which all valid banknotes are routed.
	 */
	public void connect(BidirectionalChannel<Banknote> source, UnidirectionalChannel<Banknote> sink) {
		if(phase != Phase.CONFIGURATION)
			throw new SimulationException(
				new IllegalStateException("This method may only be called during the configuration phase."));

		this.source = source;
		this.sink = sink;
	}

	private final Random pseudoRandomNumberGenerator = new Random();
	private static final int PROBABILITY_OF_FALSE_REJECTION = 1; /* out of 100 */

	private boolean isValid(Banknote banknote) {
		if(currency.equals(banknote.getCurrency()))
			for(int denomination : denominations)
				if(denomination == banknote.getValue())
					return pseudoRandomNumberGenerator.nextInt(100) >= PROBABILITY_OF_FALSE_REJECTION;

		return false;
	}

	/**
	 * Tells the banknote validator that the indicated banknote is being inserted.
	 * If the banknote is valid, a "validBanknoteDetected" event is announced to its
	 * observers; otherwise, an "invalidBanknoteDetected" event is announced to its
	 * observers.
	 * <p>
	 * If there is space in the machine to store a valid banknote, it is passed to
	 * the sink channel.
	 * </p>
	 * <p>
	 * If there is no space in the machine to store it or the banknote is invalid,
	 * the banknote is ejected to the source.
	 * </p>
	 * <p>
	 * This operation is not permissible during the configuration phase.
	 * 
	 * @param banknote
	 *            The banknote to be added. Cannot be null.
	 * @throws DisabledException
	 *             if the banknote validator is currently disabled.
	 * @throws SimulationException
	 *             If the banknote is null.
	 */
	@Override
	public void accept(Banknote banknote) throws DisabledException {
		if(phase == Phase.ERROR)
			throw new SimulationException(new IllegalStateException(
				"This method may not be used when the device is in an erroneous operation phase."));
		if(phase == Phase.CONFIGURATION)
			throw new SimulationException(
				new IllegalStateException("This method may not be called during the configuration phase."));

		if(isDisabled())
			throw new DisabledException();

		if(banknote == null)
			throw new SimulationException(new NullPointerException("banknote is null"));

		if(isValid(banknote)) {
			notifyValidBanknoteDetected(banknote);

			if(sink.hasSpace()) {
				try {
					sink.deliver(banknote);
				}
				catch(OverloadException e) {
					// Should never happen
					phase = Phase.ERROR;
					throw new SimulationException(e);
				}
			}
			else {
				try {
					source.eject(banknote);
				}
				catch(OverloadException e) {
					// Should never happen
					phase = Phase.ERROR;
					throw new SimulationException(e);
				}
			}
		}
		else {
			notifyInvalidBanknoteDetected();

			try {
				source.eject(banknote);
			}
			catch(OverloadException e) {
				// Should never happen
				phase = Phase.ERROR;
				throw new SimulationException("Unable to route banknote: sink is full");
			}
		}
	}

	@Override
	public boolean hasSpace() {
		if(phase == Phase.ERROR)
			throw new SimulationException(new IllegalStateException(
				"This method may not be used when the device is in an erroneous operation phase."));

		return true;
	}

	private void notifyValidBanknoteDetected(Banknote banknote) {
		for(BanknoteValidatorObserver observer : observers)
			observer.validBanknoteDetected(this, banknote.getCurrency(), banknote.getValue());
	}

	private void notifyInvalidBanknoteDetected() {
		for(BanknoteValidatorObserver observer : observers)
			observer.invalidBanknoteDetected(this);
	}
}
