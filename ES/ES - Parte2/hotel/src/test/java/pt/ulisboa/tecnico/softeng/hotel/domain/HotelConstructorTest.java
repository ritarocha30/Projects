package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelConstructorTest {
	private static final String HOTEL_NAME = "Londres";
	private static final String HOTEL_CODE = "XPTO123";
	private static final String HOTEL_NIF = "987654321";

	@Test
	public void success() {
		Hotel hotel = new Hotel(HOTEL_CODE, HOTEL_NAME, HOTEL_NIF);

		Assert.assertEquals(HOTEL_NAME, hotel.getName());
		Assert.assertTrue(hotel.getCode().length() == Hotel.CODE_SIZE);
		Assert.assertEquals(HOTEL_NIF, hotel.getNif());
		Assert.assertEquals(0, hotel.getNumberOfRooms());
		Assert.assertEquals(1, Hotel.hotels.size());
	}

	@Test(expected = HotelException.class)
	public void nullCode() {
		new Hotel(null, HOTEL_NAME, HOTEL_NIF);
	}

	@Test(expected = HotelException.class)
	public void blankCode() {
		new Hotel("      ", HOTEL_NAME, HOTEL_NIF);
	}

	@Test(expected = HotelException.class)
	public void emptyCode() {
		new Hotel("", HOTEL_NAME, HOTEL_NIF);
	}

    @Test(expected = HotelException.class)
    public void codeSizeLess() {
        new Hotel("123456", HOTEL_NAME, HOTEL_NIF);
    }

    @Test(expected = HotelException.class)
    public void codeSizeMore() {
        new Hotel("12345678", HOTEL_NAME, HOTEL_NIF);
    }

    @Test(expected = HotelException.class)
    public void codeNotUnique() {
        new Hotel(HOTEL_CODE, HOTEL_NAME, HOTEL_NIF);
        new Hotel(HOTEL_CODE, HOTEL_NAME + " City", HOTEL_NIF);
    }

	@Test(expected = HotelException.class)
	public void nullName() {
		new Hotel(HOTEL_CODE, null, HOTEL_NIF);
	}

	@Test(expected = HotelException.class)
	public void blankName() {
		new Hotel(HOTEL_CODE, "      ", HOTEL_NIF);
	}

	@Test(expected = HotelException.class)
	public void emptyName() {
		new Hotel(HOTEL_CODE, "", HOTEL_NIF);
	}

	@Test(expected = HotelException.class)
    public void nullNif() { new Hotel(HOTEL_CODE, HOTEL_NAME, null); }

    @Test(expected = HotelException.class)
    public void blankNif() { new Hotel(HOTEL_CODE, HOTEL_NAME,"      "); }

    @Test(expected = HotelException.class)
    public void emptyNif() { new Hotel(HOTEL_CODE, HOTEL_NAME, ""); }

    @Test(expected = HotelException.class)
    public void wrongNifLength() { new Hotel(HOTEL_CODE, HOTEL_NAME, "1234"); }

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
