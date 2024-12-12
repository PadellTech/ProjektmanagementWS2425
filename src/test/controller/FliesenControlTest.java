package test.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.dbVerbindung.DBVerbindung;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.fliesen.FliesenControl;


class FliesenControlTest {
	
	private FliesenControl fliesencontroller;
	private KundeModel kundemodel;
	private DBVerbindung db;
	private Kunde kunde;
	private int hausnummer;
	private String vorname;
	private String nachname;
	private String telefonnummer;
	private String email;
	int[] grundrissSw;
	
	@BeforeEach
	void setUp() throws Exception {
		kundemodel = KundeModel.getInstance();
		db = DBVerbindung.getInstance();
		
		hausnummer = 2; // Hat Dachgeschoss
		vorname = "Max";
		nachname = "Mustermann";
        telefonnummer = "0987654321";
        email = "Max.Mustermann@test.com";
        kunde = new Kunde(hausnummer, vorname, nachname, telefonnummer, email);
        kundemodel.setKunde(kunde);
        fliesencontroller = new FliesenControl(db,kundemodel);
//        fliesencontroller = new FliesenControl(db);
        // Annahme grundriss Option 6 wurde gewählt
        
        grundrissSw = new int[] {1, 2, 6};
	}

	@Test
	void pruefeKonstellationSonderwuenscheTest1() {
		// 7.1: Standardmäßig wird der Küchenbereich gefliest.
		int[] fliesenWuensche = new int[] {1};
		assertTrue(fliesencontroller.pruefeKonstellationSonderwuensche(fliesenWuensche), "Küchenbereich sollte gefliest werden.");
		
	}
	
	@Test
	void pruefeKonstellationSonderwuenscheTest2() {
		// 7.2: Standardmäßig wird das Bad im OG gefliest.
		int[] fliesenWuensche = new int[] {2};
		assertTrue(fliesencontroller.pruefeKonstellationSonderwuensche(fliesenWuensche), "Bad im OG sollte gefliest werden.");
	}
	
	@Test
	void pruefeKonstellationSonderwuenscheTest3() {
		// 7.3: Geht nur, wenn man 7.1 nicht auswählt.
		int[] fliesenWuensche = new int[] {3};
		assertFalse(fliesencontroller.pruefeKonstellationSonderwuensche(new int[] {1, 3}), "7.3 sollte nicht gehen, wenn 7.1 ausgewählt ist.");
		assertTrue(fliesencontroller.pruefeKonstellationSonderwuensche(fliesenWuensche), "7.3 sollte gehen, wenn 7.1 nicht ausgewählt ist.");
	}
	
	@Test
	void pruefeKonstellationSonderwuenscheTest4() {
		// 7.4: Geht nur, wenn man 7.2 nicht auswählt.
		int[] fliesenWuensche = new int[] {4};
		assertFalse(fliesencontroller.pruefeKonstellationSonderwuensche(new int[] {2, 4}), "7.4 sollte nicht gehen, wenn 7.2 ausgewählt ist.");
		assertTrue(fliesencontroller.pruefeKonstellationSonderwuensche(fliesenWuensche), "7.4 sollte gehen, wenn 7.2 nicht ausgewählt ist.");
	}

	@Test
	void pruefeKonstellationSonderwuenscheTest5() {
		// 7.5: Geht nur, wenn DG vorhanden ist und 2.6 erwünscht ist.
		int[] fliesenWuensche = new int[] {5};
		assertTrue(fliesencontroller.pruefeKonstellationSonderwuensche(fliesenWuensche), "7.5 sollte gehen, wenn DG vorhanden ist und 2.6 erwünscht ist.");
	}

	@Test
	void pruefeKonstellationSonderwuenscheTest6() {
		// 7.6: Geht nur, wenn man 7.5 auswählt.
		int[] fliesenWuensche = new int[] {5, 6};
		assertFalse(fliesencontroller.pruefeKonstellationSonderwuensche(new int[] {6}), "7.6 sollte nicht gehen, wenn 7.5 nicht ausgewählt ist.");
		assertTrue(fliesencontroller.pruefeKonstellationSonderwuensche(fliesenWuensche), "7.6 sollte gehen, wenn 7.5 ausgewählt ist.");
	}
}
