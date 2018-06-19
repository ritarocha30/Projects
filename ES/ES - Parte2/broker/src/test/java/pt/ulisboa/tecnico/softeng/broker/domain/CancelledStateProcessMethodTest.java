package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.activity.dataobjects.ActivityReservationData;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.bank.dataobjects.BankOperationData;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

@RunWith(JMockit.class)
public class CancelledStateProcessMethodTest {
	private static final String PAYMENT_CONFIRMATION = "PaymentConfirmation";
	private static final String PAYMENT_CANCELLATION = "PaymentCancellation";
	private static final String ACTIVITY_CONFIRMATION = "ActivityConfirmation";
	private static final String ACTIVITY_CANCELLATION = "ActivityCancellation";
	private static final String ROOM_CONFIRMATION = "RoomConfirmation";
	private static final String ROOM_CANCELLATION = "RoomCancellation";
	private static final String RENT_CONFIRMATION = "RentConfirmation";
	private static final String RENT_CANCELLATION = "RentCancellation";
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Adventure adventure;
	private Adventure adventure2;

	@Injectable
	private AdventureClient adventureClient;

	@Before
	public void setUp() {
		this.adventure = new Adventure(this.adventureClient, 0.1, this.begin, this.end, 300, false);
		this.adventure2 = new Adventure(this.adventureClient, 0.2, this.begin, this.end, 200, true);
		this.adventure.setState(State.CANCELLED);
		this.adventure2.setState(State.CANCELLED);
	}

	@Test
	public void didNotPayed(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface, 
			@Mocked final BankInterface bankInterface) {

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());

