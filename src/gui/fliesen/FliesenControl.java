package gui.fliesen;

import business.dbVerbindung.*;
import business.kunde.Kunde;
import business.kunde.KundeModel;
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
}
