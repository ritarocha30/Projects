package pt.ulisboa.tecnico.softeng.car.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public class VehicleData {
	private String plate;
	private String type;
	private String rentACarCode;
	private String rentACarName;
	private int kilometers;
	private double price;
	private List<RentingData> rentings;
	
	public VehicleData() {
	}

	public VehicleData(Vehicle vehicle) {
		this.plate = vehicle.getPlate();
		this.kilometers = vehicle.getKilometers();
		this.price = vehicle.getPrice();
		
		this.type = vehicle.getClass().getSimpleName();
		this.rentACarCode = vehicle.getRentACar().getCode();
		this.rentACarName = vehicle.getRentACar().getName();
		
		this.rentings = vehicle.getRentingSet().stream().sorted((r1, r2) -> r1.getBegin().compareTo(r2.getBegin()))
				.map(r -> new RentingData(r)).collect(Collectors.toList());
	}
	
	public String getPlate() {
		return this.plate;
	}
	
	public void setPlate(String plate) {
		this.plate = plate;
	}
	
	public int getKilometers() {
		return this.kilometers;
	}
	
	public void setKilometers(int kilometers) {
		this.kilometers = kilometers;
	}

	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getRentACarCode() {
		return this.rentACarCode;
	}
	
	public void setRentACarCode(String rentACarCode) {
		this.rentACarCode = rentACarCode;
	}
	
	public String getRentACarName() {
		return this.rentACarName;
	}
	
	public void setRentACarName(String rentACarName) {
		this.rentACarName = rentACarName;
	}
	
	public List<RentingData> getRentings() {
		return this.rentings;
	}

	public void setRentings(List<RentingData> rentings) {
		this.rentings = rentings;
	}

}
