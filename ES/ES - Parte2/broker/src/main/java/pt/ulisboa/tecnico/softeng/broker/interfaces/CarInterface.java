package pt.ulisboa.tecnico.softeng.broker.interfaces;

import org.joda.time.LocalDate;
import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;

public class CarInterface {
	
	public static String rentVehicle(String drivingLicense, LocalDate begin, LocalDate end, String buyerNif, String buyerIban) {
		return RentACar.rentVehicle(drivingLicense, begin, end, buyerNif, buyerIban);
	}
	
	public static String cancelRenting(String rentConfirmation) {	
		return RentACar.cancelRenting(rentConfirmation);
	}
	
	public static RentingData getRentingData(String reference) {
		return RentACar.getRentingData(reference);
	}
}

