package business.dbVerbindung;

import java.util.Optional;

public class SonderwunschDataModel {
	
	 private Optional<Integer> sonderwunschnr;
	    private String beschreibung;
	    private int preis;
	    private int kategorienr;
	    
	    
	    public SonderwunschDataModel(Integer sonderwunschnr, String beschreibung, int preis, int kategorienr) {
	        this.sonderwunschnr = Optional.ofNullable(sonderwunschnr);
	        this.beschreibung = beschreibung;
	        this.preis = preis;
	        this.kategorienr = kategorienr;
	    }

	    public Optional<Integer> getSonderwunschnr() {
	        return sonderwunschnr;
	    }

	    public void setSonderwunschnr(Optional<Integer> sonderwunschnr) {
	        this.sonderwunschnr = sonderwunschnr;
	    }

	    public String getBeschreibung() {
	        return beschreibung;
	    }

	    public void setBeschreibung(String beschreibung) {
	        this.beschreibung = beschreibung;
	    }

	    public int getPreis() {
	        return preis;
	    }

	    public void setPreis(int preis) {
	        this.preis = preis;
	    }

	    public int getKategorienr() {
	        return kategorienr;
	    }

	    public void setKategorienr(int kategorienr) {
	        this.kategorienr = kategorienr;
	    }


	    @Override
	    public String toString() {
	        return "SonderwunschDataModel{" +
	                "sonderwunschnr=" + sonderwunschnr.get() +
	                ", beschreibung='" + beschreibung + '\'' +
	                ", preis=" + preis +
	                ", kategorienr=" + kategorienr +
	                '}';
	    }

}
