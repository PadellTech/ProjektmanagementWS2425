package test.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.dbVerbindung.DBVerbindung;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.fliesen.FliesenControl;
import gui.innentueren.InnentuerControl;

class InnentuerControlTest {

	private InnentuerControl innentuerenController;
	private KundeModel kundemodel;
	private DBVerbindung db;
	private Kunde kunde;
	private int hausnummer;
	private String vorname;
	private String nachname;
	private String telefonnummer;
	private String email;
    private int[] ausgewaehlteSw_1;
    private int[] ausgewaehlteSw_2;
    private int[] ausgewaehlteSw_1_2;
    private int[] ausgewaehlteSw_3;

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
        innentuerenController = new InnentuerControl(db,kundemodel);
        
        ausgewaehlteSw_1 = new int[] {1};
        ausgewaehlteSw_2 = new int[] {2};
        ausgewaehlteSw_1_2 = new int[] {1,2};
        ausgewaehlteSw_3 = new int[] {3};
//        fliesencontroller = new FliesenControl(db);
        // Annahme grundriss Option 6 wurde gewählt
        
    }

    @Test
    void testValidSonderwuenscheWithHatDachgeschoss() {
        int y = 3;
        int z = 2;
        int[] ausgewaehlteSw = ausgewaehlteSw_1; // {1}

        boolean result = innentuerenController.pruefeKonstellationSonderwuensche(ausgewaehlteSw, y, z);
        assertTrue(result, "Valid Sonderwünsche mit hatDachgeschoss true sollten gültig sein.");
    }

    @Test
    void testInvalidSonderwuenscheYPlusZExceedsX() {
        int y = 5;
        int z = 3; // y + z = 8, x wird in getDoors() berechnet und ist 7
        int[] ausgewaehlteSw = ausgewaehlteSw_1;

        boolean result = innentuerenController.pruefeKonstellationSonderwuensche(ausgewaehlteSw, y, z);
        assertFalse(result, "Wenn y + z > x, sollte die Kombination ungültig sein.");
    }

    @Test
    void testValidSonderwuenscheWithWunschDreiAndHatDachgeschoss() {
        int y = 2;
        int z = 2;
        int[] ausgewaehlteSw = ausgewaehlteSw_3; // {3}

        boolean result = innentuerenController.pruefeKonstellationSonderwuensche(ausgewaehlteSw, y, z);
        assertTrue(result, "Wunsch 3 ausgewählt und hatDachgeschoss true sollte gültig sein.");
    }

    @Test
    void testInvalidSonderwuenscheWithWunschDreiAndNoHatDachgeschoss() {
        // Ändern von hausnummer auf 1, sodass hatDachgeschoss() false zurückgibt
        int y = 2;
        int z = 2;
        int[] ausgewaehlteSw = ausgewaehlteSw_3; // {3}

        boolean result = innentuerenController.pruefeKonstellationSonderwuensche(ausgewaehlteSw, y, z);
        assertFalse(result, "Wunsch 3 ausgewählt und hatDachgeschoss false sollte ungültig sein.");
    }

    @Test
    void testYExceedsX() {
        int y = 8; // y > x (x = 7 in getDoors())
        int z = 0;
        int[] ausgewaehlteSw = ausgewaehlteSw_1;

        boolean result = innentuerenController.pruefeKonstellationSonderwuensche(ausgewaehlteSw, y, z);
        assertFalse(result, "Wenn y > x, sollte die Kombination ungültig sein.");
    }

    @Test
    void testZExceedsX() {
        int y = 0;
        int z = 8; // z > x (x = 7 in getDoors())
        int[] ausgewaehlteSw = ausgewaehlteSw_1;

        boolean result = innentuerenController.pruefeKonstellationSonderwuensche(ausgewaehlteSw, y, z);
        assertFalse(result, "Wenn z > x, sollte die Kombination ungültig sein.");
    }

    @Test
    void testYPlusZEqualsX() {
        int y = 4;
        int z = 3; // y + z = x (x = 7)
        int[] ausgewaehlteSw = ausgewaehlteSw_1_2;

        boolean result = innentuerenController.pruefeKonstellationSonderwuensche(ausgewaehlteSw, y, z);
        assertTrue(result, "Wenn y + z = x, sollte die Kombination gültig sein.");
    }

    @Test
    void testNegativeY() {
        int y = -1;
        int z = 2;
        int[] ausgewaehlteSw = ausgewaehlteSw_1;

        boolean result = innentuerenController.pruefeKonstellationSonderwuensche(ausgewaehlteSw, y, z);
        assertFalse(result, "Negative y-Werte sollten ungültig sein.");
    }

    @Test
    void testNegativeZ() {
        int y = 2;
        int z = -1;
        int[] ausgewaehlteSw = ausgewaehlteSw_1;

        boolean result = innentuerenController.pruefeKonstellationSonderwuensche(ausgewaehlteSw, y, z);
        assertFalse(result, "Negative z-Werte sollten ungültig sein.");
    }

    @Test
    void testYAndZZero() {
        int y = 0;
        int z = 0;
        int[] ausgewaehlteSw = ausgewaehlteSw_1;

        boolean result = innentuerenController.pruefeKonstellationSonderwuensche(ausgewaehlteSw, y, z);
        assertTrue(result, "Wenn y und z Null sind, sollte die Kombination gültig sein.");
    }
}
