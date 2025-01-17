package test.gui;

import gui.fenster_aussentueren.FensterAussentuerView;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FensterAussentuerControlTest {

	private FensterAussentuerView fensterAussentuerView;

    @BeforeEach
    void setUp() {
        // Initialisiere das KundeModel mit einem Beispielkunden
        KundeModel kundeModel = KundeModel.getInstance();
        kundeModel.setKunde(new Kunde(1, "Max", "Mustermann", "123456", "testmail"));

        // Initialisiere die FensterAussentuerView mit einem Dummy Stage
        Stage stage = new Stage();
        fensterAussentuerView = new FensterAussentuerView(null, stage);

        // Mock-Daten für Sonderwünsche setzen
        fensterAussentuerView.sonderwuensche = new String[][]{
                {"Fenster1", "100.0", "1"},
                {"Fenster2", "150.0", "2"}
        };
    }

    @Test
    public void testCsvExport() {
        String expectedFileName = "1_Mustermann_FensterUndAussentueren.csv";

        // Rufe die Exportmethode auf
        try {
            fensterAussentuerView.csvExport();
        } catch (Exception exception) {
            fail("Fehler beim Exportieren der CSV-Datei: " + exception.getMessage());
        }

        // Prüfe, ob die Datei existiert
        File exportedFile = new File(expectedFileName);
        assertTrue(exportedFile.exists(), "CSV-Datei wurde nicht erstellt.");

        // Überprüfe den Inhalt der Datei
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(exportedFile))) {
            // Prüfe die Kopfzeile
            assertEquals("Bezeichnung,Sonderwunsch,Preis in Euro,Ausgewählt", bufferedReader.readLine());

            // Prüfe die Datenzeilen
            assertEquals("Fenster1,1,100.0,Nein", bufferedReader.readLine());
            assertEquals("Fenster2,2,150.0,Nein", bufferedReader.readLine());

        } catch (IOException e) {
            fail("Fehler beim Lesen der CSV-Datei: " + e.getMessage());
        } finally {
            // Lösche die Datei nach dem Test
            if (!exportedFile.delete()) {
                System.err.println("Die Testdatei konnte nicht gelöscht werden.");
            }
        }
    }
}
