package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class IRSPersistenceTest {
	private static final String SELLER_NIF = "123456789";
	private static final String SELLER_NAME = "José Vendido";
	private static final String SELLER_ADDRESS = "Somewhere";
	private static final String BUYER_NIF = "987654321";
	private static final String BUYER_NAME = "Manuel Comprado";
	private static final String BUYER_ADDRESS = "Anywhere";
	private static final String FOOD = "FOOD";
	private static final int VALUE = 16;
	private final LocalDate date = new LocalDate(2018, 02, 13);

	private IRS irs;

	@Test
	public void success() {

		atomicProcess();
		atomicAssert();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		this.irs = IRS.getIRS();
		Seller seller = new Seller(irs, SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
		Buyer buyer = new Buyer(irs, BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);

		ItemType itemType = new ItemType(irs, FOOD, VALUE);

		new Invoice(VALUE, date, itemType, seller, buyer);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		IRS irs = IRS.getIRS();

		Assert.assertEquals(2, irs.getTaxPayerSet().size());
		Assert.assertEquals(1, irs.getItemTypeSet().size());

		Assert.assertEquals(SELLER_NAME, irs.getTaxPayerByNIF(SELLER_NIF).getName());
		Assert.assertEquals(SELLER_ADDRESS, irs.getTaxPayerByNIF(SELLER_NIF).getAddress());
		Assert.assertEquals(BUYER_NAME, irs.getTaxPayerByNIF(BUYER_NIF).getName());
		Assert.assertEquals(BUYER_ADDRESS, irs.getTaxPayerByNIF(BUYER_NIF).getAddress());

		assertNotNull(irs.getItemTypeByName(FOOD));

		Assert.assertEquals(1, irs.getTaxPayerByNIF(SELLER_NIF).getInvoiceSet().size());
		Assert.assertEquals(1, irs.getTaxPayerByNIF(BUYER_NIF).getInvoiceSet().size());
	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		IRS.getIRS().delete();
	}

}
