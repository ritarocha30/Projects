package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.Assert;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class BuyerTaxReturnMethodTest {
	private final LocalDate Date = new LocalDate (1971,03,30);
	private Buyer buyer;
	private ItemType itemType;
	private Invoice invoice;
	private String YEAR = "1971";
	
	
	@Before
	public void setUp() {
		this.buyer = new Buyer("123456789", "Buyer01", "Rua José Ricardo");
		this.itemType = new ItemType("ITEM17", 23);
		this.invoice = new Invoice(9.4f, Date, "ITEM17", "999999999", "123456789");
	}
	
	@Test
	public void success() {
		
		Assert.assertEquals("123456789", this.invoice.getBuyerNIF());
		Assert.assertEquals(false, this.buyer.isSeller());
		Assert.assertNull(this.buyer.getInvoiceByReference("123456789"));
		Assert.assertEquals(Integer.parseInt(YEAR), this.invoice.getDate().getYear());
		Assert.assertEquals(9.4f, this.invoice.getValue(), 9.4f);
		Assert.assertEquals(this.buyer.taxReturn(YEAR), this.invoice.getIVA(), 2.162f);
	}
	
	@Test
	public void trueFalse() {
		Invoice other = new Invoice(9.4f, Date, "ITEM17", "999999999", "223456789");
		int year = Integer.parseInt("2000");
		
		Assert.assertFalse(other.getBuyerNIF().equals(this.buyer.getNIF()));
		Assert.assertFalse(this.invoice.getDate().getYear()==year);
	}
	
	@Test
	public void NoTaxReturnNifBranch() {
		float result = 0;
		Buyer buyer1 = new Buyer("523456789", "Buyer02", "Rua José");
			
		result += buyer1.taxReturn(this.YEAR);
		Assert.assertEquals(0f, result, 0f);
	}
	
	@Test
	public void NoTaxReturnYearBranch() {
		float result = 0;
		String nextYear = (this.YEAR + 1).toString();
			
		result += this.buyer.taxReturn(nextYear);
		Assert.assertEquals(0f, result, 0f);
	}
	
	@Test(expected = TaxException.class)
	public void nullYear() {
		this.buyer.taxReturn(null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyStringYear() {
		this.buyer.taxReturn("");
	}
	
	@Test(expected = TaxException.class)
	public void blankStringYear() {
		this.buyer.taxReturn("    ");
	}
	
	@Test(expected = TaxException.class)
	public void before1970() {
		this.buyer.taxReturn("1969");
	}
	
	
	@After
	public void tearDown() {
		IRS._taxPayers.clear();
		Invoice._invoices.clear();
		IRS._itemTypes.clear();
	}	
}