package business.grundriss;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import business.dbVerbindung.DBVerbindung;
import business.kunde.Kunde;

public class GrundrissModel {
	//Datenbank verbindung
		private DBVerbindung db;
		
		//instance
		private static GrundrissModel instance = null;
		
		
		private Kunde kunde;
		
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
		
		
		
		public List<Map<String, Object>> getSonderwunschData(int kundennummer, String kategorie) {
	        try {
	            return db.getSonderwunschData(kundennummer, kategorie);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return new ArrayList<>();
	        }
	    }
		
		
		  public int[] getCustomerWishes(int customerNumber, int wishCategory) {
		        return db.executeSelectCustomerWishes(customerNumber, wishCategory);
		    }
		  
		
		
		  public void saveSonderwunsche(int[] sonderwunschIds, int hausnummer) {
		        db.speichereSonderwuensche(sonderwunschIds, hausnummer);
		    }
		  
		  
		  public boolean hausHatDachgeschoss() {
			  return kunde != null && kunde.getHausnummer() != 1 && kunde.getHausnummer() != 6 &&
						kunde.getHausnummer() != 7 && kunde.getHausnummer() != 14 &&
						kunde.getHausnummer() != 15 && kunde.getHausnummer() != 24;
			}

		  

}
