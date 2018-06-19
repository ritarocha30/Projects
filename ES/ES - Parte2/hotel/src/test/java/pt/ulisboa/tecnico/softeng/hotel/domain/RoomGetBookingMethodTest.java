package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class RoomGetBookingMethodTest {
	private Hotel hotel;
	private Room room;
	private Booking booking;

	@Before
	public void setUp() {
        LocalDate arrival = new LocalDate(2016, 12, 19);
        LocalDate departure = new LocalDate(2016, 12, 24);

		this.hotel = new Hotel("XPTO123", "Lisboa", "987654321");
		this.room = new Room(this.hotel, "01", Type.SINGLE, 50);
		this.booking = new Booking(this.hotel, this.room, arrival, departure, "123456789", "IBAN");
	}

	@Test
	public void success() {
		assertEquals(this.booking, this.room.getBooking(this.booking.getReference()));
	}

	@Test
	public void successCancelled() {
		this.booking.cancel();

		assertEquals(this.booking, this.room.getBooking(this.booking.getCancellation()));
	}

	@Test
	public void doesNotExist() {
		assertNull(this.room.getBooking("XPTO"));
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
