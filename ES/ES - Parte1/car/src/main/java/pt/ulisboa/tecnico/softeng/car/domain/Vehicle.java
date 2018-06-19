package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class Vehicle {

    private String plate;
    private int kilometers;
    private RentACar rentACar;
    private Set<Renting> rentings = new HashSet<>();

    public Vehicle(String plate, int kilometers, RentACar rentACar) {
        checkArguments(plate, kilometers, rentACar);
        this.plate = plate;
        this.kilometers = kilometers;
        this.rentACar = rentACar;

        this.rentACar.addVehicle(this);

    }

    public void checkArguments(String plate, int kilometers, RentACar rentACar) {
        if(plate == null || kilometers < 0 || rentACar == null) {
            throw new CarException();
        }
        if(!plate.matches("[A-Z]{2}-[0-9]{2}-[0-9]{2}|[0-9]{2}-[A-Z]{2}-[0-9]{2}|[0-9]{2}-[0-9]{2}-[A-Z]{2}")) {
            throw new CarException();
        }
    }

    public String getPlate() {
        return this.plate;
    }

    public int getKilometers() {
        return this.kilometers;
    }

    public RentACar getRentACar() {
        return this.rentACar;
    }

    public Set<Renting> getRentings() { return this.rentings; }

    public boolean isFree(LocalDate begin, LocalDate end) {
        for(Renting renting : this.rentings) {
            if(renting.conflict(begin, end)) {
                return false;
            }
        }
        return true;
    }

    public Renting rent(String drivingLicense, LocalDate begin, LocalDate end) {
        if(drivingLicense == null || begin == null || end == null) {
            throw new CarException();
        }
        if(!isFree(begin, end)) {
            throw new CarException();
        }
        Renting renting = new Renting(this, drivingLicense, begin, end, this.kilometers);
        this.rentings.add(renting);

        return renting;
    }

    public Renting getRenting(String reference) {
	    for(Renting renting : this.rentings) {
	        if(renting.getReference().equals(reference)) {
                return renting;
            }
        }
        return null;
    }
}
