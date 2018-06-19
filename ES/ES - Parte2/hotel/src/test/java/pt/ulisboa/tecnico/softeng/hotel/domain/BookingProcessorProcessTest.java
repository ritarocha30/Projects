package pt.ulisboa.tecnico.softeng.hotel.domain;

import mockit.*;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;


import pt.ulisboa.tecnico.softeng.hotel.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.hotel.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;


@RunWith(JMockit.class)
public class BookingProcessorProcessTest {

    private final LocalDate arrival = new LocalDate(2016, 12, 19);
    private final LocalDate departure = new LocalDate(2016, 12, 21);
    private static final String CANCEL_PAYMENT_REFERENCE = "CancelPaymentReference";
    private static final String INVOICE_REFERENCE = "InvoiceReference";
    private static final String PAYMENT_REFERENCE = "PaymentReference";
    private static final int MAX_TIMES = 5;
    private final String clientNif = "123456789";
    private final String clientIban = "IBAN";
    private Hotel hotel;
    private Room room;
    private Booking booking;

    @Before
    public void setUp() {
        this.hotel = new Hotel("XPTO123", "Londres", "123456789");
        this.room = new Room(this.hotel, "01", Room.Type.DOUBLE, 50);
        this.booking = new Booking(this.hotel, this.room, this.arrival, this.departure, this.clientNif, this.clientIban);
    }

    @Test
    public void success(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
        new Expectations() {
            {
                BankInterface.processPayment(this.anyString, this.anyDouble);
                TaxInterface.submitInvoice((InvoiceData) this.any);
            }
        };

        this.hotel.getProcessor().submitBooking(this.booking);
    }

    @Test
    public void taxFailureOnSubmitInvoice(@Mocked final TaxInterface taxInterface,
                                             @Mocked final BankInterface bankInterface) {
        new Expectations() {
            {
                BankInterface.processPayment(this.anyString, this.anyDouble);
                this.result = PAYMENT_REFERENCE;
                TaxInterface.submitInvoice((InvoiceData) this.any);
                this.result = new TaxException();
                this.result = INVOICE_REFERENCE;
            }
        };


        this.hotel.getProcessor().submitBooking(this.booking);
        this.hotel.getProcessor().submitBooking(new Booking(this.hotel, this.room, this.arrival, this.departure, this.clientNif, this.clientIban));

        new FullVerifications(taxInterface) {
            {
                TaxInterface.submitInvoice((InvoiceData) this.any);
                this.times = MAX_TIMES;
            }
        };
    }

    @Test
    public void remoteFailureOnSubmitInvoice(@Mocked final TaxInterface taxInterface,
                                                @Mocked final BankInterface bankInterface) {
        new Expectations() {
            {
                BankInterface.processPayment(this.anyString, this.anyDouble);
                this.result = PAYMENT_REFERENCE;
                TaxInterface.submitInvoice((InvoiceData) this.any);
                this.result = new RemoteAccessException();
                this.result = INVOICE_REFERENCE;
            }
        };

        this.hotel.getProcessor().submitBooking(this.booking);
        this.hotel.getProcessor().submitBooking(new Booking(this.hotel, this.room, this.arrival, this.departure, this.clientNif, this.clientIban));

        new FullVerifications(taxInterface) {
            {
                TaxInterface.submitInvoice((InvoiceData) this.any);
                this.times = MAX_TIMES;
            }
        };
    }

    @Test
    public void bankFailureOnProcessPayment(@Mocked final TaxInterface taxInterface,
                                               @Mocked final BankInterface bankInterface) {
        new Expectations() {
            {
                BankInterface.processPayment(this.anyString, this.anyDouble);
                this.result = new BankException();
                this.result = PAYMENT_REFERENCE;
                TaxInterface.submitInvoice((InvoiceData) this.any);
                this.result = INVOICE_REFERENCE;
            }
        };

        this.hotel.getProcessor().submitBooking(this.booking);
        this.hotel.getProcessor().submitBooking(new Booking(this.hotel, this.room, this.arrival, this.departure, this.clientNif, this.clientIban));

        new FullVerifications(bankInterface) {
            {
                BankInterface.processPayment(this.anyString, this.anyDouble);
                this.times = MAX_TIMES;
            }
        };
    }

    @Test
    public void remoteFailureOnProcessPayment(@Mocked final TaxInterface taxInterface,
                                                 @Mocked final BankInterface bankInterface) {
        new Expectations() {
            {
                BankInterface.processPayment(this.anyString, this.anyDouble);
                this.result = new RemoteAccessException();
                this.result = PAYMENT_REFERENCE;
                TaxInterface.submitInvoice((InvoiceData) this.any);
                this.result = INVOICE_REFERENCE;
            }
        };

        this.hotel.getProcessor().submitBooking(this.booking);
        this.hotel.getProcessor().submitBooking(new Booking(this.hotel, this.room, this.arrival, this.departure, this.clientNif, this.clientIban));

        new FullVerifications(bankInterface) {
            {
                BankInterface.processPayment(this.anyString, this.anyDouble);
                this.times = MAX_TIMES;
            }
        };
    }

