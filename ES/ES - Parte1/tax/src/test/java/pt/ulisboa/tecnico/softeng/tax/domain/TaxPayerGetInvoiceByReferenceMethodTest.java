package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class TaxPayerGetInvoiceByReferenceMethodTest {
	private final LocalDate date = new LocalDate(2016, 12, 19);
	private TaxPayer seller;
	private Invoice invoice;
	
	@Before
	public void setUp() {
		this.seller = new Seller("111111111", "seller1", "Rua do seller1");
		new ItemType("item1", 23); 
		this.invoice = new Invoice(20000f, date, "item1", "111111111", "222222222");
	}
	
	@Test
	public void success() {
		Invoice resultInvoice = this.seller.getInvoiceByReference(this.invoice.getReference());
		
		Assert.assertTrue(this.seller.isSeller());		
		Assert.assertEquals(20000f, resultInvoice.getValue(), 0.0f);
		Assert.assertEquals(date, resultInvoice.getDate());
		Assert.assertEquals("item1", resultInvoice.getItemType());
		Assert.assertEquals("111111111", resultInvoice.getSellerNIF()); 
		Assert.assertEquals("222222222", resultInvoice.getBuyerNIF());
	}
	
	@Test(expected = TaxException.class)
	public void nullRef() {
		Assert.assertNull(seller.getInvoiceByReference(null));
	}
	
	@Test(expected = TaxException.class)
	public void emptyRef() {
		this.seller.getInvoiceByReference("");
	}
	
	@Test
	public void refDoesNotExit() {
		Assert.assertNull(this.seller.getInvoiceByReference("XPTO"));
	}
	
	@Test 
	public void callerIsNotSeller() {
		TaxPayer buyer = new Buyer("333333333", "buyer1", "Rua do buyer1");
		Assert.assertNull(buyer.getInvoiceByReference(this.invoice.getReference()));
	}
	
	@Test
	public void failSellerWithoutAnyAssociatedReference() {
		TaxPayer newSeller = new Seller("555555555", "seller2", "Rua do seller2");
		Assert.assertNull(newSeller.getInvoiceByReference(this.invoice.getReference()));
	}
	
	@After
	public void tearDown() {
		IRS._taxPayers.clear();
		IRS._itemTypes.clear();
		Invoice._invoices.clear();
	}
}