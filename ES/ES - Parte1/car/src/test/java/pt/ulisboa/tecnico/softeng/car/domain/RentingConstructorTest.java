package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingConstructorTest {

    private static final String drivingLicense = "VC12345";
    private final LocalDate begin = new LocalDate(2018, 03, 01);
    private final LocalDate end = new LocalDate(2018, 03, 03);
    private final int kilometers = 1000;
    private final int rentStart = 0;
    private Car car;
    private RentACar rentACar;

    @Before
    public void setUp(){
        this.rentACar = new RentACar("RentMaster9000");
        this.car = new Car("12-34-AB", this.kilometers, this.rentACar);
    }

    @Test
    public void success(){
        Renting rent = new Renting(this.car, this.drivingLicense, this.begin, this.end, this.rentStart);

        Assert.assertEquals(this.car, rent.getVehicle());
        Assert.assertTrue(rent.getReference().startsWith(this.car.getPlate()));
        Assert.assertTrue(rent.getReference().length() > this.car.getPlate().length());
        Assert.assertEquals(this.drivingLicense, rent.getDrivingLicense());
        Assert.assertTrue(this.begin.isEqual(rent.getBegin()));
        Assert.assertTrue(this.end.isEqual(rent.getEnd()));
        Assert.assertEquals(this.rentStart, rent.getKilometers());
    }

    @Test (expected = CarException.class)
    public void nullCar(){
        new Renting(null, this.drivingLicense, this.begin, this.end, this.rentStart);
    }

    @Test (expected = CarException.class)
    public void invalidLicense(){
        new Renting(this.car, "abc", this.begin, this.end, this.rentStart);
    }

    @Test (expected = CarException.class)
    public void nullDrivingLicense(){
        new Renting(this.car, null, this.begin, this.end, this.rentStart);
    }

    @Test (expected = CarException.class)
    public void nullBegin(){
        new Renting(this.car, this.drivingLicense, null, this.end, this.rentStart);
    }


    @Test (expected = CarException.class)
    public void nullEnd(){
        new Renting(this.car, this.drivingLicense, this.begin, null, this.rentStart);
    }

    @Test (expected = CarException.class)
    public void negativeKm(){
        new Renting(this.car, this.drivingLicense, this.begin, this.end, -1);
    }

    @After
    public void tearDown(){
        RentACar.rentACars.clear();
        RentACar.vehicles.clear();
    }

}