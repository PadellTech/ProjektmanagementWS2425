package gui.basis;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import business.kunde.*;
import gui.kunde.KundeView;

/**
 * Klasse, welche die Basis fuer die Fenster zu den Sonderwuenschen bereitstellt.
 */
public abstract class BasisView {
 
    //---Anfang Attribute der grafischen Oberflaeche---
	Stage sonderwunschStage;
	private BorderPane borderPane 		= new BorderPane();
	private GridPane gridPane 		 	= new GridPane();
	private GridPane gridPaneButtons 	= new GridPane();

   	private Label lblSonderwunsch   	= new Label("Sonderwunsch");
    private Button btnBerechnen 	 	= new Button("Preis berechnen");
    private Button btnSpeichern 	 	= new Button("Speichern");
	private Button btnExport			= new Button("CSV-Export");
    //-------Ende Attribute der grafischen Oberflaeche-------
  
   /**
    * erzeugt ein BasisView-Objekt
    */
    public BasisView(Stage sonderwunschStage){
    	this.sonderwunschStage = sonderwunschStage;
	    Scene scene = new Scene(borderPane, 560, 500);
	    sonderwunschStage.setScene(scene);
	
	    this.initListener();
    }

    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
      	borderPane.setCenter(gridPane);
		borderPane.setBottom(gridPaneButtons);

		gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setPadding(new Insets(25, 25, 25, 25));
	    gridPane.setStyle("-fx-background-color: #FED005;");
        gridPaneButtons.setHgap(10);
	    gridPaneButtons.setPadding(new Insets(25, 25, 25, 25));
	    
    	gridPane.add(lblSonderwunsch, 0, 0);
    	lblSonderwunsch.setFont(Font.font("Arial", FontWeight.BOLD, 24));
	    // Buttons

		gridPaneButtons.add(btnBerechnen, 1, 0);
	    btnBerechnen.setMinSize(150,  25);
    	gridPaneButtons.add(btnSpeichern, 2, 0);
	    btnSpeichern.setMinSize(150,  25);
		gridPaneButtons.add(btnExport,3,0);
		btnExport.setMinSize(150,  25);


	}
    
    /* Es muessen die Listener implementiert werden. */
    protected void initListener(){
		btnExport.setOnAction(aEvent ->{
			csvExport();
		});
       	btnBerechnen.setOnAction(aEvent -> {
    		berechneUndZeigePreisSonderwuensche();
     	});
        btnSpeichern.setOnAction(aEvent -> {
    		speichereSonderwuensche();
    	});
    }
    
    protected GridPane getGridPaneSonderwunsch() {
  		return this.gridPane;
  	}
	protected GridPane getGridPaneButtons(){
		return this.gridPaneButtons;
	}

  	protected Label getLblSonderwunsch() {
  		return lblSonderwunsch;
  	}
    public void Fehlermeldung(String meldung) {
        Stage meldungFenster = new Stage();
        meldungFenster.initModality(Modality.APPLICATION_MODAL); // Modalität einstellen
        meldungFenster.setTitle("Fehlermeldung");
        
        Label lblmeldung = new Label(meldung);
        lblmeldung.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        
        Button btnSchliessen = new Button("Schließen");
        btnSchliessen.setOnAction(e -> meldungFenster.close());
        
        // Layout für das Fenster
        VBox vbox = new VBox(10, lblmeldung, btnSchliessen);
        vbox.setStyle("-fx-padding: 20px; -fx-alignment: center;");
        
        // Szene erstellen und zum Fenster hinzufügen
        Scene scene = new Scene(vbox, 800, 200);
        meldungFenster.setScene(scene);
        
        // Fenster anzeigen
        meldungFenster.showAndWait();
    }
  	
  	/*
  	 * macht das BasisView-Objekt sichtbar.
  	 */
  	protected void oeffneBasisView(){ 
	    sonderwunschStage.showAndWait();
  	}
  	     	
  	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
  	protected abstract void berechneUndZeigePreisSonderwuensche();
  	
   	/* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
  	protected abstract void speichereSonderwuensche();

	/**
	 * Csv Export Methode
	 */
	protected abstract void csvExport();

}


