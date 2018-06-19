package pt.ulisboa.tecnico.softeng.car.domain;

import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import pt.ulisboa.tecnico.softeng.car.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.car.interfaces.TaxInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(JMockit.class)
public class RentACarGetRentingTest extends RollbackTestAbstractClass {
	private static final String NAME = "Eartz";
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String DRIVING_LICENSE = "lx1423";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final LocalDate date3 = LocalDate.parse("2018-01-08");
	private static final LocalDate date4 = LocalDate.parse("2018-01-09");
	private static final String NIF = "NIF";
	private static final String IBAN = "IBAN";
    private static final String IBAN_BUYER = "IBAN";
    private Renting renting;

	@Mocked
	private BankInterface bankInterface;
	@Mocked
	private TaxInterface taxInterface;

	@Override
	public void populate4Test() {
		RentACar rentACar = new RentACar(NAME, NIF, IBAN);
		Vehicle car = new Car(PLATE_CAR, 10, 10, rentACar);
		this.renting = car.rent(DRIVING_LICENSE, date1, date2, NIF, IBAN_BUYER);
		car.rent(DRIVING_LICENSE, date3, date4, NIF, IBAN_BUYER);
	}

	@Test
	public void getRenting() {
		assertEquals(this.renting, RentACar.getRenting(this.renting.getReference()));
	}
	
}
