package pt.ulisboa.tecnico.softeng.car.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;

@Controller
@RequestMapping(value = "/rentACars/{code}/vehicles/{plate}/rentings")
public class RentingController {
	private static Logger logger = LoggerFactory.getLogger(RentingController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String rentingForm(Model model, @PathVariable String code, @PathVariable String plate) {
		logger.info("rentingForm rentACarCode:{}, vehiclePlate:{}", code, plate);

		VehicleData vehicleData = CarInterface.getVehicleDataByPlate(code, plate);

		if (vehicleData == null) {
			model.addAttribute("error", "Error: it does not exist a vehicle with plate " + plate + " in rentACar with code " + code);
			model.addAttribute("rentACar", new RentACarData());
			model.addAttribute("rentACars", CarInterface.getRentACars());
			return "rentACars";
		} else {
			model.addAttribute("renting", new RentingData());
			model.addAttribute("vehicle", vehicleData);
			return "rentings";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String rentingSubmit(Model model, @PathVariable String code, @PathVariable String plate, @ModelAttribute RentingData renting) {
		logger.info("rentingSubmit rentACarCode:{}, vehiclePlate:{}, reference:{}, drivingLicense:{}, begin:{}, end:{}, clientNif:{}, clientIban:{}, price:{}",
				code, plate, renting.getReference(), renting.getDrivingLicense(), renting.getBegin(), renting.getEnd(), renting.getClientNif(), renting.getClientIban(), renting.getPrice());

		try {
			CarInterface.createRenting(code, plate, renting);
		} catch (CarException ce) {
			model.addAttribute("error", "Error: it was not possible to rent the vehicle");
			model.addAttribute("renting", renting);
			model.addAttribute("vehicle", CarInterface.getVehicleDataByPlate(code, plate));
			return "rentings";
		}

		return "redirect:/rentACars/" + code + "/vehicles/" + plate + "/rentings";
	}
}
