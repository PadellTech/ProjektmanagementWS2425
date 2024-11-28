package gui;


import gui.kunde.KundeControl;
import business.dbVerbindung.*;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		DBVerbindung dbtool = new DBVerbindung();
		try {
			dbtool.verbindung();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new KundeControl(primaryStage, dbtool);
		dbtool.executeUpdate("INSERT INTO Kunde (kundennummer, hausnummer, vorname, nachname, telefonnummer, email) " +"VALUES (3, 20, 'Max', 'Mustermann', '0123456789', 'max.mustermann@example.com');");
		dbtool.executeSelect("SELECT * FROM Kunde;");
		dbtool.executeSelect("SELECT * FROM Sonderwunschkategorie;");
		dbtool.executeSelect("SELECT * FROM Wunschoption;");
		
	}	
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}