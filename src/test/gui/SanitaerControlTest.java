package test.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.DynamicTest.stream;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import business.dbVerbindung.DBVerbindung;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.sanitaerinstallation.SanitaerControl;


public class SanitaerControlTest {
    SanitaerControl sanitaerControl;
@BeforeEach
    void setUp() throws Exception {
    	DBVerbindung dbtool = new DBVerbindung();
    	KundeModel kundeModel = KundeModel.getInstance();
        kundeModel.setKunde(new Kunde(1, "Max", "Mustermann", "123456", "testmail"));
        sanitaerControl = new SanitaerControl(kundeModel, dbtool);
    }

    @Test
    public void testExportiereSonderwuensche() throws Exception{
        sanitaerControl.exportiereSonderwuensche("Sanitärinstallation");
        FileReader fReader = new FileReader("1_Mustermann_sanitaer.csv");
        BufferedReader bufferedReader = new BufferedReader(fReader);
        assertEquals("Sonderwunsch_Name,Wunschoption_Name,Preis", bufferedReader);
    }
}
