package pt.ulisboa.tecnico.softeng.car.services.local.dataobjects;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.domain.Renting;

public class RentingData {
	private String reference;
	private String plate;
	private String drivingLicense;
	private String rentACarCode;
	private LocalDate begin;
	private LocalDate end;
	private String paymentReference;
	private String invoiceReference;
	private double price;
	private String clientNif;
	private String clientIban;
	
	public RentingData() {
	}

	public RentingData(Renting renting) {
		this.reference = renting.getReference();
		this.drivingLicense = renting.getDrivingLicense();
		this.begin = renting.getBegin();
		this.end = renting.getEnd();
		this.clientNif = renting.getClientNif();
		this.clientIban = renting.getClientIban();
		this.price = renting.getPrice();
		
		this.plate = renting.getVehicle().getPlate();
		this.rentACarCode = renting.getVehicle().getRentACar().getCode();
		
		this.paymentReference = renting.getPaymentReference();
		this.invoiceReference = renting.getInvoiceReference();
	}

	public String getReference() {
		return this.reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getPlate() {
		return this.plate;
	}
	
	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getDrivingLicense() {
		return this.drivingLicense;
	}
	
	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public String getRentACarCode() {
		return this.rentACarCode;
	}
	
	public void setRentACarCode(String rentACarCode) {
		this.rentACarCode = rentACarCode;
	}

	public LocalDate getBegin() {
		return this.begin;
	}
	
	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}

	public LocalDate getEnd() {
		return this.end;
	}
	
	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public String getPaymentReference() {
		return this.paymentReference;
	}
	
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getInvoiceReference() {
		return this.invoiceReference;
	}
	
	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}

	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getClientNif() {
		return this.clientNif;
	}
	
	public void setClientNif(String clientNif) {
		this.clientNif = clientNif;
	}
	
	public String getClientIban() {
		return this.clientIban;
	}
	
	public void setClientIban(String clientIban) {
		this.clientIban = clientIban;
	}

}
