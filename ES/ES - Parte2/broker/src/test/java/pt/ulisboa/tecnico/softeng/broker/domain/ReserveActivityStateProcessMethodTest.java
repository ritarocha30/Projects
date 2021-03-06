package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;

@RunWith(JMockit.class)
public class ReserveActivityStateProcessMethodTest {
	private static final String IBAN = "BK01987654321";
	private static final String NIF = "123456789";
	private static final double AMOUNT = 300;
	private static final double MARGIN = 0.1;
	private static final int AGE = 20;
	private static final String ACTIVITY_CONFIRMATION = "ActivityConfirmation";
	private static final LocalDate begin = new LocalDate(2016, 12, 19);
	private static final LocalDate end = new LocalDate(2016, 12, 21);
	private Adventure adventure;

	@Injectable
	private AdventureClient adventureClient;

	@Before
	public void setUp() {
		this.adventure = new Adventure(this.adventureClient, MARGIN, begin, end, AMOUNT, false);
		this.adventure.setState(State.RESERVE_ACTIVITY);
	}

	@Test
	public void successNoBookRoomNoRentACar(@Mocked final ActivityInterface activityInterface) {
		Adventure sameDayAdventure = new Adventure(this.adventureClient, MARGIN, begin, begin, AMOUNT, false);
		sameDayAdventure.setState(State.RESERVE_ACTIVITY);

		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, begin, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;
			}
		};

		sameDayAdventure.process();

		Assert.assertEquals(State.PROCESS_PAYMENT, sameDayAdventure.getState());
	}
	
	@Test
	public void successNoBookRoomRentACar(@Mocked final ActivityInterface activityInterface) {
		Adventure sameDayAdventure = new Adventure(this.adventureClient, MARGIN, begin, begin, AMOUNT, true);
		sameDayAdventure.setState(State.RESERVE_ACTIVITY);

		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, begin, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;
			}
		};

		sameDayAdventure.process();

		Assert.assertEquals(State.RENT_VEHICLE, sameDayAdventure.getState());
	}

	@Test
	public void successBookRoomNoRentACar(@Mocked final ActivityInterface activityInterface) {		
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.BOOK_ROOM, this.adventure.getState());
	}
	
	@Test
	public void successBookRoomRentACar(@Mocked final ActivityInterface activityInterface) {
		Adventure adventure = new Adventure(this.adventureClient, MARGIN, begin, end, AMOUNT, true);
		adventure.setState(State.RESERVE_ACTIVITY);
		
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.BOOK_ROOM, this.adventure.getState());
	}

	@Test
	public void activityException(@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, AGE, NIF, IBAN);
				this.result = new ActivityException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

	@Test
	public void singleRemoteAccessException(@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, AGE, NIF, IBAN);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.RESERVE_ACTIVITY, this.adventure.getState());
	}

	@Test
	public void maxRemoteAccessException(@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, AGE, NIF, IBAN);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

	@Test
	public void maxMinusOneRemoteAccessException(@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, AGE, NIF, IBAN);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.RESERVE_ACTIVITY, this.adventure.getState());
	}

	@Test
	public void twoRemoteAccessExceptionOneSuccess(@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, AGE, NIF, IBAN);
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 2) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							return ACTIVITY_CONFIRMATION;
						}
					}
				};
				this.times = 3;

			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.BOOK_ROOM, this.adventure.getState());
	}

	@Test
	public void oneRemoteAccessExceptionOneActivityException(@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, AGE, NIF, IBAN);
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 1) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							throw new ActivityException();
						}
					}
				};
				this.times = 2;
			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}
}