package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MotorcycleConstructorMethodTest {

    private final String plate = "81-54-HL";
    private final int kilometers = 100;
    private RentACar rentACar;
    private Motorcycle motorcycle;

    @Before
    public void setUp() {
        this.rentACar = new RentACar("RENT02");
        this.motorcycle = new Motorcycle(this.plate, this.kilometers, this.rentACar);
    }

    @Test
    public void success() {
        Assert.assertEquals(this.plate, motorcycle.getPlate());
        Assert.assertEquals(this.kilometers, motorcycle.getKilometers());
        Assert.assertEquals(this.rentACar, motorcycle.getRentACar());
    }
}
