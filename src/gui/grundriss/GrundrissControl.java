package gui.grundriss;

import java.sql.SQLException;
import java.util.ArrayList;

import business.dbVerbindung.DBVerbindung;
import business.grundriss.Grundriss;
import business.grundriss.GrundrissModel;
import business.kunde.KundeModel;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Grundriss-Varianten
 * kontrolliert.
 */
public final class GrundrissControl {
	
	// das View-Objekt des Grundriss-Fensters
	private GrundrissView grundrissView;
    private GrundrissModel grundrissModel;

	private DBVerbindung connection;
    private KundeModel kundeModel;

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
	}
	 
	/**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView(){ 
		this.grundrissView.oeffneGrundrissView();
	}

	public ArrayList<Grundriss>  leseGrundrissSonderwuensche(){
		//this.connection = DBVerbindung.getInstance();		
		try {
    		return grundrissModel.ladeSonderwuensche();
    	} catch(SQLException ex) {
    		ex.printStackTrace();
    		}
    	return new ArrayList<Grundriss>(); // ändern oder so
    } 
	
	
	
	public void speichereGrundrissSonderwuensche(ArrayList<Integer> ausgewaehlteSw) {
    	try {
    		grundrissModel.speichereSonderwuensche(ausgewaehlteSw);
    	} catch(SQLException ex) {
    		ex.printStackTrace();
    	}
    }
	
	
	 
    public void loescheGrundrissSonderwuensche(ArrayList<Integer> nichtAusgewaehlteSw) {
    	try {
    		grundrissModel.loescheSonderwuensche(nichtAusgewaehlteSw);
    	} catch(SQLException ex) {
    		ex.printStackTrace();
    	}
    }

	
	
    public double berechneSonderwunschpreise(ArrayList<Integer> ausgewaehlteSw, ArrayList<String> dazugehoerigePreise) {
    	double gesamtpreis = 0;

    	for (int i = 0; i < ausgewaehlteSw.size(); i++) {
    		gesamtpreis += Double.parseDouble(dazugehoerigePreise.get(i));
    	}

    	return gesamtpreis;
    }
    
	public boolean pruefeKonstellationSonderwuensche(ArrayList<Integer> ausgewaehlteSw){
		boolean erlaubt = true;
    	ArrayList<String> fehlermeldungen = new ArrayList<String>();

    	if (ausgewaehlteSw.contains(2)) {
        	if (!ausgewaehlteSw.contains(1)) {
        		erlaubt = false;
        		fehlermeldungen.add("Sonderwunsch 2.2 kann nur gewählt werden, wenn zuvor 2.1 ausgewählt wurde");
        	}
        } 
    	
    	if (grundrissModel.hausHatDachgeschoss()) { // Dachgeschoss
    		if (ausgewaehlteSw.contains(6)) {
    			if (!ausgewaehlteSw.contains(5)) {
    				erlaubt = false;
    				fehlermeldungen.add("Sonderwunsch 2.6 ist nur mit Sonderwunsch 2.5 kombinierbar");
    			}
    		}
    	} else {
    		if (ausgewaehlteSw.contains(4) || ausgewaehlteSw.contains(5) || ausgewaehlteSw.contains(6)) {
    			erlaubt = false;
    			fehlermeldungen.add("Sonderwunsch 2.4 bis 2.6 ist nur möglich, wenn ein Dachgeschoss (DG) vorhanden");
    		}
    	}
    	
    	String[] erlaubt_fehlermeldungen = new String[2];
    	erlaubt_fehlermeldungen[0] = String.valueOf(erlaubt);
    	erlaubt_fehlermeldungen[1] = String.join(" und ", fehlermeldungen);

    	return erlaubt;
			}
}
