package gui.grundriss;
 
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import business.dbVerbindung.DBVerbindung;
import business.grundriss.GrundrissModel;
import business.kunde.KundeModel;
import gui.kunde.KundeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Grundriss-Varianten
 * kontrolliert.
 */
public final class GrundrissControl {
	
	// das View-Objekt des Grundriss-Fensters
	private GrundrissView grundrissView;
	private DBVerbindung connection;
	private KundeModel kundeModel;
    private GrundrissModel grundrissModel;

	

	/**
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
	 * Fenster fuer die Sonderwuensche zum Grundriss.
	 * @param grundrissStage, Stage fuer das View-Objekt zu den Sonderwuenschen zum Grundriss
	 */
	public GrundrissControl(KundeModel kundeModel, DBVerbindung connection){  
	   	Stage stageGrundriss = new Stage();
    	stageGrundriss.initModality(Modality.APPLICATION_MODAL);
    	this.grundrissView = new GrundrissView(this, stageGrundriss);
    	this.connection = connection;
    	this.kundeModel = kundeModel;
	}
	    
	/**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView(){ 
		this.grundrissView.oeffneGrundrissView();
	}
 
    
    public void zeigeBild(){
        this.grundrissView.zeigeBild(kundeModel.hatDachgeschoss());
    }
 
	public String[][] leseGrundrissSonderwuensche(){
		this.connection = DBVerbindung.getInstance();
		return connection.executeSelectNameAndPrice("Wunschoption", 1);
    } 
	
	
	
	
	public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
	    // Wandelt das Array in eine Liste (Java 8 kompatibel)
	    List<Integer> ausgewaehlteSwList = Arrays.stream(ausgewaehlteSw).boxed().collect(Collectors.toList());

	    // Regel 1: Sonderwunsch 2.2 nur mit 2.1 kombinierbar
	    if (ausgewaehlteSwList.contains(2) && !ausgewaehlteSwList.contains(1)) {
	        return false;
	    }

	    // Regel 2: Dachgeschoss-Sonderwünsche prüfen
	    if (grundrissModel.hausHatDachgeschoss()) {
	        if (ausgewaehlteSwList.contains(6) && !ausgewaehlteSwList.contains(5)) {
	            return false;
	        }
	    } else {
	        // Wenn kein Dachgeschoss vorhanden, dürfen 2.4 bis 2.6 nicht ausgewählt sein
	        if (ausgewaehlteSwList.contains(4) || ausgewaehlteSwList.contains(5) || ausgewaehlteSwList.contains(6)) {
	            return false;
	        }
	    }

	    return true;
	}

	

    public void speichereSonderwuensche(int[] sonderwunsch_id)
    {
    	try {
    	connection.speichereSonderwuensche(sonderwunsch_id,KundeView.getComboboxValue());
    	} catch(Exception e)
    	{
    		this.grundrissView.Fehlermeldung("Es wurde kein Kunde ausgewaehlt");
    	}
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
