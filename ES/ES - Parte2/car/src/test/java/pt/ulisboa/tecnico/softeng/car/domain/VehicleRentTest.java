package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleRentTest {
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String DRIVING_LICENSE = "lx1423";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-09");
	private static final String NIF_SELLER = "987654321";
	private static final String IBAN_SELLER = "BK011111111";
	private static final String NIF_BUYER = "999999999";
	private static final String IBAN_BUYER = "BK011234567";
	private static final double PRICE_CAR = 15000;
	private Car car;

	@Before
	public void setUp() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF_SELLER, IBAN_SELLER);
		this.car = new Car(PLATE_CAR, 10, rentACar, PRICE_CAR);
	}

	@Test(expected = CarException.class)
	public void doubleRent() {
		car.rent(DRIVING_LICENSE, date1, date2, NIF_BUYER, IBAN_BUYER);
		car.rent(DRIVING_LICENSE, date1, date2, NIF_BUYER, IBAN_BUYER);
	}

	@Test(expected = CarException.class)
	public void beginIsNull() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF_SELLER, IBAN_SELLER);
		Vehicle car = new Car(PLATE_CAR, 10, rentACar, PRICE_CAR);
		car.rent(DRIVING_LICENSE, null, date2, NIF_BUYER, IBAN_BUYER);
	}

	@Test(expected = CarException.class)
	public void endIsNull() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF_SELLER, IBAN_SELLER);
		Vehicle car = new Car(PLATE_CAR, 10, rentACar, PRICE_CAR);
		car.rent(DRIVING_LICENSE, date1, null, NIF_BUYER, IBAN_BUYER);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
