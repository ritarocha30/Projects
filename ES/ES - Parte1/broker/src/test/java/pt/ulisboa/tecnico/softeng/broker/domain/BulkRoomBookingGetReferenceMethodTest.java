package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.dataobjects.RoomBookingData;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BulkRoomBookingGetReferenceMethodTest {

    private final int number = 10;
    private final LocalDate departure = new LocalDate(2018, 03, 03);
    private final LocalDate arrival = new LocalDate(2018, 03, 01);
    private BulkRoomBooking bulkRoomBooking;
    private Hotel hotel;

    @Before
    public void setUp() {
        this.hotel = new Hotel("HOTEL01", "London");
        new Room(this.hotel, "1", Room.Type.DOUBLE);
        new Room(this.hotel, "2", Room.Type.SINGLE);
        this.bulkRoomBooking = new BulkRoomBooking(this.number, this.arrival, this.departure);
    }

    @Test
    public void nullReference() {
        String reference = this.bulkRoomBooking.getReference(null);
        Assert.assertNull(reference);
    }

    @After
    public void tearDown() {
        this.hotel.hotels.clear();
        this.bulkRoomBooking.getReferences().clear();
    }
}
