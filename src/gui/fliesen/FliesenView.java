package gui.fliesen;

import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu
 * den Fliesen bereitstellt.
 */
public class FliesenView extends BasisView{

    // das Control-Objekt des Fliesen-Fensters
    private FliesenControl fliesenControl;

    private String[][] sonderwuensche;
 // --- Anfang Attribute der grafischen Oberfläche ---
    private Label[] lblPlatzhalter = new Label[6]; // Array für Labels
    private TextField[] txtPreisPlatzhalter = new TextField[6]; // Array für Textfelder
    private Label[] lblPlatzhalterEuro = new Label[6]; // Array für Euro Labels
    private CheckBox[] chckBxPlatzhalter = new CheckBox[6]; // Array für Checkboxes
    //-------Ende Attribute der grafischen Oberflaeche-------

    /**
     * erzeugt ein Fliesen-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param fliesenControl FliesenControl, enthaelt das zugehoerige Control
     * @param fliesenStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public FliesenView (FliesenControl fliesenControl, Stage fliesenStage){
        super(fliesenStage);
        this.fliesenControl = fliesenControl;
        fliesenStage.setTitle("Sonderwünsche zu Fliesen-Varianten");
        sonderwuensche = this.leseFliesenSonderwuensche();
        this.initKomponenten();
        
    }

    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Heizungs-Varianten");

        // Initialisieren der Arrays mit den entsprechenden Elementen
        for (int i = 0; i < 6; i++) {
            lblPlatzhalter[i] = new Label(sonderwuensche[i][0]);
            txtPreisPlatzhalter[i] = new TextField();
            lblPlatzhalterEuro[i] = new Label("Euro");
            chckBxPlatzhalter[i] = new CheckBox();
            txtPreisPlatzhalter[i].setText(sonderwuensche[i][1]);

            // Setze Textfelder auf nicht editierbar
            txtPreisPlatzhalter[i].setEditable(false);

            // Füge alle Komponenten zum GridPane hinzu
            super.getGridPaneSonderwunsch().add(lblPlatzhalter[i], 0, i + 1);
            super.getGridPaneSonderwunsch().add(txtPreisPlatzhalter[i], 1, i + 1);
            super.getGridPaneSonderwunsch().add(lblPlatzhalterEuro[i], 2, i + 1);
            super.getGridPaneSonderwunsch().add(chckBxPlatzhalter[i], 3, i + 1);
        }
    }
    /**
     * macht das FliesenView-Objekt sichtbar.
     */
    public void oeffneFliesenView(){
        super.oeffneBasisView();
    }

    private String[][] leseFliesenSonderwuensche(){
        return this.fliesenControl.leseFliesenSonderwuensche();
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
        if (!fliesenControl.pruefeKonstellationSonderwuensche(ph)) {
            return;
        }


        // Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
        // aus dem Control aufgerufen, dann die Sonderwuensche gespeichert.
    }


}


