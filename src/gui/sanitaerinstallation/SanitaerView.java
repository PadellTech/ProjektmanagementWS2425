package gui.sanitaerinstallation;

import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu
 * den Sanitaer bereitstellt.
 */
public class SanitaerView extends BasisView{

    // das Control-Objekt des Sanitaer-Fensters
    private SanitaerControl sanitaerControl;

    //---Anfang Attribute der grafischen Oberflaeche---
    private Label lblPlatzhalter
            = new Label("Platzhalter");
    private TextField txtPreisPlatzhalter 	= new TextField();
    private Label lblPlatzhalterEuro 		= new Label("Euro");
    private CheckBox chckBxPlatzhalter 		= new CheckBox();
    //-------Ende Attribute der grafischen Oberflaeche-------

    /**
     * erzeugt ein Sanitaer-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param sanitaerControl SanitaerControl, enthaelt das zugehoerige Control
     * @param sanitaerStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public SanitaerView (SanitaerControl sanitaerControl, Stage sanitaerStage){
        super(sanitaerStage);
        this.sanitaerControl = sanitaerControl;
        sanitaerStage.setTitle("Sonderwünsche zu Sanitär-Varianten");

        this.initKomponenten();
        this.leseSanitaerSonderwuensche();
    }

    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Sanitär-Varianten");
        super.getGridPaneSonderwunsch().add(lblPlatzhalter, 0, 1);
        super.getGridPaneSonderwunsch().add(txtPreisPlatzhalter, 1, 1);
        txtPreisPlatzhalter.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblPlatzhalterEuro, 2, 1);
        super.getGridPaneSonderwunsch().add(chckBxPlatzhalter, 3, 1);
    }

    /**
     * macht das SanitaerView-Objekt sichtbar.
     */
    public void oeffneSanitaerView(){
        super.oeffneBasisView();
    }

    private void leseSanitaerSonderwuensche(){
        this.sanitaerControl.leseSanitaerSonderwuensche();
    }

    /* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
    protected void berechneUndZeigePreisSonderwuensche(){
        // Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
        // aus dem Control aufgerufen, dann der Preis berechnet.
    }

    /* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
    protected void speichereSonderwuensche(){
        //TODO: placeholder array since we dont have prober storage off extra wishes atm.
        //TODO: Modify when the other dudes have established a database connection and storing of wishes is finalised.
        int[] ph = {1,2,3,4,5};
        if (!sanitaerControl.pruefeKonstellationSonderwuensche(ph)) {
            return;
        }


        // Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
        // aus dem Control aufgerufen, dann die Sonderwuensche gespeichert.
    }


}


