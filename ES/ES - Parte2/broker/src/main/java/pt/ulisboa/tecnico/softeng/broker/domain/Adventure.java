package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Adventure {
	private static Logger logger = LoggerFactory.getLogger(Adventure.class);

	public static enum State {
		PROCESS_PAYMENT, RESERVE_ACTIVITY, BOOK_ROOM, RENT_VEHICLE, TAX_PAYMENT, UNDO, CONFIRMED, CANCELLED
	}
	
	private final String type = "ADVENTURE";
	private final AdventureClient client;
	private final double margin;
	private final boolean rentACar;

	private final LocalDate begin;
	private final LocalDate end;
	private double amount;
	private String paymentConfirmation;
	private String paymentCancellation;
	private String roomConfirmation;
	private String roomCancellation;
	private String activityConfirmation;
	private String activityCancellation;
	private String rentConfirmation;
	private String rentCancellation;
	private String taxConfirmation;
	private String taxCancellation;
	
	private AdventureState state;

	public Adventure(AdventureClient client, double margin, LocalDate begin, LocalDate end, double amount, boolean rentACar) {
		checkArguments(client, margin, begin, end, amount);

		this.client = client;
		this.margin = margin;
		this.rentACar = rentACar;
		this.begin = begin;
		this.end = end;
		this.amount = 0;
		
		client.getBroker().addAdventure(this);

		setState(State.RESERVE_ACTIVITY);
	}

	private void checkArguments(AdventureClient client, double margin, LocalDate begin, LocalDate end, double amount) {
		if (client == null || begin == null || end == null) {
			throw new BrokerException();
		}

		if (end.isBefore(begin)) {
			throw new BrokerException();
		}

		if (amount < 1 || margin < 0 || margin > 1) {
			throw new BrokerException();
		}
	}
	
	public AdventureClient getClient() {
		return this.client;
	}
	
	public Broker getBroker() {
		return this.client.getBroker();
	}
	
	public String getType() {
		return this.type;
	}

	public LocalDate getBegin() {
		return this.begin;
	}

	public LocalDate getEnd() {
		return this.end;
	}

	public double getAmount() {
		return this.amount;
	}
	
	public double addValue(double value) {
		return this.amount + value;
	}
	
	public double getAmountWithMargin() {
		return this.amount * (1 + this.margin);
	}
	
	public double getMargin() {
		return this.margin;
	}
	
	public boolean getRentACar() {
		return this.rentACar;
	}
	
	public String getPaymentConfirmation() {
		return this.paymentConfirmation;
	}

	public void setPaymentConfirmation(String paymentConfirmation) {
		this.paymentConfirmation = paymentConfirmation;
	}

	public String getPaymentCancellation() {
		return this.paymentCancellation;
	}

	public void setPaymentCancellation(String paymentCancellation) {
		this.paymentCancellation = paymentCancellation;
	}

	public String getActivityConfirmation() {
		return this.activityConfirmation;
	}

	public void setActivityConfirmation(String activityConfirmation) {
		this.activityConfirmation = activityConfirmation;
	}

	public String getActivityCancellation() {
		return this.activityCancellation;
	}

	public void setActivityCancellation(String activityCancellation) {
		this.activityCancellation = activityCancellation;
	}

	public String getRoomConfirmation() {
		return this.roomConfirmation;
	}

	public void setRoomConfirmation(String roomConfirmation) {
		this.roomConfirmation = roomConfirmation;
	}

	public String getRoomCancellation() {
		return this.roomCancellation;
	}

	public void setRoomCancellation(String roomCancellation) {
		this.roomCancellation = roomCancellation;
	}
	
	public String getRentConfirmation() {
		return this.rentConfirmation;
	}

	public void setRentConfirmation(String rentConfirmation) {
		this.rentConfirmation = rentConfirmation;
	}
		
	public String getRentCancellation() {
		return this.rentCancellation;
	}

	public void setRentCancellation(String rentCancellation) {
		this.rentCancellation = rentCancellation;
	}
	
	public String getTaxConfirmation() {
		return this.taxConfirmation;
	}

	public void setTaxConfirmation(String taxConfirmation) {
		this.taxConfirmation = taxConfirmation;
	}
		
	public String getTaxCancellation() {
		return this.taxCancellation;
	}

	public void setTaxCancellation(String taxCancellation) {
		this.taxCancellation = taxCancellation;
	}
	
	public State getState() {
		return this.state.getState();
	}

	public void setState(State state) {
		switch (state) {
		case RESERVE_ACTIVITY:
			this.state = new ReserveActivityState();
			break;
		case BOOK_ROOM:
			this.state = new BookRoomState();
			break;
		case RENT_VEHICLE:
			this.state = new RentVehicleState();
			break;
		case UNDO:
			this.state = new UndoState();
			break;
		case PROCESS_PAYMENT:
			this.state = new ProcessPaymentState();
			break;
		case TAX_PAYMENT:
			this.state = new TaxPaymentState();
			break;
		case CONFIRMED:
			this.state = new ConfirmedState();
			break;
		case CANCELLED:
			this.state = new CancelledState();
			break;
		default:
			new BrokerException();
			break;
		}
	}

	public void process() {
		this.state.process(this);
	}

	public boolean cancelRoom() {
		return getRoomConfirmation() != null && getRoomCancellation() == null;
	}

	public boolean cancelActivity() {
		return getActivityConfirmation() != null && getActivityCancellation() == null;
	}

	public boolean cancelPayment() {
		return getPaymentConfirmation() != null && getPaymentCancellation() == null;
	}
	
	public boolean cancelRenting() {
		return getRentConfirmation() != null && getRentCancellation() == null;
	}

}
