package main;
	
import domein.DomeinController;
import gui.BeginScherm;
import gui.RegistreerScherm;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class StartUpGui extends Application 
{
	@Override
	public void start(Stage primaryStage) 
	{
		try {
			DomeinController dc = new DomeinController();
			BeginScherm root = new BeginScherm(dc);
			Scene scene = new Scene(root,770,461);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Zatre - Main");
		    primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}