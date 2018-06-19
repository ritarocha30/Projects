package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingConflictTest {
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String DRIVING_LICENSE = "br112233";
	private static final LocalDate date0 = LocalDate.parse("2018-01-05");
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final LocalDate date3 = LocalDate.parse("2018-01-08");
	private static final LocalDate date4 = LocalDate.parse("2018-01-09");
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String NIF_BUYER = "999999999";
	private static final String NIF_SELLER = "987654321";
	private static final String IBAN_BUYER = "BK011234567";
	private static final String IBAN_SELLER = "BK011111111";
	private static final double PRICE = 15000;
	private Car car;

	@Before
	public void setUp() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF_SELLER, IBAN_SELLER);
		this.car = new Car(PLATE_CAR, 10, rentACar, PRICE);
	}

	@Test()
	public void retingIsBeforeDates() {
		Renting renting = new Renting(DRIVING_LICENSE, date1, date2, car, NIF_BUYER, IBAN_BUYER);
		assertFalse(renting.conflict(date3, date4));
	}

	@Test()
	public void retingIsBeforeDatesSameDayInterval() {
		Renting renting = new Renting(DRIVING_LICENSE, date1, date2, car, NIF_BUYER, IBAN_BUYER);
		assertFalse(renting.conflict(date3, date3));
	}

	@Test()
	public void rentingEndsOnStartDate() {
		Renting renting = new Renting(DRIVING_LICENSE, date1, date2, car, NIF_BUYER, IBAN_BUYER);
		assertTrue(renting.conflict(date2, date3));
	}

	@Test()
	public void rentingStartsOnEndDate() {
		Renting renting = new Renting(DRIVING_LICENSE, date1, date2, car, NIF_BUYER, IBAN_BUYER);
		assertTrue(renting.conflict(date1, date1));
	}

	@Test()
	public void rentingStartsDuringInterval() {
		Renting renting = new Renting(DRIVING_LICENSE, date1, date2, car, NIF_BUYER, IBAN_BUYER);
		assertTrue(renting.conflict(date0, date3));
	}

	@Test(expected = CarException.class)
	public void endBeforeBegin() {
		Renting renting = new Renting(DRIVING_LICENSE, date1, date2, car, NIF_BUYER, IBAN_BUYER);
		renting.conflict(date2, date1);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
