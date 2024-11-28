package gui.kunde;

import business.kunde.*;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Klasse, welche das Grundfenster mit den Kundendaten bereitstellt.
 */
public class KundeView{
 
	// das Control-Objekt des Grundfensters mit den Kundendaten
	private KundeControl kundeControl;
	// das Model-Objekt des Grundfensters mit den Kundendaten
	private KundeModel kundeModel;

    //---Anfang Attribute der grafischen Oberflaeche---
	private BorderPane borderPane 		= new BorderPane();
	private GridPane gridPane 			= new GridPane();
	private Label lblKunde    	      	= new Label("Kunde");
    private Label lblNummerHaus     	= new Label("Plannummer des Hauses");
    private ComboBox<Integer> 
        cmbBxNummerHaus                 = new ComboBox<Integer>();
    private Label lblVorname         	= new Label("Vorname");
    private TextField txtVorname     	= new TextField();
    private Label lblNachname         	= new Label("Nachname");
    private TextField txtNachname     	= new TextField(); 
    private Label lblNummer         	= new Label("Telefonnummer");
    private TextField txtNummer    		= new TextField(); 
    private Label lblEmail	        	= new Label("Email");
    private TextField txtEmail	     	= new TextField(); 
    private Button btnAnlegen	 	  	= new Button("Anlegen");
    private Button btnAendern 	      	= new Button("�ndern");
    private Button btnLoeschen 	 		= new Button("L�schen");
    private MenuBar mnBar 			  	= new MenuBar();
    private Menu mnSonderwuensche    	= new Menu("Sonderw�nsche");
    private MenuItem mnItmGrundriss  	= new MenuItem("Grundrissvarianten");
	private MenuItem mnItmSanitaer		= new MenuItem("Sanit�rvarianten");
	private MenuItem mnItmFliesen		= new MenuItem("Fliesenvarianten");
	private MenuItem mnItmInnentuer		= new MenuItem("Innent�rvarianten");
	private MenuItem mnItmHeizung		= new MenuItem("Heizungsvarianten");
	private MenuItem mnItmFensterAussentuer		= new MenuItem("Fenster und Au�ent�r-varianten");


	//-------Ende Attribute der grafischen Oberflaeche-------
  
    /**
     * erzeugt ein KundeView-Objekt und initialisiert die Steuerelemente der Maske
     * @param kundeControl KundeControl, enthaelt das zugehoerige Control
     * @param primaryStage Stage, enthaelt das Stage-Objekt fuer diese View
     * @param kundeModel KundeModel, enthaelt das zugehoerige Model
    */
    public KundeView (KundeControl kundeControl, Stage primaryStage, 
    	KundeModel kundeModel){
        this.kundeControl = kundeControl;
        this.kundeModel = kundeModel;
        
        primaryStage.setTitle(this.kundeModel.getUeberschrift());	
	    Scene scene = new Scene(borderPane, 550, 400);
	    primaryStage.setScene(scene);
        primaryStage.show();

	    this.initKomponenten();
	    this.initListener();
    }

 
    /* initialisiert die Steuerelemente auf der Maske */
    private void initKomponenten(){
    	borderPane.setCenter(gridPane);
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setPadding(new Insets(25, 25, 25, 25));
       	
	    gridPane.add(lblKunde, 0, 1);
       	lblKunde.setMinSize(150, 40);
	    lblKunde.setFont(Font.font("Arial", FontWeight.BOLD, 24));
	    gridPane.add(lblNummerHaus, 0, 2);
	    gridPane.add(cmbBxNummerHaus, 1, 2);
	    cmbBxNummerHaus.setMinSize(150,  25);
	    cmbBxNummerHaus.setItems(this.kundeModel.getPlannummern());
	    gridPane.add(lblVorname, 0, 3);
	    gridPane.add(txtVorname, 1, 3);
	    gridPane.add(lblNachname, 0, 4);
	    gridPane.add(txtNachname, 1, 4);
	    gridPane.add(lblNummer, 0, 5);
	    gridPane.add(txtNummer, 1, 5);
	    gridPane.add(lblEmail, 0, 6);
	    gridPane.add(txtEmail, 1, 6);
	    
	    // Buttons
	    gridPane.add(btnAnlegen, 0, 7);
	    btnAnlegen.setMinSize(150,  25);
	    gridPane.add(btnAendern, 1, 7);
	    btnAendern.setMinSize(150,  25);
	    gridPane.add(btnLoeschen, 2, 7);
	    btnLoeschen.setMinSize(150,  25);
	    // MenuBar und Menu
	    borderPane.setTop(mnBar);
	    mnBar.getMenus().add(mnSonderwuensche);
	    mnSonderwuensche.getItems().add(mnItmGrundriss);
		mnSonderwuensche.getItems().add(mnItmFensterAussentuer);
		mnSonderwuensche.getItems().add(mnItmInnentuer);
		mnSonderwuensche.getItems().add(mnItmHeizung);
		mnSonderwuensche.getItems().add(mnItmSanitaer);
		mnSonderwuensche.getItems().add(mnItmFliesen);
    }

    /* initialisiert die Listener zu den Steuerelementen auf de Maske */
    private void initListener(){
    	cmbBxNummerHaus.setOnAction(aEvent-> {
    		 holeInfoDachgeschoss();  
    		 leseKunden();
     	});
       	btnAnlegen.setOnAction(aEvent-> {
 	        legeKundenAn();
	    });
    	btnAendern.setOnAction(aEvent-> {
           	aendereKunden();
	    });
       	btnLoeschen.setOnAction(aEvent-> { 
           	loescheKunden();
	    });
      	mnItmGrundriss.setOnAction(aEvent-> {
 	        kundeControl.oeffneGrundrissControl(); 
	    });
		  mnItmSanitaer.setOnAction(aEvent -> {
			  kundeControl.oeffneSanitaerControl();
		  });
		  mnItmFliesen.setOnAction(aEvent -> {
			  kundeControl.oeffneFliesenControl();
		  });
		mnItmHeizung.setOnAction(aEvent -> {
			kundeControl.oeffneHeizungControl();
		});
		mnItmInnentuer.setOnAction(aEvent -> {
			kundeControl.oeffneInnentuerControl();
		});
		mnItmFensterAussentuer.setOnAction(aEvent -> {
			kundeControl.oeffneFensterAussentuercontrol();
		});
    }
    
    private void holeInfoDachgeschoss(){ 
    }
    
    private void leseKunden(){
    }
    
    private void legeKundenAn(){
         Kunde kunde = new Kunde(cmbBxNummerHaus.getValue() ,txtVorname.getText(), txtNachname.getText(), txtNummer.getText(), txtEmail.getText());
         // Objekt kunde fuellen
		 if(!kundeModel.validateUser(kunde)){
			 return;
		 }
         kundeControl.speichereKunden(kunde);
   	}
    
  	private void aendereKunden(){
   	}
  	
   	private void loescheKunden(){
   	}
   	
   /** zeigt ein Fehlermeldungsfenster an
    * @param ueberschrift, Ueberschrift fuer das Fehlermeldungsfenster
    * @param meldung, String, welcher die Fehlermeldung enthaelt
    */
    public void zeigeFehlermeldung(String ueberschrift, String meldung){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setHeaderText(ueberschrift);
        alert.setContentText(meldung);
        alert.show();
    }

}


