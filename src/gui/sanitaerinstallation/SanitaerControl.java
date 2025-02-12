package gui.sanitaerinstallation;

import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.kunde.KundeView;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import business.dbVerbindung.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.SQLException;
import  java.io.IOException;


/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Sanitaer-Varianten
 * kontrolliert.
 */
public final class SanitaerControl {

    // das View-Objekt des Sanitaer-Fensters
    private SanitaerView sanitaerView;
    private KundeModel kundeModel;
    private DBVerbindung connection;

    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zum Sanitaer.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public SanitaerControl(KundeModel kundeModel, DBVerbindung connection){
        Stage stageSanitaer = new Stage();
        stageSanitaer.initModality(Modality.APPLICATION_MODAL);
        this.sanitaerView = new SanitaerView(this, stageSanitaer);
        this.kundeModel = kundeModel;
        this.connection = connection;
    }

    /**
     * macht das SanitaerView-Objekt sichtbar.
     */
    public void oeffneSanitaerView(){
        this.sanitaerView.oeffneSanitaerView();
    }

    public String[][] leseSanitaerSonderwuensche(){
    	this.connection = DBVerbindung.getInstance();
    	return connection.executeSelectNameAndPrice("Wunschoption", 5);
    }
    public void speichereSonderwuensche(int[] sonderwunsch_id)
    {
    	try {
    	connection.speichereSonderwuensche(sonderwunsch_id,KundeView.getComboboxValue());
    	} catch(Exception e)
    	{
    		this.sanitaerView.Fehlermeldung("Es wurde kein Kunde ausgewaehlt");
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
        boolean wunschEins = false;
        boolean wunschZwei = false;
        boolean wunschDrei = false;
        boolean wunschVier = false;

        for(int current: ausgewaehlteSw){
            switch (current) {
                case 1:
                    wunschEins = true;
                    break;
                case 2:
                    wunschZwei = true;
                    break;
                case 3:
                    wunschDrei = true;
                    break;
                case 4:
                    wunschVier = true;
                    break;
            }
        }
        if(wunschZwei && !hatDachgeschoss){
            return false;
        }
        if(wunschDrei && wunschEins) {
            return false;
        }
        if(wunschVier && ( wunschZwei|| hatDachgeschoss)){
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
