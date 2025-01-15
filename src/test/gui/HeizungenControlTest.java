package test.gui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import business.dbVerbindung.DBVerbindung;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.heizungen.HeizungControl;

public class HeizungenControlTest {
    private HeizungControl heizungenControl;
    private KundeModel kundeModel;
    private DBVerbindung dbtool;
    private int[] ausgewaehlteSw_1;
    private int[] ausgewaehlteSw_2;
    private int[] ausgewaehlteSw_3;
    private int[] ausgewaehlteSw_4;
    private int[] ausgewaehlteSw_5;
    private int[] ausgewaehlteSw_1_2;
    private int[] ausgewaehlteSw_6;

    @BeforeAll
    public void initArrays() throws Exception{
        ausgewaehlteSw_1 = new int[] {1};
        ausgewaehlteSw_2 = new int[] {2};
        ausgewaehlteSw_3 = new int[] {3};
        ausgewaehlteSw_4 = new int[] {4};
        ausgewaehlteSw_5 = new int[] {5};
        ausgewaehlteSw_1_2 = new int[] {1, 2};
        ausgewaehlteSw_6 = new int[] {6};
    }

    @BeforeEach
	public void setUp() throws Exception{
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
	}
	
	@AfterEach
	public void tearDown() throws Exception{
		dbtool = null;
        kundeModel = null;
        heizungenControl = null;
	}

    @AfterAll
    public void deinitArrays() throws Exception{
        ausgewaehlteSw_1 = null;
        ausgewaehlteSw_2 = null;
        ausgewaehlteSw_3 = null;
        ausgewaehlteSw_4 = null;
        ausgewaehlteSw_5 = null;
        ausgewaehlteSw_1_2 = null;
        ausgewaehlteSw_6 = null;
    }

    @Test
    public void zusatzHeizungen() {
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
        kundeModel.setKunde(new Kunde(1, "Test", "TestN", "123456", "test"));

        boolean result;

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_1, 0, -1, -1);
        assertFalse(result, "0 ist nicht als anz zusatz Heizungen gueltig");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_1, 6, -1, -1);
        assertFalse(result, "6 ist nicht als anz zusatz Heizungen gueltig");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_1, 3, -1, -1);
        assertTrue(result, "3 ist als anz zusatz Heizungen gueltig");
    }

    @Test
    public void heizungenOhneRippenOhneDachgeschoss() {
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
        kundeModel.setKunde(new Kunde(1, "Test", "TestN", "123456", "test"));

        boolean result;

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_2, -1, 0, -1);
        assertFalse(result, "0 ist nicht als anz rippenlose Heizungen gueltig");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_2, -1, 8, -1);
        assertFalse(result, "8 ist nicht als anz rippenlose Heizungen ohne DG und ohne weitere Heizungen gueltig");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_1_2, 3, 8, -1);
        assertTrue(result, "8 ist als anz rippenlose Heizungen ohne DG und mit weiteren Heizungen gueltig");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_2, 3, 8, -1);
        assertFalse(result, "8 ist nicht als anz rippenlose Heizungen ohne DG und mit weiteren Heizungen gueltig, x beeinflusst die gesamtzahl nciht, wenn der wunsch 1 nicht angegeben wurde");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_2, -1, 5, -1);
        assertTrue(result, "5 ist als anz rippenlose Heizungen gueltig");
    }

    @Test
    public void heizungenOhneRippenMitDachgeschoss() {
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
        kundeModel.setKunde(new Kunde(2, "Test", "TestN", "123456", "test"));

        boolean result;

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_2, -1, 0, -1);
        assertFalse(result, "0 ist nicht als anz rippenlose Heizungen gueltig");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_2, -1, 12, -1);
        assertFalse(result, "12 ist nicht als anz rippenlose Heizungen mit DG und ohne weitere Heizungen gueltig");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_1_2, 3, 12, -1);
        assertTrue(result, "12 ist als anz rippenlose Heizungen mit DG und mit weiteren Heizungen gueltig");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_2, 3, 12, -1);
        assertFalse(result, "8 ist nicht als anz rippenlose Heizungen mit DG und mit weiteren Heizungen gueltig, x beeinflusst die gesamtzahl nciht, wenn der wunsch 1 nicht angegeben wurde");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_2, -1, 5, -1);
        assertTrue(result, "5 ist als anz rippenlose Heizungen gueltig");
    }

    @Test
    public void HandtuchheizkoerperOhneDG() {
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
        kundeModel.setKunde(new Kunde(1, "Test", "TestN", "123456", "test"));

        boolean result;

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_3, -1, -1, 1);
        assertFalse(result, "1 ist nicht als anz handtuchheizkörpern gueltig, da es kein DG gibt");
    }

    @Test
    public void HandtuchheizkoerperMitDG() {
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
        kundeModel.setKunde(new Kunde(2, "Test", "TestN", "123456", "test"));

        boolean result;

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_3, -1, -1, 0);
        assertFalse(result, "0 ist nicht als anz handtuchheizkörpern gueltig");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_3, -1, -1, 3);
        assertFalse(result, "3 ist nicht als anz handtuchheizkörpern gueltig");

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_3, -1, -1, 1);
        assertFalse(result, "1 ist als anz handtuchheizkörpern gueltig");
    }

    @Test
    public void vierOhneDG() {
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
        kundeModel.setKunde(new Kunde(1, "Test", "TestN", "123456", "test"));

        boolean result;

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_4, -1, -1, -1);
        assertTrue(result, "Sollte moeglich sein, da es kein DG gibt");
    }

    @Test
    public void vierMitDG() {
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
        kundeModel.setKunde(new Kunde(2, "Test", "TestN", "123456", "test"));

        boolean result;

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_4, -1, -1, -1);
        assertFalse(result, "Sollte nicht moeglich sein, da es ein DG gibt");
    }

    @Test
    public void fuenfOhneDG() {
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
        kundeModel.setKunde(new Kunde(1, "Test", "TestN", "123456", "test"));

        boolean result;

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_5, -1, -1, -1);
        assertFalse(result, "Sollte nicht moeglich sein, da es kein DG gibt");
    }

    @Test
    public void fuenfMitDG() {
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
        kundeModel.setKunde(new Kunde(2, "Test", "TestN", "123456", "test"));

        boolean result;

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_5, -1, -1, -1);
        assertTrue(result, "Sollte moeglich sein, da es ein DG gibt");
    }

    @Test
    public void nichtVorhandenerSw() {
        dbtool = new DBVerbindung();
    	kundeModel = KundeModel.getInstance();
        heizungenControl = new HeizungControl(kundeModel, dbtool);
        kundeModel.setKunde(new Kunde(1, "Test", "TestN", "123456", "test"));

        boolean result;

        result = heizungenControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw_6, -1, -1, -1);
        assertFalse(result, "Sollte nicht moeglich sein, da es kein Sw 6 gibt");
    }
}
