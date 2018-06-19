package pt.ulisboa.tecnico.softeng.car.services.local;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.Motorcycle;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;;

public class CarInterface {

	@Atomic(mode = TxMode.READ)
	public static List<RentACarData> getRentACars() {
		return FenixFramework.getDomainRoot().getRentACarSet().stream().map(r -> new RentACarData(r)).collect(Collectors.toList());
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createRentACar(RentACarData rentACarData) {
		new RentACar(rentACarData.getName(), rentACarData.getNif(), rentACarData.getIban());
	}

	@Atomic(mode = TxMode.READ)
	public static RentACarData getRentACarDataByCode(String code) {
		RentACar rentACar = getRentACarByCode(code);
		if (rentACar != null) {
			return new RentACarData(rentACar);
		}
		return null;
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createVehicle(String rentACarCode, VehicleData vehicleData) {
		if(vehicleData.getType().equals("Car")) {
			new Car(vehicleData.getPlate(), vehicleData.getKilometers(), vehicleData.getPrice(), getRentACarByCode(rentACarCode));
		} else {
			new Motorcycle(vehicleData.getPlate(), vehicleData.getKilometers(), vehicleData.getPrice(), getRentACarByCode(rentACarCode));
		}
	}

	@Atomic(mode = TxMode.READ)
	public static VehicleData getVehicleDataByPlate(String code, String plate) {
		Vehicle vehicle = getVehicleByPlate(code, plate);
		if (vehicle != null) {
			return new VehicleData(vehicle);
		}
		return null;
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createRenting(String code, String plate, RentingData renting) {
		Vehicle vehicle = getVehicleByPlate(code, plate);
		if (vehicle != null) {
			new Renting(renting.getDrivingLicense(), renting.getBegin(), renting.getEnd(), vehicle, renting.getClientNif(), renting.getClientIban());
		}
		throw new CarException();
	}

	@Atomic(mode = TxMode.WRITE)
	public static String rentVehicle(Class<? extends Vehicle> vehicleType, String drivingLicense, String buyerNIF, String buyerIBAN, LocalDate begin, LocalDate end) {
		for (RentACar rentACar : FenixFramework.getDomainRoot().getRentACarSet()) {
			if (rentACar != null) {
				return RentACar.rent(vehicleType, drivingLicense, buyerNIF, buyerIBAN, begin, end);
			}
		}
		throw new CarException();
	}

	@Atomic(mode = TxMode.WRITE)
	public static String cancelRenting(String reference) {
		for (RentACar rentACar : FenixFramework.getDomainRoot().getRentACarSet()) {
			if (rentACar != null) {
				return RentACar.cancelRenting(reference);
			}
		}	
		throw new CarException();
	}

	@Atomic(mode = TxMode.READ)
	public static RentingData getRentingData(String reference) {
		for (RentACar rentACar : FenixFramework.getDomainRoot().getRentACarSet()) {
			if (rentACar != null) {
				return RentACar.getRentingData(reference);
			}
		}
		throw new CarException();
	}

	private static RentACar getRentACarByCode(String code) {
		return FenixFramework.getDomainRoot().getRentACarSet().stream().filter(r -> r.getCode().equals(code)).findFirst().orElse(null);
	}

	private static Vehicle getVehicleByPlate(String code, String plate) {
		RentACar rentACar = getRentACarByCode(code);
		if (rentACar != null) {
			return rentACar.getVehicleSet().stream().filter(v -> v.getPlate().equals(plate)).findFirst().orElse(null);
		}
		return null;
	}
}
