package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;

public class BulkRoomBookingProcessBookingMethodTest {

    private final int number = 2;
    private final LocalDate departure = new LocalDate(2018, 3, 3);
    private final LocalDate arrival = new LocalDate(2018, 3, 1);
    private Set<String> references = new HashSet<>();
    private BulkRoomBooking bulkRoomBooking;
    private Hotel hotel;

    @Before
    public void setUp() {
        this.hotel = new Hotel("HOTEL01", "London");
        new Room(this.hotel, "1", Room.Type.DOUBLE);
        new Room(this.hotel, "2", Room.Type.SINGLE);
        new Room(this.hotel, "3", Room.Type.DOUBLE);
        new Room(this.hotel, "4", Room.Type.SINGLE);
        this.bulkRoomBooking = new BulkRoomBooking(this.number, this.arrival, this.departure);
    }

    @Test
    public void success() {
        this.bulkRoomBooking.processBooking();
        this.references = HotelInterface.bulkBooking(this.number, this.arrival, this.departure);

        Assert.assertEquals(this.bulkRoomBooking.getReferences().size(), this.references.size());
    }


}
