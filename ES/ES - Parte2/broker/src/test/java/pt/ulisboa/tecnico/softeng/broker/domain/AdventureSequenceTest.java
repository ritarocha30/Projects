package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

@RunWith(JMockit.class)
public class AdventureSequenceTest {
	private static final String IBAN = "BK01987654321";
	private static final String NIF = "123456789";
	private static final String NIF2 = "987654321";
	private static final String DRIVING_LICENSE = "B1";
	private static final String ITEM_TYPE = "Things";
	private static final double AMOUNT = 300;
	private static final double MARGIN = 0.1;
	private static final int AGE = 20;
	private static final String PAYMENT_CONFIRMATION = "PaymentConfirmation";
	private static final String PAYMENT_CANCELLATION = "PaymentCancellation";
	private static final String ACTIVITY_CONFIRMATION = "ActivityConfirmation";
	private static final String ACTIVITY_CANCELLATION = "ActivityCancellation";
	private static final String ROOM_CONFIRMATION = "RoomConfirmation";
	private static final String ROOM_CANCELLATION = "RoomCancellation";
	private static final String RENT_CONFIRMATION = "RentConfirmation";
	private static final String RENT_CANCELLATION = "RentCancellation";
	private static final String TAX_CONFIRMATION = "TaxConfirmation";
	private static final String TAX_CANCELLATION = "TaxCancellation";
	private static final LocalDate arrival = new LocalDate(2016, 12, 19);
	private static final LocalDate departure = new LocalDate(2016, 12, 21);
	private static final InvoiceData invoiceData = new InvoiceData(NIF, NIF2, ITEM_TYPE, AMOUNT, arrival);

	@Injectable
	private AdventureClient adventureClient;

	@Test
	public void successSequenceAllStates(@Mocked final BankInterface bankInterface, @Mocked final ActivityInterface activityInterface, 
			@Mocked final CarInterface carInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;
				
				CarInterface.rentVehicle(DRIVING_LICENSE, arrival, departure, NIF, IBAN);
				this.result = RENT_CONFIRMATION;
				
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;
				
				TaxInterface.submitInvoice(invoiceData);
				this.result = TAX_CONFIRMATION;

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);

				HotelInterface.getRoomBookingData(ROOM_CONFIRMATION);
				
