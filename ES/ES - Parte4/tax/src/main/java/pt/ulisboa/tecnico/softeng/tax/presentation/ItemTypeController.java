package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.ItemTypeData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.IRSData;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.TaxPayerData;

import java.util.List;

@Controller
@RequestMapping(value = "/itemTypes")
public class ItemTypeController {
    private static Logger logger = LoggerFactory.getLogger(ItemTypeController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String itemTypeForm(Model model) {
        logger.info("itemTypeForm");

        List<ItemTypeData> itemTypes = TaxInterface.getIRSData().getItemTypes();


        model.addAttribute("itemType", new ItemTypeData());
        model.addAttribute("itemTypes", TaxInterface.getIRSData().getItemTypes());
        return "itemTypes";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String itemTypeSubmit(Model model, @ModelAttribute ItemTypeData itemTypeData) {
        logger.info("itemTypeSubmit name:{}, tax:{}", itemTypeData.getName(), itemTypeData.getTax());


        IRSData irsData = new IRSData();

        try {
            TaxInterface.createItemType(irsData, itemTypeData);
        } catch (TaxException te) {
            model.addAttribute("error", "Error: it was not possible to submit this item");
            model.addAttribute("itemType", TaxInterface.getItemTypeByName(itemTypeData.getName()));
            return "itemTypes";
        }

        return "redirect:/itemTypes";
    }
}
