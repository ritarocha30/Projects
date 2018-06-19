package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;


public class BulkBookingMethodTest {

    private static final int number = 10;
    private static final String code = "BR01";
    private static final String name = "SaulGoodman";
    private final LocalDate departure = new LocalDate(2018, 03, 03);
    private final LocalDate arrival = new LocalDate(2018, 03, 01);


    @Test
    public void success(){
        Broker broker = new Broker(this.code, this.name);
        broker.bulkBooking(this.number, this.arrival, this.departure);

    }
    @After
    public void tearDown(){
        Broker.brokers.clear();
    }
}
