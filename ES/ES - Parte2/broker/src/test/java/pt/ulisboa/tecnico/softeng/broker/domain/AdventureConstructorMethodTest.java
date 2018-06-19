package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class AdventureConstructorMethodTest {
	private Broker broker;
	private AdventureClient client;
	private static final double MARGIN = 0.1;
	private static final double AMOUNT = 300;
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);

	@Before
	public void setUp() {
		this.broker = new Broker("BR01", "eXtremeADVENTURE", "999111999", "111999111", "BK01987654321");
		this.client = new AdventureClient(this.broker, "BK011234567", "123456789", "B1" , 20);
	}

	@Test
	public void successFalseRentACar() {
		Adventure adventure = new Adventure(this.client, MARGIN, this.begin, this.end, AMOUNT, false);
		
		Assert.assertEquals(this.client, adventure.getClient());
		Assert.assertEquals(MARGIN, adventure.getMargin(), 0.0);
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.end, adventure.getEnd());
		Assert.assertEquals(AMOUNT * (1 + MARGIN), adventure.getAmount(), 331);
		Assert.assertEquals(false, adventure.getRentACar());
		
		Assert.assertTrue(this.broker.hasAdventure(adventure));
		
		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
		Assert.assertNull(adventure.getRentConfirmation());
		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getTaxConfirmation());
	}
	
	@Test
	public void successTrueRentACar() {
		Adventure adventure = new Adventure(this.client, MARGIN, this.begin, this.end, AMOUNT, true);
		
		Assert.assertEquals(this.client, adventure.getClient());
		Assert.assertEquals(MARGIN, adventure.getMargin(), 0.0);
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.end, adventure.getEnd());
		Assert.assertEquals(AMOUNT * (1 + MARGIN), adventure.getAmount(), 331);
		Assert.assertEquals(true, adventure.getRentACar());
		
		Assert.assertTrue(this.broker.hasAdventure(adventure));
		
		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
		Assert.assertNull(adventure.getRentConfirmation());
		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getTaxConfirmation());
	}

	@Test(expected = BrokerException.class)
	public void nullClient() {
		new Adventure(null, MARGIN, this.begin, this.end, AMOUNT, false);
	}

	@Test(expected = BrokerException.class)
	public void nullBegin() {
		new Adventure(this.client, MARGIN, null, this.end, AMOUNT, false);
	}

	@Test(expected = BrokerException.class)
	public void nullEnd() {
		new Adventure(this.client, MARGIN, this.begin, null, AMOUNT, false);
	}
	
	@Test(expected = BrokerException.class)
	public void zeroAmount() {
		new Adventure(this.client, MARGIN, this.begin, this.end, 0, false);
	}

	@Test
	public void success1Amount() {
		Adventure adventure = new Adventure(this.client, MARGIN, this.begin, this.end, 1, false);
		
		Assert.assertEquals(this.client, adventure.getClient());
		Assert.assertEquals(MARGIN, adventure.getMargin(), 0.0);
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.end, adventure.getEnd());
		Assert.assertEquals(1 * (1 + MARGIN), adventure.getAmount(), 2);
		Assert.assertEquals(false, adventure.getRentACar());
		
		Assert.assertTrue(this.broker.hasAdventure(adventure));
		
		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
		Assert.assertNull(adventure.getRentConfirmation());
		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getTaxConfirmation());
	}
	
	@Test(expected = BrokerException.class)
	public void negativeMargin() {
		new Adventure(this.client, -0.1, this.begin, this.end, AMOUNT, false);
	}
	
	@Test
	public void success0Margin() {
		Adventure adventure = new Adventure(this.client, 0, this.begin, this.end, AMOUNT, false);
		
		Assert.assertEquals(this.client, adventure.getClient());
		Assert.assertEquals(0, adventure.getMargin(), 0.0);
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.end, adventure.getEnd());
		Assert.assertEquals(AMOUNT * (1 + 0), adventure.getAmount(), 301);
		Assert.assertEquals(false, adventure.getRentACar());
		
		Assert.assertTrue(this.broker.hasAdventure(adventure));

		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
		Assert.assertNull(adventure.getRentConfirmation());
		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getTaxConfirmation());
	}
	
	@Test
	public void success1Margin() {
		Adventure adventure = new Adventure(this.client, 1, this.begin, this.end, AMOUNT, false);
		
		Assert.assertEquals(this.client, adventure.getClient());
		Assert.assertEquals(1, adventure.getMargin(), 0.0);
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.end, adventure.getEnd());
		Assert.assertEquals(AMOUNT * (1 + 1), adventure.getAmount(), 601);
		Assert.assertEquals(false, adventure.getRentACar());
		
		Assert.assertTrue(this.broker.hasAdventure(adventure));

		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
		Assert.assertNull(adventure.getRentConfirmation());
		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getTaxConfirmation());
	}
	
	@Test(expected = BrokerException.class)
	public void over1Margin() {
		new Adventure(this.client, 1.1, this.begin, this.end, AMOUNT, false);
	}

	@Test
	public void successEqualDates() {
		Adventure adventure = new Adventure(this.client, MARGIN, this.begin, this.begin, AMOUNT, false);
		
		Assert.assertEquals(this.client, adventure.getClient());
		Assert.assertEquals(MARGIN, adventure.getMargin(), 0.0);
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.begin, adventure.getEnd());
		Assert.assertEquals(AMOUNT * (1 + MARGIN), adventure.getAmount(), 331);
		Assert.assertEquals(false, adventure.getRentACar());
		
		Assert.assertTrue(this.broker.hasAdventure(adventure));

		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
		Assert.assertNull(adventure.getRentConfirmation());
		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getTaxConfirmation());
	}

	@Test(expected = BrokerException.class)
	public void inconsistentDates() {
		new Adventure(this.client, MARGIN, this.begin, this.begin.minusDays(1), AMOUNT, false);
	}

	@After
	public void tearDown() {
		Broker.brokers.clear();
		AdventureClient.adventureClients.clear();
	}

}
