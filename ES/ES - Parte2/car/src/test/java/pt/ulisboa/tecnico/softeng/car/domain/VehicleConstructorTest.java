package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleConstructorTest {
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String PLATE_MOTORCYCLE = "44-33-HZ";
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String NIF_SELLER = "987654321";
	private static final String IBAN_SELLER = "BK011111111";
	private static final double PRICE_CAR = 15000;
	private static final double PRICE_MOTORCYCLE = 15000;
	private RentACar rentACar;

	@Before
	public void setUp() {
		this.rentACar = new RentACar(RENT_A_CAR_NAME, NIF_SELLER, IBAN_SELLER);
	}

	@Test
	public void success() {
		Vehicle car = new Car(PLATE_CAR, 10, this.rentACar, PRICE_CAR);
		Vehicle motorcycle = new Motorcycle(PLATE_MOTORCYCLE, 10, this.rentACar, PRICE_MOTORCYCLE);

		assertEquals(PLATE_CAR, car.getPlate());
		assertTrue(this.rentACar.hasVehicle(PLATE_CAR));
		assertEquals(PRICE_CAR, car.getPrice(), 0.0d);
		assertEquals(PLATE_MOTORCYCLE, motorcycle.getPlate());
		assertTrue(this.rentACar.hasVehicle(PLATE_MOTORCYCLE));
		assertEquals(PRICE_MOTORCYCLE, motorcycle.getPrice(), 0.0d);
	}

	@Test(expected = CarException.class)
	public void negativePrice() {
		new Car(PLATE_CAR, 10, this.rentACar, -1);
	}
	
	@Test(expected = CarException.class)
	public void zeroPrice() {
		new Car(PLATE_CAR, 10, this.rentACar, 0);
	}
	
	
	
	@Test(expected = CarException.class)
	public void emptyLicensePlate() {
		new Car("", 10, this.rentACar, PRICE_CAR);
	}

	@Test(expected = CarException.class)
	public void nullLicensePlate() {
		new Car(null, 10, this.rentACar, PRICE_CAR);
	}

	@Test(expected = CarException.class)
	public void invalidLicensePlate() {
		new Car("AA-XX-a", 10, this.rentACar, PRICE_CAR);
	}

	@Test(expected = CarException.class)
	public void invalidLicensePlate2() {
		new Car("AA-XX-aaa", 10, this.rentACar, PRICE_CAR);
	}

	@Test(expected = CarException.class)
	public void duplicatedPlate() {
		new Car(PLATE_CAR, 0, this.rentACar, PRICE_CAR);
		new Car(PLATE_CAR, 0, this.rentACar, PRICE_CAR);
	}

	@Test(expected = CarException.class)
	public void duplicatedPlateDifferentRentACar() {
		new Car(PLATE_CAR, 0, rentACar, PRICE_CAR);
		RentACar rentACar2 = new RentACar(RENT_A_CAR_NAME + "2", "999999999", "BK011199911");
		new Car(PLATE_CAR, 2, rentACar2, PRICE_CAR);
	}

	@Test(expected = CarException.class)
	public void negativeKilometers() {
		new Car(PLATE_CAR, -1, this.rentACar, PRICE_CAR);
	}

	@Test(expected = CarException.class)
	public void noRentACar() {
		new Car(PLATE_CAR, 0, null, PRICE_CAR);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}

}
