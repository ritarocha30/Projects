package pt.ulisboa.tecnico.softeng.tax.services.local;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.domain.*;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.IRSData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.ItemTypeData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.TaxPayerData;
public class TaxInterface {

	@Atomic(mode = TxMode.WRITE)
	public static IRSData getIRSData() {
		FenixFramework.getDomainRoot().getIrs();
		IRS irs = IRS.getIRSInstance();
		return new IRSData(irs);
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static List<TaxPayerData> getTaxPayers() {
		return FenixFramework.getDomainRoot().getIrs().getTaxPayerSet().stream().map(h -> new TaxPayerData(h))
				.collect(Collectors.toList());
	}
	

	@Atomic(mode = TxMode.WRITE)
	public static void createTaxPayer(TaxPayerData taxPayerData) {
		if (taxPayerData.getType().equals("Buyer")) {
			new Buyer(taxPayerData.getIrs(), taxPayerData.getName(), taxPayerData.getNif(), taxPayerData.getAddress());
		}
		if(taxPayerData.getType().equals("Seller")) {
			new Seller(taxPayerData.getIrs(), taxPayerData.getName(), taxPayerData.getNif(), taxPayerData.getAddress());
		}
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static TaxPayerData getTaxPayerDataByNif(String nif) {
		TaxPayer taxPayer = IRS.getIRSInstance().getTaxPayerByNIF(nif);
		if (taxPayer == null) {
			return null;
		}
		return new TaxPayerData(taxPayer);
	}
	
    @Atomic(mode = TxMode.READ)
    public static ItemType getItemTypeByName(String name){
        return IRS.getIRSInstance().getItemTypeByName(name);
    }

    @Atomic(mode = TxMode.WRITE)
    public static void createItemType(IRSData irsData, ItemTypeData itemTypeData){
        new ItemType(itemTypeData.getIRS(), itemTypeData.getName(), itemTypeData.getTax());
    }

    @Atomic(mode = TxMode.READ)
    public static Invoice getInvoiceByReference(String nif, String reference){
	    TaxPayer taxPayer = IRS.getIRSInstance().getTaxPayerByNIF(nif);
	    return taxPayer.getInvoiceByReference(reference);
    }
    
    @Atomic(mode = TxMode.WRITE)
    public static void createInvoiceData(InvoiceData invoiceData){
        new InvoiceData(invoiceData.getSellerNIF(), invoiceData.getBuyerNIF(), invoiceData.getItemType(), invoiceData.getValue(),
                invoiceData.getDate());
    }
    
    @Atomic(mode = TxMode.WRITE)
	public static String submitInvoice(InvoiceData invoiceData) {
		return IRS.submitInvoice(invoiceData);
	}
    
    @Atomic(mode = TxMode.WRITE)
	public static String cancelInvoice(String reference) {
    	IRS.cancelInvoice(reference);
    	return "Canceled";
	}
	
}