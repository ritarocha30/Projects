package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.services.remote.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.RemoteAccessException;

public class ProcessPaymentState extends ProcessPaymentState_Base {
	public static final int MAX_REMOTE_ERRORS = 3;

	@Override
	public State getValue() {
		return State.PROCESS_PAYMENT;
	}

	@Override
	public void process() {
		try {
			getAdventure().setPaymentConfirmation(
					BankInterface.processPayment(getAdventure().getIban(), getAdventure().getAmount()));
		} catch (BankException be) {
			getAdventure().setState(State.UNDO);
			return;
		} catch (RemoteAccessException rae) {
			incNumOfRemoteErrors();
			if (getNumOfRemoteErrors() == MAX_REMOTE_ERRORS) {
				getAdventure().setState(State.UNDO);
			}
			return;
		}

		getAdventure().setState(State.TAX_PAYMENT);
	}

}
