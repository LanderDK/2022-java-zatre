package gui;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.foutAantalSpelersException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BeginScherm extends Pane 
{
	Locale locale_nl_NL = new Locale("nl", "NL");
	Locale locale_en_EN = new Locale("en", "EN");
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale_nl_NL);
	private DomeinController dc;
	private BorderPane borderPane;
	public BeginScherm(DomeinController dc) {
		this.dc = dc;
		buildgui();
	}

	private void buildgui() {

		// main menubar die wordt aangemaakt
		borderPane = new BorderPane();
		MenuBar menuBar = new MenuBar();
		borderPane.setTop(menuBar);

		// menu file + submenu about & exit
		Menu fileMenu = new Menu(resourceBundle.getString("file"));
		MenuItem aboutMenuItem = new MenuItem(resourceBundle.getString("about"));
		aboutMenuItem.setOnAction(this::buttonAboutPushed);
		MenuItem exitMenuItem = new MenuItem(resourceBundle.getString("exit"));
		exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+x"));
		exitMenuItem.setOnAction(this::buttonExitPushed);
		fileMenu.getItems().addAll(aboutMenuItem, new SeparatorMenuItem(), exitMenuItem);
		menuBar.getMenus().add(fileMenu);

		// menu taal + submenu dutch & english
		Menu languageMenu = new Menu(resourceBundle.getString("taal"));
		MenuItem dutchMenuItem = new MenuItem(resourceBundle.getString("nederlands"));
		dutchMenuItem.setOnAction(this::buttonDutchPushed);
		MenuItem englishMenuItem = new MenuItem(resourceBundle.getString("engels"));
		englishMenuItem.setOnAction(this::buttonEnglishPushed);
		languageMenu.getItems().addAll(dutchMenuItem, new SeparatorMenuItem(), englishMenuItem);
		menuBar.getMenus().add(languageMenu);

		ImageView ivImage = new ImageView(new Image(getClass().getResourceAsStream("/images/zatre.png")));

		Label lblWelkom = new Label(resourceBundle.getString("welkom"));
		lblWelkom.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		lblWelkom.setPadding(new Insets(200, 200, 200, 200));

		/*
		 * Label lblMaakKeuze = new Label("Maak uw keuze uit\nde opties hieronder:");
		 * lblMaakKeuze.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		 * lblMaakKeuze.setLayoutX(20); lblMaakKeuze.setLayoutY(90);
		 */

		Button btnRegistreer = new Button(resourceBundle.getString("registreer"));
		btnRegistreer.setLayoutX(280);
		btnRegistreer.setLayoutY(250);
		btnRegistreer.setMinWidth(100);
		btnRegistreer.setOnAction(this::buttonRegistreerPushed);

		Button btnSelecteer = new Button(resourceBundle.getString("selecteerspeler"));
		btnSelecteer.setLayoutX(390);
		btnSelecteer.setLayoutY(250);
		btnSelecteer.setMinWidth(100);
		btnSelecteer.setOnAction(this::buttonSelecteerPushed);

		Button btnStart = new Button(resourceBundle.getString("speelspel"));
		btnStart.setLayoutX(280);
		btnStart.setLayoutY(280);
		btnStart.setMinWidth(100);
		btnStart.setOnAction(this::buttonStartPushed);

		Button btnExit = new Button(resourceBundle.getString("verlaatspel"));
		btnExit.setLayoutX(390);
		btnExit.setLayoutY(280);
		btnExit.setMinWidth(100);
		btnExit.setOnAction(this::buttonExitPushed);

//		BackgroundImage myBI= new BackgroundImage(new Image("https://cdn.discordapp.com/attachments/914950375717609513/969587977397358622/zatre.png",32,32,false,true),
//		        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
//		          BackgroundSize.DEFAULT);
//		//then you set to your node
//		this.setBackground(new Background(myBI));

		this.getChildren().addAll(ivImage, btnRegistreer, btnSelecteer, btnStart, btnExit, menuBar);
	}

	private void buttonRegistreerPushed(ActionEvent event) {
		RegistreerScherm rs = new RegistreerScherm(dc);
		Scene scene = new Scene(rs, 400, 400);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Zatre - Registratie");
		stage.show();
	}

	private void buttonSelecteerPushed(ActionEvent event) {
		SelecteerSchermMain ss = new SelecteerSchermMain(dc);
		Scene scene = new Scene(ss, 400, 400);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Zatre - Selectie");
		stage.show();
	}

	private void buttonStartPushed(ActionEvent event) {
		try {
			this.dc.maakSpel(this.dc);
			SpeelSchermMain ssm = new SpeelSchermMain(this.dc);
			Scene scene = new Scene(ssm, 1800, 850);
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Zatre - Spel");
			stage.setMaximized(true);
			stage.setFullScreen(false);
			stage.show();
			dc.setSpeelScherm(ssm);
			dc.startSpel();
		} catch (foutAantalSpelersException ex) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText(ex.getMessage());
			alert.showAndWait();
		}
	}

	private void buttonAboutPushed(ActionEvent event) {
		Alert alertAbout = new Alert(Alert.AlertType.INFORMATION);
		alertAbout.setHeaderText(resourceBundle.getString("aboutmessage"));
		alertAbout.setContentText(resourceBundle.getString("aboutmessageDetails"));
		alertAbout.showAndWait();
	}

	private void buttonExitPushed(ActionEvent event) {
		Alert alertExit = new Alert(Alert.AlertType.CONFIRMATION);
		alertExit.setHeaderText(resourceBundle.getString("leavemessage"));
		Optional<ButtonType> result = alertExit.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			System.exit(0);
		}
	}

	private void buttonDutchPushed(ActionEvent event) {
		resourceBundle = ResourceBundle.getBundle("res.bundle", locale_nl_NL);
//		Alert alertToDo = new Alert(Alert.AlertType.WARNING);
//		alertToDo.setHeaderText("U heeft de taal Nederlands geselecteerd.");
//		alertToDo.showAndWait();
		buildgui();

	}

	private void buttonEnglishPushed(ActionEvent event) {
		resourceBundle = ResourceBundle.getBundle("res.bundle", locale_en_EN);
//		Alert alertToDo = new Alert(Alert.AlertType.WARNING);
//		alertToDo.setHeaderText("You have selected the language English.");
//		alertToDo.showAndWait();
		buildgui();

	}
}
