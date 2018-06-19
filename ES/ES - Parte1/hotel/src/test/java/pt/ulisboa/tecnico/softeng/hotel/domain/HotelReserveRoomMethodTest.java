package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelReserveRoomMethodTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 24);
	private Hotel hotel;
	private Room room;
	
	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Paris");
		this.room = new Room(this.hotel, "01", Type.DOUBLE);
	}
	
	@Test
    public void success() {
        String reference = Hotel.reserveRoom(this.room.getType(), this.arrival, this.departure);
        
        assertEquals(reference, this.room.getBooking(reference).getReference());
    }
	
	@Test(expected = HotelException.class)
	public void noAvailableRoom() {
		Hotel.reserveRoom(Type.DOUBLE, this.arrival, this.departure);

		assertNull(Hotel.reserveRoom(Type.DOUBLE, this.arrival, this.departure));
	}
	
	@Test(expected = HotelException.class)
	public void nullType() {
		Hotel.reserveRoom(null, this.arrival, this.departure);
	}
	
	@Test(expected = HotelException.class)
	public void nullArrival() {
		Hotel.reserveRoom(Type.DOUBLE, null, departure);
	}
	
	@Test(expected = HotelException.class)
	public void nullDeparture() {
		Hotel.reserveRoom(Type.DOUBLE, this.arrival, null);
	}
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}