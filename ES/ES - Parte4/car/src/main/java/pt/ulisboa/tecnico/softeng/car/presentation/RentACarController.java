package pt.ulisboa.tecnico.softeng.car.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;

@Controller
@RequestMapping(value = "/rentACars")
public class RentACarController {
	private static Logger logger = LoggerFactory.getLogger(RentACarController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String rentACarForm(Model model) {
		logger.info("rentACarForm");
		model.addAttribute("rentACar", new RentACarData());
		model.addAttribute("rentACars", CarInterface.getRentACars());
		return "rentACars";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String rentACarSubmit(Model model, @ModelAttribute RentACarData rentACar) {
		logger.info("rentACarSubmit code:{}, name:{}, nif:{}, iban:{}",
				rentACar.getCode(), rentACar.getName(), rentACar.getNif(), rentACar.getIban());

		try {
			CarInterface.createRentACar(rentACar);
		} catch (CarException ce) {
			model.addAttribute("error", "Error: it was not possible to create the rentACar");
			model.addAttribute("rentACar", rentACar);
			model.addAttribute("rentACars", CarInterface.getRentACars());
			return "rentACars";
		}

		return "redirect:/rentACars";
	}

}
