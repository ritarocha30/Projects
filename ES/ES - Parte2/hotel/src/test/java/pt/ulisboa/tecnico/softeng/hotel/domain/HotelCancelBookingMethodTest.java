package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelCancelBookingMethodTest {
	private Hotel hotel;
	private Room room;
	private Booking booking;

	@Before
	public void setUp() {
	    LocalDate arrival = new LocalDate(2016, 12, 19);
	    LocalDate departure = new LocalDate(2016, 12, 21);

		this.hotel = new Hotel("XPTO123", "Paris", "987654321");
		this.room = new Room(this.hotel, "01", Type.DOUBLE, 100);
		this.booking = new Booking(this.hotel, this.room, arrival, departure, "123456789", "IBAN");
	}

	@Test
	public void success() {
		String cancel = Hotel.cancelBooking(this.booking.getReference());

		Assert.assertTrue(this.booking.isCancelled());
		Assert.assertEquals(cancel, this.booking.getCancellation());
	}

	@Test(expected = HotelException.class)
	public void doesNotExist() {
		Hotel.cancelBooking("XPTO");
	}

	@Test(expected = HotelException.class)
	public void nullReference() {
		Hotel.cancelBooking(null);
	}

	@Test(expected = HotelException.class)
	public void emptyReference() {
		Hotel.cancelBooking("");
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
