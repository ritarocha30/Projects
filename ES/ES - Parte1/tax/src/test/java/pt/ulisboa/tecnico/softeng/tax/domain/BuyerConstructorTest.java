package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BuyerConstructorTest {
	private static final String BUYER_NIF = "999999999";
	private static final String BUYER_NAME = "BUYER01";
	private static final String BUYER_ADDRESS = "Rua Zeca Afonso";
	
	
	@Test
	public void success() {
		Buyer buyer = new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
		
		assertTrue(buyer.getNIF().length() == IRS.NIF_SIZE);
		assertEquals(BUYER_NAME, buyer.getName());
		assertEquals(BUYER_ADDRESS, buyer.getAddress());
	}
	
	@Test(expected = TaxException.class)
	public void nullBuyerNIF() {
		new Buyer(null, BUYER_NAME, BUYER_ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void nullBuyerName() {
		new Buyer(BUYER_NIF, null, BUYER_ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void nullBuyerAddress() {
		new Buyer(BUYER_NIF, BUYER_NAME, null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyBuyerNIF() {
		new Buyer("", BUYER_NAME, BUYER_ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void emptyBuyerName() {
		new Buyer(BUYER_NIF,"", BUYER_ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void emptyBuyerAddress() {
		new Buyer(BUYER_NIF, BUYER_NAME,"");
	}
	

	@Test(expected = TaxException.class)
	public void nifNumberOfDitigsGreaterThan9() {
		new Buyer("9999999999", BUYER_NAME, BUYER_ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void nifNumberOfDitigsLessThan9() {
		new Buyer("99999999", BUYER_NAME, BUYER_ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void notUniqueNIF() {
		new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
		new Buyer(BUYER_NIF, "BUYER02", "Rua José Ricardo");
	}

	@After
	public void tearDown() {
		IRS._taxPayers.clear();
	}

}