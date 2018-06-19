package pt.ulisboa.tecnico.softeng.broker.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class AdventureProcessMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Broker broker;
	private AdventureClient adventureClient;
	private String clientIBAN;
	private String brokerIBAN;
	private String brokerBuyerNIF = "999999999";
	private String brokerSellerNIF = "111111111";
	private String adventureClientNIF = "000000000";

	@Before
	public void setUp() {
		Bank bank = new Bank("Money", "BK01");
		
		Client brokerMoney = new Client(bank, "AventurasDoQuim");
		Account account1 = new Account(bank, brokerMoney);
		this.brokerIBAN = account1.getIBAN();
		account1.deposit(100000);
		
		this.broker = new Broker("BR01", "eXtremeADVENTURE", brokerBuyerNIF, brokerSellerNIF, this.brokerIBAN);
		
		Client adventureClient = new Client(bank, "António");
		Account account2 = new Account(bank, adventureClient);
		this.clientIBAN = account2.getIBAN();
		account2.deposit(100000);
		
		this.adventureClient = new AdventureClient(this.broker, this.clientIBAN, adventureClientNIF, "B1" , 20);
		
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure", "NIF", "IBAN");
		Activity activity = new Activity(provider, "Bush Walking", 18, 80, 10);
		new ActivityOffer(activity, this.begin, this.end, 90);
		new ActivityOffer(activity, this.begin, this.begin, 30);

		Hotel hotel = new Hotel("XPTO123", "Paris", "987654321");
		new Room(hotel, "01", Type.SINGLE, 100);
		
		RentACar rentACar = new RentACar("Eartz", "123456789", "BK123456789");
		new Car("00-00-AZ", 10, rentACar, 15000);
	}

	@Test
	public void success() {
		Adventure adventure = new Adventure(this.adventureClient, 0.1, this.begin, this.end, 300, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		assertEquals(Adventure.State.CONFIRMED, adventure.getState());
		assertNotNull(adventure.getActivityConfirmation());
		assertNotNull(adventure.getRoomConfirmation());
		assertNotNull(adventure.getRentConfirmation());
		assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getTaxConfirmation());
	}

	@Test
	public void successNoHotel() {
		Adventure adventure = new Adventure(this.adventureClient, 0.1, this.begin, this.begin, 300, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		assertEquals(Adventure.State.CONFIRMED, adventure.getState());
		assertNotNull(adventure.getActivityConfirmation());
		assertNull(adventure.getRoomConfirmation());
		assertNotNull(adventure.getRentConfirmation());
		assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getTaxConfirmation());
	}
	
	@Test
	public void successNoCar() {
		Adventure adventure = new Adventure(this.adventureClient, 0.1, this.begin, this.end, 300, false);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		assertEquals(Adventure.State.CONFIRMED, adventure.getState());
		assertNotNull(adventure.getActivityConfirmation());
		assertNotNull(adventure.getRoomConfirmation());
		assertNull(adventure.getRentConfirmation());
		assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getTaxConfirmation());
	}
	
	@Test
	public void successNoHotelNoCar() {
		Adventure adventure = new Adventure(this.adventureClient, 0.1, this.begin, this.begin, 300, false);

		adventure.process();
		adventure.process();
		adventure.process();

		assertEquals(Adventure.State.CONFIRMED, adventure.getState());
		assertNotNull(adventure.getActivityConfirmation());
		assertNull(adventure.getRoomConfirmation());
		assertNull(adventure.getRentConfirmation());
		assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getTaxConfirmation());
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
		Hotel.hotels.clear();
		ActivityProvider.providers.clear();
		Broker.brokers.clear();
		AdventureClient.adventureClients.clear();
	}
}
