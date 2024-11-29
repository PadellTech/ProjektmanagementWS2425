package gui.innentueren;

import business.dbVerbindung.DBVerbindung;
import business.kunde.KundeModel;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Innentuer-Varianten
 * kontrolliert.
 */
public final class InnentuerControl {

    // das View-Objekt des Innentuer-Fensters
    private InnentuerView innentuerView;
    private KundeModel kundeModel;
    private DBVerbindung connection;

    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zum Innentuer.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public InnentuerControl(KundeModel kundeModel, DBVerbindung connection){
        Stage stageInnentuer = new Stage();
        stageInnentuer.initModality(Modality.APPLICATION_MODAL);
        this.innentuerView = new InnentuerView(this, stageInnentuer);
        this.kundeModel = kundeModel;
        this.connection = connection;
    }

    /**
     * macht das InnentuerView-Objekt sichtbar.
     */
    public void oeffneInnentuerView(){
        this.innentuerView.oeffneInnentuerView();
    }

    public String[][] leseInnentuerSonderwuensche(){
    	this.connection = DBVerbindung.getInstance();
    	return connection.executeSelectNameAndPrice("Wunschoption", 3);
    }

    /**
     * Validiert die Sonderwünsche zu Innentueren
     *
     * @param ausgewaehlteSw - die gegebenen Sonderwuensche
     * @param y - die gegebene Anzahl von Klarglastueren
     * @param z - die gegebene Anzahl von Milchglastueren
     * @return - Ob die Sonderwunschkombination gueltig ist
     */
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw, int y, int z){
        boolean hatDachgeschoss = kundeModel.hatDachgeschoss();
        boolean wunschEins = false;
        boolean wunschZwei = false;
        boolean wunschDrei = false;
        int anzahlTueren = getDoors();

        for(int current: ausgewaehlteSw) {
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
            }
        }

        if(wunschEins && (y < 1 || y > anzahlTueren)) {
            return false;
        }
        if(wunschZwei && (z < 1 || z > anzahlTueren)){
            return false;
        }

        if (wunschEins && wunschZwei && (y+z > anzahlTueren)) {
            return false;
        }
        if(wunschDrei && !hatDachgeschoss){
            return false;
        }
        return true;
    }

    /**
     * Hilfsmethode welche die Anzahl der Innentüren zählt.
     *
     * @return - Die Anzahl der Innentüren
     */
    private int getDoors(){
        if(kundeModel.getKunde() == null){
            return 0;
        }
        boolean hatDachgeschoss = kundeModel.hatDachgeschoss();
        boolean wunschZwei = false;
        boolean wunschDrei = false;
        boolean wunschVier = false;
        boolean wunschSechs =false;

        int[] grundrissSw = connection.executeSelectCustomerWishes(kundeModel.getKunde().getHausnummer(),1);
        for(int current: grundrissSw){
            switch (current){
                case 2:
                    wunschZwei = true;
                    break;
                case 3:
                    wunschDrei = true;
                    break;
                case 4:
                    wunschVier = true;
                    break;
                case 6:
                    wunschSechs = true;
                    break;
            }
        }
        int keller = hatDachgeschoss ? 1: 2;
        int eg = wunschZwei ? 1 : 0;
        int og = wunschDrei ? 3 : 4;
        int dg = 0;
        if (hatDachgeschoss && wunschVier && wunschSechs){
            dg = 2;
        } else if (hatDachgeschoss && (wunschVier || wunschSechs)){
            dg = 1;
        }

        return keller + eg +og +dg;
    }
}
