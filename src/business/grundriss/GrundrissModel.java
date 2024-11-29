package business.grundriss;

import java.sql.SQLException;
import java.util.ArrayList;

import business.dbVerbindung.DBVerbindung;
import business.dbVerbindung.SonderwunschDataModel;
import business.kunde.Kunde;
import business.kunde.KundeModel;

public class GrundrissModel {
	//Datenbank verbindung
		private DBVerbindung db;
		
		
		//instance
		private static GrundrissModel instance = null;
				
		private Kunde kunde;
		
	    private KundeModel kundeModel;

	    
		private int[] kundeSw = new int[6];
		
		private GrundrissModel() {	
			this.db = DBVerbindung.getInstance();
		}
		
		//Singelton
		public static GrundrissModel getInstance() {
			if(instance == null){
	            instance = new GrundrissModel();
	        }

	        return instance;
		}
		
		public void setKunde(Kunde kunde) {
			this.kunde = kunde;
		}
		
		public Kunde getKunde() {
			return this.kunde;
		}
		
		public int[] getKundeSw() {
			return kundeSw;
		}
		
		
		
		public ArrayList<Grundriss> ladeSonderwuensche() throws SQLException {
			
			ArrayList<Grundriss> grundrisse = new ArrayList<Grundriss>();

			return grundrisse;
		}
		
	
		
		public boolean hausHatDachgeschoss() {
			if(this.kundeModel.hatDachgeschoss()) {
				return true;
			}
			
			return false;
		}
		
		 
		public void speichereSonderwuensche(ArrayList<Integer> sonderwuensche) throws SQLException {

		}
		
		
		public void loescheSonderwuensche(ArrayList<Integer> sonderwuensche) throws SQLException {
			 
		}
	
}
