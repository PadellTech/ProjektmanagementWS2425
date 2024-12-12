package gui.fliesen;
<<<<<<< HEAD
=======
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
>>>>>>> 6deeefba1adf6cff920bb2d299d36904d2881204

import business.dbVerbindung.*;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.kunde.KundeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Fliesen-Varianten
 * kontrolliert.
 */
public final class FliesenControl {

	// das View-Objekt des Fliesen-Fensters
	private FliesenView fliesenView;
	private KundeModel kundeModel;
	private DBVerbindung connection;

<<<<<<< HEAD
	/**
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum Fenster
	 * fuer die Sonderwuensche zum Fliesen.
	 * 
	 * @param kundeModel, KundeModel zum abgreifen der Kunden
	 */
	public FliesenControl(KundeModel kundeModel, DBVerbindung connection) {
		Stage stageFliesen = new Stage();
		stageFliesen.initModality(Modality.APPLICATION_MODAL);
		this.fliesenView = new FliesenView(this, stageFliesen);
		this.kundeModel = kundeModel;
		this.connection = connection;
	}
	//Controller ohne View for Test Purpose
	public FliesenControl(DBVerbindung connection, KundeModel kundeModel) {
		this.kundeModel = kundeModel;
		this.connection = connection;
	}
	
=======
    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zum Fliesen.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public FliesenControl(KundeModel kundeModel, DBVerbindung connection){
        Stage stageFliesen = new Stage();
        stageFliesen.initModality(Modality.APPLICATION_MODAL);
        this.fliesenView = new FliesenView(this, stageFliesen);
        this.kundeModel = kundeModel;
        this.connection = connection;
    }
    public void speichereSonderwuensche(int[] sonderwunsch_id)
    {
    	try {
    	connection.speichereSonderwuensche(sonderwunsch_id,KundeView.getComboboxValue());
    	} catch(Exception e)
    	{
    		this.fliesenView.Fehlermeldung("Es wurde kein Kunde ausgewaehlt");
    	}
    }
>>>>>>> 6deeefba1adf6cff920bb2d299d36904d2881204

	public void speichereSonderwuensche(int[] sonderwunsch_id) {
		try {
			connection.speichereSonderwuensche(sonderwunsch_id, kundeModel.getKunde().getHausnummer());
		} catch (Exception e) {
			this.fliesenView.Fehlermeldung("Es wurde kein Kunde ausgewaehlt");
		}
	}

	/**
	 * macht das FliesenView-Objekt sichtbar.
	 */
	public void oeffneFliesenView() {
		this.fliesenView.oeffneFliesenView();
	}

	public String[][] leseFliesenSonderwuensche() {
		this.connection = DBVerbindung.getInstance();
		return connection.executeSelectNameAndPrice("Wunschoption", 6);
	}

<<<<<<< HEAD
	/**
	 * Validates the given combination of extra wishes
	 *
	 * @param ausgewaehlteSw - the given extra wishes
	 * @return - whether or not the combination of wishes is valid
	 */
	public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
		boolean hatDachgeschoss = kundeModel.hatDachgeschoss();
		boolean wunschEins = false;
		boolean wunschZwei = false;
		boolean wunschDrei = false;
		boolean wunschVier = false;
		boolean wunschFuenf = false;
		boolean wunschSechs = false;

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
			case 6:
				wunschSechs = true;
				break;
			}
		}
		if (kundeModel.getKunde() == null) {
			return false;
		}
//		int[] grundrissSw = connection.executeSelectCustomerWishes(kundeModel.getKunde().getHausnummer(), 1);
		int[] grundrissSw = new int[] {1,2,3};
		boolean grundrissWunschSechs = false;
		for (Integer current : grundrissSw) {
			if (current == 6) {
				grundrissWunschSechs = true;
				break;
			}
		}
		if (wunschDrei && wunschEins) {
			return false;
		}
		if (wunschVier && wunschZwei) {
			return false;
		}
		if (wunschFuenf && (!hatDachgeschoss || !grundrissWunschSechs)) {
			return false;
		}
		if (wunschSechs && !wunschFuenf) {
			return false;
		}
		return true;
	}
=======
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
                case 5:
                    wunschFuenf = true;
                    break;
                case 6:
                    wunschSechs = true;
                    break;
            }
        }
        if(kundeModel.getKunde() == null){
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
        if(wunschDrei && wunschEins){
            return false;
        }
        if(wunschVier && wunschZwei){
            return false;
        }
        if(wunschFuenf && (!hatDachgeschoss || !grundrissWunschSechs)){
            return false;
        }
        if(wunschSechs && !wunschFuenf){
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
>>>>>>> 6deeefba1adf6cff920bb2d299d36904d2881204
}
