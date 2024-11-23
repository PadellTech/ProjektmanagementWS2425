package gui.sanitaerinstallation;

import business.kunde.Kunde;
import business.kunde.KundeModel;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Sanitaer-Varianten
 * kontrolliert.
 */
public final class SanitaerControl {

    // das View-Objekt des Sanitaer-Fensters
    private SanitaerView sanitaerView;
    private KundeModel kundeModel;

    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zum Sanitaer.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public SanitaerControl(KundeModel kundeModel){
        Stage stageSanitaer = new Stage();
        stageSanitaer.initModality(Modality.APPLICATION_MODAL);
        this.sanitaerView = new SanitaerView(this, stageSanitaer);
        this.kundeModel = kundeModel;
    }

    /**
     * macht das SanitaerView-Objekt sichtbar.
     */
    public void oeffneSanitaerView(){
        this.sanitaerView.oeffneSanitaerView();
    }

    public void leseSanitaerSonderwuensche(){
    }

    /**
     * Validates the given combination of extra wishes
     *
     * @param ausgewaehlteSw    - the given extra wishes
     * @return  - whether or not the combination of wishes is valid
     */
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw){
        boolean hatDachgeschoss = kundeModel.hatDachgeschoss();
        boolean wunschEins = false;
        boolean wunschZwei = false;
        boolean wunschDrei = false;
        boolean wunschVier = false;

        for(int current: ausgewaehlteSw){
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
            }
        }
        if(wunschZwei && !hatDachgeschoss){
            return false;
        }
        if(wunschDrei && wunschEins) {
            return false;
        }
        if(wunschVier && ( wunschZwei|| hatDachgeschoss)){
            return false;
        }
        return true;
    }
}