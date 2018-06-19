package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetRentingDataTest {

	private static final String NAME1 = "eartz";
	private static final String PLATE_CAR1 = "aa-00-11";
	private static final String DRIVING_LICENSE = "br123";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final String NIF = "123456789";
	private static final String IBAN = "BK011234567";
	private static final double PRICE1 = 15000;
	private static final String NIF1 = "999999999";
	private static final String IBAN1 = "BK011111111";
	private Car car;
	private Renting renting;

	@Before
	public void setUp() {
		RentACar rentACar1 = new RentACar(NAME1, NIF1, IBAN1);
		this.car = new Car(PLATE_CAR1, 10, rentACar1, PRICE1);
	}

	@Test
	public void success() {
		Renting renting = car.rent(DRIVING_LICENSE, date1, date2, NIF, IBAN);
		RentingData rentingData = RentACar.getRentingData(renting.getReference());
		assertEquals(renting.getReference(), rentingData.getReference());
		assertEquals(DRIVING_LICENSE, rentingData.getDrivingLicense());
		assertEquals(date1, rentingData.getBegin());
		assertEquals(date2, rentingData.getEnd());
		assertEquals(0, PLATE_CAR1.compareToIgnoreCase(rentingData.getPlate()));
		assertEquals(this.car.getRentACar().getCode(), rentingData.getRentACarCode());
		assertEquals(PRICE1, this.car.getPrice(), 15000d);
	}
	
	@Test(expected = CarException.class)
	public void successCancelled() {
		RentACar rentACar = new RentACar("rentz", "123456789", IBAN1);
		this.car = new Car("aa-00-12", 10, rentACar, PRICE1);
		this.renting = new Renting(DRIVING_LICENSE, date1, date2, car, NIF, IBAN);
		rentACar.getProcessor().submitRenting(this.renting);
		this.renting.cancel();
		
		RentingData rentingData = RentACar.getRentingData(renting.getReference());
		
		assertEquals(this.renting.getReference(), rentingData.getReference());
		assertEquals(this.renting.getCancellation(), rentingData.getCancellation());
		assertEquals(DRIVING_LICENSE, rentingData.getDrivingLicense());
		assertEquals(date1, rentingData.getBegin());
		assertEquals(date2, rentingData.getEnd());
		assertEquals(0, "aa-00-12".compareToIgnoreCase(rentingData.getPlate()));
		assertEquals(this.car.getRentACar().getCode(), rentingData.getRentACarCode());
		assertEquals(PRICE1, this.car.getPrice(), 15000d);
		assertEquals(this.renting.getCancellationDate(), rentingData.getCancellationDate());
		
		
	}

	@Test(expected = CarException.class)
	public void getRentingDataFail() {
		RentACar.getRentingData("1");
	}
	
	@Test(expected = CarException.class)
	public void NullReference() {
		RentACar.getRentingData(null);
	}
	
	@Test(expected = CarException.class)
	public void EmptyReference() {
		RentACar.getRentingData("");
	}
	
	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
