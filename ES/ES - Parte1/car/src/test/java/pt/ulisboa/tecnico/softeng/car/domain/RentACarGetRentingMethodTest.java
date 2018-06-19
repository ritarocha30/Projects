package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetRentingMethodTest {
    private RentACar rentACar;
    private LocalDate begin;
    private LocalDate end;
    private Car car;
    private Renting renting;

    @Before
    public void setUp(){
        rentACar = new RentACar("RentYourCar");
        begin = new LocalDate(2018, 03, 01);
        end = new LocalDate(2018, 03, 03);
        car = new Car("01-AA-01", 0, rentACar);
        renting = car.rent("A001", begin, end);
    }

    @Test
    public void success(){
        Renting renting1 = rentACar.getRenting(renting.getReference());
        Assert.assertEquals(renting.getBegin(), renting1.getBegin());
        Assert.assertEquals(renting.getEnd(), renting1.getEnd());
        Assert.assertEquals(renting.getDrivingLicense(), renting1.getDrivingLicense());
    }

    @Test
    public void noSuchReference(){
        Renting renting = rentACar.getRenting("000");
        Assert.assertNull(renting);
    }

    @Test (expected = CarException.class)
    public void nullReference(){
        Renting renting = rentACar.getRenting(null);
    }

    @After
    public void tearDown(){
        RentACar.rentACars.clear();
        RentACar.vehicles.clear();
    }
	
}
