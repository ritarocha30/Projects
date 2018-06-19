package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RentACarGetAllAvailableCarsMethodTest {
    private RentACar rentACar;
    private Renting renting;
    private Car car1;
    private Car car2;
    private Car car3;
    private LocalDate begin;
    private LocalDate begin1;
    private LocalDate end;
    private LocalDate end1;
    private Renting renting1;
    private Renting renting2;

    @Before
    public void setUp(){
        begin = new LocalDate(2018, 03, 01);
        end = new LocalDate(2018, 03, 03);
        begin1 = new LocalDate(2018, 04, 01);
        end1 = new LocalDate(2018, 04, 03);
        rentACar = new RentACar("Rent My Car");
    }

    @Test
    public void success(){
        Assert.assertTrue(rentACar.getAllAvailableCars(begin, end).isEmpty());
        car1 = new Car("01-AB-01", 0, rentACar); // Available car no renting
        Assert.assertEquals(1, rentACar.getAllAvailableCars(begin, end).size());
        car2 = new Car("01-AB-02", 0, rentACar); // Car rented at that time
        renting1 = car2.rent("A001", begin, end);
        Assert.assertEquals(1, rentACar.getAllAvailableCars(begin, end).size());
        car3 = new Car("01-AB-03", 0, rentACar); // Available car/Rented at other time
        renting2 = car3.rent("A001", begin1, end1);
        Assert.assertEquals(2, rentACar.getAllAvailableCars(begin,end).size());

    }

    @After
    public void tearDown(){
        RentACar.rentACars.clear();
        RentACar.vehicles.clear();
    }

}
