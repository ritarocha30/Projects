package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelBulkBookingMethodTest {
    private static final String BUYER_NIF = "123456789";
    private static final String BUYER_IBAN = "IBAN";
	private static final LocalDate ARRIVAL = new LocalDate(2016, 12, 19);
	private static LocalDate DEPARTURE = new LocalDate(2016, 12, 21);
	private Hotel hotel;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Londres", "987654321");
		new Room(this.hotel, "01", Type.DOUBLE, 100.00);
		new Room(this.hotel, "02", Type.SINGLE, 50.00);
		new Room(this.hotel, "03", Type.DOUBLE, 100.00);
		new Room(this.hotel, "04", Type.SINGLE, 50.00);

		this.hotel = new Hotel("XPTO124", "Paris", "246813579");
		new Room(this.hotel, "01", Type.DOUBLE, 150.00);
		new Room(this.hotel, "02", Type.SINGLE, 100.00);
		new Room(this.hotel, "03", Type.DOUBLE, 150.00);
		new Room(this.hotel, "04", Type.SINGLE, 100.00);
	}

	@Test
	public void success() {
		Set<String> references = Hotel.bulkBooking(2, ARRIVAL, DEPARTURE, BUYER_NIF, BUYER_IBAN);

		assertEquals(2, references.size());
	}

	@Test(expected = HotelException.class)
	public void zeroNumber() {
		Hotel.bulkBooking(0, ARRIVAL, DEPARTURE, BUYER_NIF, BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
	public void noRooms() {
		Hotel.hotels.clear();
		this.hotel = new Hotel("XPTO124", "Paris", "246813579");

		Hotel.bulkBooking(3, ARRIVAL, DEPARTURE, BUYER_NIF, BUYER_IBAN);
	}

	@Test
	public void OneNumber() {
		Set<String> references = Hotel.bulkBooking(1, ARRIVAL, DEPARTURE, BUYER_NIF, BUYER_IBAN);

		assertEquals(1, references.size());
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		Hotel.bulkBooking(2, null, DEPARTURE, BUYER_NIF, BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		Hotel.bulkBooking(2, ARRIVAL, null, BUYER_NIF, BUYER_IBAN);
	}

	@Test
	public void reserveAll() {
		Set<String> references = Hotel.bulkBooking(8, ARRIVAL, DEPARTURE, BUYER_NIF, BUYER_IBAN);

		assertEquals(8, references.size());
	}

	@Test
	public void reserveAllPlusOne() {
		try {
			Hotel.bulkBooking(9, ARRIVAL, DEPARTURE, BUYER_NIF, BUYER_IBAN);
			fail();
		} catch (HotelException he) {
			assertEquals(8, Hotel.getAvailableRooms(8, ARRIVAL, DEPARTURE).size());
		}
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
