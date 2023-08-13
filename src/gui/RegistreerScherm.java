package gui;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Locale;
import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.SpelerBestaatAlException;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RegistreerScherm extends Pane
{
	private TextField txtUsername;
	private TextField txtJaar;
	private Button btnConfrim;
	Button btnTerug;
	MenuItem exitMenuItem;
	MenuItem aboutMenuItem;
	Menu fileMenu;
	MenuItem englishMenuItem;
	MenuItem dutchMenuItem;
	Menu languageMenu;
	Label lblJaar;
	Label lblUsername;
	Label lblRegistreerScherm;
	Locale locale_nl_NL = new Locale("nl", "NL");
	Locale locale_en_EN = new Locale("en", "EN");
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale_nl_NL);
	private DomeinController dc;

	public RegistreerScherm(DomeinController dc) {
		this.dc = dc;
		buildgui();
	}
	
	private void buildgui()
	{
		// main menubar die wordt aangemaakt
		BorderPane borderPane = new BorderPane ();
		MenuBar menuBar = new MenuBar();
		borderPane.setTop(menuBar);
				
		//menu file + submenu about & exit
		fileMenu = new Menu(resourceBundle.getString("file"));
		aboutMenuItem = new MenuItem(resourceBundle.getString("about"));
		aboutMenuItem.setOnAction(this::buttonAboutPushed);
		exitMenuItem = new MenuItem(resourceBundle.getString("exit"));
		exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+x"));
		exitMenuItem.setOnAction(this::buttonExitPushed);
		fileMenu.getItems().addAll(aboutMenuItem, new SeparatorMenuItem(), exitMenuItem);
		menuBar.getMenus().add(fileMenu);

		// menu taal + submenu dutch & english
		languageMenu = new Menu(resourceBundle.getString("taal"));
		dutchMenuItem = new MenuItem(resourceBundle.getString("nederlands"));
		dutchMenuItem.setOnAction(this::buttonDutchPushed);
		englishMenuItem = new MenuItem(resourceBundle.getString("engels"));
		englishMenuItem.setOnAction(this::buttonEnglishPushed);
		languageMenu.getItems().addAll(dutchMenuItem, new SeparatorMenuItem(), englishMenuItem);
		menuBar.getMenus().add(languageMenu);
		
		lblRegistreerScherm = new Label(resourceBundle.getString("registreer"));
		lblRegistreerScherm.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		lblRegistreerScherm.setPadding(new Insets(40, 20, 20, 20));
		
		lblUsername = new Label(resourceBundle.getString("selectTcGebruikersnaam"));
		lblUsername.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		lblUsername.setLayoutX(20);
		lblUsername.setLayoutY(93);
		
		txtUsername = new TextField("");
		txtUsername.setLayoutX(135);
		txtUsername.setLayoutY(90);
		
		lblJaar = new Label(resourceBundle.getString("selectTcGeboortejaar"));
		lblJaar.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		lblJaar.setLayoutX(20);
		lblJaar.setLayoutY(133);
		
		txtJaar = new TextField("");
		txtJaar.setLayoutX(135);
		txtJaar.setLayoutY(130);
		
		btnConfrim = new Button(resourceBundle.getString("registernow"));
		btnConfrim.setLayoutX(135);
		btnConfrim.setLayoutY(200);
		btnConfrim.setOnAction(this::registreerSpeler);
		
		btnTerug = new Button(resourceBundle.getString("terug"));
		btnTerug.setOnAction(this::buttonTerugPressed);
		btnTerug.setLayoutX(20);
		btnTerug.setLayoutY(200);
		
		this.getChildren().addAll(
				lblRegistreerScherm, lblUsername, txtUsername,
				lblJaar, txtJaar, btnConfrim, menuBar, btnTerug
				);
	}
	
	public void toonSpeler(String[] speler) {
		if (speler != null) {
			System.out.printf("%nGebruikersnaam: %s%n", speler[0]);
			System.out.printf("Geboorte jaar: %s%n", speler[1]);
			System.out.printf("En heeft momenteel %s speelkansen%n", speler[2]);
		}
	}
	
	public void registreerSpeler(ActionEvent event) 
	{
		Alert alertRegistreerdeSpeler = new Alert(Alert.AlertType.WARNING);
		try {
			dc.registreerSpeler(txtUsername.getText(), Integer.valueOf(txtJaar.getText()));
			String[] speler = dc.geefLaatstGemaakteSpeler();
			alertRegistreerdeSpeler = new Alert(Alert.AlertType.INFORMATION);
			alertRegistreerdeSpeler.setHeaderText(resourceBundle.getString("createaccountsucces"));
			alertRegistreerdeSpeler.setContentText(String.format(txtUsername.getText() +" geboren in " + Integer.valueOf(txtJaar.getText()) + " met " + speler[2] + " speelkans%s.", 
				Integer.valueOf(speler[2]) > 1 ? "en" : ""));
			txtUsername.clear();
			txtJaar.clear();
		} catch (SpelerBestaatAlException ex) {
			alertRegistreerdeSpeler.setHeaderText(ex.getMessage());
		} catch (InputMismatchException ex) {
			alertRegistreerdeSpeler.setHeaderText(ex.getMessage());
		} catch (NumberFormatException ex) {
			alertRegistreerdeSpeler.setHeaderText(resourceBundle.getString("wrong"));
		} catch (IllegalArgumentException ex) {
			alertRegistreerdeSpeler.setHeaderText(ex.getMessage());
		} finally {
			alertRegistreerdeSpeler.showAndWait();
		}
		
	}
	private void resetText(){
		this.btnTerug.setText(resourceBundle.getString("terug"));
		this.btnConfrim.setText(resourceBundle.getString("registernow"));
		this.exitMenuItem.setText(resourceBundle.getString("exit"));
		this.aboutMenuItem.setText(resourceBundle.getString("about"));
		this.fileMenu.setText(resourceBundle.getString("file"));
		this.languageMenu.setText(resourceBundle.getString("taal"));
		this.dutchMenuItem.setText(resourceBundle.getString("nederlands"));
		this.englishMenuItem.setText(resourceBundle.getString("engels"));
		this.lblRegistreerScherm.setText(resourceBundle.getString("registreer"));
		this.lblUsername.setText(resourceBundle.getString("selectTcGebruikersnaam"));
		this.lblJaar.setText(resourceBundle.getString("selectTcGeboortejaar"));
	}
	
	private void buttonConfirmPressed(ActionEvent event)
	{
		Alert alertToDo = new Alert(Alert.AlertType.WARNING);
		alertToDo.setHeaderText("Nog te implementeren!");
		alertToDo.showAndWait();
		BeginScherm bs = new BeginScherm(dc);
		Scene scene = new Scene(bs, 770,461);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Zatre - Main");
		stage.show();
	}
	
	private void buttonAboutPushed(ActionEvent event)
	{
		Alert alertAbout = new Alert(Alert.AlertType.INFORMATION);
		alertAbout.setHeaderText(resourceBundle.getString("aboutmessage"));
		alertAbout.setContentText(resourceBundle.getString("aboutmessageDetails"));
		alertAbout.showAndWait();
	}
	
	private void buttonExitPushed(ActionEvent event)
	{
		Alert alertExit = new Alert(Alert.AlertType.CONFIRMATION);
		alertExit.setHeaderText(resourceBundle.getString("leavemessage"));
		Optional<ButtonType> result = alertExit.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK)
		{
			System.exit(0);
		}
	}
	private void buttonTerugPressed(ActionEvent event) 
	{
		BeginScherm bs = new BeginScherm(dc);
		Scene scene = new Scene(bs, 770, 461);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Zatre - Main");
		stage.show();
	}
	
	private void buttonDutchPushed(ActionEvent event) {
		resourceBundle = ResourceBundle.getBundle("res.bundle", locale_nl_NL);
//		Alert alertToDo = new Alert(Alert.AlertType.WARNING);
//		alertToDo.setHeaderText("U heeft de taal Nederlands geselecteerd.");
//		alertToDo.showAndWait();
		resetText();

	}

	private void buttonEnglishPushed(ActionEvent event) {
		resourceBundle = ResourceBundle.getBundle("res.bundle", locale_en_EN);
//		Alert alertToDo = new Alert(Alert.AlertType.WARNING);
//		alertToDo.setHeaderText("You have selected the language English.");
//		alertToDo.showAndWait();
		resetText();

	}
}
