package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.car.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

@RunWith(JMockit.class)
public class RentACarRentVehicleMethodTest {	
	private static final String NAME1 = "eartz";
	private static final String NAME2 = "eart";
	private static final String PLATE_CAR1 = "aa-00-11";
	private static final String DRIVING_LICENSE = "br123";
	private static final LocalDate date1 = LocalDate.parse("2018-01-04");
	private static final LocalDate date4 = LocalDate.parse("2018-01-08");
	private static final String NIF1 = "123456789";
	private static final String NIF2 = "987654321";
	private static final String IBAN1 = "BK011234567";
	private static final String IBAN2 = "BK009876500";
	private static final String NIF = "999999999";
	private static final String IBAN = "BK011111111";
	private RentACar rentACar1;
	private RentACar rentACar2;
	private RentACar rentACar3;
	
	
	@Before
	public void setup() {
		rentACar1 = new RentACar(NAME1, NIF1, IBAN1);
		rentACar2 = new RentACar(NAME2, NIF2, IBAN2);
		Vehicle car1 = new Car(PLATE_CAR1, 10, rentACar1, 2500);
	}
	
	@Test
	public void numberOfRentACars() {
		Assert.assertTrue(RentACar.rentACars.size() == 2);
	}
	
	@Test
	public void nameOfRentACars() {
		Assert.assertTrue(RentACar.rentACars.contains(rentACar1));
		Assert.assertTrue(RentACar.rentACars.contains(rentACar2));
	}
	
	@Test(expected = CarException.class)
	public void rentVehicleNoOption() {
		String ref = RentACar.rentVehicle(DRIVING_LICENSE, date4, date1, NIF, IBAN);
	}
	
	@Test
	public void nullRentVehicle() {
		assertFalse(RentACar.rentACars.contains(rentACar3));
	}
	
	
	@Test
	public void rentVehicle(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);

				TaxInterface.submitInvoice((InvoiceData) this.any);
			}
		};
		
		String ref = RentACar.rentVehicle(DRIVING_LICENSE, date1, date4, NIF, IBAN);

		Assert.assertTrue(ref != null);		
	}
	
	
	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
