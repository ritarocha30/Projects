package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Broker {
	private static Logger logger = LoggerFactory.getLogger(Broker.class);

	public static Set<Broker> brokers = new HashSet<>();

	private final String code;
	private final String name;
	private final String buyerNIF;
	private final String sellerNIF;
	private final String IBAN;
	private final Set<Adventure> adventures = new HashSet<>();
	private final Set<BulkRoomBooking> bulkBookings = new HashSet<>();

	public Broker(String code, String name, String buyerNIF, String sellerNIF, String IBAN) {
		checkCode(code);
		this.code = code;

		checkName(name);
		this.name = name;
		
		checkBuyerNIF(buyerNIF);
		this.buyerNIF = buyerNIF;
		
		checkSellerNIF(sellerNIF);
		this.sellerNIF = sellerNIF;
		
		checkIBAN(IBAN);
		this.IBAN = IBAN;

		Broker.brokers.add(this);
	}

	private void checkCode(String code) {
		if (code == null || code.trim().length() == 0) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getCode().equals(code)) {
				throw new BrokerException();
			}
		}
	}

	private void checkName(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new BrokerException();
		}
	}
	
	private void checkBuyerNIF(String buyerNIF) {
		if (buyerNIF == null || buyerNIF.trim().length() == 0 || buyerNIF.length() != 9) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getBuyerNIF().equals(buyerNIF)) {
				throw new BrokerException();
			}
		}
	}
	
	private void checkSellerNIF(String sellerNIF) {
		if (sellerNIF == null || sellerNIF.trim().length() == 0 || sellerNIF.length() != 9) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getSellerNIF().equals(sellerNIF)) {
				throw new BrokerException();
			}
		}
	}
	
	private void checkIBAN(String IBAN) {
		if (IBAN == null || IBAN.trim().length() == 0) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getIBAN().equals(IBAN)) {
				throw new BrokerException();
			}
		}
	}
	
	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}
	
	public String getBuyerNIF(){
		return this.buyerNIF;
	}
	
	public String getSellerNIF(){
		return this.sellerNIF;
	}
	
	public String getIBAN(){
		return this.IBAN;
	}

	public int getNumberOfAdventures() {
		return this.adventures.size();
	}

	public void addAdventure(Adventure adventure) {
		this.adventures.add(adventure);
	}

	public boolean hasAdventure(Adventure adventure) {
		return this.adventures.contains(adventure);
	}

	public void bulkBooking(int number, LocalDate arrival, LocalDate departure, String NIF, String IBAN) {
		BulkRoomBooking bulkBooking = new BulkRoomBooking(number, arrival, departure, NIF, IBAN);
		this.bulkBookings.add(bulkBooking);
		bulkBooking.processBooking();
	}

}
