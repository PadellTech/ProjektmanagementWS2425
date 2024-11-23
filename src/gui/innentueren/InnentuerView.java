package gui.innentueren;

import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu
 * den Innentuer bereitstellt.
 */
public class InnentuerView extends BasisView{

    // das Control-Objekt des Innentuer-Fensters
    private InnentuerControl innentuerControl;

    //---Anfang Attribute der grafischen Oberflaeche---
    private Label lblPlatzhalter
            = new Label("Platzhalter");
    private TextField txtPreisPlatzhalter 	= new TextField();
    private Label lblPlatzhalterEuro 		= new Label("Euro");
    private CheckBox chckBxPlatzhalter 		= new CheckBox();
    //-------Ende Attribute der grafischen Oberflaeche-------

    /**
     * erzeugt ein Innentuer-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param innentuerControl InnentuerControl, enthaelt das zugehoerige Control
     * @param innentuerStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public InnentuerView (InnentuerControl innentuerControl, Stage innentuerStage){
        super(innentuerStage);
        this.innentuerControl = innentuerControl;
        innentuerStage.setTitle("Sonderwünsche zu Innentürvarianten");

        this.initKomponenten();
        this.leseInnentuerSonderwuensche();
    }

    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Innentür-Varianten");
        super.getGridPaneSonderwunsch().add(lblPlatzhalter, 0, 1);
        super.getGridPaneSonderwunsch().add(txtPreisPlatzhalter, 1, 1);
        txtPreisPlatzhalter.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblPlatzhalterEuro, 2, 1);
        super.getGridPaneSonderwunsch().add(chckBxPlatzhalter, 3, 1);
    }

    /**
     * macht das InnentuerView-Objekt sichtbar.
     */
    public void oeffneInnentuerView(){
        super.oeffneBasisView();
    }

    private void leseInnentuerSonderwuensche(){
        this.innentuerControl.leseInnentuerSonderwuensche();
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
        if (!innentuerControl.pruefeKonstellationSonderwuensche(ph,1,3)) {
            return;
        }


        // Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
        // aus dem Control aufgerufen, dann die Sonderwuensche gespeichert.
    }


}


