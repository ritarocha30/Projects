package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;


public class Seller extends TaxPayer {

	public Seller(String NIF, String name, String address) {
		super(NIF, name, address);
	}
	
	public float toPay(String year) {
		if (year == null || year.trim().length() == 0) {
			throw new TaxException();
		}
		
		int yearAux = Integer.parseInt(year);
		
		if (yearAux < 1970) {
			throw new TaxException();
		}
		
		float result = 0;
		for(Invoice invoice : Invoice._invoices) {
			if(invoice.getSellerNIF().equals(this.getNIF()) && (invoice.getDate().getYear() == yearAux)) {
				result += (invoice.getValue() * invoice.getIVA());
			}
		}
		return result;
	}
	
	public boolean isSeller() {
		return true;
	}

}