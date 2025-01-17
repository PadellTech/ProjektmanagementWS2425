package gui.heizungen;

import business.kunde.KundeModel;
import gui.kunde.KundeView;
import business.dbVerbindung.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import  java.io.IOException;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Heizung-Varianten
 * kontrolliert.
 */
public final class HeizungControl {

    // das View-Objekt des Heizung-Fensters
    private HeizungView heizungView;
    private KundeModel kundeModel;
    private DBVerbindung connection;

    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zum Heizung.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public HeizungControl(KundeModel kundeModel, DBVerbindung connection){
        Stage stageHeizung = new Stage();
        stageHeizung.initModality(Modality.APPLICATION_MODAL);
        this.heizungView = new HeizungView(this, stageHeizung);
        this.kundeModel = kundeModel;
    }

    /**
     * macht das HeizungView-Objekt sichtbar.
     */
    public void oeffneHeizungView(){
        this.heizungView.oeffneHeizungView();
    }

    public String[][] leseHeizungSonderwuensche(){
    	this.connection = DBVerbindung.getInstance();
    	return connection.executeSelectNameAndPrice("Wunschoption", 4);
    }
    public void speichereSonderwuensche(int[] sonderwunsch_id)
    {
    	try {
    	connection.speichereSonderwuensche(sonderwunsch_id,KundeView.getComboboxValue());
    	} catch(Exception e)
    	{
    		this.heizungView.Fehlermeldung("Es wurde kein Kunde ausgewaehlt");
    	}
    }

    public void loescheSonderwuensche(int[] sonderwunsch_id) {
        try {
            connection.loescheSonderwuensche(sonderwunsch_id, KundeView.getComboboxValue());
        } catch (Exception e) {
            this.heizungView.Fehlermeldung("Fehler beim Löschen der Sonderwünsche.");
        }
    }
    
    
    /**
     * Validiert die Sonderwünsche zu Heizkoerpern
     *
     * @param ausgewaehlteSw - die gegebenen Sonderwuensche
     * @param x - gegebene Anzahl zusätzlicher Heizkoerper
     * @param z - gegebene Anzahl von Heikoerpern ohne Heizrippen
     * @param y - gegebene Anzahl der Handtuchheizkoerper
     * @return - Ob die Sonderwunschkombination gueltig ist
     */
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw, int x, int z, int y) {
        boolean hatDachgeschoss = kundeModel.hatDachgeschoss();
        boolean wunschEins = false;
        boolean wunschZwei = false;
        boolean wunschDrei = false;
        boolean wunschVier = false;
        boolean wunschFuenf = false;

        for (int current : ausgewaehlteSw) {
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
                case 5:
                    wunschFuenf = true;
                    break;
            }
        }
        int anzahlHeizkoerper = getAnzahlHeizkoerper();
        if (wunschEins && (x < 1 || x > 5)) {
            return false;
        }
        if (wunschZwei && (z < 1 || z > x + anzahlHeizkoerper)) {
            return false;
        }
        if (wunschDrei) {
            if(kundeModel.getKunde() == null) {
                return false;
            }
            int[] grundrissSw = connection.executeSelectCustomerWishes(KundeView.getComboboxValue(), 1);
            boolean grundrissWunschSechs = false;
            for (Integer current : grundrissSw) {
                if (current == 6) {
                    grundrissWunschSechs = true;
                    break;
                }
            }
            if (y < 1 || y > 2) {
                return false;
            }
            if (y == 2 && (!hatDachgeschoss || !grundrissWunschSechs)) {
                return false;
            }
        }
        if (wunschVier && hatDachgeschoss) {
            return false;
        }
        if (wunschFuenf && !hatDachgeschoss) {
            return false;
        }
        return true;
    }

    /**
     * Hilfsmethode welche die Anzahl der Heizkörper zählt.
     *
     * @return - Die Anzahl der Heizkörper
     */
    private int getAnzahlHeizkoerper(){
        boolean hatDachgeschoss = kundeModel.hatDachgeschoss();
        boolean wunschDrei = false;
        boolean wunschSechs =false;
        if(kundeModel.getKunde() == null) {
            return 0;
        }
        int[] grundrissSw = connection.executeSelectCustomerWishes(KundeView.getComboboxValue(), 1);

        for(int current: grundrissSw){
            switch (current){
                case 3:
                    wunschDrei = true;
                    break;
                case 6:
                    wunschSechs = true;
                    break;
            }
        }
        int keller = hatDachgeschoss ? 1: 2;
        int eg = 2;
        int og = wunschDrei ? 3 : 4;
        int dg = 0;
        if(hatDachgeschoss){
            dg = wunschSechs ? 3:2;
        }
        return keller + eg +og +dg;
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
