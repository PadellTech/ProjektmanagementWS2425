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
		//dbtool.executeSelect("SELECT * FROM Sonderwunschkategorie");
	}	
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}