package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

public class IRSSubmitInvoiceMethodTest {
	
	private InvoiceData _invoiceData = new InvoiceData();
	
	@Before
	public void setUp() {
		new ItemType("ITEM_TYPE", 23);
		
		this._invoiceData.setValue((float) 100);
		this._invoiceData.setDate(new LocalDate(2018, 3, 16));
		this._invoiceData.setItemType("ITEM_TYPE");
		this._invoiceData.setSellerNIF("SELLERNIF");
		this._invoiceData.setBuyerNIF("BUYER_NIF");
		this._invoiceData.setIVA((float) 100 * (23/100));
		this._invoiceData.setReference("InvoiceReference" + "1");
	}

	@Test
	public void success() {
		IRS.getOnlyIRS().submitInvoice(this._invoiceData);
		Assert.assertEquals(1, Invoice._invoices.size());
	}

	@After
	public void tearDown() {
		Invoice._invoices.clear();
		IRS._itemTypes.clear();
	}
	
}