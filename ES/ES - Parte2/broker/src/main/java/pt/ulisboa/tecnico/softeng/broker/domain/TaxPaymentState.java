package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class TaxPaymentState extends AdventureState{
	
	@Override
	public State getState() {
		return State.TAX_PAYMENT;
	}

	@Override
	public void process(Adventure adventure) {
		try {
			InvoiceData invoiceData = new InvoiceData(adventure.getBroker().getSellerNIF(), adventure.getClient().getNIF(), adventure.getType(),
					adventure.getAmountWithMargin(), adventure.getBegin());			
			adventure.setTaxConfirmation(TaxInterface.submitInvoice(invoiceData));
		} catch (TaxException be) {
			adventure.setState(State.UNDO);
			return;
		} catch (RemoteAccessException rae) {
			adventure.setState(State.UNDO);
			return;
		}

		adventure.setState(State.CONFIRMED);
	}

}
