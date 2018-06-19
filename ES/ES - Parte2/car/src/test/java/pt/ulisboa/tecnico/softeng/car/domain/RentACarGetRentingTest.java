package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RentACarGetRentingTest {
	private static final String NAME1 = "eartz";
	private static final String PLATE_CAR1 = "aa-00-11";
	private static final String DRIVING_LICENSE = "br123";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final LocalDate date3 = LocalDate.parse("2018-01-08");
	private static final LocalDate date4 = LocalDate.parse("2018-01-09");
	private static final String NIF = "123456789";
	private static final String NIF1 = "999999999";
	private static final String NIF2 = "987654321";
	private static final String IBAN = "BK011234567";
	private static final String IBAN1 = "BK011111111";
	private static final String IBAN2 = "BK009876500";
	private static final double PRICE1 = 15000;	
	private Renting renting;

	@Before
	public void setUp() {
		RentACar rentACar1 = new RentACar(NAME1, NIF1, IBAN1);
		Vehicle car1 = new Car(PLATE_CAR1, 10, rentACar1, PRICE1);
		this.renting = car1.rent(DRIVING_LICENSE, date1, date2, NIF, IBAN);
		car1.rent(DRIVING_LICENSE, date3, date4, NIF2, IBAN2);
	}

	@Test
	public void getRenting() {
		assertEquals(this.renting, RentACar.getRenting(this.renting.getReference()));
	}

	public void nonExistent() {
		assertNull(RentACar.getRenting("a"));
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
