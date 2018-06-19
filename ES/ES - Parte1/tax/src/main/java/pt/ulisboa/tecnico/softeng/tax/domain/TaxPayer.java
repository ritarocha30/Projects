package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public abstract class TaxPayer {
	
	private String _NIF;
	private String _name;
	private String _address;
	
	public TaxPayer(String NIF, String name, String address) {
		checkArguments(NIF, name, address);
		
		this._NIF = NIF;
		this._name = name;
		this._address = address;
		
		IRS._taxPayers.add(this);
	}
	
	private void checkArguments(String NIF, String name, String address){
		if (NIF == null || name == null || address == null || NIF.trim().equals("") || name.trim().equals("") || address.trim().equals("")){
			throw new TaxException();
		}
		
		if (NIF.length() != IRS.NIF_SIZE){
			throw new TaxException();
		}
		
		for (TaxPayer taxpayer : IRS._taxPayers){
			if (taxpayer.getNIF().equals(NIF)){
				throw new TaxException();
			}
		}
	}
	
	public String getNIF(){ 
		return _NIF; 
	}

	public String getName(){ 
		return _name; 
	}

	public String getAddress(){ 
		return _address; 
	}
	
	public Invoice getInvoiceByReference(String ref){
		if (ref == null || ref.trim().length() == 0) {
			throw new TaxException();
		}
		if(this.isSeller()) {
			for (Invoice invoice : Invoice._invoices) {
				if(invoice.getReference().equals(ref)) {
					if(invoice.getSellerNIF().equals(this.getNIF())) {
						return invoice;	
					}
				}
			}
		}
		return null;
	}
	
	public abstract boolean isSeller();
}