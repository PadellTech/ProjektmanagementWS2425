package gui.fenster_aussentueren;

import business.kunde.KundeModel;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den FensterAussentuer-Varianten
 * kontrolliert.
 */
public final class FensterAussentuerControl {

    // das View-Objekt des FensterAussentuer-Fensters
    private FensterAussentuerView fatView;
    private KundeModel kundeModel;

    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zu FensterAussentuer.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public FensterAussentuerControl(KundeModel kundeModel){
        Stage stageFensterAussentuer = new Stage();
        stageFensterAussentuer.initModality(Modality.APPLICATION_MODAL);
        this.fatView = new FensterAussentuerView(this, stageFensterAussentuer);
        this.kundeModel = kundeModel;
    }

    /**
     * macht das FensterAussentuerView-Objekt sichtbar.
     */
    public void oeffneFensterAussentuerView(){
        this.fatView.oeffneFensterAussentuerView();
    }

    public void leseFensterAussentuerSonderwuensche(){
    }

    /**
     * Validates the given combination of extra wishes
     *
     * @param ausgewaehlteSw    - the given extra wishes
     * @return  - whether or not the combination of wishes is valid
     */
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw){
        boolean hatDachgeschoss = kundeModel.hatDachgeschoss();

        boolean wunschZwei = false;        // 3.2
        boolean wunschVier = false;      // 3.4
        boolean wunschFuenf = false;     // 3.5
        boolean wunschSechs = false;     // 3.6
        boolean wunschSieben = false;    // 3.7
        boolean wunschAcht = false;      // 3.8
        boolean wunschNeun = false;      // 3.9

        for (int current : ausgewaehlteSw) {
            switch (current) {
                case 2: wunschZwei = true; break;       // 3.2
                case 4: wunschVier = true; break;     // 3.4
                case 5: wunschFuenf = true; break;    // 3.5
                case 6: wunschSechs = true; break;    // 3.6
                case 7: wunschSieben = true; break;   // 3.7
                case 8: wunschAcht = true; break;     // 3.8
                case 9: wunschNeun = true; break;     // 3.9
            }
        }
        if (wunschZwei && !hatDachgeschoss) {
            return false;
        }
        if (wunschSieben && !wunschVier) {
            return false;
        }
        if (wunschAcht && !wunschFuenf) {
            return false;
        }
        if (wunschNeun && !wunschSechs) {
            return false;
        }
        return true;
    }
}
