package pt.ulisboa.tecnico.softeng.car.services.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import pt.ulisboa.tecnico.softeng.car.services.remote.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.car.services.remote.exceptions.TaxException;
import pt.ulisboa.tecnico.softeng.car.services.remote.exceptions.RemoteAccessException;

public class TaxInterface {
	
	private static Logger logger = LoggerFactory.getLogger(TaxInterface.class);

	private static String ENDPOINT = "http://localhost:8086";
	
	public static String submitInvoice(InvoiceData invoiceData) {
		logger.info("submitInvoice sellerNIF:{}, buyerNIF:{}, itemType:{}, value:{}, date:{}", 
				invoiceData.getSellerNIF(), invoiceData.getBuyerNIF(), invoiceData.getItemType(), invoiceData.getValue(), invoiceData.getDate());

		RestTemplate restTemplate = new RestTemplate();
		try {
			String result = restTemplate.postForObject(ENDPOINT + 
					"/rest/taxs/submitInvoice?sellerNIF=" + invoiceData.getSellerNIF() + 
					"&buyerNIF=" + invoiceData.getBuyerNIF() + 
					"&itemType=" + invoiceData.getItemType() +
					"&value=" + invoiceData.getValue() +
					"&date=" + invoiceData.getDate() , null, String.class);
			return result;
		} catch (HttpClientErrorException e) {
			throw new TaxException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

	public static void cancelInvoice(String invoiceReference) {
		logger.info("cancelInvoice reference:{}", invoiceReference);

		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.postForObject(ENDPOINT + "/rest/taxs/cancel?reference=" + invoiceReference, null, null);
		} catch (HttpClientErrorException e) {
			throw new TaxException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}
}
