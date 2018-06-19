package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRS {
	
	private static IRS onlyIRS;
	
	public static Set<ItemType> _itemTypes = new HashSet<>();
	public static Set<TaxPayer> _taxPayers = new HashSet<>();
	public final static int NIF_SIZE = 9;
	
	private IRS() {
	}
	
	static {
		onlyIRS = new IRS();
	}
	
	public static IRS getOnlyIRS() {
		return onlyIRS;
	}
	
	public ItemType getItemTypeByName(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new TaxException();
		}
		for (ItemType itemType : IRS._itemTypes) {
			if (itemType.getName().equals(name)) {
				return itemType;
			}
		}
		return null;
	}
	
	public TaxPayer getTaxPayerByNIF(String buyerNIF) {
		if (buyerNIF == null || buyerNIF.trim().length() == 0 || buyerNIF.length() != NIF_SIZE) {
			throw new TaxException();
		}
		for(TaxPayer taxPayer : _taxPayers) {
			if(taxPayer.getNIF().equals(buyerNIF)) {
				if(taxPayer.isSeller()) {
					throw new TaxException();
				}
				return taxPayer;
			}
		}
		return null;		
	}
	
	public void submitInvoice(InvoiceData invoiceData) {
		new Invoice(invoiceData.getValue(), invoiceData.getDate(), invoiceData.getItemType(), invoiceData.getSellerNIF(), invoiceData.getBuyerNIF());
	}
}