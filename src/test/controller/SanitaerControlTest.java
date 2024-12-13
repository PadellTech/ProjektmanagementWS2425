package test.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.dbVerbindung.DBVerbindung;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.sanitaerinstallation.SanitaerControl;

class SanitaerControlTest {

    private SanitaerControl sanitaerControl;
    private KundeModel kundeModel;
    private DBVerbindung db;
    private Kunde kunde;
    private int hausnummer;


    @BeforeEach
    void setUp() throws Exception {
        kundeModel = KundeModel.getInstance();
        db = DBVerbindung.getInstance();

        hausnummer = 2; // Haus mit Dachgeschoss

        kunde = new Kunde(hausnummer, "Max", "Mustermann", "0987654321", "Max.Mustermann@test.com");
        kundeModel.setKunde(kunde);
        
        sanitaerControl = new SanitaerControl(db, kundeModel);
    }

    @Test
    void pruefeKonstellationSonderwuenscheTest6_2() {
        // 6.2: Geht nur, falls DG vorhanden ist
        int[] sonderwuenscheMitDG = new int[] {2};
        int[] sonderwuenscheOhneDG = new int[] {2};
        
        assertTrue(sanitaerControl.pruefeKonstellationSonderwuensche(sonderwuenscheMitDG), "6.2 sollte gehen, wenn DG vorhanden ist.");

        assertFalse(sanitaerControl.pruefeKonstellationSonderwuensche(sonderwuenscheOhneDG), "6.2 sollte nicht gehen, wenn DG nicht vorhanden ist.");
    }

    @Test
    void pruefeKonstellationSonderwuenscheTest6_3() {
        // 6.3: Geht nur, falls 6.1 nicht erwünscht ist
        int[] sonderwuenscheMit6_1 = new int[] {1, 3};
        int[] sonderwuenscheOhne6_1 = new int[] {3};

        assertFalse(sanitaerControl.pruefeKonstellationSonderwuensche(sonderwuenscheMit6_1), "6.3 sollte nicht gehen, wenn 6.1 ausgewählt ist.");
        assertTrue(sanitaerControl.pruefeKonstellationSonderwuensche(sonderwuenscheOhne6_1), "6.3 sollte gehen, wenn 6.1 nicht ausgewählt ist.");
    }

    @Test
    void pruefeKonstellationSonderwuenscheTest6_4() {
        // 6.4: Geht nur, falls DG vorhanden ist und 6.2 nicht erwünscht ist
        int[] sonderwuenscheMit6_2 = new int[] {4, 2};
        int[] sonderwuenscheOhne6_2MitDG = new int[] {4};
        int[] sonderwuenscheOhne6_2OhneDG = new int[] {4};

        assertFalse(sanitaerControl.pruefeKonstellationSonderwuensche(sonderwuenscheMit6_2), "6.4 sollte nicht gehen, wenn 6.2 ausgewählt ist.");
        assertTrue(sanitaerControl.pruefeKonstellationSonderwuensche(sonderwuenscheOhne6_2MitDG), "6.4 sollte gehen, wenn 6.2 nicht ausgewählt ist und DG vorhanden ist.");

        assertFalse(sanitaerControl.pruefeKonstellationSonderwuensche(sonderwuenscheOhne6_2OhneDG), "6.4 sollte nicht gehen, wenn DG nicht vorhanden ist.");
    }
}
