package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;

public class IRSData {

	private IRS irs;
	private List<TaxPayerData> taxPayers;
	private List<InvoiceData> invoices;
	private List<ItemTypeData> itemTypes;
	
	
	public IRSData() {
	}
	
	public IRSData(IRS irs) {
		this.irs = IRS.getIRSInstance();
		this.taxPayers = irs.getTaxPayerSet().stream().sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))
				.map(t -> new TaxPayerData(t)).collect(Collectors.toList());
		this.invoices = irs.getInvoiceSet().stream().sorted((i1, i2) -> i1.getReference().compareTo(i2.getReference()))
				.map(i -> new InvoiceData(i)).collect(Collectors.toList());
		this.itemTypes = irs.getItemTypeSet().stream().sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))
				.map(t -> new ItemTypeData(t)).collect(Collectors.toList());
	}
	
	
	public IRS getIRS() {
		return this.irs;
	}
	
	public void setIRS(IRS irs) {
		this.irs = irs;
	}
	
	public List<TaxPayerData> getTaxPayers(){
		return this.taxPayers;
	}
	
	public void setTaxPayers(List<TaxPayerData> taxPayers) {
		this.taxPayers = taxPayers;
	}
	
	public List<InvoiceData> getInvoices(){
		return this.invoices;
	}
	
	public void setInvoices(List<InvoiceData> invoices) {
		this.invoices =  invoices;
	}
	
	public List<ItemTypeData> getItemTypes(){
		return this.itemTypes;
	}
	
	public void setItemTypes(List<ItemTypeData> itemTypes) {
		this.itemTypes =  itemTypes;
	}


}
