package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

public class IRSCancelInvoiceTest {
    private static final String SELLER_NIF = "123456789";
    private static final String BUYER_NIF = "987654321";
    private static final String FOOD = "FOOD";
    private static final int VALUE = 16;
    private final LocalDate date = new LocalDate(2018, 02, 13);

    private IRS irs;

    @Before
    public void setUp() {
        this.irs = IRS.getIRS();
        new Seller(this.irs, SELLER_NIF, "José Vendido", "Somewhere");
        new Buyer(this.irs, BUYER_NIF, "Manuel Comprado", "Anywhere");
        new ItemType(this.irs, FOOD, VALUE);
    }

    @Test
    public void success() {
        InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, FOOD, VALUE, this.date);
        String invoiceReference = this.irs.submitInvoice(invoiceData);

        Invoice sellerInvoice = this.irs.getTaxPayerByNIF(SELLER_NIF).getInvoiceByReference(invoiceReference);
        Invoice buyerInvoice = this.irs.getTaxPayerByNIF(BUYER_NIF).getInvoiceByReference(invoiceReference);

        assertEquals(Invoice.InvoiceState.ACTIVE, sellerInvoice.getState());
        assertEquals(Invoice.InvoiceState.ACTIVE, buyerInvoice.getState());

        IRS.cancelInvoice(invoiceReference);

        assertEquals(Invoice.InvoiceState.CANCELED, sellerInvoice.getState());
        assertEquals(Invoice.InvoiceState.CANCELED, buyerInvoice.getState());
    }

    @After
    public void tearDown() {
        this.irs.clearAll();
    }
}
