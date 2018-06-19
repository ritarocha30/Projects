package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class Renting {

	private static int counter = 0;
	private String cancellation;
	private final String reference;
	private final String drivingLicense;
	private final LocalDate begin;
	private final LocalDate end;
	private LocalDate cancellationDate;
	private int kilometers;
	private Vehicle vehicle;


	public Renting(Vehicle vehicle, String drivingLicense, LocalDate begin, LocalDate end, int	kilometers){

		checkArguments(vehicle, drivingLicense, begin, end, kilometers);
		this.vehicle = vehicle;
		this.drivingLicense = drivingLicense;
		this.begin = begin;
		this.end = end;
		this.kilometers = kilometers;

		this.reference = vehicle.getPlate() + Integer.toString(++Renting.counter);
	}

	private void checkArguments(Vehicle vehicle, String drivingLicense, LocalDate begin, LocalDate end, int kilometers){
		if (vehicle == null || drivingLicense == null || begin == null || end == null || kilometers < 0){
			throw new CarException();
		}
		if (!drivingLicense.matches("[A-Z]+[0-9]+")) {
			throw new CarException();
		}
	}

	public String getReference() {
		return reference;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public String getDrivingLicense() {
		return drivingLicense;
	}

	public LocalDate getBegin() {
		return begin;
	}

	public LocalDate getEnd() {
		return end;
	}

	public int getKilometers() {
		return kilometers;
	}

    public LocalDate getCancellationDate() {
        return this.cancellationDate;
    }

    public String getCancellation(){return  this.cancellation;}


    public String cancel() {
        this.cancellation = this.reference + "CANCEL";
        this.cancellationDate = new LocalDate();
        return this.cancellation;
    }

    public boolean isCancelled() {
        return this.cancellation != null;
    }

	public boolean conflict(LocalDate begin, LocalDate end){
            if (isCancelled()){
                return false;
            }

			if (begin.equals(end)) {
				return true;
			}

			if (end.isBefore(begin)) {
				throw new CarException();
			}

			if ((begin.equals(this.begin) || begin.isAfter(this.begin)) && begin.isBefore(this.end)) {
				return true;
			}

			if ((end.equals(this.end) || end.isBefore(this.end))
					&& end.isAfter(this.begin)) {
				return true;
			}

			if ((begin.isBefore(this.begin) && end.isAfter(this.end))) {
				return true;
			}

			return false;
		}

	public int checkout(int kilometers){
		int travel;
		travel = kilometers - this.kilometers;
		if (travel < 0) {
			throw new CarException();
		}
		this.kilometers = kilometers;
		return travel;
	}


}