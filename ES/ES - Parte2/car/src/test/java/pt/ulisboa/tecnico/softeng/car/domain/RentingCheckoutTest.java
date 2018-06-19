package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingCheckoutTest {
	private static final String NAME1 = "eartz";
	private static final String PLATE_CAR1 = "aa-00-11";
	private static final String DRIVING_LICENSE = "br123";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final String NIF = "123456789";
	private static final String NIF1 = "999999999";
	private static final String IBAN = "BK011234567";
	private static final String IBAN1 = "BK011111111";
	private static final double PRICE1 = 15000;	
	private Car car;

	@Before
	public void setUp() {
		RentACar rentACar1 = new RentACar(NAME1, NIF1, IBAN1);
		this.car = new Car(PLATE_CAR1, 10, rentACar1, PRICE1);
	}

	@Test
	public void checkout() {
		Renting renting = car.rent(DRIVING_LICENSE, date1, date2, NIF, IBAN);
		renting.checkout(100);
		assertEquals(110, car.getKilometers());
	}

	@Test(expected = CarException.class)
	public void failCheckout() {
		Renting renting = car.rent(DRIVING_LICENSE, date1, date2, NIF, IBAN);
		renting.checkout(-10);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}