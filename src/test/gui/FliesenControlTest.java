package test.gui;

import org.junit.jupiter.api.BeforeEach;

import gui.fliesen.FliesenControl;
import gui.fliesen.FliesenView;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import java.io.File;
import business.dbVerbindung.DBVerbindung;
import business.kunde.Kunde;
import business.kunde.KundeModel;


	public class FliesenControlTest {

	    private FliesenControl fliesenControl;
	    private FliesenView fliesenView;

	    @BeforeEach
	    void setUp() throws Exception {
	        DBVerbindung dbtool = DBVerbindung.getInstance(); // Singleton-Instanz verwenden
	        KundeModel kundeModel = KundeModel.getInstance();
	        kundeModel.setKunde(new Kunde(1, "Max", "Mustermann", "123456", "testmail"));

	        fliesenControl = new FliesenControl(kundeModel, dbtool);

	        // Mock-Daten für Sonderwünsche
	        fliesenControl.sonderwuensche = new String[][]{
	            {"Fliese1", "10.0", "1"},
	        };

	        // Erstelle die FliesenView-Instanz
	        fliesenView = new FliesenView(fliesenControl, null);
	    }

	    @Test
	    public void testCsvExport() {
	        String expectedFileName = "1_Mustermann_fliesenSW.csv";

	        // Export ausführen
	        try {
	            fliesenView.csvExport(); // Aufruf der csvExport-Methode von FliesenView
	        } catch (Exception exception) {
	            fail("Fehler beim Exportieren der CSV-Datei: " + exception.getMessage());
	        }

	        // Prüfen, ob die Datei existiert
	        File exportedFile = new File(expectedFileName);
	        if (!exportedFile.exists()) {
	            fail("CSV-Datei wurde nicht erstellt.");
	        }

	        // Datei lesen und prüfen
	        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(exportedFile))) {
	            // Prüfen des Headers
	            assertEquals("Bezeichnung,Sonderwunsch,Preis in Euro,Ausgewählt", bufferedReader.readLine());

	            // Prüfen der Datenzeilen
	            assertEquals("Fliese1,1,10.0,Nein", bufferedReader.readLine());
	        } catch (IOException e) {
	            fail("Fehler beim Lesen der CSV-Datei: " + e.getMessage());
	        } finally {
	            // Cleanup: Datei löschen
	            exportedFile.delete();
	        }
	    }
	}



