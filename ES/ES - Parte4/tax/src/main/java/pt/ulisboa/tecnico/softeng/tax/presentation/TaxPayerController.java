package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.IRSData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.TaxPayerData;

@Controller
@RequestMapping(value = "/taxPayers")
public class TaxPayerController {
	private static Logger logger = LoggerFactory.getLogger(TaxPayerController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String taxPayerForm(Model model) {
		logger.info("taxPayerForm");
		
		IRSData irsData = TaxInterface.getIRSData();
		
		model.addAttribute("taxPayer", new TaxPayerData());
		model.addAttribute("irs", irsData);
		model.addAttribute("irs", TaxInterface.getIRSData());
		return "taxPayers";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String taxPayerSubmit(Model model, @ModelAttribute TaxPayerData taxPayerData) {
		logger.info("taxPayerSubmit name:{}, nif:{}, address:{}, type:{}", taxPayerData.getName(), taxPayerData.getNif(),
				taxPayerData.getAddress(), taxPayerData.getType());

		try {
			TaxInterface.createTaxPayer(taxPayerData);
		} catch (TaxException te) {
			model.addAttribute("error", "Error: it was not possible to create the Tax Payer");
			model.addAttribute("taxPayer", taxPayerData);
			model.addAttribute("irs", TaxInterface.getIRSData());
			return "taxPayers";
		}

		return "redirect:/taxPayers";
	}
}
