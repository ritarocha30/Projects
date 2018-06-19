package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RentACarConstructorTest extends RollbackTestAbstractClass {
	private static final String NAME = "Eartz";
	private static final String NIF = "NIF";
	private static final String IBAN = "IBAN";

	@Override
    public void populate4Test() {
    }

	@Test
	public void success() {
		RentACar rentACar = new RentACar(NAME, NIF, IBAN);
		assertEquals(NAME, rentACar.getName());
		assertEquals(NIF, rentACar.getNif());
		assertEquals(IBAN, rentACar.getIban());
		assertNotNull(rentACar.getCode());
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

}
