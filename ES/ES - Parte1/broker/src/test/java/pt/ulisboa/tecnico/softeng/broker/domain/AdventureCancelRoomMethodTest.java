package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AdventureCancelRoomMethodTest {
    private static final int AGE = 20;
    private static final int AMOUNT = 300;
    private static final String IBAN = "BK011234567";
    private Broker broker;
    private final LocalDate begin = new LocalDate(2016, 12, 19);
    private final LocalDate end = new LocalDate(2016, 12, 21);

    @Before
    public void setUp() {
        this.broker = new Broker("BR01", "eXtremeADVENTURE");
    }

    @Test
    public void roomCancelation(){
        Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);
        adventure.setRoomCancellation("canceled");
        Assert.assertEquals("canceled", adventure.getRoomCancellation());
    }

    @Test
    public void success(){
        Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);
        adventure.setRoomConfirmation("Confirmed");
        Assert.assertTrue(adventure.cancelRoom());
    }

    @Test
    public void notConfirmed(){
        Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);
        Assert.assertFalse(adventure.cancelRoom());
    }

    @Test
    public void canceled(){
        Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);
        adventure.setRoomCancellation("Canceled");
        Assert.assertFalse(adventure.cancelRoom());
    }

    @Test
    public void confirmCanceled(){
        Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);
        adventure.setRoomConfirmation("Confirmed");
        adventure.setRoomCancellation("Canceled");
        Assert.assertFalse(adventure.cancelRoom());
    }

    @After
    public void tearDown() {
        Broker.brokers.clear();
    }
}
