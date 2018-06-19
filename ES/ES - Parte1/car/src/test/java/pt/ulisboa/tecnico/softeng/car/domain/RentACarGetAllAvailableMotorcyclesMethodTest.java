package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RentACarGetAllAvailableMotorcyclesMethodTest {
    private RentACar rentACar;
    private Renting renting;
    private Motorcycle motorcycle1;
    private Motorcycle motorcycle2;
    private Motorcycle motorcycle3;
    private LocalDate begin;
    private LocalDate begin1;
    private LocalDate end;
    private LocalDate end1;

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
        Assert.assertTrue(rentACar.getAllAvailableMotorcycles(begin, end).isEmpty());
        motorcycle1 = new Motorcycle("01-AB-01", 0, rentACar); /* Available motorcycle no renting */
        Assert.assertEquals(1, rentACar.getAllAvailableMotorcycles(begin, end).size());
        motorcycle2 = new Motorcycle("01-AB-02", 0, rentACar); /* Motorcycle rented at that time */
        motorcycle2.rent("A001", begin, end);
        Assert.assertEquals(1, rentACar.getAllAvailableMotorcycles(begin, end).size());
        motorcycle3 = new Motorcycle("01-AB-03", 0, rentACar); /* Available motorcycle/Rented at other time */
        motorcycle3.rent("A001", begin1, end1);
        Assert.assertEquals(2, rentACar.getAllAvailableMotorcycles(begin,end).size());
    }

    @After
    public void tearDown(){
        RentACar.rentACars.clear();
        RentACar.vehicles.clear();
    }
	
}
