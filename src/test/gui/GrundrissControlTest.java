package test.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import business.dbVerbindung.DBVerbindung;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.grundriss.GrundrissControl;


public class GrundrissControlTest {
    GrundrissControl grundrissControl;

    @BeforeEach
    void setUp() throws Exception {
    	DBVerbindung dbtool = new DBVerbindung();
    	KundeModel kundeModel = KundeModel.getInstance();
        kundeModel.setKunde(new Kunde(1, "Max", "Mustermann", "123456", "testmail"));
        grundrissControl = new GrundrissControl(kundeModel, dbtool);
    }

    @Test
    public void testExportiereSonderwuensche() throws Exception{
        try {
            grundrissControl.exportiereSonderwuensche("Grundriss-Varianten");
        } catch (NullPointerException exception) {
            fail(exception);
        }
        
        try {
            FileReader fReader = new FileReader("1_Mustermann_Grundriss-Varianten.csv");
            BufferedReader bufferedReader = new BufferedReader(fReader);
            assertEquals("Sonderwunsch_Name,Wunschoption_Name,Preis", bufferedReader.readLine());
            bufferedReader.close();
        } catch (FileNotFoundException exception) {
            fail("Datei nicht gefunden!\n" + exception);
        } 
    }
}