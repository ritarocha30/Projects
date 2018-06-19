package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class AdventureClient {
	
	public static Set<AdventureClient> adventureClients = new HashSet<>();
	
	private static String drivingLicenseFormat = "^[a-zA-Z]+\\d+$";
	private static int counter = 0;
	
	private final String _ID;
	private final Broker _broker;
	private final String _IBAN;
	private final String _NIF;
	private final String _drivingLicense;
	private final int _age;
	
	public AdventureClient(Broker broker, String IBAN, String NIF, String drivingLicense, int age) {
		checkArguments(broker, IBAN, NIF, drivingLicense, age);
		
		this._ID = broker.getCode() + Integer.toString(++counter);
		this._broker = broker;
		this._IBAN = IBAN;
		this._NIF = NIF;
		this._drivingLicense = drivingLicense;
		this._age = age;
		
		AdventureClient.adventureClients.add(this);
	}
	
	private void checkArguments(Broker broker, String IBAN, String NIF, String drivingLicense, int age) {
		if(broker == null || IBAN == null || IBAN.trim().length() == 0 || NIF == null || NIF.trim().length() == 0 || NIF.length() != 9) {
			throw new BrokerException();
		}
		
		if (drivingLicense == null || drivingLicense.trim().length() == 0 || !drivingLicense.matches(drivingLicenseFormat)) {
			throw new BrokerException();
		}
		
		if (age < 18 || age > 100) {
			throw new BrokerException();
		}
		
	}
	
	public String getID() {
		return this._ID;
	}

	public Broker getBroker() {
		return this._broker;
	}

	public String getIBAN() {
		return this._IBAN;
	}
	
	public String getNIF() {
		return this._NIF;
	}
	
	public String getDrivingLicense() {
		return this._drivingLicense;
	}
	
	public int getAge() {
		return this._age;
	}

}
