package gui.fliesen;

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

    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zum Fliesen.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public FliesenControl(KundeModel kundeModel){
        Stage stageFliesen = new Stage();
        stageFliesen.initModality(Modality.APPLICATION_MODAL);
        this.fliesenView = new FliesenView(this, stageFliesen);
        this.kundeModel = kundeModel;
    }

    /**
     * macht das FliesenView-Objekt sichtbar.
     */
    public void oeffneFliesenView(){
        this.fliesenView.oeffneFliesenView();
    }

    public void leseFliesenSonderwuensche(){
    }

    /**
     * Validates the given combination of extra wishes
     *
     * @param ausgewaehlteSw    - the given extra wishes
     * @return  - whether or not the combination of wishes is valid
     */
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw){
        boolean hatDachgeschoss = kundeModel.hatDachgeschoss();
        //TODO: Placeholder da leseGrundrisssonderwuensche noch nicht implementiert
        boolean grundrissSw = false;
        boolean wunschEins = false;
        boolean wunschZwei = false;
        boolean wunschDrei = false;
        boolean wunschVier = false;
        boolean wunschFuenf = false;
        boolean wunschSechs = false;

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
                case 5:
                    wunschFuenf = true;
                    break;
                case 6:
                    wunschSechs = true;
                    break;
            }
        }
        if(wunschDrei && wunschEins){
            return false;
        }
        if(wunschVier && wunschZwei){
            return false;
        }
        if(wunschFuenf && (!hatDachgeschoss || !grundrissSw));{

        }
        if(wunschSechs && !wunschFuenf){
            return false;
        }
        return true;
    }
}
