package pt.ulisboa.tecnico.softeng.activity.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.FullVerifications;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

@RunWith(JMockit.class)
public class BookingContructorMethodTest {
	private static final int AMOUNT = 30;
	private static final String IBAN = "IBAN";
	private static final String NIF = "123456789";
	private ActivityProvider provider;
	private ActivityOffer offer;

	@Before
	public void setUp() {
		this.provider = new ActivityProvider("XtremX", "ExtremeAdventure", "NIF", IBAN);
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);

		LocalDate begin = new LocalDate(2016, 12, 19);
		LocalDate end = new LocalDate(2016, 12, 21);
		this.offer = new ActivityOffer(activity, begin, end, AMOUNT);
	}

	@Test
	public void success() {
		Booking booking = new Booking(this.provider, this.offer, NIF, IBAN);

		assertTrue(booking.getReference().startsWith(this.provider.getCode()));
		assertTrue(booking.getReference().length() > ActivityProvider.CODE_SIZE);
		assertEquals(1, this.offer.getNumberOfBookings());
		assertEquals(NIF, booking.getNif());
		assertEquals(IBAN, booking.getIban());
		assertEquals(AMOUNT, booking.getAmount(), 0);
	}

	@Test(expected = ActivityException.class)
	public void nullProvider() {
		new Booking(null, this.offer, NIF, IBAN);

		new FullVerifications() {
		};
	}

	@Test(expected = ActivityException.class)
	public void nullOffer() {
		new Booking(this.provider, null, NIF, IBAN);

		new FullVerifications() {
		};
	}

	@Test(expected = ActivityException.class)
	public void nullNIF() {
		new Booking(null, this.offer, null, IBAN);

		new FullVerifications() {
		};
	}

	@Test(expected = ActivityException.class)
	public void emptyIBAN() {
		new Booking(this.provider, null, NIF, "     ");
	}

	@Test(expected = ActivityException.class)
	public void nullIBAN() {
		new Booking(null, this.offer, NIF, null);

		new FullVerifications() {
		};
	}

	@Test(expected = ActivityException.class)
	public void emptyNIF() {
		new Booking(this.provider, null, "     ", IBAN);
	}

	@Test
	public void bookingEqualCapacity() {
		new Booking(this.provider, this.offer, NIF, IBAN);
		new Booking(this.provider, this.offer, NIF, IBAN);
		new Booking(this.provider, this.offer, NIF, IBAN);
		try {
			new Booking(this.provider, this.offer, NIF, IBAN);
			fail();
		} catch (ActivityException ae) {
			Assert.assertEquals(3, this.offer.getNumberOfBookings());
		}
	}

	@Test
	public void bookingEqualCapacityButHasCancelled() {
		new Booking(this.provider, this.offer, NIF, IBAN);
		new Booking(this.provider, this.offer, NIF, IBAN);
		Booking booking = new Booking(this.provider, this.offer, NIF, IBAN);
		booking.cancel();
		new Booking(this.provider, this.offer, NIF, IBAN);

		Assert.assertEquals(3, this.offer.getNumberOfBookings());
	}

	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}
