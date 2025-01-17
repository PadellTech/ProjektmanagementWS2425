package gui.fenster_aussentueren;

import business.kunde.KundeModel;
import gui.kunde.KundeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import business.dbVerbindung.*;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den FensterAussentuer-Varianten
 * kontrolliert.
 */
public final class FensterAussentuerControl {

    // das View-Objekt des FensterAussentuer-Fensters
    private FensterAussentuerView fatView;
    private KundeModel kundeModel;
    private DBVerbindung connection;

    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zu FensterAussentuer.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public FensterAussentuerControl(KundeModel kundeModel, DBVerbindung connection){
        Stage stageFensterAussentuer = new Stage();
        stageFensterAussentuer.initModality(Modality.APPLICATION_MODAL);
        this.fatView = new FensterAussentuerView(this, stageFensterAussentuer);
        this.kundeModel = kundeModel;
        this.connection = connection;
    }
 
    /**
     * macht das FensterAussentuerView-Objekt sichtbar.
     */
    public void oeffneFensterAussentuerView(){
        this.fatView.oeffneFensterAussentuerView();
    }

    public String[][] leseFensterAussentuerSonderwuensche(){
    	this.connection = DBVerbindung.getInstance();
    	return connection.executeSelectNameAndPrice("Wunschoption", 2);
    }
    public void berechnePreis() {
    	
    }
    public void speichereSonderwuensche(int[] sonderwunsch_id)
    {
    	try {
    	connection.speichereSonderwuensche(sonderwunsch_id,KundeView.getComboboxValue());
    	} catch(Exception e)
    	{
    		this.fatView.Fehlermeldung("Es wurde kein Kunde ausgewaehlt");
    	}
    }
    
    public void loescheSonderwuensche(int[] sonderwunsch_id)
    {
    	try {
    	    connection.loescheSonderwuensche(sonderwunsch_id,KundeView.getComboboxValue());
    	} catch(Exception e) {
    		this.fatView.Fehlermeldung("Fehler beim speichern des loeschens der Sonderwuensche");
    	}
    }

    /**
     * Validates the given combination of extra wishes
     *
     * @param ausgewaehlteSw    - the given extra wishes
     * @return  - whether or not the combination of wishes is valid
     */
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw){
        boolean hatDachgeschoss = kundeModel.hatDachgeschoss();

        boolean wunschZwei = false;        // 3.2
        boolean wunschVier = false;      // 3.4
        boolean wunschFuenf = false;     // 3.5
        boolean wunschSechs = false;     // 3.6
        boolean wunschSieben = false;    // 3.7
        boolean wunschAcht = false;      // 3.8
        boolean wunschNeun = false;      // 3.9

        for (int current : ausgewaehlteSw) {
            switch (current) {
                case 2: wunschZwei = true; break;       // 3.2
                case 4: wunschVier = true; break;     // 3.4
                case 5: wunschFuenf = true; break;    // 3.5
                case 6: wunschSechs = true; break;    // 3.6
                case 7: wunschSieben = true; break;   // 3.7
                case 8: wunschAcht = true; break;     // 3.8
                case 9: wunschNeun = true; break;     // 3.9
            }
        }
        if (wunschZwei && !hatDachgeschoss) {
            return false;
        }
        if (wunschSieben && !wunschVier) {
            return false;
        }
        if (wunschAcht && !wunschFuenf) {
            return false;
        }
        if (wunschNeun && !wunschSechs) {
            return false;
        }
        return true;
    }
    public void exportiereSonderwuensche(String kategorie) {
    	int kundennummer = KundeView.getComboboxValue();
    	try {
            // Abrufen des Nachnamens des Kunden
            DBVerbindung connection = DBVerbindung.getInstance();
            String nachname = connection.getCustomerLastname(kundennummer);
            if (nachname == null || nachname.isEmpty()) {
                System.out.println("Kunde mit Kundennummer " + kundennummer + " nicht gefunden.");
                return;
            }

            // Sonderwünsche für den Kunden und die angegebene Kategorie abrufen
            List<Map<String, Object>> sonderwunschDaten = connection.getSonderwunschData(kundennummer, kategorie);

            if (sonderwunschDaten.isEmpty()) {
                System.out.println("Keine Daten für Kategorie '" + kategorie + "' und Kundennummer " + kundennummer + " gefunden.");
                return;
            }

            // Dateiname erstellen
            String dateiname = kundennummer + "_" + nachname + "_" + kategorie + ".csv";

            try (FileWriter writer = new FileWriter(dateiname)) {
                // Header der CSV-Datei
                writer.append("Sonderwunsch_Name,Wunschoption_Name,Preis\n");

                // Daten schreiben
                for (Map<String, Object> eintrag : sonderwunschDaten) {
                    writer.append(eintrag.get("Sonderwunsch_Name").toString())
                          .append(",")
                          .append(eintrag.get("Wunschoption_Name").toString())
                          .append(",")
                          .append(eintrag.get("Preis").toString())
                          .append("\n");
                }

                System.out.println("Die Datei " + dateiname + " wurde erfolgreich exportiert.");
            } catch (IOException e) {
                System.out.println("Fehler beim Schreiben der Datei: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Daten: " + e.getMessage());
        }
    }
}
