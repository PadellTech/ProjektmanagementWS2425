package gui.heizungen;

import business.kunde.KundeModel;
import business.dbVerbindung.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Heizung-Varianten
 * kontrolliert.
 */
public final class HeizungControl {

    // das View-Objekt des Heizung-Fensters
    private HeizungView heizungView;
    private KundeModel kundeModel;
    private DBVerbindung connection;

    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zum Heizung.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public HeizungControl(KundeModel kundeModel, DBVerbindung connection){
        Stage stageHeizung = new Stage();
        stageHeizung.initModality(Modality.APPLICATION_MODAL);
        this.heizungView = new HeizungView(this, stageHeizung);
        this.kundeModel = kundeModel;
    }

    /**
     * macht das HeizungView-Objekt sichtbar.
     */
    public void oeffneHeizungView(){
        this.heizungView.oeffneHeizungView();
    }

    public String[][] leseHeizungSonderwuensche(){
    	this.connection = DBVerbindung.getInstance();
    	return connection.executeSelectNameAndPrice("Wunschoption", 4);
    }

    /**
     * Validiert die Sonderwünsche zu Heizkoerpern
     *
     * @param ausgewaehlteSw - die gegebenen Sonderwuensche
     * @param x - gegebene Anzahl zusätzlicher Heizkoerper
     * @param z - gegebene Anzahl von Heikoerpern ohne Heizrippen
     * @param y - gegebene Anzahl der Handtuchheizkoerper
     * @return - Ob die Sonderwunschkombination gueltig ist
     */
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw, int x, int z, int y) {
        boolean hatDachgeschoss = kundeModel.hatDachgeschoss();
        boolean wunschEins = false;
        boolean wunschZwei = false;
        boolean wunschDrei = false;
        boolean wunschVier = false;
        boolean wunschFuenf = false;

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
            }
        }
        int anzahlHeizkoerper = getAnzahlHeizkoerper();
        if (wunschEins && (x < 1 || x > 5)) {
            return false;
        }
        if (wunschZwei && (z < 1 || z > x + anzahlHeizkoerper)) {
            return false;
        }
        if (wunschDrei) {
            int[] grundrissSw = connection.executeSelectCustomerWishes(kundeModel.getKunde().getHausnummer(), 1);
            boolean grundrissWunschSechs = false;
            for (Integer current : grundrissSw) {
                if (current == 6) {
                    grundrissWunschSechs = true;
                    break;
                }
            }
            if (y < 1 || y > 2) {
                return false;
            }
            if (y == 2 && (!hatDachgeschoss || !grundrissWunschSechs)) {
                return false;
            }
        }
        if (wunschVier && hatDachgeschoss) {
            return false;
        }
        if (wunschFuenf && !hatDachgeschoss) {
            return false;
        }
        return true;
    }

    /**
     * Hilfsmethode welche die Anzahl der Heizkörper zählt.
     *
     * @return - Die Anzahl der Heizkörper
     */
    private int getAnzahlHeizkoerper(){
        boolean hatDachgeschoss = kundeModel.hatDachgeschoss();
        boolean wunschDrei = false;
        boolean wunschSechs =false;
        int[] grundrissSw = connection.executeSelectCustomerWishes(kundeModel.getKunde().getHausnummer(), 1);

        for(int current: grundrissSw){
            switch (current){
                case 3:
                    wunschDrei = true;
                    break;
                case 6:
                    wunschSechs = true;
                    break;
            }
        }
        int keller = hatDachgeschoss ? 1: 2;
        int eg = 2;
        int og = wunschDrei ? 3 : 4;
        int dg = 0;
        if(hatDachgeschoss){
            dg = wunschSechs ? 3:2;
        }
        return keller + eg +og +dg;
    }
}
