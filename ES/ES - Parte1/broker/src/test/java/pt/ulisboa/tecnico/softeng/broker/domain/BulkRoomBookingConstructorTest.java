package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class BulkRoomBookingConstructorTest {

    private static final int number = 10;
    private final LocalDate arrival = new LocalDate(2018, 03, 03);
    private final LocalDate departure = new LocalDate(2018, 03, 01);


    @Test
    public void success(){
        BulkRoomBooking bulk = new BulkRoomBooking(this.number, this.arrival, this.departure);

        Assert.assertEquals(this.number, bulk.getNumber());
        Assert.assertEquals(this.arrival, bulk.getArrival());
        Assert.assertEquals(this.departure, bulk.getDeparture());

    }
}
