package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarConstructorTest {
	private static final String NAME = "eartz";
	private static final String NIF = "123456789";
	private static final String IBAN = "BK011234567";

	@Test
	public void success() {
		RentACar rentACar = new RentACar(NAME, NIF, IBAN);
		assertEquals(NAME, rentACar.getName());
		assertTrue(NIF.length() == rentACar.getNif().length());
		assertEquals(IBAN, rentACar.getIban());
		assertEquals(1, RentACar.rentACars.size());
	}

	@Test(expected = CarException.class)
	public void nullName() {
		new RentACar(null, NIF, IBAN);
	}

	@Test(expected = CarException.class)
	public void emptyName() {
		new RentACar("", NIF, IBAN);
	}
	
	@Test(expected = CarException.class)
	public void nullNif() {
		new RentACar(NAME, null, IBAN);
	}

	@Test(expected = CarException.class)
	public void emptyNif() {
		new RentACar(NAME, "", IBAN);
	}

	@Test(expected = CarException.class)
	public void nullIban() {
		new RentACar(NAME, NIF, null);
	}
	
	@Test(expected = CarException.class)
	public void emptyIban() {
		new RentACar(NAME, NIF, "");
	}
	
	@Test(expected = CarException.class)
	public void notUniqueNif() {
		new RentACar("XPTO", NIF, IBAN);
		new RentACar(NAME, NIF, IBAN + "2");		
	}

	@Test(expected = CarException.class)
	public void notUniqueName() {
		new RentACar(NAME, NIF, IBAN);
		new RentACar(NAME, NIF + "2", IBAN + "34");
		
	}
	
	@Test(expected = CarException.class)
	public void lessthenNineDigitsNIF() {
		new RentACar(NAME, "12345678", IBAN);
	}
	
	@Test(expected = CarException.class)
	public void morethenNineDigitsNIF() {
		new RentACar(NAME, "3323456789", IBAN);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
	}
}
