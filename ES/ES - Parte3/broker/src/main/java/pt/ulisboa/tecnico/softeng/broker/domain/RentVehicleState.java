package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentVehicleState extends RentVehicleState_Base {
	public static final int MAX_REMOTE_ERRORS = 5;

	@Override
	public State getValue() {
		return State.RENT_VEHICLE;
	}

	@Override
	public void process() {
		try {
			// For now we will only reserve cars
			String reference = CarInterface.rentCar(Car.class, getAdventure().getClient().getDrivingLicense(),
					getAdventure().getBroker().getNifAsBuyer(), getAdventure().getBroker().getIBAN(),
					getAdventure().getBegin(), getAdventure().getEnd());

			getAdventure().incAmountToPay(CarInterface.getRentingData(reference).getPrice());

			getAdventure().setRentingConfirmation(reference);

		} catch (CarException ce) {
			getAdventure().setState(State.UNDO);
			return;
		} catch (RemoteAccessException rae) {
			incNumOfRemoteErrors();
			if (getNumOfRemoteErrors() == MAX_REMOTE_ERRORS) {
				getAdventure().setState(State.UNDO);
			}
			return;
		}

		getAdventure().setState(State.PROCESS_PAYMENT);
	}

}
