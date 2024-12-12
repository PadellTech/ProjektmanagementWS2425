package gui.heizungen;

import gui.basis.BasisView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
    public void berechneUndZeigePreisSonderwuensche() {
        double gesamtpreis = 0.0;

        for (int i = 0; i < chckBxPlatzhalter.length; i++) {
            // Prüfen, ob die Checkbox ausgewählt ist
            if (chckBxPlatzhalter[i].isSelected()) {
                try {
                    // Den Preis aus der entsprechenden Spalte der sonderwuensche-Array lesen und addieren
                    double preis = Double.parseDouble(sonderwuensche[i][1]);
                    gesamtpreis += preis;
                } catch (NumberFormatException e) {
                    // Falls ein ungültiger Preis-String vorhanden ist, wird dieser ignoriert
                    System.err.println("Ungültiger Preis für Sonderwunsch an Position " + i + ": " + sonderwuensche[i][1]);
                }
            }
        }
        // Berechnen des Gesamtpreises

        // Neues Fenster erstellen
        Stage preisFenster = new Stage();
        preisFenster.initModality(Modality.APPLICATION_MODAL); // Modalität einstellen
        preisFenster.setTitle("Gesamtpreis");

        // Label mit dem Gesamtpreis
        Label lblGesamtpreis = new Label("Gesamtpreis der ausgewählten Sonderwünsche: " + gesamtpreis + " Euro");
        lblGesamtpreis.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");

        // Schließen-Button
        Button btnSchliessen = new Button("Schließen");
        btnSchliessen.setOnAction(e -> preisFenster.close());

        // Layout für das Fenster
        VBox vbox = new VBox(10, lblGesamtpreis, btnSchliessen);
        vbox.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        // Szene erstellen und zum Fenster hinzufügen
        Scene scene = new Scene(vbox, 800, 200);
        preisFenster.setScene(scene);

        // Fenster anzeigen
        preisFenster.showAndWait();
        System.out.println(gesamtpreis);
    }

    /* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
    protected void speichereSonderwuensche(){
        // Zählen der ausgewählten Checkboxen, um die Größe des Arrays festzulegen
        int count = 0;
        for (CheckBox checkBox : chckBxPlatzhalter) {
            if (checkBox.isSelected()) {
                count++;
            }
        }

        // Erstellen eines Arrays der richtigen Größe
        int[] ausgewaehlteSonderwuensche = new int[count];
        int index = 0;

        // Hinzufügen der IDs der ausgewählten Sonderwünsche in das Array
        for (int i = 0; i < chckBxPlatzhalter.length; i++) {
            if (chckBxPlatzhalter[i].isSelected()) {
                try {
                    // Die Sonderwunsch-ID wird aus dem zweidimensionalen Array `sonderwuensche` gelesen
                    int sonderwunschId = Integer.parseInt(sonderwuensche[i][2]); // Annahme: Spalte 2 enthält die ID
                    ausgewaehlteSonderwuensche[index] = sonderwunschId;
                    index++;
                } catch (NumberFormatException e) {
                    // Falls eine ungültige ID vorhanden ist, wird sie ignoriert
                    System.err.println("Ungültige ID für Sonderwunsch an Position " + i + ": " + sonderwuensche[i][2]);
                }
            }
        }
        this.heizungControl.speichereSonderwuensche(ausgewaehlteSonderwuensche);
    }

    @Override
    protected void csvExport() {

    }

}


