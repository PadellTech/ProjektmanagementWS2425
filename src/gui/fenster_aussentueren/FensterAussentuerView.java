package gui.fenster_aussentueren;

import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu
 * den FensterAussentuer bereitstellt.
 */
public class FensterAussentuerView extends BasisView{

    // das Control-Objekt des FensterAussentuer-Fensters
    private FensterAussentuerControl fatControl;

    //---Anfang Attribute der grafischen Oberflaeche---
    private Label lblPlatzhalter
            = new Label("Platzhalter");
    private TextField txtPreisPlatzhalter 	= new TextField();
    private Label lblPlatzhalterEuro 		= new Label("Euro");
    private CheckBox chckBxPlatzhalter 		= new CheckBox();
    //-------Ende Attribute der grafischen Oberflaeche-------

    /**
     * erzeugt ein FensterAussentuer-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param fatControl FensterAussentuerControl, enthaelt das zugehoerige Control
     * @param fatStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public FensterAussentuerView (FensterAussentuerControl fatControl, Stage fatStage){
        super(fatStage);
        this.fatControl = fatControl;
        fatStage.setTitle("Sonderwünsche zu Fenster und Aussentüren-Varianten");

        this.initKomponenten();
        this.leseFensterAussentuerSonderwuensche();
    }

    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Fenster und Aussentür-Varianten");
        super.getGridPaneSonderwunsch().add(lblPlatzhalter, 0, 1);
        super.getGridPaneSonderwunsch().add(txtPreisPlatzhalter, 1, 1);
        txtPreisPlatzhalter.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblPlatzhalterEuro, 2, 1);
        super.getGridPaneSonderwunsch().add(chckBxPlatzhalter, 3, 1);
    }

    /**
     * macht das FensterAussentuerView-Objekt sichtbar.
     */
    public void oeffneFensterAussentuerView(){
        super.oeffneBasisView();
    }

    private void leseFensterAussentuerSonderwuensche(){
        this.fatControl.leseFensterAussentuerSonderwuensche();
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
        if (!fatControl.pruefeKonstellationSonderwuensche(ph)) {
            return;
        }


        // Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
        // aus dem Control aufgerufen, dann die Sonderwuensche gespeichert.
    }


}


