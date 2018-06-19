package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.car.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.car.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

@RunWith(JMockit.class)
public class RentACarCancelRentingMethodTest {
	
	private static final String NAME1 = "Rentz";
	private static final String NAME2 = "RentCars";
	private static final String PLATE_CAR1 = "aa-04-11";
	private static final String DRIVING_LICENSE = "br143";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date4 = LocalDate.parse("2018-01-09");
	private static final String NIF1 = "123256789";
	private static final String NIF2 = "987652321";
	private static final String IBAN1 = "BK012234567";
	private static final String IBAN2 = "BK002876500";
	private static final String NIF = "199999999";
	private static final String IBAN = "BK011111112";
	private RentACar rentACar1;
	private RentACar rentACar2;
	private Renting rent;
	private Vehicle car1;
	
	
	@Before
	public void setup() {
		rentACar1 = new RentACar(NAME1, NIF1, IBAN1);
		rentACar2 = new RentACar(NAME2, NIF2, IBAN2);
		Vehicle car1 = new Car(PLATE_CAR1, 10, rentACar1, 2500);
		this.rent = car1.rent(DRIVING_LICENSE, date1, date4, NIF, IBAN);
	}
	
	@Test
	public void success(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);

				TaxInterface.submitInvoice((InvoiceData) this.any);
			}
		};

		this.rentACar1.getProcessor().submitRenting(rent);

		String cancel = RentACar.cancelRenting(rent.getReference());

		assertTrue(rent.isCancelled());
		assertEquals(cancel, rent.getCancellation());
	}
	
	@Test(expected = CarException.class)
	public void doesNotExist() {
		this.rentACar1.getProcessor().submitRenting(new Renting(DRIVING_LICENSE, date1, date4, car1, NIF, IBAN));
		
		RentACar.cancelRenting("XPTO");
	}
	
	
	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
