package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSGetItemTypeByNameMethodTest {

	private ItemType _itemType;
	private IRS _irs = IRS.getOnlyIRS();

	@Before
	public void setUp() {
		this._itemType = new ItemType("ITEM_TYPE_NAME", 23);
	}

	@Test
	public void success() {
		ItemType result = _irs.getItemTypeByName("ITEM_TYPE_NAME");
		Assert.assertEquals(result, this._itemType);
	}

	@Test(expected = TaxException.class)
	public void nullName() {
		_irs.getItemTypeByName(null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyName() {
		_irs.getItemTypeByName("");
	}
	
	@Test
	public void itemTypeDoesNotExist() {
		ItemType result = _irs.getItemTypeByName("OTHER_NAME");
		Assert.assertNull(result);
	}

	@After
	public void tearDown() {
		IRS._itemTypes.clear();
	}
	
}
