package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SellerToPayMethodTest {
	private final LocalDate date = new LocalDate(2016, 12, 19);
	private Seller seller;
	private Invoice invoice;
	private Integer year = date.getYear();
	private String yearS = year.toString();
	
	@Before
	public void setUp() {
		this.seller = new Seller("111111111", "seller1", "Rua do seller1");
		new ItemType("Carro", 23);
		this.invoice = new Invoice(20000f, date, "Carro", "111111111", "444444444");
	}
	
	@Test
	public void success() {
		float result = 0;
		result += (this.invoice.getValue() * this.invoice.getIVA());
		assertEquals(result, this.seller.toPay(this.yearS), 0f);
	}
	
	@Test
	public void sellerHasNothingToPayNIFBranch() {
		float result = 0;
		Seller seller2 = new Seller("222222222", "seller2", "Rua do seller2");
		
		result += seller2.toPay(this.yearS);
		
		assertEquals(0f, result, 0f);
	}
	
	@Test
	public void sellerHasNothingToPayYearBranch() {
		float result = 0;
		String yearSPlus1 = (this.yearS + 1).toString();
		
		result += this.seller.toPay(yearSPlus1);
		
		assertEquals(0f, result, 0f);
	}
	
	@Test(expected = TaxException.class)
	public void nullYear() {
		this.seller.toPay(null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyYear() {
		this.seller.toPay("");
	}

	@Test(expected = TaxException.class)
	public void yearBefore1970() {
		assertNull(this.seller.toPay("1969"));
	}

	@After
	public void tearDown() {
		IRS._itemTypes.clear();
		IRS._taxPayers.clear();
		Invoice._invoices.clear();
	}
}	

