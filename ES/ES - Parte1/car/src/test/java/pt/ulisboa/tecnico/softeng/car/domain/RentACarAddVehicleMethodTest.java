package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarAddVehicleMethodTest {
    private RentACar rentACar;

    @Before
    public void setUp() {
        rentACar = new RentACar("Rent My Car");
    }

    @Test
    public void success(){
        Assert.assertEquals(0, rentACar.getVehicles().size());
        new Car("01-AB-01", 0, rentACar);
        Assert.assertEquals(1, rentACar.getVehicles().size());
        new Car("01-AB-02", 0, rentACar);
        Assert.assertEquals(2, rentACar.getVehicles().size());
    }

    @Test (expected = CarException.class)
    public void duplicatePlates(){
        new Car("01-AB-01", 0, rentACar);
        new Car("01-AB-01", 0, rentACar);
    }

    @After
    public void tearDown(){
       RentACar.vehicles.clear();
       RentACar.rentACars.clear();
    }

}
