package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.apache.tomcat.jni.Local;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.IRSData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.ItemTypeData;



@Controller
@RequestMapping(value = "/invoices")
public class InvoiceController {

    private static Logger logger = LoggerFactory.getLogger(ItemTypeController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String invoiceForm(Model model) {
        logger.info("invoiceForm");

        IRSData irsData = TaxInterface.getIRSData();

        model.addAttribute("invoice", new InvoiceData());
        model.addAttribute("irs", irsData);
        model.addAttribute("irs", TaxInterface.getIRSData());
        return "invoices";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String invoiceSubmit(Model model, @PathVariable String nif, @ModelAttribute InvoiceData invoiceData) {
        logger.info("invoiceSubmit nif:{}, , sellerNif:{}, buyerNif:{}, itemType:{}, value:{}, date:{}",
                nif, invoiceData.getSellerNIF(), invoiceData.getBuyerNIF(), invoiceData.getItemType(), invoiceData.getValue(), invoiceData.getDate());
        try {
            TaxInterface.createInvoiceData(invoiceData);

        } catch (TaxException te) {
            model.addAttribute("error", "Error: it was not possible to submit this invoice");
            model.addAttribute("invoice", invoiceData);
            model.addAttribute("taxPayer", TaxInterface.getTaxPayerDataByNif(nif));
            return "invoices";
        }

        return "redirect:/invoices";
    }
}