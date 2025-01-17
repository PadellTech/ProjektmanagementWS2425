package gui.kunde;

import java.sql.SQLException;

import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.fliesen.FliesenControl;
import gui.grundriss.GrundrissControl;
import gui.innentueren.InnentuerControl;
import gui.fenster_aussentueren.FensterAussentuerControl;
import gui.heizungen.HeizungControl;
import gui.sanitaerinstallation.SanitaerControl;
import javafx.stage.Stage;
import business.dbVerbindung.*;

/**
 * Klasse, welche das Grundfenster mit den Kundendaten kontrolliert.
 */
public class KundeControl {
       
    // das View-Objekt des Grundfensters mit den Kundendaten
	private KundeView kundeView;
    // das Model-Objekt des Grundfensters mit den Kundendaten
    private KundeModel kundeModel;
    /* das GrundrissControl-Objekt fuer die Sonderwuensche
       zum Grundriss zu dem Kunden */
    private GrundrissControl grundrissControl;

	private SanitaerControl sanitaerControl;
	private FliesenControl fliesenControl;
	private FensterAussentuerControl fensterAussentuerControl;
	private HeizungControl heizungControl;
	private InnentuerControl innentuerControl;
	private DBVerbindung connection;
    
    /**
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
	 * Grundfenster mit den Kundendaten.
	 * @param primaryStage, Stage fuer das View-Objekt zu dem Grundfenster mit den Kundendaten
	 */
    public KundeControl(Stage primaryStage, DBVerbindung connection) { 
        this.kundeModel = KundeModel.getInstance(); 
        this.kundeView = new KundeView(this, primaryStage, kundeModel);
        this.connection = connection;
    }
    
    /*
     * erstellt, falls nicht vorhanden, ein Grundriss-Control-Objekt.
     * Das GrundrissView wird sichtbar gemacht.
     */
    public void oeffneGrundrissControl(){
    	if (this.grundrissControl == null){
    		this.grundrissControl = new GrundrissControl(kundeModel, connection);
      	}
    	this.grundrissControl.oeffneGrundrissView();
    }

	public void oeffneSanitaerControl(){
		if (this.sanitaerControl == null){
			this.sanitaerControl = new SanitaerControl(kundeModel, connection);
		}
		this.sanitaerControl.oeffneSanitaerView();
	}

	public void oeffneHeizungControl(){
		if (this.heizungControl == null){
			//connection.notNull();
			this.heizungControl = new HeizungControl(kundeModel, connection);
		}
		this.heizungControl.oeffneHeizungView();
	}
	public void oeffneFensterAussentuercontrol(){
		if (this.fensterAussentuerControl == null){
			this.fensterAussentuerControl = new FensterAussentuerControl(kundeModel, connection);
		}
		this.fensterAussentuerControl.oeffneFensterAussentuerView();
	}
	public void oeffneInnentuerControl(){
		if (this.innentuerControl == null){
			this.innentuerControl = new InnentuerControl(kundeModel, connection);
		}
		this.innentuerControl.oeffneInnentuerView();
	}
	public void oeffneFliesenControl(){
		if (this.fliesenControl == null){
			connection.notNull();
			this.fliesenControl = new FliesenControl(kundeModel, connection);
		}
		this.fliesenControl.oeffneFliesenView();
	}
    
	/**
	 * speichert ein Kunde-Objekt in die Datenbank
	 * @param kunde, Kunde-Objekt, welches zu speichern ist
	 */
    public void speichereKunden(Kunde kunde){
      	try{
    		kundeModel.speichereKunden(kunde,this.connection);
    	}
    	catch(SQLException exc){
    		exc.printStackTrace();
    		this.kundeView.zeigeFehlermeldung("SQLException",
                "Fehler beim Speichern in die Datenbank");
    	}
    	catch(Exception exc){
    		exc.printStackTrace();
    		this.kundeView.zeigeFehlermeldung("Exception",
                "Unbekannter Fehler");
    	}
    }
    public void loescheKunden(int hausnummer){
        // Erstelle das SQL-DELETE Statement, um den Kunden mit der angegebenen Hausnummer zu löschen
        String sql = "DELETE FROM Kunde WHERE hausnummer = "+hausnummer+";";
        
        // Führe das DELETE Statement aus, indem die Hausnummer als Parameter übergeben wird
        this.connection.executeUpdate(sql);
    }
    public void aendereKunden(String sql) {
    	this.connection.executeUpdate(sql);
    }
    public void aktualisiereKunde(int hausnummer)
    {
    	//this.kundeModel.getKunde().setHausnummer(hausnummer);
    }

}
