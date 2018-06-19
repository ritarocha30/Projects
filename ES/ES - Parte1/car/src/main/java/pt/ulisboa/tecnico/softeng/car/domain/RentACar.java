package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RentACar {
	public static Set<RentACar> rentACars = new HashSet<>();
	public static Set<Vehicle> vehicles = new HashSet<>();
	public Set<Car> cars = new HashSet<>();
	public Set<Motorcycle> motorcycles = new HashSet<>();

	private static int counter = 0;
	private final String name;
	private final String code;
	
	public RentACar(String name){
		checkArguments(name);
		this.name = name;
		this.code = "RaC" + Integer.toString(++counter);
		RentACar.rentACars.add(this);
	}

	private void checkArguments(String name){
		if (name == null || name.trim().isEmpty()){
			throw new CarException("Invalid RentACar name.");
		}
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public Set<Car> getCars() {
		return cars;
	}

	public Set<Motorcycle> getMotorcycles() {
		return motorcycles;
	}

	public Set<Vehicle> getVehicles(){
		Set<Vehicle> vehicles = new HashSet<>();
		vehicles.addAll(cars);
		vehicles.addAll(motorcycles);
		return  vehicles;
	}

	public void addVehicle(Vehicle vehicle){
		for(Vehicle current: vehicles){
			if(current.getPlate().equals(vehicle.getPlate())){
				throw new CarException("Duplicate Plates");
			}
		}
		if(vehicle instanceof Car) {
			cars.add((Car) vehicle);
		} else {
			motorcycles.add((Motorcycle) vehicle);
		}
		vehicles.add(vehicle);
    }

    public ArrayList<Car> getAllAvailableCars(LocalDate begin, LocalDate end){
        ArrayList<Car> availableCars = new ArrayList<>();
        for(Car car:cars){
            if(car.isFree(begin, end)){
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    public ArrayList<Motorcycle> getAllAvailableMotorcycles(LocalDate begin, LocalDate end){
        ArrayList<Motorcycle> availableMotorcycles = new ArrayList<>();
        for(Motorcycle motorcycle : motorcycles){
            if(motorcycle.isFree(begin, end)){
                availableMotorcycles.add(motorcycle);
            }
        }
        return  availableMotorcycles;
    }

    public static RentingData getRentingData (String reference){
        for(RentACar rentACar: RentACar.rentACars) {
            Renting renting;
            for (Vehicle vehicle : rentACar.getVehicles()) {
                renting = vehicle.getRenting(reference);
                if (renting != null) {
                    return new RentingData(renting.getReference(), vehicle.getPlate(), renting.getDrivingLicense(), vehicle.getRentACar().getCode(), renting.getBegin(), renting.getEnd());
                }
            }
        }
        throw new CarException("No such renting");
    }

    public Renting getRenting (String reference) {
        Renting renting;
        if(reference == null){
            throw new CarException("getRenting null reference");
        }
        for(Vehicle vehicle : this.getVehicles()){
            renting = vehicle.getRenting(reference);
            if(renting != null){
                return renting;
            }
        }
        return null;
    }

}
