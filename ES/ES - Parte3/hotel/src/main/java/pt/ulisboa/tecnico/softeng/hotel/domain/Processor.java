package pt.ulisboa.tecnico.softeng.hotel.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.hotel.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Processor extends Processor_Base{

	public void submitBooking(Booking booking) {
		addBooking(booking);
		processInvoices();
	}

	private void processInvoices() {
		final Set<Booking> failedToProcess = new HashSet<>();
		for (final Booking booking : getBookingSet()) {
			if (!booking.isCancelled()) {
				if (booking.getPaymentReference() == null) {
					try {
						booking.setPaymentReference(BankInterface.processPayment(booking.getIban(), booking.getPrice()));
					} catch (BankException | RemoteAccessException ex) {
						failedToProcess.add(booking);
						continue;
					}
				}
				final InvoiceData invoiceData = new InvoiceData(booking.getProviderNif(), booking.getNif(),
						Booking.getType(), booking.getPrice(), booking.getArrival());
				try {
					booking.setInvoiceReference(TaxInterface.submitInvoice(invoiceData));
				} catch (TaxException | RemoteAccessException ex) {
					failedToProcess.add(booking);
				}
			} else {
				try {
					if (booking.getCancelledPaymentReference() == null) {
						booking.setCancelledPaymentReference(
								BankInterface.cancelPayment(booking.getPaymentReference()));
					}
					if (!booking.isCancelledInvoice()) {
						TaxInterface.cancelInvoice(booking.getInvoiceReference());
						booking.setCancelledInvoice(true);
					}
				} catch (BankException | TaxException | RemoteAccessException ex) {
					failedToProcess.add(booking);
				}

			}
		}

		clearBookingsToProcess();
		addBookingsToProcess(failedToProcess);

	}

	private void clearBookingsToProcess () {
	    for(Booking booking : getBookingSet()) {
	    	booking.setProcessor(null);
	    	removeBooking(booking);
       }
	}
	
	public void addBookingsToProcess(Set<Booking> failedToProcess) {
	    for(Booking booking : failedToProcess)
	        addBooking(booking);
    }
	
	public void delete() {	
		setHotel(null);
		deleteDomainObject();
	}

}