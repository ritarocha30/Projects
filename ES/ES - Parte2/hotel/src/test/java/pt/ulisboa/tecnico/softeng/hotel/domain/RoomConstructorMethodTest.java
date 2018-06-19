package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class RoomConstructorMethodTest {
	private static final String ROOM_NUMBER = "01";
    private static final double ROOM_PRICE = 100.00;
    private Hotel hotel;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Londres", "987654321");
	}

	@Test
	public void success() {
		Room room = new Room(this.hotel, ROOM_NUMBER, Type.DOUBLE, ROOM_PRICE);

		Assert.assertEquals(this.hotel, room.getHotel());
		Assert.assertEquals(ROOM_NUMBER, room.getNumber());
		Assert.assertEquals(Type.DOUBLE, room.getType());
		Assert.assertEquals(ROOM_PRICE, room.getPrice(), 0);
		Assert.assertEquals(1, this.hotel.getNumberOfRooms());
	}

	@Test(expected = HotelException.class)
	public void nullHotel() {
		new Room(null, ROOM_NUMBER, Type.DOUBLE, ROOM_PRICE);
	}

	@Test(expected = HotelException.class)
	public void nullRoomNumber() {
		new Room(this.hotel, null, Type.DOUBLE, ROOM_PRICE);
	}

    @Test(expected = HotelException.class)
    public void blankRoomNumber() {
        new Room(this.hotel, "     ", Type.DOUBLE, ROOM_PRICE);
    }

	@Test(expected = HotelException.class)
	public void emptyRoomNumber() {
		new Room(this.hotel, "", Type.DOUBLE, ROOM_PRICE);
	}

	@Test(expected = HotelException.class)
	public void nonAlphanumericRoomNumber() {
		new Room(this.hotel, "JOSE", Type.DOUBLE, ROOM_PRICE);
	}

	@Test
	public void nonUniqueRoomNumber() {
		new Room(this.hotel, "01", Type.SINGLE, ROOM_PRICE);
		try {
			new Room(this.hotel, "01", Type.DOUBLE, ROOM_PRICE);
			fail();
		} catch (HotelException he) {
			Assert.assertEquals(1, this.hotel.getNumberOfRooms());
		}
	}

	@Test(expected = HotelException.class)
	public void nullType() {
		new Room(this.hotel, ROOM_NUMBER, null, ROOM_PRICE);
	}

	@Test(expected = HotelException.class)
    public void zeroPrice() { new Room(this.hotel, ROOM_NUMBER, Type.DOUBLE, 0); }

	@Test(expected = HotelException.class)
    public void negativePrice() { new Room(this.hotel, ROOM_NUMBER, Type.DOUBLE, -1); }

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
