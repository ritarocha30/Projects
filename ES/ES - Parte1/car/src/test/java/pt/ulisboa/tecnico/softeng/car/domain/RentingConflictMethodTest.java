package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingConflictMethodTest{

    private static final String drivingLicense = "VC12345";

    private final LocalDate begin = new LocalDate(2016, 12, 19);
    private final LocalDate end = new LocalDate(2016, 12, 24);
    private final int kilometers = 1000;
    private Car car;
    private RentACar rentACar;
    private Renting renting;

    @Before
    public void setUp(){
        this.rentACar = new RentACar("BestDealershipInEUW");
        this.car = new Car("XX-11-22", this.kilometers, this.rentACar);
        this.renting = new Renting(this.car, this.drivingLicense, this.begin, this.end, this.kilometers);
    }

    @Test
    public void argumentsAreConsistent() {
        Assert.assertFalse(this.renting.conflict(new LocalDate(2016, 12, 9), new LocalDate(2016, 12, 15)));
    }

    @Test
    public void cancellationDate(){
        Assert.assertNotEquals(this.renting.getCancellationDate(), this.renting.getBegin());
    }

    @Test
    public void cancelled(){
        Assert.assertEquals(this.renting.cancel(), renting.getCancellation());
    }

    @Test
    public void noConflictBecauseItIsCancelled() {
        this.renting.cancel();
        Assert.assertFalse(this.renting.conflict(this.renting.getBegin(), this.renting.getEnd()));
    }

    @Test(expected = CarException.class)
    public void argumentsAreInconsistent() {
        this.renting.conflict(new LocalDate(2016, 12, 15), new LocalDate(2016, 12, 9));
    }

    @Test
    public void argumentsSameDay() {
        Assert.assertTrue(this.renting.conflict(new LocalDate(2016, 12, 9), new LocalDate(2016, 12, 9)));
    }

    @Test
    public void beginAndendAreBeforeRenting() {
        Assert.assertFalse(this.renting.conflict(this.begin.minusDays(10), this.begin.minusDays(4)));
    }

    @Test
    public void beginAndendAreBeforeRentingButendIsEqualToRentingBegin() {
        Assert.assertFalse(this.renting.conflict(this.begin.minusDays(10), this.begin));
    }

    @Test
    public void beginAndendAreAfterRenting() {
        Assert.assertFalse(this.renting.conflict(this.end.plusDays(4), this.end.plusDays(10)));
    }

    @Test
    public void beginAndendAreAfterRentingButbeginIsEqualToRenting() {
        Assert.assertFalse(this.renting.conflict(this.end, this.end.plusDays(10)));
    }

    @Test
    public void beginIsBeforeRentingbeginAndendIsAfterRentingEnd() {
        Assert.assertTrue(this.renting.conflict(this.begin.minusDays(4), this.end.plusDays(4)));
    }

    @Test
    public void beginIsEqualRentingbeginAndendIsAfterRentingEnd() {
        Assert.assertTrue(this.renting.conflict(this.begin, this.end.plusDays(4)));
    }

    @Test
    public void beginIsBeforeRentingBeginAndendIsEqualRentingEnd() {
        Assert.assertTrue(this.renting.conflict(this.begin.minusDays(4), this.end));
    }

    @Test
    public void beginIsBeforeRentingBeginAndendIsBetweenRenting() {
        Assert.assertTrue(this.renting.conflict(this.begin.minusDays(4), this.end.minusDays(3)));
    }

    @Test
    public void beginIsBetweenRentingAndendIsAfterRentingEnd() {
        Assert.assertTrue(this.renting.conflict(this.begin.plusDays(3), this.end.plusDays(6)));
    }

    @After
    public void tearDown(){
        RentACar.rentACars.clear();
        RentACar.vehicles.clear();
    }

}