package pt.ulisboa.tecnico.softeng.broker.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;
import pt.ulisboa.tecnico.softeng.broker.services.local.BrokerInterface;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.AdventureData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.BrokerData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.ClientData;

@Controller
@RequestMapping(value = "/brokers/{brokerCode}/clients")
public class ClientController {
    private static Logger logger = LoggerFactory.getLogger(ClientController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String showClients(Model model, @PathVariable String brokerCode) {
        logger.info("showClients code:{}", brokerCode);

        BrokerData brokerData = BrokerInterface.getBrokerDataByCode(brokerCode, BrokerData.CopyDepth.CLIENTS);

        if (brokerData == null) {
            model.addAttribute("error", "Error: it does not exist a broker with the code " + brokerCode);
            model.addAttribute("broker", new BrokerData());
            model.addAttribute("brokers", BrokerInterface.getBrokers());
            return "brokers";
        } else {
            model.addAttribute("client", new ClientData());
            model.addAttribute("broker", brokerData);
            return "clients";
        }
    }


    @RequestMapping(method = RequestMethod.POST)
    public String submitClient(Model model, @PathVariable String brokerCode,
                                  @ModelAttribute ClientData clientData) {
        logger.info("clientSubmit brokerCode:{}, age:{}, driverLicense:{}, iban:{}, nif:{}", brokerCode,
                clientData.getAge(), clientData.getDrivingLicense(),
                clientData.getIban(), clientData.getNif());

        try {
            BrokerInterface.createClient(brokerCode, clientData);
        } catch (BrokerException be) {
            model.addAttribute("error", "Error: it was not possible to create the adventure");
            model.addAttribute("client", clientData);
            model.addAttribute("broker", BrokerInterface.getBrokerDataByCode(brokerCode, BrokerData.CopyDepth.CLIENTS));
            return "clients";
        }

        return "redirect:/brokers/" + brokerCode + "/clients";
    }
}


