package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

import static junit.framework.TestCase.assertTrue;

public class HotelReserveRoomMethodTest {
    private static final String BUYER_NIF = "123456789";
    private static final String BUYER_IBAN = "IBAN";
    private final LocalDate arrival = new LocalDate(2016, 12, 19);
    private final LocalDate departure = new LocalDate(2016, 12, 24);
    private Room room;
    private Hotel hotel;
    private Booking booking;

    @Before
    public void setUp() {
        this.hotel = new Hotel("XPTO123", "Londres", "987654321");
        this.room = new Room(hotel, "01", Room.Type.SINGLE, 50.00);
        this.booking = new Booking(this.hotel, this.room, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
    }

    @Test
    public void success() {
        String ref = Hotel.reserveRoom(Room.Type.SINGLE, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
        assertTrue(ref.startsWith("XPTO12"));
    }

    @Test(expected = HotelException.class)
    public void noHotels() {
        Hotel.hotels.clear();
        Hotel.reserveRoom(Room.Type.SINGLE, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
    }

    @Test(expected = HotelException.class)
    public void noVacancy() {
        this.hotel.removeRooms();
        String ref = Hotel.reserveRoom(Room.Type.SINGLE, this.arrival, new LocalDate(2016, 12, 25), BUYER_NIF, BUYER_IBAN);
        System.out.println(ref);
    }

    @Test(expected = HotelException.class)
    public void noRooms() {
        hotel.removeRooms();
        Hotel.reserveRoom(Room.Type.SINGLE, arrival, new LocalDate(2016, 12, 25), BUYER_NIF, BUYER_IBAN);
    }

    @After
    public void tearDown() {
        Hotel.hotels.clear();
    }

}