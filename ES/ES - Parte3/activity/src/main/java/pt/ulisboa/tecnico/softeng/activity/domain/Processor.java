package pt.ulisboa.tecnico.softeng.activity.domain;

import java.awt.print.Book;
import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.activity.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.activity.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.activity.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Processor extends Processor_Base {
	// important to use a set to avoid double submission of the same booking when it
	// is cancelled while trying to pay or send invoice
    //private final Set<Booking> bookingToProcess = new HashSet<>();


	public void submitBooking(Booking booking) {
		addBooking(booking);
		processInvoices();
	}

	private void processInvoices() {
		Set<Booking> failedToProcess = new HashSet<>();
		for (Booking booking : getBookingSet()) {
			if (!booking.isCancelled()) {
				if (booking.getPaymentReference() == null) {
					try {
						booking.setPaymentReference(
								BankInterface.processPayment(booking.getIban(), booking.getAmount()));
					} catch (BankException | RemoteAccessException ex) {
						failedToProcess.add(booking);
						continue;
					}
				}
				InvoiceData invoiceData = new InvoiceData(booking.getProviderNif(), booking.getNif(), booking.getType(),
						booking.getAmount(), booking.getDate());
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
					TaxInterface.cancelInvoice(booking.getInvoiceReference());
					booking.setCancelledInvoice(true);
				} catch (BankException | TaxException | RemoteAccessException ex) {
					failedToProcess.add(booking);
				}

			}
		}

		clearBookingsToProcess();
        addBookingsToProcess(failedToProcess);
		//this.bookingToProcess.clear();
		//this.bookingToProcess.addAll(failedToProcess);

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
		setActivityProvider(null);

		deleteDomainObject();
	}

}