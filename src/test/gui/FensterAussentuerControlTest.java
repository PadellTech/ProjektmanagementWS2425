package test.gui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.fenster_aussentueren.FensterAussentuerControl;
import gui.kunde.KundeControl;
import javafx.stage.Stage;
import business.dbVerbindung.DBVerbindung;
import business.kunde.Kunde;
import business.kunde.KundeModel;

class FensterAussentuerControlTest {
    private FensterAussentuerControl faController;
    private int[] ausgewaehlteSw_2;
    private int[] ausgewaehlteSw_7;
    private int[] ausgewaehlteSw_4_7;
    private int[] ausgewaehlteSw_8;
    private int[] ausgewaehlteSw_5_8;
    private int[] ausgewaehlteSw_9;
    private int[] ausgewaehlteSw_6_9;

    @BeforeEach
    void setUp() throws Exception {
    	DBVerbindung dbtool = new DBVerbindung();
    	KundeModel kundeModel = KundeModel.getInstance();
        faController = new FensterAussentuerControl(kundeModel, dbtool);

        // Initialize the arrays
        ausgewaehlteSw_2 = new int[] {2};
        ausgewaehlteSw_7 = new int[] {7};
        ausgewaehlteSw_4_7 = new int[] {4,7};
        ausgewaehlteSw_8 = new int[] {8};
        ausgewaehlteSw_5_8 = new int[] {5,8};
        ausgewaehlteSw_9 = new int[] {9};
        ausgewaehlteSw_6_9 = new int[] {6,9};
        kundeModel.setKunde(new Kunde(1, "Test", "TestN", "123456", "test"));
    }

    @Test
    void testPruefeKonstellationSonderwuensche() throws Exception {
        
        boolean result;
   
        result = faController.pruefeKonstellationSonderwuensche(ausgewaehlteSw_2);
        assertFalse(result, "Wunsch 2 sollte gültig sein, wenn DG vorhanden ist.");

        // Test 3.7 geht nur, wenn 3.4 ausgesucht wurde.
        // Fall: Wunsch 7 ohne Wunsch 4
        result = faController.pruefeKonstellationSonderwuensche(ausgewaehlteSw_7);
        assertFalse(result, "Wunsch 7 sollte ungültig sein, wenn Wunsch 4 nicht ausgewählt wurde.");

        // Fall: Wunsch 7 mit Wunsch 4
        result = faController.pruefeKonstellationSonderwuensche(ausgewaehlteSw_4_7);
        assertTrue(result, "Wunsch 7 sollte gültig sein, wenn Wunsch 4 ausgewählt wurde.");

        // Test 3.8 geht nur, wenn 3.5 ausgesucht wurde.
        // Fall: Wunsch 8 ohne Wunsch 5
        result = faController.pruefeKonstellationSonderwuensche(ausgewaehlteSw_8);
        assertFalse(result, "Wunsch 8 sollte ungültig sein, wenn Wunsch 5 nicht ausgewählt wurde.");

        // Fall: Wunsch 8 mit Wunsch 5
        result = faController.pruefeKonstellationSonderwuensche(ausgewaehlteSw_5_8);
        assertTrue(result, "Wunsch 8 sollte gültig sein, wenn Wunsch 5 ausgewählt wurde.");

        // Test 3.9 geht nur, wenn 3.6 ausgesucht wurde.
        // Fall: Wunsch 9 ohne Wunsch 6
        result = faController.pruefeKonstellationSonderwuensche(ausgewaehlteSw_9);
        assertFalse(result, "Wunsch 9 sollte ungültig sein, wenn Wunsch 6 nicht ausgewählt wurde.");

        // Fall: Wunsch 9 mit Wunsch 6
        result = faController.pruefeKonstellationSonderwuensche(ausgewaehlteSw_6_9);
        assertTrue(result, "Wunsch 9 sollte gültig sein, wenn Wunsch 6 ausgewählt wurde.");
    }
}
