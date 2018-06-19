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
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

@RunWith(JMockit.class)
public class RentVehicleStateProcessMethodTest {
	private static final LocalDate begin = new LocalDate(2016, 12, 19);
	private static final LocalDate end = new LocalDate(2016, 12, 21);
	private static final String RENT_CONFIRMATION = "RentConfirmation";
	private static final String DRIVING_LICENSE = "br123";
	private static final double MARGIN = 0.3;
	private static final double AMOUNT = 300;
	private static final String IBAN = "BK011234567";
	private static final String NIF = "123456789";
	private Adventure adventure;

	@Injectable
	private AdventureClient client;
	
	@Before
	public void setUp() {
		this.adventure = new Adventure(this.client,  MARGIN, begin, end, AMOUNT, false);
		this.adventure.setState(State.RENT_VEHICLE);
	}
	
	
	@Test
	public void successRentVehicle(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, NIF, IBAN);
				this.result = RENT_CONFIRMATION;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.PROCESS_PAYMENT, this.adventure.getState());
	}
	
	
	@Test
	public void carException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, NIF, IBAN);
				this.result = new CarException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}
	
	@Test
	public void maxRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, NIF, IBAN);
				this.result = new RemoteAccessException();
				this.times = RentVehicleState.MAX_REMOTE_ERRORS;
			}
		};

		for (int i = 0; i < RentVehicleState.MAX_REMOTE_ERRORS; i++) {
			this.adventure.process();
		}

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}
	
	@Test
	public void maxMinusOneRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, NIF, IBAN);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		
		Assert.assertEquals(State.RENT_VEHICLE, this.adventure.getState());
	}
	
	@Test
	public void twoRemoteAccessExceptionOneSuccess(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, NIF, IBAN);
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 2) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							return RENT_CONFIRMATION;
						}
					}
				};
				this.times = 3;
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		
		Assert.assertEquals(State.PROCESS_PAYMENT, this.adventure.getState());
	}
	
	@Test
	public void oneRemoteAccessExceptionOneCarException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, NIF, IBAN);
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 1) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							throw new CarException();
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