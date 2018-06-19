package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemTypeConstructorTest {
	
	private final String ITEM_TYPE = "ITEM01";
	private final int TAX = 12;
	
	@Test
	public void success() {
		ItemType item = new ItemType(ITEM_TYPE, TAX);

		Assert.assertEquals(ITEM_TYPE, item.getName());
		Assert.assertEquals(TAX, item.getTax());
	}
	
	@Test(expected = TaxException.class)
	public void nullItemType() {
		new ItemType(null, TAX);
	}
	
	@Test(expected = TaxException.class)
	public void emptyItemType() {
		new ItemType("", TAX);
	}
	
	@Test(expected = TaxException.class)
	public void nonUniqueItemType() {
		new ItemType(ITEM_TYPE, 2);
		new ItemType(ITEM_TYPE, 7);
	}
	
	@Test
	public void UniqueItemType() {
		new ItemType(ITEM_TYPE, 2);
		new ItemType("ITEM02", 7);
		Assert.assertEquals(2, IRS._itemTypes.size());
	}
	
	@Test(expected = TaxException.class)
	public void notnegativeTax() {
		new ItemType(ITEM_TYPE, -2);
	}
	
	@After
	public void tearDown() {
		IRS._itemTypes.clear();
	}
}