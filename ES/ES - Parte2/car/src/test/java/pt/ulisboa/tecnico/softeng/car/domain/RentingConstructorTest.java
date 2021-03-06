package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingConstructorTest {
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String DRIVING_LICENSE = "br112233";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final String NIF_BUYER = "999999999";
	private static final String NIF_SELLER = "987654321";
	private static final String IBAN_BUYER = "BK011234567";
	private static final String IBAN_SELLER = "BK011111111";
	private static final double PRICE = 15000;
	private Car car;

	@Before
	public void setUp() {
		RentACar rentACar1 = new RentACar(RENT_A_CAR_NAME, NIF_SELLER, IBAN_SELLER);
		this.car = new Car(PLATE_CAR, 10, rentACar1, PRICE);
	}

	@Test
	public void success() {
		Renting renting = new Renting(DRIVING_LICENSE, date1, date2, car, NIF_BUYER, IBAN_BUYER);
		assertEquals(DRIVING_LICENSE, renting.getDrivingLicense());
	}

	@Test(expected = CarException.class)
	public void nullDrivingLicense() {
		new Renting(null, date1, date2, car, NIF_BUYER, IBAN_BUYER);
	}

	@Test(expected = CarException.class)
	public void emptyDrivingLicense() {
		new Renting("", date1, date2, car, NIF_BUYER, IBAN_BUYER);
	}

	@Test(expected = CarException.class)
	public void invalidDrivingLicense() {
		new Renting("12", date1, date2, car, NIF_BUYER, IBAN_BUYER);
	}

	@Test(expected = CarException.class)
	public void nullBegin() {
		new Renting(DRIVING_LICENSE, null, date2, car, NIF_BUYER, IBAN_BUYER);
	}

	@Test(expected = CarException.class)
	public void nullEnd() {
		new Renting(DRIVING_LICENSE, date1, null, car, NIF_BUYER, IBAN_BUYER);
	}
	
	@Test(expected = CarException.class)
	public void endBeforeBegin() {
		new Renting(DRIVING_LICENSE, date2, date1, car, NIF_BUYER, IBAN_BUYER);
	}

	@Test(expected = CarException.class)
	public void nullCar() {
		new Renting(DRIVING_LICENSE, date1, date2, null, NIF_BUYER, IBAN_BUYER);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}

}
