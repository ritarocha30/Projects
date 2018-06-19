package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;


public class AdventureCancelPaymentMethodTest {

    private static final int AGE = 20;
    private static final int AMOUNT = 300;
    private Broker broker;
    private final LocalDate begin = new LocalDate(2016, 12, 19);
    private final LocalDate end = new LocalDate(2016, 12, 21);

    @Before
    public void setUp() {
        this.broker = new Broker("BR01", "eXtremeADVENTURE");
    }

    @Test
    public void success(){
        Bank bank = new Bank("CGD", "1234");
        Client client = new Client(bank, "John");
        Account account = new Account(bank, client);
        String IBAN = account.getIBAN();
        Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);
        account.deposit(AMOUNT);
        String paymentCode = Bank.processPayment(IBAN, AMOUNT);
        adventure.setPaymentConfirmation(paymentCode);
        Assert.assertTrue(adventure.cancelPayment());
    }

    @Test
    public void paymentCancelled(){
        Bank bank = new Bank("CGD", "1234");
        Client client = new Client(bank, "John");
        Account account = new Account(bank, client);
        String IBAN = account.getIBAN();
        Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);
        account.deposit(AMOUNT);
        String paymentCode = Bank.processPayment(IBAN, AMOUNT);
        adventure.setPaymentConfirmation(paymentCode);
        adventure.setPaymentCancellation(paymentCode);
        Assert.assertFalse(adventure.cancelPayment());
    }

    @Test
    public void paymentNotConfirmed(){
        Bank bank = new Bank("CGD", "1234");
        Client client = new Client(bank, "John");
        Account account = new Account(bank, client);
        String IBAN = account.getIBAN();
        Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);
        Assert.assertFalse(adventure.cancelPayment());
    }

    @Test
    public void paymentCancelation(){
        String IBAN = "BK011234567";
        Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);
        adventure.setPaymentCancellation("canceled");
        Assert.assertEquals("canceled", adventure.getPaymentCancellation());
    }

    @After
    public void tearDown() {
        Broker.brokers.clear();
        Bank.banks.clear();
    }
}
