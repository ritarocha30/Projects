package pt.ulisboa.tecnico.softeng.car.domain;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class VehicleConstructorMethodTest {

    private RentACar rentACar;
    private Vehicle car;
    private final String plate = "13-19-MP";
    private final int kilometers = 1000;

    @Before
    public void setUp() {
        this.rentACar = new RentACar("RENT01");
        this.car = new Car(this.plate, this.kilometers, this.rentACar);
    }

    @Test
    public void success() {
        Assert.assertEquals(this.plate, car.getPlate());
        Assert.assertEquals(this.kilometers, car.getKilometers());
        Assert.assertEquals(this.rentACar, car.getRentACar());
    }

    @Test (expected = CarException.class)
    public void nullPlate() {
        new Car(null, this.kilometers, this.rentACar);
    }

    @Test (expected = CarException.class)
    public void invalidPlate() {
        new Car("PPP", this.kilometers, this.rentACar);
    }

    @Test (expected = CarException.class)
    public void negativeKilometers() {
        new Car(this.plate, -1, this.rentACar);
    }

    @Test (expected = CarException.class)
    public void nullRentACar() {
        new Car(this.plate, this.kilometers, null);
    }

    @After
    public void tearDown() {
        RentACar.vehicles.clear();
        RentACar.rentACars.clear();
    }
}
