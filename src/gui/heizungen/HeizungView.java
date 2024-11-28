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
    private String[][] sonderwuensche;
 // --- Anfang Attribute der grafischen Oberfläche ---
    private Label[] lblPlatzhalter = new Label[5]; // Array für Labels
    private TextField[] txtPreisPlatzhalter = new TextField[5]; // Array für Textfelder
    private Label[] lblPlatzhalterEuro = new Label[5]; // Array für Euro Labels
    private CheckBox[] chckBxPlatzhalter = new CheckBox[5]; // Array für Checkboxes
    // --- Ende Attribute der grafischen Oberfläche ---

    /**
     * Erzeugt ein Heizung-Objekt, belegt das zugehörige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param heizungControl HeizungControl, enthält das zugehörige Control
     * @param heizungStage Stage, enthält das Stage-Objekt für diese View
     */
    public HeizungView(HeizungControl heizungControl, Stage heizungStage) {
        super(heizungStage);
        this.heizungControl = heizungControl;
        heizungStage.setTitle("Sonderwünsche zu Heizungsvarianten");

        sonderwuensche = this.leseHeizungSonderwuensche();
        this.initKomponenten();
    }

    /* Initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Heizungs-Varianten");

        // Initialisieren der Arrays mit den entsprechenden Elementen
        for (int i = 0; i < 5; i++) {
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
     * macht das HeizungView-Objekt sichtbar.
     */
    public void oeffneHeizungView(){
        super.oeffneBasisView();
    }

    private String[][] leseHeizungSonderwuensche(){
        return this.heizungControl.leseHeizungSonderwuensche();
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


