package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Invoice extends Invoice_Base {

	@Override
	public int getCounter() {
		int counter = super.getCounter() + 1;
		setCounter(counter);
		return counter;
	}

	Invoice(double value, LocalDate date, ItemType itemType, Seller seller, Buyer buyer) {
		checkArguments(value, date, itemType, seller, buyer);

		setReference(Integer.toString(getCounter()));
		setValue(value);
		setDate(date);
		setItemType(itemType);
		setInvoiceSeller(seller);
		setInvoiceBuyer(buyer);
		setIva(value * itemType.getTax() / 100);
		setCanceled(false);

        buyer.addInvoice(this);
        seller.addInvoice(this);

	}

	private void checkArguments(double value, LocalDate date, ItemType itemType, Seller seller, Buyer buyer) {
		if (value <= 0.0f) {
			throw new TaxException();
		}

		if (date == null || date.getYear() < 1970) {
			throw new TaxException();
		}

		if (itemType == null) {
			throw new TaxException();
		}

		if (seller == null) {
			throw new TaxException();
		}

		if (buyer == null) {
			throw new TaxException();
		}
	}

	public void cancel() {
		setCanceled(true);
	}

	public boolean isCancelled() {
		return getCanceled();
	}

	public void delete() {
		for(TaxPayer taxPayer : getTaxPayerSet()){
			removeTaxPayer(taxPayer);
		}
		setInvoiceBuyer(null);
		setInvoiceSeller(null);
		setItemType(null);
		deleteDomainObject();
	}


}
