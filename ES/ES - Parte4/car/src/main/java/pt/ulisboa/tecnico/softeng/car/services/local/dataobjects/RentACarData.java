package pt.ulisboa.tecnico.softeng.car.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.softeng.car.domain.RentACar;

public class RentACarData {
	private String code;
	private String name;
	private String nif;
	private String iban;
	private List<VehicleData> vehicles;
	
	public RentACarData() {
	}

	public RentACarData(RentACar rentACar) {
		this.code = rentACar.getCode();
		this.name = rentACar.getName();
		this.nif = rentACar.getNif();
		this.iban = rentACar.getIban();
		
		this.vehicles = rentACar.getVehicleSet().stream().sorted((v1, v2) -> v1.getPlate().compareTo(v2.getPlate()))
				.map(v -> new VehicleData(v)).collect(Collectors.toList());
	}

	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return this.nif;
	}
	
	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getIban() {
		return this.iban;
	}
	
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public List<VehicleData> getVehicles() {
		return this.vehicles;
	}

	public void setVehicles(List<VehicleData> vehicles) {
		this.vehicles = vehicles;
	}

}
