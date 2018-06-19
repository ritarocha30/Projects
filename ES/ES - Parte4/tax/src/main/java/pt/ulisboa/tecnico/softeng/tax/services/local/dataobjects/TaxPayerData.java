package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;

public class TaxPayerData {
	
	private IRS irs;
	private String nif;
	private String name;
	private String address;
	private String type;
	
	public TaxPayerData() {
	}
	
	public TaxPayerData(TaxPayer taxPayer) {
		this.irs = taxPayer.getIrs();
		this.nif = taxPayer.getNif();
		this.name = taxPayer.getName();
		this.address = taxPayer.getAddress();
		this.type = taxPayer.getClass().getSimpleName();
	}
	
	public IRS getIrs() {
		return this.irs;
	}
	
	public void setIrs(IRS irs) {
		this.irs = irs;
	}
	
	public String getNif() {
		return this.nif;
	}
	
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
