package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class BuyerToReturnTest {
	private static final String SELLER_NIF = "123456789";
	private static final String BUYER_NIF = "987654321";
	private static final String FOOD = "FOOD";
	private static final int TAX = 10;
	private final LocalDate date = new LocalDate(2018, 02, 13);

	private Seller seller;
	private Buyer buyer;
	private ItemType itemType;

	@Before
	public void setUp() {
		IRS irs = IRS.getIRS();
		this.seller = new Seller(irs, SELLER_NIF, "José Vendido", "Somewhere");
		this.buyer = new Buyer(irs, BUYER_NIF, "Manuel Comprado", "Anywhere");
		this.itemType = new ItemType(irs, FOOD, TAX);
	}

	@Test
	public void success() {
		new Invoice(100, this.date, this.itemType, this.seller, this.buyer);
		new Invoice(100, this.date, this.itemType, this.seller, this.buyer);
		new Invoice(50, this.date, this.itemType, this.seller, this.buyer);

		double value = this.buyer.taxReturn(2018);

		assertEquals(1.25d, value, 0.00d);
	}

	@Test
	public void yearWithoutInvoices() {
		new Invoice(100, this.date, this.itemType, this.seller, this.buyer);
		new Invoice(100, this.date, this.itemType, this.seller, this.buyer);
		new Invoice(50, this.date, this.itemType, this.seller, this.buyer);

		double value = this.buyer.taxReturn(2017);

		assertEquals(0.0d, value, 0.00d);
	}

	@Test
	public void noInvoices() {
		double value = this.buyer.taxReturn(2018);

		assertEquals(0.0d, value, 0.00d);
	}

	@Test(expected = TaxException.class)
	public void before1970() {
		this.buyer.taxReturn(1969);
	}

	public void equal1970() {
		double value = this.buyer.taxReturn(1969);

		assertEquals(0.0d, value, 0.00d);
	}

	@After
	public void tearDown() {
		IRS.getIRS().clearAll();
	}

}
