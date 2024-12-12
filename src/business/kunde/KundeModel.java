package business.kunde;

import java.sql.SQLException;

import business.dbVerbindung.DBVerbindung;
import javafx.collections.*;
import java.util.Random;
  
/** 
 * Klasse, welche das Model des Grundfensters mit den Kundendaten enthaelt.
 */
public final class KundeModel {
	
	// enthaelt den aktuellen Kunden
	private Kunde kunde;
	Random random = new Random();
	
	/* enthaelt die Plannummern der Haeuser, diese muessen vielleicht noch
	   in eine andere Klasse verschoben werden */
	ObservableList<Integer> plannummern = 
	    FXCollections.observableArrayList(
		0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24);
	

	// enthaelt das einzige KundeModel-Objekt
	private static KundeModel kundeModel;



	/**
	 * Hilfsmethode um zu checken ob ein Haus ein Dachgeschoss hat.
	 * @return
	 */
	public boolean hatDachgeschoss(){
		return kunde != null && kunde.getHausnummer() != 1 && kunde.getHausnummer() != 6 &&
				kunde.getHausnummer() != 7 && kunde.getHausnummer() != 14 &&
				kunde.getHausnummer() != 15 && kunde.getHausnummer() != 24;
	}
	
	// privater Konstruktor zur Realisierung des Singleton-Pattern
	private KundeModel(){
		super();
	}
	
	/**
	 *  Methode zum Erhalt des einzigen KundeModel-Objekts.
	 *  Das Singleton-Pattern wird realisiert.
	 *  @return KundeModel, welches das einzige Objekt dieses
	 *          Typs ist.
	 */
	public static KundeModel getInstance(){
		if(kundeModel == null){
			kundeModel = new KundeModel();
		}
		return kundeModel;	
	}
	
	/**
	 * gibt die Ueberschrift zum Grundfenster mit den Kundendaten heraus
	 * @return String, Ueberschrift zum Grundfenster mit den Kundendaten 
	 */
	public String getUeberschrift(){
		return "Verwaltung der Sonderwunschlisten";
	}
	
	/**
	 * gibt saemtliche Plannummern der Haeuser des Baugebiets heraus.
	 * @return ObservableList<Integer> , enthaelt saemtliche Plannummern der Haeuser
	 */
	public ObservableList<Integer> getPlannummern(){
		return this.plannummern; 
	}
		 	
	// ---- Datenbankzugriffe -------------------
	
	/**
	 * speichert ein Kunde-Objekt in die Datenbank
	 * @param kunde, Kunde-Objekt, welches zu speichern ist
	 * @throws SQLException, Fehler beim Speichern in die Datenbank
	 * @throws Exception, unbekannter Fehler
	 */
	public void speichereKunden(Kunde kunde, DBVerbindung connection)
	    throws SQLException, Exception{
     
   	    this.kunde = kunde;
   	    // Speicherung des Kunden in der DB
   	    if(this.validateUser(kunde)) {
   	    	connection.executeUpdate(
   	    		    "INSERT INTO Kunde (kundennummer, hausnummer, vorname, nachname, telefonnummer, email) " +
   	    		    "VALUES ("+random.nextInt(65000)+", " + this.kunde.getHausnummer() + ", '" + this.kunde.getVorname() + "', '" + 
   	    		    this.kunde.getNachname() + "', '" + this.kunde.getTelefonnummer() + "', '" + 
   	    		    this.kunde.getEmail() + "');"
   	    		);

   	 System.out.println("Update erfolgreich");
   	    }
   	    else {
   	    	System.out.println("Nicht validiert");
   	    }
	}

	/**
	 * Validates the given User.
	 *
	 * @param kunde - the given user
	 * @return		- whether the users parameters are valid
	 */
	public boolean validateUser(Kunde kunde){
		if(kunde == null){
			return false;
		}
		boolean validEmail = kunde.getEmail() != null && kunde.getEmail().contains("@");
		boolean validPhone = kunde.getTelefonnummer() != null && kunde.getTelefonnummer().matches("\\d+");
		if(!validEmail && !validPhone){
			return false;
		}
		if(kunde.getNachname() == null || kunde.getNachname().trim().isEmpty()) {
			return false;
		}
		if (kunde.getHausnummer() < 1 || kunde.getHausnummer() > 24) {
			return false;
		}
		return true;
	}
	public Kunde getKunde() {
		return kunde;
  }
	public void setKunde(Kunde k) {
		this.kunde = k;
	}

	public int getHausnummmer() {
		return this.kunde.getHausnummer();
	}
}
