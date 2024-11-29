package business.grundriss;

import java.util.Optional;

public class Grundriss {
	public Optional<Integer> nr;
	public String beschreibung;
	public double preis;

	public Grundriss(Optional<Integer> nr, String beschreibung, double preis) {
		this.nr = nr;
		this.beschreibung = beschreibung;
		this.preis = preis;
	}
	
	public Optional<Integer> getNr() {
		return this.nr;
	}
	
	public String getBeschreibung() {
		return this.beschreibung;
	}

	public double getPreis() {
		return this.preis;
	}
}

