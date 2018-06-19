package pt.ulisboa.tecnico.softeng.hotel.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class Room {
	public static enum Type {
		SINGLE, DOUBLE
	}

	private final Hotel hotel;
	private final String number;
	private final Type type;
	private final double price;
	private final Set<Booking> bookings = new HashSet<>();

	public Room(Hotel hotel, String number, Type type, double price) {
		checkArguments(hotel, number, type, price);

		this.hotel = hotel;
		this.number = number;
		this.type = type;
		this.price = price;

		this.hotel.addRoom(this);
	}

	private void checkArguments(Hotel hotel, String number, Type type, double price) {
		if (hotel == null || number == null || number.trim().length() == 0 || type == null || price < 1) {
			throw new HotelException();
		}

		if (!number.matches("\\d*")) {
			throw new HotelException();
		}
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public String getNumber() {
		return this.number;
	}

	public Type getType() {
		return this.type;
	}

	public double getPrice() { return this.price; }

	int getNumberOfBookings() {
		int count = 0;

        for (Booking booking : this.bookings) {
            if (!booking.isCancelled()) {
                count++;
            }
        }
        return count;
	}

	void addBooking(Booking booking) {
	    this.bookings.add(booking);
    }

	public boolean isFree(Type type, LocalDate arrival, LocalDate departure) {
		if (!type.equals(this.type)) {
			return false;
		}

		for (Booking booking : this.bookings) {
			if (booking.conflict(arrival, departure)) {
				return false;
			}
		}

		return true;
	}

	public Booking getBooking(String reference) {
		for (Booking booking : this.bookings) {
			if (booking.getReference().equals(reference)
					|| (booking.isCancelled() && booking.getCancellation().equals(reference))) {
				return booking;
			}
		}
		return null;
	}

}