    @Test
    public void successCancel(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
        new Expectations() {
            {
                TaxInterface.submitInvoice((InvoiceData) this.any);
                BankInterface.processPayment(this.anyString, this.anyDouble);

                TaxInterface.cancelInvoice(this.anyString);
                BankInterface.cancelPayment(this.anyString);
            }
        };

        this.hotel.getProcessor().submitBooking(this.booking);
        this.booking.cancel();
    }

    @Test
    public void bankExceptionOnCancelPayment(@Mocked final TaxInterface taxInterface,
                                                @Mocked final BankInterface bankInterface) {
        new Expectations() {
            {
                TaxInterface.submitInvoice((InvoiceData) this.any);
                BankInterface.processPayment(this.anyString, this.anyDouble);

                BankInterface.cancelPayment(this.anyString);
                this.result = new BankException();
                this.result = CANCEL_PAYMENT_REFERENCE;
                TaxInterface.cancelInvoice(this.anyString);
            }
        };

        this.hotel.getProcessor().submitBooking(this.booking);
        this.booking.cancel();
        this.hotel.getProcessor().submitBooking(new Booking(this.hotel, this.room, this.arrival, this.departure, this.clientNif, this.clientIban));

        new FullVerifications(bankInterface) {
            {
                BankInterface.cancelPayment(this.anyString);
                this.times = MAX_TIMES;
            }
        };
    }

    @Test
    public void remoteExceptionOnCancelPayment(@Mocked final TaxInterface taxInterface,
                                                  @Mocked final BankInterface bankInterface) {
        new Expectations() {
            {
                TaxInterface.submitInvoice((InvoiceData) this.any);
                BankInterface.processPayment(this.anyString, this.anyDouble);

                BankInterface.cancelPayment(this.anyString);
                this.result = new RemoteAccessException();
                this.result = CANCEL_PAYMENT_REFERENCE;
                TaxInterface.cancelInvoice(this.anyString);
            }
        };

        this.hotel.getProcessor().submitBooking(this.booking);
        this.booking.cancel();
        this.hotel.getProcessor().submitBooking(new Booking(this.hotel, this.room, this.arrival, this.departure, this.clientNif, this.clientIban));

        new FullVerifications(bankInterface) {
            {
                BankInterface.cancelPayment(this.anyString);
                this.times = MAX_TIMES;
            }
        };
    }

    @Test
    public void taxExceptionOnCancelInvoice(@Mocked final TaxInterface taxInterface,
                                               @Mocked final BankInterface bankInterface) {
        new Expectations() {
            {
                BankInterface.processPayment(this.anyString, this.anyDouble);
                TaxInterface.submitInvoice((InvoiceData) this.any);
                BankInterface.cancelPayment(this.anyString);
                this.result = CANCEL_PAYMENT_REFERENCE;
                TaxInterface.cancelInvoice(this.anyString);
                this.result = new Delegate() {
                    int i = 0;

                    public void delegate() {
                        if (this.i < 1) {
                            this.i++;
                            throw new TaxException();
                        }
                    }
                };
            }
        };

        this.hotel.getProcessor().submitBooking(this.booking);
        this.booking.cancel();
        this.hotel.getProcessor().submitBooking(new Booking(this.hotel, this.room, this.arrival, this.departure, this.clientNif, this.clientIban));

        new FullVerifications(taxInterface) {
            {
                TaxInterface.cancelInvoice(this.anyString);
                this.times = MAX_TIMES;
            }
        };
    }

    @Test
    public void remoteExceptionOnCancelInvoice(@Mocked final TaxInterface taxInterface,
                                                  @Mocked final BankInterface bankInterface) {
        new Expectations() {
            {
                BankInterface.processPayment(this.anyString, this.anyDouble);
                TaxInterface.submitInvoice((InvoiceData) this.any);

                BankInterface.cancelPayment(this.anyString);
                this.result = CANCEL_PAYMENT_REFERENCE;
                TaxInterface.cancelInvoice(this.anyString);
                this.result = new Delegate() {
                    int i = 0;

                    public void delegate() {
                        if (this.i < 1) {
                            this.i++;
                            throw new RemoteAccessException();
                        }
                    }
                };

            }
        };

        this.hotel.getProcessor().submitBooking(this.booking);
        this.booking.cancel();
        this.hotel.getProcessor().submitBooking(new Booking(this.hotel, this.room, this.arrival, this.departure, this.clientNif, this.clientIban));

        new FullVerifications(taxInterface) {
            {
                TaxInterface.cancelInvoice(this.anyString);
                this.times = MAX_TIMES;
            }
        };
    }

    @After
    public void tearDown() {
        Hotel.hotels.clear();
    }
}


