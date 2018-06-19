package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class InvoiceConstructorTest {
	
	private final LocalDate DATE = new LocalDate (2018,03,30);
	private final LocalDate EXCEPTION_DATE = new LocalDate (1969,01,01);
	
	@Before
	public void setUp(){
		ItemType item = new ItemType("ITEM01", 23);
	}
	
	@Test
	public void sucess() {
		new Invoice (96.4f, DATE, "ITEM01", "253698521", "326591478");
		Assert.assertEquals(1, Invoice._invoices.size(), 0.0);
	}
		
	@Test
	public void get(){
		Invoice invoice = new Invoice (96.4f, DATE, "ITEM01", "253698521", "326591478");
		float result = (float) (96.4 * 0.23);
		
		Assert.assertEquals(96.4f, invoice.getValue(), 97f);
		Assert.assertEquals(DATE, invoice.getDate());
		Assert.assertEquals("ITEM01", invoice.getItemType());
		Assert.assertEquals("253698521", invoice.getSellerNIF());
		Assert.assertEquals("326591478", invoice.getBuyerNIF());
		Assert.assertNotNull(invoice.getReference());
		Assert.assertEquals(result, invoice.getIVA(), 23f);
	}
	
	@Test(expected = TaxException.class)
	public void nullItemType(){
		new Invoice (96.4f, DATE, null, "253698521", "326591478");
	}
	
	@Test(expected = TaxException.class)
	public void nullSellerNif(){
		new Invoice (96.4f, DATE, "ITEM01", null, "326591478");
	}
	
	@Test(expected = TaxException.class)
	public void nullBuyerNif(){
		new Invoice (96.4f, DATE, "ITEM01", "253698521", null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyItemType() {
		new Invoice (96.4f, DATE, "", "253698521", "326591478");
	}
	
	@Test(expected = TaxException.class)
	public void emptySellerNif() {
		new Invoice (96.4f, DATE, "ITEM01", "", "326591478");
	}
	
	@Test(expected = TaxException.class)
	public void emptyBuyerNif() {
		new Invoice (96.4f, DATE, "ITEM01", "253698521", "");
	}
	
	@Test(expected = TaxException.class)
	public void blankItemType() {
		new Invoice (96.4f, DATE, "  ", "253698521", "326591478");
	}
	
	@Test(expected = TaxException.class)
	public void blankSellerNif() {
		new Invoice (96.4f, DATE, "ITEM01", "  ", "326591478");
	}
	
	@Test(expected = TaxException.class)
	public void blankBuyerNif() {
		new Invoice (96.4f, DATE, "ITEM01", "253698521", "  ");
	}
	
	@Test(expected = TaxException.class)
	public void dateBefore1970() {
		new Invoice (96.4f, EXCEPTION_DATE, "ITEM01", "253698521", "326591478");
	}
	
	@Test(expected = TaxException.class)
	public void NifSellerMoreThan9() {
		new Invoice (96.4f, EXCEPTION_DATE, "ITEM01", "25356698521", "326591478");
	}
	
	@Test(expected = TaxException.class)
	public void NifBuyerMoreThan9() {
		new Invoice (96.4f, EXCEPTION_DATE, "ITEM01", "253698521", "326591847478");
	}
	
	@After
	public void tearDown(){
		IRS._itemTypes.clear();
		Invoice._invoices.clear();
	}
}
