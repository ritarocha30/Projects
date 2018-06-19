package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Assert;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarConstructorTest {

    @Test
    public void success(){
        RentACar rentACar = new RentACar("XPTO");

        Assert.assertEquals("XPTO", rentACar.getName());
        Assert.assertTrue(rentACar.getCode().startsWith("RaC"));
        Assert.assertTrue(rentACar.getCars().isEmpty());
        Assert.assertTrue(rentACar.getMotorcycles().isEmpty());
    }

    @Test (expected = CarException.class)
    public void nullName(){
        RentACar rentACar = new RentACar(null);
    }

    @Test (expected = CarException.class)
    public void emptyName(){
        RentACar rentACar = new RentACar("");
    }
	
}
