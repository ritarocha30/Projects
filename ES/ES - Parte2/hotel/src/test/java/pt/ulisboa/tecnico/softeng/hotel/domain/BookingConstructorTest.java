package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BookingConstructorTest {
    private static final String BUYER_NIF = "123456789";
    private static final String BUYER_IBAN = "IBAN";
    private static final double PRICE = 50.00;
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);
	private Hotel hotel;
	private Room room;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Londres", "987654321");
		this.room = new Room(this.hotel, "01", Room.Type.SINGLE, PRICE);
 	}

	@Test
	public void success() {
		Booking booking = new Booking(this.hotel, this.room, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);

		Assert.assertTrue(booking.getReference().startsWith(this.hotel.getCode()));
		Assert.assertTrue(booking.getReference().length() > Hotel.CODE_SIZE);
		Assert.assertEquals(1, this.room.getNumberOfBookings());
		Assert.assertEquals(this.arrival, booking.getArrival());
		Assert.assertEquals(this.departure, booking.getDeparture());
		Assert.assertEquals(BUYER_NIF, booking.getBuyerNif());
		Assert.assertEquals(BUYER_IBAN, booking.getBuyerIban());
		Assert.assertEquals(PRICE, booking.getAmount(), 0);
	}

	@Test(expected = HotelException.class)
	public void nullHotel() {
		new Booking(null, this.room, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
    public void nullRoom() { new Booking(this.hotel, null, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN); }

	@Test(expected = HotelException.class)
	public void nullArrival() {
		new Booking(this.hotel, null, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
	}

    @Test
    public void arrivalEqualDeparture() {
        new Booking(this.hotel, this.room, this.arrival, this.arrival, BUYER_NIF, BUYER_IBAN);
    }

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		new Booking(this.hotel, this.room, this.arrival, null, BUYER_NIF, BUYER_IBAN);
	}

    @Test(expected = HotelException.class)
    public void departureBeforeArrival() {
        new Booking(this.hotel, this.room, this.arrival, this.arrival.minusDays(1), BUYER_NIF, BUYER_IBAN);
    }

	@Test(expected = HotelException.class)
    public void nullBuyerNif() { new Booking(this.hotel, this.room, this.arrival, this.departure, null, BUYER_IBAN); }

    @Test(expected = HotelException.class)
    public void blankBuyerNif() {
        new Booking(this.hotel, this.room, this.arrival, this.departure, "     ", BUYER_IBAN);
    }

    @Test(expected = HotelException.class)
    public void emptyBuyerNif() { new Booking(this.hotel, this.room, this.arrival, this.departure, "", BUYER_IBAN); }

    @Test(expected = HotelException.class)
    public void wrongBuyerNifLength() { new Booking(this.hotel, this.room, this.arrival, this.departure, "123456", BUYER_IBAN); }

    @Test(expected = HotelException.class)
    public void nullBuyerIban() { new Booking(this.hotel, this.room, this.arrival, this.departure, BUYER_NIF, null); }

    @Test(expected = HotelException.class)
    public void blankBuyerIban() {
        new Booking(this.hotel, this.room, this.arrival, this.departure, BUYER_NIF, "     ");
    }

    @Test(expected = HotelException.class)
    public void emptyBuyerIban() {
	    new Booking(this.hotel, this.room, this.arrival, this.departure, BUYER_NIF, "");
    }

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
