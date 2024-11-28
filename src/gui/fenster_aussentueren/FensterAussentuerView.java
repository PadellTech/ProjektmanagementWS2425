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

    private String[][] sonderwuensche;
 // --- Anfang Attribute der grafischen Oberfläche ---
    private Label[] lblPlatzhalter = new Label[9]; // Array für Labels
    private TextField[] txtPreisPlatzhalter = new TextField[9]; // Array für Textfelder
    private Label[] lblPlatzhalterEuro = new Label[9]; // Array für Euro Labels
    private CheckBox[] chckBxPlatzhalter = new CheckBox[9]; // Array für Checkboxes
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
        sonderwuensche = this.leseFensterAussentuerSonderwuensche();
        this.initKomponenten();
        
    }

    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Heizungs-Varianten");

        // Initialisieren der Arrays mit den entsprechenden Elementen
        for (int i = 0; i < 9; i++) {
            lblPlatzhalter[i] = new Label(sonderwuensche[i][0]);
            txtPreisPlatzhalter[i] = new TextField();
            lblPlatzhalterEuro[i] = new Label("Euro");
            chckBxPlatzhalter[i] = new CheckBox();

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
     * macht das FensterAussentuerView-Objekt sichtbar.
     */
    public void oeffneFensterAussentuerView(){
        super.oeffneBasisView();
    }

    private String[][] leseFensterAussentuerSonderwuensche(){
        return this.fatControl.leseFensterAussentuerSonderwuensche();
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


