package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSGetTaxPayerByNIFMethodTest {
	
	private TaxPayer _buyer;
	private IRS _irs = IRS.getOnlyIRS();

	@Before
	public void setUp() {
		this._buyer = new Buyer("123456789", "buyer", "addressOfBuyer");
	}

	@Test
	public void successforBuyer() {
		TaxPayer result = _irs.getTaxPayerByNIF("123456789");
		Assert.assertEquals(result, this._buyer);
	}
	
	@Test(expected = TaxException.class)
	public void failforSeller() {
		new Seller("123412340", "seller", "addressOfSeller");
		_irs.getTaxPayerByNIF("123412340");
	}

	@Test(expected = TaxException.class)
	public void nullNIF() {
		_irs.getTaxPayerByNIF(null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyNIF() {
		_irs.getTaxPayerByNIF("");
	}
	
	@Test(expected = TaxException.class)
	public void NotNineDigitsNIF() {
		_irs.getTaxPayerByNIF("0123456789");
	}
	
	@Test
	public void taxPayerDoesNotExist() {
		TaxPayer result = _irs.getTaxPayerByNIF("987654321");
		Assert.assertNull(result);
	}

	@After
	public void tearDown() {
		IRS._taxPayers.clear();
	}
	
}
