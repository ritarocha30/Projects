package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VehicleIsFreeMethodTest {

    private final String drivingLicense = "LX12345";
    private final LocalDate begin = new LocalDate(2018, 3, 27);
    private final LocalDate end = new LocalDate(2018, 4, 9);
    private Vehicle car;
    private Vehicle motorcycle;

    @Before
    public void setUp() {
        RentACar rentACar = new RentACar("RENT01");
        this.car = new Car("13-19-MP", 100, rentACar);
        this.motorcycle = new Motorcycle("24-90-HV", 500, rentACar);
    }

    @Test
    public void success() {
        Renting renting = this.car.rent(this.drivingLicense, this.begin, this.end);
        Assert.assertEquals(false, this.car.isFree(this.begin, this.end));
        Assert.assertEquals(true, this.motorcycle.isFree(this.begin, this.end));

        String cancellation = renting.cancel();
        Assert.assertTrue(cancellation, renting.isCancelled());
        Assert.assertEquals(true, this.car.isFree(this.begin, this.end));
    }

    @After
    public void tearDown() {
        this.car.getRentings().clear();
        this.motorcycle.getRentings().clear();
        RentACar.rentACars.clear();
        RentACar.vehicles.clear();
    }
}