				CarInterface.getRentingData(RENT_CONFIRMATION);
				
				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
			}
		};

		Adventure adventure = new Adventure(this.adventureClient, MARGIN, arrival, departure, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}
	
	@Test
	public void successSequenceNoHotel(@Mocked final BankInterface bankInterface, @Mocked final ActivityInterface activityInterface, 
			@Mocked final CarInterface carInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, arrival, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;
				
				CarInterface.rentVehicle(DRIVING_LICENSE, arrival, arrival, NIF, IBAN);
				this.result = RENT_CONFIRMATION;
				
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;
				
				TaxInterface.submitInvoice(invoiceData);
				this.result = TAX_CONFIRMATION;

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				
				CarInterface.getRentingData(RENT_CONFIRMATION);
				
				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
			}
		};

		Adventure adventure = new Adventure(this.adventureClient, MARGIN, arrival, arrival, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}
	
	@Test
	public void successSequenceNoCar(@Mocked final BankInterface bankInterface, @Mocked final ActivityInterface activityInterface, 
			@Mocked final CarInterface carInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;
				
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;
				
				TaxInterface.submitInvoice(invoiceData);
				this.result = TAX_CONFIRMATION;;

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);

				HotelInterface.getRoomBookingData(ROOM_CONFIRMATION);
				
				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
			}
		};

		Adventure adventure = new Adventure(this.adventureClient, MARGIN, arrival, departure, AMOUNT, false);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}
	
	@Test
	public void successSequenceNoCarNoHotel(@Mocked final BankInterface bankInterface, @Mocked final ActivityInterface activityInterface, 
			@Mocked final CarInterface carInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, arrival, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;
				
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;
				
				TaxInterface.submitInvoice(invoiceData);
				this.result = TAX_CONFIRMATION;

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				
				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
			}
		};

		Adventure adventure = new Adventure(this.adventureClient, MARGIN, arrival, arrival, AMOUNT, false);

		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceOne(@Mocked final BankInterface bankInterface, @Mocked final ActivityInterface activityInterface, 
			@Mocked final CarInterface carInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = new ActivityException();
			}
		};

		Adventure adventure = new Adventure(this.adventureClient, MARGIN, arrival, departure, AMOUNT, true);

		try {
			adventure.process();
			Assert.fail();
		}
		catch (ActivityException Ae) {
			Assert.assertNull(adventure.getActivityConfirmation());
			Assert.assertEquals(State.UNDO, adventure.getState());
		}
	}
	
	@Test
	public void unsuccessSequenceTwo(@Mocked final BankInterface bankInterface, @Mocked final ActivityInterface activityInterface, 
			@Mocked final CarInterface carInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;
				
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = new HotelException();
				
				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;
			}
		};

		Adventure adventure = new Adventure(this.adventureClient, MARGIN, arrival, departure, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}
	
	@Test
	public void unsuccessSequenceThree(@Mocked final BankInterface bankInterface, @Mocked final ActivityInterface activityInterface, 
			@Mocked final CarInterface carInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;
				
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;
				
				CarInterface.rentVehicle(DRIVING_LICENSE, arrival, arrival, NIF, IBAN);
				this.result = new CarException();
				
				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;
				
				HotelInterface.cancelBooking(ROOM_CONFIRMATION);
				this.result = ROOM_CANCELLATION;
			}
		};

		Adventure adventure = new Adventure(this.adventureClient, MARGIN, arrival, departure, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}
	
	@Test
	public void unsuccessSequenceFour(@Mocked final BankInterface bankInterface, @Mocked final ActivityInterface activityInterface, 
			@Mocked final CarInterface carInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;
				
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;
				
				CarInterface.rentVehicle(DRIVING_LICENSE, arrival, departure, NIF, IBAN);
				this.result = RENT_CONFIRMATION;
				
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = new BankException();
				
				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;
				
				HotelInterface.cancelBooking(ROOM_CONFIRMATION);
				this.result = ROOM_CANCELLATION;
				
				CarInterface.cancelRenting(ROOM_CONFIRMATION);
				this.result = RENT_CANCELLATION;
			}
		};

		Adventure adventure = new Adventure(this.adventureClient, MARGIN, arrival, departure, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}
	
	@Test
	public void unsuccessSequenceFive(@Mocked final BankInterface bankInterface, @Mocked final ActivityInterface activityInterface, 
			@Mocked final CarInterface carInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;
				
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;
				
				CarInterface.rentVehicle(DRIVING_LICENSE, arrival, departure, NIF, IBAN);
				this.result = RENT_CONFIRMATION;
				
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;
				
				TaxInterface.submitInvoice(invoiceData);
				this.result = new TaxException();
				
				BankInterface.cancelPayment(PAYMENT_CONFIRMATION);
				this.result = PAYMENT_CANCELLATION;
				
				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;
				
				HotelInterface.cancelBooking(ROOM_CONFIRMATION);
				this.result = ROOM_CANCELLATION;
				
				CarInterface.cancelRenting(ROOM_CONFIRMATION);
				this.result = RENT_CANCELLATION;
			}
		};

		Adventure adventure = new Adventure(this.adventureClient, MARGIN, arrival, departure, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceX(@Mocked final BankInterface bankInterface, @Mocked final ActivityInterface activityInterface, 
			@Mocked final CarInterface carInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;
				
				CarInterface.rentVehicle(DRIVING_LICENSE, arrival, departure, NIF, IBAN);
				this.result = RENT_CONFIRMATION;

				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
				this.result = new BankException();
				this.times = ConfirmedState.MAX_BANK_EXCEPTIONS;

				BankInterface.cancelPayment(PAYMENT_CONFIRMATION);
				this.result = PAYMENT_CANCELLATION;

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;

				HotelInterface.cancelBooking(ROOM_CONFIRMATION);
				this.result = ROOM_CANCELLATION;
				
				CarInterface.cancelRenting(ROOM_CONFIRMATION);
				this.result = RENT_CANCELLATION;
			}
		};

		Adventure adventure = new Adventure(this.adventureClient, MARGIN, arrival, departure, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		for (int i = 0; i < ConfirmedState.MAX_BANK_EXCEPTIONS; i++) {
			adventure.process();
		}
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	
}