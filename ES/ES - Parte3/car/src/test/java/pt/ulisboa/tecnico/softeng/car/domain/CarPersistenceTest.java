package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CarPersistenceTest {
    private static final String RENT_A_CAR_NAME = "Eartz";
    private static final String NIF = "NIF";
    private static final String IBAN = "IBAN";
    private static final String PLATE_CAR = "22-33-HZ";
    private static final String DRIVING_LICENSE = "br112233";
    private static final String BUYER_IBAN = "IBAN2";
    private static final String BUYER_NIF = "NIF2";

    private final LocalDate begin = new LocalDate(2017, 04, 01);
    private final LocalDate end = new LocalDate(2017, 04, 15);


    @Test
    public void success() {
        atomicProcess();
        atomicAssert();
    }

    @Atomic(mode = TxMode.WRITE)
    public void atomicProcess() {
        RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF, IBAN);
        Vehicle car = new Car(PLATE_CAR, 10,  20.0, rentACar);

        new Renting(DRIVING_LICENSE, begin, end, car, BUYER_NIF, BUYER_IBAN);
    }

    @Atomic(mode = TxMode.READ)
    public void atomicAssert() {
        assertEquals(1, FenixFramework.getDomainRoot().getRentACarSet().size());

        List<RentACar> rentACars = new ArrayList<>(FenixFramework.getDomainRoot().getRentACarSet());
        RentACar rentACar = rentACars.get(0);

        assertEquals(RENT_A_CAR_NAME, rentACar.getName());
        assertNotNull(rentACar.getCode());
        assertEquals(NIF, rentACar.getNif());
        assertEquals(IBAN, rentACar.getIban());
        assertEquals(1, rentACar.getVehicleSet().size());

        List<Vehicle> vehicles = new ArrayList<>(rentACar.getVehicleSet());
        Vehicle vehicle = vehicles.get(0);

        assertEquals(PLATE_CAR, vehicle.getPlate());
        assertEquals(10, vehicle.getKilometers());
        assertTrue(20.0 == vehicle.getPrice());
        assertEquals(1, vehicle.getRentingSet().size());

        List<Renting> rentings = new ArrayList<>(vehicle.getRentingSet());
        Renting renting = rentings.get(0);

        assertNotNull(renting.getReference());
        assertEquals(DRIVING_LICENSE, renting.getDrivingLicense());
        assertEquals(BUYER_NIF, renting.getBuyerNIF());
        assertEquals(BUYER_IBAN, renting.getBuyerIBAN());
        assertEquals(begin, renting.getBegin());
        assertEquals(end, renting.getEnd());
        assertNull(renting.getCancellationReference());
        assertNull(renting.getCancellationDate());
        assertFalse(renting.getCancelledInvoice());
        assertNull(renting.getCancellationDate());
    }

    @After
    @Atomic(mode = TxMode.WRITE)
    public void tearDown() {
        for (RentACar rentACar : FenixFramework.getDomainRoot().getRentACarSet()) {
            rentACar.delete();
        }
    }
}
