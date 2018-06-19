package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class Booking {
	private static int counter = 0;

    private static final String type = "HOTEL";
	private String cancellation;
	private LocalDate cancellationDate;
    private String paymentReference;
    private String invoiceReference;
    private boolean cancelledInvoice = false;
    private String cancelledPaymentReference = null;
    private final String reference;
    private final Hotel hotel;
    private final LocalDate arrival;
	private final LocalDate departure;
	private final String buyerNif;
	private final String buyerIban;
	private final String hotelNif;
	private final double amount;
	private final LocalDate bookingDate;

	Booking(Hotel hotel, Room room, LocalDate arrival, LocalDate departure, String buyerNif, String buyerIban) {
		checkArguments(hotel, room, arrival, departure, buyerNif, buyerIban);

		this.reference = hotel.getCode() + Integer.toString(++Booking.counter);
		this.hotel = hotel;
		this.arrival = arrival;
		this.departure = departure;
		this.buyerNif = buyerNif;
		this.buyerIban = buyerIban;
		this.hotelNif = hotel.getNif();
		this.amount = room.getPrice();
		this.bookingDate = arrival;

		room.addBooking(this);
	}

	private void checkArguments(Hotel hotel, Room room, LocalDate arrival, LocalDate departure, String buyerNif, String buyerIban) {
		if (hotel == null || room == null || arrival == null || departure == null || buyerNif == null || buyerIban == null) {
			throw new HotelException();
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}

		if (buyerNif.trim().length() == 0 || buyerIban.trim().length() == 0) {
		    throw new HotelException();
        }

        if (buyerNif.length() != 9) {
            throw new HotelException();
        }
	}

	public String getType() { return this.type; }

    public String getCancellation() {
        return this.cancellation;
    }

    public LocalDate getCancellationDate() {
        return this.cancellationDate;
    }

    public String getPaymentReference() { return this.paymentReference; }

    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }

    public String getInvoiceReference() { return this.invoiceReference; }

    public void setInvoiceReference(String invoiceReference) { this.invoiceReference = invoiceReference; }

	public boolean isCancelledInvoice() { return this.cancelledInvoice; }

    public void setCancelledInvoice(boolean cancelledInvoice) {
        this.cancelledInvoice = cancelledInvoice;
    }

    public String getCancelledPaymentReference() {
        return this.cancelledPaymentReference;
    }

    public void setCancelledPaymentReference(String cancelledPaymentReference) {
        this.cancelledPaymentReference = cancelledPaymentReference;
    }

    public String getReference() {
		return this.reference;
	}

    public LocalDate getArrival() { return this.arrival; }

	public LocalDate getDeparture() {
		return this.departure;
	}

	public String getBuyerNif() { return this.buyerNif; }

	public String getBuyerIban() { return this.buyerIban; }

	public String getHotelNif() { return this.hotelNif; }

	public double getAmount() { return this.amount; }

    public LocalDate getBookingDate() { return this.bookingDate; }

	boolean conflict(LocalDate arrival, LocalDate departure) {
		if (isCancelled()) {
			return false;
		}

		if (arrival.equals(departure)) {
			return true;
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}

		if ((arrival.equals(this.arrival) || arrival.isAfter(this.arrival)) && arrival.isBefore(this.departure)) {
			return true;
		}

		if ((departure.equals(this.departure) || departure.isBefore(this.departure))
				&& departure.isAfter(this.arrival)) {
			return true;
		}

		if ((arrival.isBefore(this.arrival) && departure.isAfter(this.departure))) {
			return true;
		}

		return false;
	}

	public String cancel() {
		this.cancellation = "CANCEL" + this.reference;
		this.cancellationDate = new LocalDate();

		this.hotel.getProcessor().submitBooking(this);

		return this.cancellation;
	}

	public boolean isCancelled() {
		return this.cancellation != null;
	}

}
