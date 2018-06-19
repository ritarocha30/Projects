package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.assertEquals;

public class SellerConstructorTest {
	private static final String NIF = "123456789";
	private static final String NAME = "Test Name";
	private static final String ADDRESS= "Test Adress";
	Seller seller;
		
	@Test
	public void success() {
		this.seller = new Seller(NIF, NAME, ADDRESS);
		
		assertEquals(NIF, seller.getNIF());
		assertEquals(9, NIF.length());
		assertEquals(NAME, seller.getName());
		assertEquals(ADDRESS, seller.getAddress());
	}
	
	@Test(expected = TaxException.class)
	public void nullNIF() {
		new Seller(null, NAME, ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void nullName() {
		new Seller(NIF, null, ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void nullAddress() {
		new Seller(NIF, NAME, null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyNIF() {
		new Seller(" ", NAME, ADDRESS);
	} 
	
	@Test(expected = TaxException.class)
	public void emptyName() {
		new Seller(NIF, " ", ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void emptyAddress() {
		new Seller(NIF, NAME, " ");
	}
	
	@Test(expected = TaxException.class)
	public void nifNumberOfDitisLesserThan9() {
		new Seller("12345678", NAME, ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void nifNumberOfDitisGreaterThan9() {
		new Seller("1234567890", NAME, ADDRESS);
	}
}
