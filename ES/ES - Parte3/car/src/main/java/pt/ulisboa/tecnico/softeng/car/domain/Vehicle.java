package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public abstract class Vehicle extends Vehicle_Base {
	private static Logger logger = LoggerFactory.getLogger(Vehicle.class);
	private static String plateFormat = "..-..-..";

	protected Vehicle() {
	}

	public Vehicle(String plate, int kilometers, double price, RentACar rentACar) {
		logger.debug("Vehicle plate: {}", plate);
		checkArguments(plate, kilometers, rentACar);

		setPlate(plate);
		setKilometers(kilometers);
		setPrice(price);
		setRentACar(rentACar);

		rentACar.addVehicle(this);
	}

	public void delete() {
		setRentACar(null);
		for(Renting renting : getRentingSet()) {
			renting.delete();
		}

		deleteDomainObject();
	}

	protected static void checkArguments(String plate, int kilometers, RentACar rentACar) {
		if (plate == null || !plate.matches(plateFormat)) {
			throw new CarException();
		} else {
			for(RentACar rental : FenixFramework.getDomainRoot().getRentACarSet()) {
				for(Vehicle vehicle : rental.getVehicleSet()) {
					if(vehicle.getPlate().equals(plate)) {
						throw new CarException();
					}
				}
			}
		}
		if (kilometers < 0) {
			throw new CarException();
		} else if (rentACar == null) {
			throw new CarException();
		}
	}

	/**
	 * @param kilometers
	 *            the kilometers to set
	 */
	public void addKilometers(int kilometers) {
		if (kilometers < 0) {
			throw new CarException();
		}
		setKilometers(getKilometers() + kilometers);
	}

	public boolean isFree(LocalDate begin, LocalDate end) {
		if (begin == null || end == null) {
			throw new CarException();
		}
		for (Renting renting : getRentingSet()) {
			if (renting.conflict(begin, end)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Add a <code>Renting</code> object to the vehicle. Use with caution --- no
	 * validation is being made.
	 *
	 * @param renting
	 */
	public void addRenting(Renting renting) {
		getRentingSet().add(renting);
	}

	/**
	 * Lookup for a <code>Renting</code> with the given reference.
	 *
	 * @param reference
	 * @return Renting with the given reference
	 */
	public Renting getRenting(String reference) {
		return getRentingSet()
				.stream()
				.filter(renting -> renting.getReference().equals(reference)
						|| renting.isCancelled() && renting.getCancellationReference().equals(reference))
				.findFirst()
				.orElse(null);
	}

	/**
	 * @param drivingLicense
	 * @param begin
	 * @param end
	 * @return
	 */
	public Renting rent(String drivingLicense, LocalDate begin, LocalDate end, String buyerNIF, String buyerIBAN) {
		if (!isFree(begin, end)) {
			throw new CarException();
		}

		Renting renting = new Renting(drivingLicense, begin, end, this, buyerNIF, buyerIBAN);
		this.addRenting(renting);

		this.getRentACar().getProcessor().submitRenting(renting);

		return renting;
	}
}
