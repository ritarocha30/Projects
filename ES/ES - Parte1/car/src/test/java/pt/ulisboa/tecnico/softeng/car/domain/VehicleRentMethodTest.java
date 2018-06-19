package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleRentMethodTest {

    private final String drivingLicense = "LX12345";
    private final LocalDate begin = new LocalDate(2018, 3, 27);
    private final LocalDate end = new LocalDate(2018, 4, 9);
    private Vehicle car;

    @Before
    public void setUp() {
        RentACar rentACar = new RentACar("RENT01");
        this.car = new Car("13-19-MP", 100, rentACar);
    }

    @Test
    public void success() {
        Renting renting = this.car.rent(this.drivingLicense, this.begin, this.end);

        Assert.assertEquals(1, this.car.getRentings().size());
        Assert.assertTrue(renting.getReference().length() > 0);
        Assert.assertEquals(this.begin, renting.getBegin());
        Assert.assertEquals(this.end, renting.getEnd());
    }

    @Test(expected = CarException.class)
    public void nullDrivingLicense() {
        this.car.rent(null, this.begin, this.end);
    }

    @Test(expected = CarException.class)
    public void nullBegin() {
        this.car.rent(this.drivingLicense, null, this.end);
    }

    @Test(expected = CarException.class)
    public void nullEnd() {
        this.car.rent(this.drivingLicense, this.begin, null);
    }

    @Test(expected = CarException.class)
    public void vehicleNotFree() {
        this.car.rent(this.drivingLicense, this.begin, this.end);
        this.car.rent(this.drivingLicense, this.begin, this.end);
    }

    @After
    public void tearDown() {
        this.car.getRentings().clear();
        RentACar.rentACars.clear();
        RentACar.vehicles.clear();
    }
}
