package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VehicleGetRentingMethodTest {

    private final String drivingLicense = "LX12345";
    private final LocalDate begin = new LocalDate(2018, 3, 27);
    private final LocalDate end = new LocalDate(2018, 4, 9);
    private Vehicle car;
    private Renting renting;

    @Before
    public void setUp() {
        RentACar rentACar = new RentACar("RENT01");
        this.car = new Car("13-19-MP", 100, rentACar);
        this.renting = this.car.rent(this.drivingLicense, this.begin, this.end);
    }

    @Test
    public void success() {
        Assert.assertEquals(this.renting, this.car.getRenting(this.renting.getReference()));
    }

    @Test
    public void noRenting() {
        Assert.assertNull(this.car.getRenting("XPTO"));
    }

    @After
    public void tearDown() {
        this.car.getRentings().clear();
        RentACar.rentACars.clear();
        RentACar.vehicles.clear();
    }
}
