package test.business.kunde;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import business.kunde.Kunde;

public class KundeTest {
    private Kunde kunde;
	private int hausnummer;
	private String vorname;
	private String nachname;
	private String telefonnummer;
	private String email;
    
    @BeforeEach
	public void setUp() throws Exception{
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
	void konstruktorTesten() {
        kunde = new Kunde(hausnummer, vorname, nachname, telefonnummer, email);

		assertTrue(kunde.getHausnummer() == hausnummer, "Die Hausnummer stimmt nicht!");
		assertTrue(kunde.getVorname() == vorname, "Der Vorname stimmt nicht!");
		assertTrue(kunde.getNachname() == nachname, "Der Nachname stimmt nicht!");
		assertTrue(kunde.getTelefonnummer() == telefonnummer, "Die Telefonnummer stimmt nicht!");
		assertTrue(kunde.getEmail() == email, "Die Email stimmt nicht!");
	}
	
	@Test
	void setMethodenTesten() {
		kunde = new Kunde(hausnummer, vorname, nachname, telefonnummer, email);

		hausnummer = 5000;
		vorname = "Tim";
		nachname = "Tanne";
        telefonnummer = "1234567890";
        email = "Tim.Tanne@pmorg.com";

		kunde.setHausnummer(hausnummer);
		kunde.setVorname(vorname);
		kunde.setNachname(nachname);
		kunde.setTelefonnummer(telefonnummer);
		kunde.setEmail(email);

		assertTrue(kunde.getHausnummer() == hausnummer, "Die Hausnummer stimmt nicht!");
		assertTrue(kunde.getVorname() == vorname, "Der Vorname stimmt nicht!");
		assertTrue(kunde.getNachname() == nachname, "Der Nachname stimmt nicht!");
		assertTrue(kunde.getTelefonnummer() == telefonnummer, "Die Telefonnummer stimmt nicht!");
		assertTrue(kunde.getEmail() == email, "Die Email stimmt nicht!");
	}
}
