package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingCheckoutMethodTest {


    private static final String drivingLicense = "VC12345";
    private final LocalDate begin = new LocalDate(2018, 03, 01);
    private final LocalDate end = new LocalDate(2018, 03, 03);
    private final int kilometers = 1000;
    private Car car;
    private RentACar rentACar;

    @Before
    public void buildUp(){
        this.rentACar = new RentACar("BestDealershipInEUW");
        this.car = new Car("XX-22-22", this.kilometers, this.rentACar);
    }

    @Test
    public void success(){
        Renting rent = new Renting(this.car, this.drivingLicense, this.begin, this.end, this.kilometers);
        Assert.assertEquals(this.kilometers, rent.checkout(2000));
        Assert.assertEquals(2000, rent.getKilometers());
    }

    @Test (expected = CarException.class)
    public void negativeTravel(){
        Renting rent = new Renting(this.car, this.drivingLicense, this.begin, this.end, this.kilometers);
        rent.checkout(900);
    }

    @After
    public void tearDown(){
        RentACar.rentACars.clear();
        RentACar.vehicles.clear();
    }
}