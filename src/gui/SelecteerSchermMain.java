package gui;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import domein.DomeinController;
import domein.Speler;
import domein.SpelerRepository;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SelecteerSchermMain extends Pane {
	MenuItem exitMenuItem;
	MenuItem aboutMenuItem;
	Menu fileMenu;
	MenuItem englishMenuItem;
	MenuItem dutchMenuItem;
	Menu languageMenu;
	Label lblSelecteerSchermMain;
	Button btnSelecteerSpelers;
	Button btnTerug;
	private TableView<Speler> tvSpelers;
	private DomeinController dc;
	Locale locale_nl_NL = new Locale("nl", "NL");
	Locale locale_en_EN = new Locale("en", "EN");
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale_nl_NL);
	TableColumn<Speler, String> tcGebruikersNaam;
	TableColumn<Speler, Integer> tcGebJaar;

	public SelecteerSchermMain(DomeinController dc) {
		this.dc = dc;
		buildgui();
	}

	private void buildgui() {
		// main menubar die wordt aangemaakt
		BorderPane borderPane = new BorderPane();
		MenuBar menuBar = new MenuBar();
		borderPane.setTop(menuBar);

		// menu file + submenu about & exit
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


		lblSelecteerSchermMain = new Label(resourceBundle.getString("selectzatre"));
		lblSelecteerSchermMain.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		lblSelecteerSchermMain.setPadding(new Insets(40, 20, 20, 20));

		btnSelecteerSpelers = new Button(resourceBundle.getString("selectplayers"));
		btnSelecteerSpelers.setOnAction(this::buttonSelecteerSpelerPressed);
		btnSelecteerSpelers.setLayoutX(20);
		btnSelecteerSpelers.setLayoutY(80);

		btnTerug = new Button(resourceBundle.getString("terug"));
		btnTerug.setOnAction(this::buttonTerugPressed);
		btnTerug.setLayoutX(20);
		btnTerug.setLayoutY(300);

		tvSpelers = new TableView<Speler>();
		tvSpelers.setPlaceholder(new Label(resourceBundle.getString("noplayerselected")));
		tvSpelers.setLayoutX(20);
		tvSpelers.setLayoutY(120);
		tvSpelers.setMinHeight(400);
		tvSpelers.setMinWidth(10);
		tcGebruikersNaam = new TableColumn<>("Gebruikersnaam");
		tcGebruikersNaam.setCellValueFactory(new PropertyValueFactory<>("gebruikersNaam"));
		tcGebJaar = new TableColumn<>("Geboortejaar");
		tcGebJaar.setCellValueFactory(new PropertyValueFactory<>("geboorteJaar"));
		tvSpelers.getColumns().addAll(tcGebruikersNaam, tcGebJaar);

		this.getChildren().addAll(lblSelecteerSchermMain, menuBar, btnSelecteerSpelers, tvSpelers, btnTerug);
		verversGeselecteerdeSpelers();
	}
	private void resetText(){
		this.btnTerug.setText(resourceBundle.getString("terug"));
		this.exitMenuItem.setText(resourceBundle.getString("exit"));
		this.aboutMenuItem.setText(resourceBundle.getString("about"));
		this.fileMenu.setText(resourceBundle.getString("file"));
		this.languageMenu.setText(resourceBundle.getString("taal"));
		this.dutchMenuItem.setText(resourceBundle.getString("nederlands"));
		this.englishMenuItem.setText(resourceBundle.getString("engels"));
		this.lblSelecteerSchermMain.setText(resourceBundle.getString("selectzatre"));
		this.btnSelecteerSpelers.setText(resourceBundle.getString("selectplayers"));
		this.tcGebruikersNaam.setText(resourceBundle.getString("selectTcGebruikersnaam"));
		this.tcGebJaar.setText(resourceBundle.getString("selectTcGeboortejaar"));
		
	}

	private void verversGeselecteerdeSpelers() {
		SpelerRepository sr = new SpelerRepository();
		for (Speler speler : sr.geefGeselecteerdeSpelers()) {
			tvSpelers.getItems().add(speler);
		}
	}

	public void toonSpeler(String[] speler) {
		if (speler != null) {
			Alert alertToonSpelers = new Alert(Alert.AlertType.INFORMATION);
			alertToonSpelers.setHeaderText(resourceBundle.getString("selectplayersshow"));
			alertToonSpelers.setContentText(resourceBundle.getString("username") + speler[0] + resourceBundle.getString("geboortejaar") + speler[1]
					+ resourceBundle.getString("andhas") + speler[2] + resourceBundle.getString("chances"));
			alertToonSpelers.showAndWait();
		}
	}

	private void buttonSelecteerSpelerPressed(ActionEvent event) {
		SelecteerSpelerScherm sss = new SelecteerSpelerScherm(dc);
		Scene scene = new Scene(sss, 400, 400);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Zatre - Selectie");
		stage.show();
	}

	private void buttonTerugPressed(ActionEvent event) {
		BeginScherm bs = new BeginScherm(dc);
		Scene scene = new Scene(bs, 770, 461);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Zatre - Main");
		stage.show();
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