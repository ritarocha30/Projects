package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Invoice {
	
	public static Set<Invoice> _invoices = new HashSet<>();
	private static int _counter = 0;
	private final LocalDate _minDate = new LocalDate(1970, 1, 1);
	
	private float _IVA;
	private float _value;
	private LocalDate _date;
	private String _sellerNIF;
	private String _buyerNIF;
	private String _itemType;
	private String _reference;
	
	public Invoice(float value, LocalDate date, String itemType, String sellerNIF, String buyerNIF) {
		checkArguments(itemType, sellerNIF, buyerNIF, date);
		
		this._reference = "InvoiceReference" + Integer.toString(++Invoice._counter);
		this._IVA = value * (IRS.getOnlyIRS().getItemTypeByName(itemType).getTax()/100f);
		this._value = value;
		this._date = date;
		this._itemType = itemType;
		this._sellerNIF = sellerNIF;
		this._buyerNIF = buyerNIF;
		
		_invoices.add(this);
	}
	
	public void checkArguments(String itemType, String sellerNIF, String buyerNIF, LocalDate date) {
		
		/* Arguments can not be null */
		if (itemType == null || sellerNIF == null || buyerNIF == null) {
			throw new TaxException();
		}
		
		/* Arguments can not be empty */
		if (itemType.trim().length() == 0 || sellerNIF.trim().length() == 0 || buyerNIF.trim().length() == 0) {
			throw new TaxException();
		}
		
		/* NIFs with 9 digits */
		if (sellerNIF.length() != IRS.NIF_SIZE || buyerNIF.length() != IRS.NIF_SIZE) {
			throw new TaxException();
		}
		
		/* Date can not be earlier than 1970 */
		if (date.isBefore(_minDate)) {
			throw new TaxException();
		}
		
	}
	
	public String getSellerNIF() {
		return this._sellerNIF;
	}
	
	public String getBuyerNIF() {
		return this._buyerNIF;
	}
	
	public String getItemType() {
		return this._itemType;
	}
	
	public float getValue() {
		return this._value;
	}
	
	public LocalDate getDate() {
		return this._date;
	}
	
	public String getReference() {
		return this._reference;
	}
	
	public float getIVA() {
		return this._IVA;
	}

}
