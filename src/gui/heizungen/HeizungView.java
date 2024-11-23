package gui.heizungen;

import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu
 * den Heizung bereitstellt.
 */
public class HeizungView extends BasisView{

    // das Control-Objekt des Heizung-Fensters
    private HeizungControl heizungControl;

    //---Anfang Attribute der grafischen Oberflaeche---
    private Label lblPlatzhalter
            = new Label("Platzhalter");
    private TextField txtPreisPlatzhalter 	= new TextField();
    private Label lblPlatzhalterEuro 		= new Label("Euro");
    private CheckBox chckBxPlatzhalter 		= new CheckBox();
    //-------Ende Attribute der grafischen Oberflaeche-------

    /**
     * erzeugt ein Heizung-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param heizungControl HeizungControl, enthaelt das zugehoerige Control
     * @param heizungStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public HeizungView (HeizungControl heizungControl, Stage heizungStage){
        super(heizungStage);
        this.heizungControl = heizungControl;
        heizungStage.setTitle("Sonderwünsche zu Heizungsvarianten");

        this.initKomponenten();
        this.leseHeizungSonderwuensche();
    }

    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Heizungs-Varianten");
        super.getGridPaneSonderwunsch().add(lblPlatzhalter, 0, 1);
        super.getGridPaneSonderwunsch().add(txtPreisPlatzhalter, 1, 1);
        txtPreisPlatzhalter.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblPlatzhalterEuro, 2, 1);
        super.getGridPaneSonderwunsch().add(chckBxPlatzhalter, 3, 1);
    }

    /**
     * macht das HeizungView-Objekt sichtbar.
     */
    public void oeffneHeizungView(){
        super.oeffneBasisView();
    }

    private void leseHeizungSonderwuensche(){
        this.heizungControl.leseHeizungSonderwuensche();
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
        if (!heizungControl.pruefeKonstellationSonderwuensche(ph,1,2,3)) {
            return;
        }


        // Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
        // aus dem Control aufgerufen, dann die Sonderwuensche gespeichert.
    }


}


