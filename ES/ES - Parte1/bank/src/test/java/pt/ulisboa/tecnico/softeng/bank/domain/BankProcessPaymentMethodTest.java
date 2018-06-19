package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BankProcessPaymentMethodTest {
	private Bank bank;
	private Account account;
	private String iban;

	@Before
	public void setUp() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.iban = this.account.getIBAN();
		this.account.deposit(500);
	}

	@Test
	public void success() {
		Bank.processPayment(this.iban, 100);
		Assert.assertEquals(400, this.account.getBalance());
		
	}
	
	@Test
	public void successTwoBanks() {
		Bank otherBank = new Bank("Money", "BK02");
		Client otherClient = new Client(otherBank, "Manuel");
		Account otherAccount = new Account(otherBank, otherClient);
		String otherIban = otherAccount.getIBAN();
		otherAccount.deposit(1000);

		Bank.processPayment(otherIban, 100);
		Assert.assertEquals(900, otherAccount.getBalance());

		Bank.processPayment(this.iban, 100);
		Assert.assertEquals(400, this.account.getBalance());
	}
	
	@Test (expected = BankException.class)
	public void noBanks(){
		Bank.banks.clear();
		Bank.processPayment(account.getIBAN(), account.getBalance());
	}

	@Test(expected = BankException.class)
	public void nullIBAN() {
		Bank.processPayment(null, 100);
	}

	@Test(expected = BankException.class)
	public void emptyIBAN() {
		Bank.processPayment("", 100);
	}
	
	@Test(expected = BankException.class)
	public void blankSpacesIBAN() {
		Bank.processPayment(" ", 100);
	}

	@Test(expected = BankException.class)
	public void notExistIBAN() {
		Bank.processPayment("otherIBAN", 0);
	}
	
	@Test(expected = BankException.class)
	public void zeroAmount() {
		Bank.processPayment(this.iban, 0);
	}
	
	@Test
	public void oneAmount() {
		Bank.processPayment(this.iban, 1);
		Assert.assertEquals(499, this.account.getBalance());
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
	}

}