		new Verifications() {
			{
				ActivityInterface.getActivityReservationData(this.anyString);
				this.times = 0;

				HotelInterface.getRoomBookingData(this.anyString);
				this.times = 0;
				
				BankInterface.getOperationData(this.anyString);
				this.times = 0;
			}
		};
	}
	
	@Test
	public void didNotPayedWithCar(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface,
			@Mocked final CarInterface carInterface, @Mocked final BankInterface bankInterface) {

		this.adventure2.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure2.getState());

		new Verifications() {
			{
				ActivityInterface.getActivityReservationData(this.anyString);
				this.times = 0;

				HotelInterface.getRoomBookingData(this.anyString);
				this.times = 0;
				
				CarInterface.getRentingData(this.anyString);
				this.times = 0;
				
				BankInterface.getOperationData(this.anyString);
				this.times = 0;
			}
		};
	}

	@Test
	public void cancelledPaymentFirstBankException(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface,
		@Mocked final BankInterface bankInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);
		this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);
		this.adventure.setRoomCancellation(ROOM_CANCELLATION);
		this.adventure.setPaymentConfirmation(PAYMENT_CONFIRMATION);
		this.adventure.setPaymentCancellation(PAYMENT_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);
				
				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
				
				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
				this.result = new BankException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void cancelledPaymentFirstRemoteAccessException(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface,
		@Mocked final BankInterface bankInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);
		this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);
		this.adventure.setRoomCancellation(ROOM_CANCELLATION);
		this.adventure.setPaymentConfirmation(PAYMENT_CONFIRMATION);
		this.adventure.setPaymentCancellation(PAYMENT_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);
				
				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
					
				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void cancelledPaymentSecondBankException(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface,
			@Mocked final BankInterface bankInterface) {
			this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
			this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);
			this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);
			this.adventure.setRoomCancellation(ROOM_CANCELLATION);
			this.adventure.setPaymentConfirmation(PAYMENT_CONFIRMATION);
			this.adventure.setPaymentCancellation(PAYMENT_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);
				
				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
				
				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
				this.result = new BankOperationData();

				BankInterface.getOperationData(PAYMENT_CANCELLATION);
				this.result = new BankException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void cancelledPaymentSecondRemoteAccessException(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface,
			@Mocked final BankInterface bankInterface) {
			this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
			this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);
			this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);
			this.adventure.setRoomCancellation(ROOM_CANCELLATION);
			this.adventure.setPaymentConfirmation(PAYMENT_CONFIRMATION);
			this.adventure.setPaymentCancellation(PAYMENT_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);
				
				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
				
				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
				this.result = new BankOperationData();
				BankInterface.getOperationData(PAYMENT_CANCELLATION);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void cancelledPayment(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface,
			@Mocked final BankInterface bankInterface) {
			this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
			this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);
			this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);
			this.adventure.setRoomCancellation(ROOM_CANCELLATION);
			this.adventure.setPaymentConfirmation(PAYMENT_CONFIRMATION);
			this.adventure.setPaymentCancellation(PAYMENT_CANCELLATION);
			
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);
				
				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);

				BankInterface.getOperationData(PAYMENT_CANCELLATION);
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void cancelledActivity(@Mocked final ActivityInterface activityInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
	
	@Test
	public void cancelledActivityActivityException(@Mocked final ActivityInterface activityInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				this.result = new ActivityReservationData();
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);
				this.result = new ActivityException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
	
	@Test
	public void cancelledActivityRemoteAccessException(@Mocked final ActivityInterface activityInterface) {	
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				this.result = new ActivityReservationData();
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
	

	@Test
	public void cancelledRoom(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);
		this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);
		this.adventure.setRoomCancellation(ROOM_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);

				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
	
	@Test
	public void cancelledRoomHotelException(@Mocked final ActivityInterface activityInterface,
		@Mocked final HotelInterface hotelInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);
		this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);
		this.adventure.setRoomCancellation(ROOM_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);

				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
				this.result = new HotelException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
	
	@Test
	public void cancelledRoomRemoteAccessException(@Mocked final ActivityInterface activityInterface, 
		@Mocked final HotelInterface hotelInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);
		this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);
		this.adventure.setRoomCancellation(ROOM_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);

				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
	
	@Test
	public void cancelledVehicleCarException(@Mocked final ActivityInterface activityInterface,
		@Mocked final HotelInterface hotelInterface, @Mocked final CarInterface carInterface) {
		this.adventure2.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure2.setActivityCancellation(ACTIVITY_CANCELLATION);
		this.adventure2.setRoomConfirmation(ROOM_CONFIRMATION);
		this.adventure2.setRoomCancellation(ROOM_CANCELLATION);
		this.adventure2.setRentConfirmation(RENT_CONFIRMATION);
		this.adventure2.setRentCancellation(RENT_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);
				
				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
				
				CarInterface.getRentingData(RENT_CANCELLATION);
				this.result = new CarException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
	
	@Test
	public void cancelledVehicleRemoteAcessException(@Mocked final ActivityInterface activityInterface,
		@Mocked final HotelInterface hotelInterface, @Mocked final CarInterface carInterface) {
		this.adventure2.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure2.setActivityCancellation(ACTIVITY_CANCELLATION);
		this.adventure2.setRoomConfirmation(ROOM_CONFIRMATION);
		this.adventure2.setRoomCancellation(ROOM_CANCELLATION);
		this.adventure2.setRentConfirmation(RENT_CONFIRMATION);
		this.adventure2.setRentCancellation(RENT_CANCELLATION);

		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);
				
				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
				
				CarInterface.getRentingData(RENT_CANCELLATION);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
	
	@Test
	public void cancelledRentVehicle(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface,
			@Mocked CarInterface carInterface) {
		this.adventure2.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure2.setActivityCancellation(ACTIVITY_CANCELLATION);
		this.adventure2.setRoomConfirmation(ROOM_CONFIRMATION);
		this.adventure2.setRoomCancellation(ROOM_CANCELLATION);
		this.adventure2.setRentConfirmation(RENT_CONFIRMATION);
		this.adventure2.setRentCancellation(RENT_CANCELLATION);
		
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);

				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
				
				CarInterface.getRentingData(RENT_CANCELLATION);
			}
		};

		this.adventure2.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure2.getState());
	}

}