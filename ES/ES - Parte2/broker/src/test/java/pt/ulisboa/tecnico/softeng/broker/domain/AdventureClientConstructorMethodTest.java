package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class AdventureClientConstructorMethodTest {	
	private static final String IBAN = "BK011234567";
	private static final String NIF = "123456789";
	private static final String DRIVINGLICENSE = "B1";
	private static final int AGE = 20;
	private Broker broker;
	
	@Before
	public void setUp() {
		this.broker = new Broker("BR01", "eXtremeADVENTURE", "999111999", "111999111", "BK01987654321");
	}

	@Test
	public void success() {	
		AdventureClient client = new AdventureClient(this.broker, IBAN, NIF, DRIVINGLICENSE , AGE);
		
		Assert.assertEquals(this.broker, client.getBroker());
		Assert.assertEquals(IBAN, client.getIBAN());
		Assert.assertEquals(NIF, client.getNIF());
		Assert.assertEquals(DRIVINGLICENSE, client.getDrivingLicense());
		Assert.assertEquals(AGE, client.getAge());
	}

	@Test(expected = BrokerException.class)
	public void nullBroker() {
		new AdventureClient(null, IBAN, NIF, DRIVINGLICENSE , AGE);
	}

	@Test(expected = BrokerException.class)
	public void nullIBAN() {
		new AdventureClient(this.broker, null, NIF, DRIVINGLICENSE , AGE);
	}

	@Test(expected = BrokerException.class)
	public void nullNIF() {
		new AdventureClient(this.broker, IBAN, null, DRIVINGLICENSE , AGE);
	}
	
	@Test(expected = BrokerException.class)
	public void nullDrivingLicense() {
		new AdventureClient(this.broker, IBAN, NIF, null , AGE);
	}
	
	@Test(expected = BrokerException.class)
	public void emptyIBAN() {
		new AdventureClient(this.broker, "   ", NIF, DRIVINGLICENSE , AGE);
	}
	
	@Test(expected = BrokerException.class)
	public void emptyNIF() {
		new AdventureClient(this.broker, IBAN, "   ", DRIVINGLICENSE , AGE);
	}
	
	@Test(expected = BrokerException.class)
	public void emptyDrivingLicense() {
		new AdventureClient(this.broker, IBAN, NIF, "   " , AGE);
	}
	
	@Test
	public void successEqual18() {
		AdventureClient clientAge18 = new AdventureClient(this.broker, IBAN, NIF, DRIVINGLICENSE , 18);
		
		Assert.assertEquals(this.broker, clientAge18.getBroker());
		Assert.assertEquals(IBAN, clientAge18.getIBAN());
		Assert.assertEquals(NIF, clientAge18.getNIF());
		Assert.assertEquals(DRIVINGLICENSE, clientAge18.getDrivingLicense());
		Assert.assertEquals(18, clientAge18.getAge());
	}

	@Test(expected = BrokerException.class)
	public void lessThan18Age() {
		new AdventureClient(this.broker, IBAN, NIF, DRIVINGLICENSE , 17);
	}

	@Test
	public void successEqual100() {
		AdventureClient clientAge100 = new AdventureClient(this.broker, IBAN, NIF, DRIVINGLICENSE , 100);

		Assert.assertEquals(this.broker, clientAge100.getBroker());
		Assert.assertEquals(IBAN, clientAge100.getIBAN());
		Assert.assertEquals(NIF, clientAge100.getNIF());
		Assert.assertEquals(DRIVINGLICENSE, clientAge100.getDrivingLicense());
		Assert.assertEquals(100, clientAge100.getAge());
	}

	@Test(expected = BrokerException.class)
	public void overThan100Age() {
		new AdventureClient(this.broker, IBAN, NIF, DRIVINGLICENSE , 101);
	}
	
	@Test(expected = BrokerException.class)
	public void NIFTooLarge() {
		new AdventureClient(this.broker, IBAN, "0123456789", DRIVINGLICENSE , AGE);
	}
	
	@Test(expected = BrokerException.class)
	public void NIFTooShort() {
		new AdventureClient(this.broker, IBAN, "12345678", DRIVINGLICENSE , AGE);
	}
	
	@Test(expected = BrokerException.class)
	public void wrongDrivingLicenseFormat() {
		new AdventureClient(this.broker, IBAN, NIF, "1B" , AGE);
	}

	@After
	public void tearDown() {
		Broker.brokers.clear();
		AdventureClient.adventureClients.clear();
	}

}
