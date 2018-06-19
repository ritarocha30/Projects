package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Buyer extends TaxPayer {

	public Buyer(String NIF, String name, String address) {
		super(NIF, name, address);
	}
	
	public float taxReturn(String year) {
		if (year == null || year.trim().length() == 0) {
			throw new TaxException();
		}
		
		int yearAux = Integer.parseInt(year);
		
		if (yearAux < 1970) {
			throw new TaxException();
		}
		
		float result = 0;
		for(Invoice invoice : Invoice._invoices) {
			if(invoice.getBuyerNIF().equals(this.getNIF()) && (invoice.getDate().getYear() == yearAux)) {
				result += (invoice.getValue() * invoice.getIVA());
			}
		}
		return (result * 0.05f);
	}
	
	public boolean isSeller() {
		return false;
	}
}