package pt.ulisboa.tecnico.softeng.activity.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

public class ActivityProviderReserveActivityMethodTest {
	private static final int AGE = 40;
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);

	private ActivityProvider provider;
	private Activity activity;

	@Before
	public void setUp() {
		this.provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		this.activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);
		new ActivityOffer(activity, begin, end);
	}

	@Test
	public void success() {
		String reference = ActivityProvider.reserveActivity(this.begin, this.end, AGE);
		assertEquals(reference, ActivityProvider.getActivityReservationData(reference).getReference());
	}

	@Test(expected = ActivityException.class)
	public void nullBeginDate() {
		assertNull(ActivityProvider.reserveActivity(null, this.end, AGE));
	}
	
	@Test(expected = ActivityException.class)
	public void nullEndDate() {
		assertNull(ActivityProvider.reserveActivity(this.begin, null, AGE));
	}
	
	@Test(expected = ActivityException.class)
	public void nullMinAgeMinusOne() {
		assertNull(ActivityProvider.reserveActivity(this.begin, this.end, 17));
	}
	
	@Test(expected = ActivityException.class)
	public void nullMaxAge() {
		assertNull(ActivityProvider.reserveActivity(this.begin, this.end, 100));
	}

	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}
