package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemType {
	
	private int _tax;
	private String _itemType;
	
	public ItemType(String itemType, int tax) {
		checkArguments(itemType, tax);
		
		this._itemType = itemType;
		this._tax = tax;
		
		IRS._itemTypes.add(this);
	}
	
	private void checkArguments(String itemType, int tax) {
		if (itemType == null || tax < 0 || itemType.trim().length() == 0) {
			throw new TaxException();
		}
		
		for (ItemType item : IRS._itemTypes){
			if (item.getName().equals(itemType)){
				throw new TaxException();
			}
		}
	}
	
	
	public String getName() { 
		return this._itemType; 
	}
	
	public int getTax() {
		return this._tax;
	}
}