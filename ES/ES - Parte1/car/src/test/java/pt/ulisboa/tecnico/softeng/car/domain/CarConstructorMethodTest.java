package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CarConstructorMethodTest {
    
    private final String plate = "24-90-HV";
    private final int kilometers = 500;
    private RentACar rentACar;
    private Car car;
    
    @Before
    public void setUp() {
        this.rentACar = new RentACar("RENT03");
        this.car = new Car(this.plate, this.kilometers, this.rentACar);
    }

    @Test
    public void success() {
        Assert.assertEquals(this.plate, car.getPlate());
        Assert.assertEquals(this.kilometers, car.getKilometers());
        Assert.assertEquals(this.rentACar, car.getRentACar());
    }
}
