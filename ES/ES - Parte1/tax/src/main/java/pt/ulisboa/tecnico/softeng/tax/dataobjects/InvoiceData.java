package pt.ulisboa.tecnico.softeng.tax.dataobjects;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;

public class InvoiceData {
	
	private String _sellerNIF;
	private String _buyerNIF;
	private String _itemType;
	private float _value;
	private LocalDate _date;
	private String _reference;
	private float _IVA;

	public InvoiceData() {
	}

	public InvoiceData(Invoice invoice) {
		this._sellerNIF = invoice.getSellerNIF();
		this._buyerNIF = invoice.getBuyerNIF();
		this._itemType = invoice.getItemType();
		this._value = invoice.getValue();
		this._date = invoice.getDate();
		this._reference = invoice.getReference();
		this._IVA = invoice.getIVA();
	}
	
	public String getSellerNIF() {
		return this._sellerNIF;
	}
	
	public void setSellerNIF(String sellerNIF) {
		this._sellerNIF = sellerNIF;
	}
	
	public String getBuyerNIF() {
		return this._buyerNIF;
	}
	
	public void setBuyerNIF(String buyerNIF) {
		this._buyerNIF = buyerNIF;
	}
	
	
	public String getItemType() {
		return this._itemType;
	}
	
	public void setItemType(String itemType) {
		this._itemType = itemType;
	}
	
	
	public float getValue() {
		return this._value;
	}
	
	public void setValue(float value) {
		this._value = value;
	}
	
	public LocalDate getDate() {
		return this._date;
	}
	
	public void setDate(LocalDate date) {
		this._date = date;
	}
		
	public String getReference() {
		return this._reference;
	}
	
	public void setReference(String reference) {
		this._reference = reference;
	}
	
	public float getIVA() {
		return this._IVA;
	}
	
	public void setIVA(float IVA) {
		this._IVA = IVA;
	}
	
}
