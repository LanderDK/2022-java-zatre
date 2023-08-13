package gui;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.DubbelGeselecteerdException;
import exceptions.SpelerNietGevondenException;
import exceptions.TeWeinigSpeelKansenException;
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

public class SelecteerSpelerScherm extends Pane {
	MenuItem exitMenuItem;
	MenuItem aboutMenuItem;
	Menu fileMenu;
	MenuItem englishMenuItem;
	MenuItem dutchMenuItem;
	Menu languageMenu;
	Label lblSelecteerSpelerScherm;
	Label lblUsername;
	Label lblJaar;
	Button btnTerug;
	private DomeinController dc = new DomeinController();
	private TextField txtUsername;
	private TextField txtJaar;
	private Button btnVoegToe;
	Locale locale_nl_NL = new Locale("nl", "NL");
	Locale locale_en_EN = new Locale("en", "EN");
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale_nl_NL);

	public SelecteerSpelerScherm(DomeinController dc) {
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

		lblSelecteerSpelerScherm = new Label(resourceBundle.getString("selectplayers"));
		lblSelecteerSpelerScherm.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		lblSelecteerSpelerScherm.setPadding(new Insets(40, 20, 20, 20));

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

		btnVoegToe = new Button(resourceBundle.getString("addplayer"));
		btnVoegToe.setOnAction(this::buttonVoegToePressed);
		btnVoegToe.setLayoutX(157);
		btnVoegToe.setLayoutY(165);

		btnTerug = new Button(resourceBundle.getString("terug"));
		btnTerug.setOnAction(this::buttonTerugPressed);
		btnTerug.setLayoutX(20);
		btnTerug.setLayoutY(165);

		this.getChildren().addAll(lblSelecteerSpelerScherm, lblUsername, txtUsername, lblJaar, txtJaar, btnTerug,
				btnVoegToe, menuBar);
	}
	
	private void resetText(){
		this.btnTerug.setText(resourceBundle.getString("terug"));
		this.exitMenuItem.setText(resourceBundle.getString("exit"));
		this.aboutMenuItem.setText(resourceBundle.getString("about"));
		this.fileMenu.setText(resourceBundle.getString("file"));
		this.languageMenu.setText(resourceBundle.getString("taal"));
		this.dutchMenuItem.setText(resourceBundle.getString("nederlands"));
		this.englishMenuItem.setText(resourceBundle.getString("engels"));
		this.lblUsername.setText(resourceBundle.getString("selectTcGebruikersnaam"));
		this.lblJaar.setText(resourceBundle.getString("selectTcGeboortejaar"));
		this.lblSelecteerSpelerScherm.setText(resourceBundle.getString("selectplayers"));
		this.btnVoegToe.setText(resourceBundle.getString("addplayer"));
		
	}

	private void buttonVoegToePressed(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		try {
			dc.selecteerSpeler(txtUsername.getText(), Integer.valueOf(txtJaar.getText()));
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(resourceBundle.getString("playerselected"));
			txtUsername.clear();
			txtJaar.clear();
		} catch (TeWeinigSpeelKansenException ex) {
			alert.setHeaderText(ex.getMessage());
		} catch (DubbelGeselecteerdException ex) {
			alert.setHeaderText(ex.getMessage());
		} catch (SpelerNietGevondenException ex) {
			alert.setHeaderText(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			alert.setHeaderText(ex.getMessage());
		} finally {
			alert.showAndWait();
		}
	}

	private void buttonTerugPressed(ActionEvent event) {
		SelecteerSchermMain ss = new SelecteerSchermMain(dc);
		Scene scene = new Scene(ss, 400, 400);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Zatre - Selectie");
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
