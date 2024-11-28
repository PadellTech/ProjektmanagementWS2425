package test.business;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import business.kunde.*;

public class KundeModelTest {
    private Kunde kunde;
    private KundeModel kundeModel;
	private int hausnummer;
	private String vorname;
	private String nachname;
	private String telefonnummer;
	private String email;
    
    @BeforeEach
	public void setUp() throws Exception{
        kundeModel = KundeModel.getInstance();

        hausnummer = 20;
		vorname = "Max";
		nachname = "Mustermann";
        telefonnummer = "0987654321";
        email = "Max.Mustermann@test.com";
	}
	
	@AfterEach
	public void tearDown() throws Exception{
		kunde = null;
	}

    @Test
    void checkValidationForValidInputs() {
        kunde = new Kunde(hausnummer, vorname, nachname, telefonnummer, email);
        assertTrue(kundeModel.validateUser(kunde), "Kein Valider Input");
    }

    @Test
    void checkValidationForInvalidHausnummer() {
        hausnummer = 2000;

        kunde = new Kunde(hausnummer, vorname, nachname, telefonnummer, email);

        assertFalse(kundeModel.validateUser(kunde), "Invalide Hausnummer wird nicht erkannt!");
    }

    @Test
    void checkValidationForInvalidName() {
        vorname = "Max";
		nachname = "";

        kunde = new Kunde(hausnummer, vorname, nachname, telefonnummer, email);

        assertFalse(kundeModel.validateUser(kunde), "Invalider Name wird nicht erkannt!");
    }

    @Test
    void checkValidationForInvalidKontaktdaten() {
        telefonnummer = "ABCDEFGHIJKLMNOP";
        email = "Max.test.com";

        kunde = new Kunde(hausnummer, vorname, nachname, telefonnummer, email);

        assertFalse(kundeModel.validateUser(kunde), "Invalide Telefonnummer wird nicht erkannt!");
    }
}