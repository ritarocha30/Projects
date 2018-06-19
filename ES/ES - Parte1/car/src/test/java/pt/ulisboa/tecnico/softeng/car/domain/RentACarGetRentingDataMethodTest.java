package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;



public class RentACarGetRentingDataMethodTest {
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
        RentingData rentingData = RentACar.getRentingData(renting.getReference());
        Assert.assertEquals(begin, rentingData.getBegin());
        Assert.assertEquals(end, rentingData.getEnd());
        Assert.assertEquals("A001", rentingData.getDrivingLicense());
        Assert.assertEquals(car.getPlate(), rentingData.getPlate());
    }

    @Test (expected = CarException.class)
    public void noSuchReference(){
        RentingData rentingData = rentACar.getRentingData("000");
    }

    @Test (expected = CarException.class)
    public void nullReference(){
        RentingData rentingData = rentACar.getRentingData(null);
    }

	@After
    public void tearDown(){
        RentACar.rentACars.clear();
        RentACar.vehicles.clear();
    }
}
