package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpeelSchermMain extends Pane {
	private GridPane gridScore;
	private DomeinController dc;
	private Label lblAanDeBeurt;
	private GridPane bord;
	private Button getrokkenSteentje1;
	private Button getrokkenSteentje2;
	private Button getrokkenSteentje3;
	Locale locale_nl_NL = new Locale("nl", "NL");
	Locale locale_en_EN = new Locale("en", "EN");
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale_nl_NL);
	MenuItem aboutMenuItem;
	MenuItem exitMenuItem;
	MenuItem stopMenuItem;
	Menu languageMenu;
	Menu fileMenu;
	MenuItem dutchMenuItem;
	MenuItem englishMenuItem;
	private String huidigeSpeler;

	public SpeelSchermMain(DomeinController dc) {
		this.dc = dc;
		buildgui(dc);
	}

	private void buildgui(DomeinController dc) {
		this.dc = dc;
		// main menubar die wordt aangemaakt
		this.getStylesheets().add(getClass().getResource("/domein/guiOpmaak.css").toExternalForm());
		BorderPane borderPane = new BorderPane();
		MenuBar menuBar = new MenuBar();
		borderPane.setTop(menuBar);

		// menu file + submenu about & exit
		fileMenu = new Menu("Bestand");
		MenuItem fullScreenMenuItem = new MenuItem("FullScreen");
		fullScreenMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+F11"));
		fullScreenMenuItem.setOnAction(this::buttonFullScreenPushed);
		MenuItem fullScreenWindowedMenuItem = new MenuItem("FullScreen Windowed");
		fullScreenWindowedMenuItem.setAccelerator(KeyCombination.keyCombination("F11"));
		fullScreenWindowedMenuItem.setOnAction(this::buttonFullScreenWindowedPushed);
		aboutMenuItem = new MenuItem("Over");
		aboutMenuItem.setOnAction(this::buttonAboutPushed);
		stopMenuItem = new MenuItem("Stop Huidig Spel");
		stopMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
		stopMenuItem.setOnAction(this::buttonStopPushed);
		exitMenuItem = new MenuItem("Sluit Applicatie");
		exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+x"));
		exitMenuItem.setOnAction(this::buttonExitPushed);

		fileMenu.getItems().addAll(fullScreenMenuItem, new SeparatorMenuItem(), fullScreenWindowedMenuItem,
				new SeparatorMenuItem(), aboutMenuItem, new SeparatorMenuItem(), stopMenuItem, new SeparatorMenuItem(),
				exitMenuItem);
		menuBar.getMenus().add(fileMenu);

		// menu taal + submenu dutch & english
		languageMenu = new Menu(resourceBundle.getString("taal"));
		dutchMenuItem = new MenuItem(resourceBundle.getString("nederlands"));
		dutchMenuItem.setOnAction(this::buttonDutchPushed);
		englishMenuItem = new MenuItem(resourceBundle.getString("engels"));
		englishMenuItem.setOnAction(this::buttonEnglishPushed);
		languageMenu.getItems().addAll(dutchMenuItem, new SeparatorMenuItem(), englishMenuItem);
		menuBar.getMenus().add(languageMenu);

		lblAanDeBeurt = new Label("");
		lblAanDeBeurt.setLayoutX(50);
		lblAanDeBeurt.setLayoutY(50);

		getrokkenSteentje1 = new Button();
		getrokkenSteentje1.setId("getrokkenSteentjeButton1");
		getrokkenSteentje1.setLayoutX(20);
		getrokkenSteentje1.setLayoutY(950);
		getrokkenSteentje1.setMinHeight(50);
		getrokkenSteentje1.setMinWidth(50);
		getrokkenSteentje1.setOnAction(this::getrokkenSteentjeClick);
		getrokkenSteentje2 = new Button();
		getrokkenSteentje2.setId("getrokkenSteentjeButton2");
		getrokkenSteentje2.setLayoutX(75);
		getrokkenSteentje2.setLayoutY(950);
		getrokkenSteentje2.setMinHeight(50);
		getrokkenSteentje2.setMinWidth(50);
		getrokkenSteentje2.setOnAction(this::getrokkenSteentjeClick);
		getrokkenSteentje3 = new Button();
		getrokkenSteentje3.setId("getrokkenSteentjeButton3");
		getrokkenSteentje3.setLayoutX(130);
		getrokkenSteentje3.setLayoutY(950);
		getrokkenSteentje3.setMinHeight(50);
		getrokkenSteentje3.setMinWidth(50);
		getrokkenSteentje3.setOnAction(this::getrokkenSteentjeClick);

		this.getChildren().addAll(menuBar, lblAanDeBeurt, getrokkenSteentje1, getrokkenSteentje2, getrokkenSteentje3);
		maakScoreBladButtons();
		refreshBord();
	}

	private void resetText() {
		this.exitMenuItem.setText(resourceBundle.getString("exit"));
		this.aboutMenuItem.setText(resourceBundle.getString("about"));
		this.stopMenuItem.setText(resourceBundle.getString("stopgame"));
		this.fileMenu.setText(resourceBundle.getString("file"));
		this.languageMenu.setText(resourceBundle.getString("taal"));
		this.dutchMenuItem.setText(resourceBundle.getString("nederlands"));
		this.englishMenuItem.setText(resourceBundle.getString("engels"));
		// als nederlands actief -> voeg spatie toe tussen naam & text
		// als nederlands !actief -> geen spatie
		if (resourceBundle == ResourceBundle.getBundle("res.bundle", locale_nl_NL))
			this.lblAanDeBeurt.setText(huidigeSpeler + " " + resourceBundle.getString("playersturn"));
		else
			this.lblAanDeBeurt.setText(huidigeSpeler + resourceBundle.getString("playersturn"));
		refreshScoreBlad(dc.geefScoreBladVanSpeler(huidigeSpeler).getScoreBlad());
	}

	private void maakScoreBladButtons() {
		List<String[]> spelers = dc.geefGeselecteerdeSpelers();
		int spatie = 1050;
		for (String[] speler : spelers) {
			Button btn = new Button(speler[0]);
			btn.setId(speler[0]);
			btn.setLayoutX(spatie);
			btn.setLayoutY(175);
			btn.setOnAction(this::spelerButtonKlik);
			spatie += 75;
			this.getChildren().add(btn);
		}
	}

	private void spelerButtonKlik(ActionEvent e) {
		Button btn = (Button) e.getSource();
		refreshScoreBlad(dc.geefScoreBladVanSpeler(btn.getId()).getScoreBlad());
	}

	public void updateBeurt(int[] steentjes, String gebruikersNaam, List<List<Integer>> scoreBlad) {
		legGetrokkenSteentjes(steentjes);
		this.huidigeSpeler = gebruikersNaam;
		lblAanDeBeurt.setText(gebruikersNaam + " is aan de beurt.");
		refreshScoreBlad(scoreBlad);

	}

	private void getrokkenSteentjeClick(ActionEvent e) {
		Button btn = (Button) e.getSource();
		if (btn.getId() == "getrokkenSteentjeButton1")
			dc.getrokkenSteentjeClick(1);
		else if (btn.getId() == "getrokkenSteentjeButton2")
			dc.getrokkenSteentjeClick(2);
		else if (btn.getId() == "getrokkenSteentjeButton3")
			dc.getrokkenSteentjeClick(3);
	}

	private void legGetrokkenSteentjes(int[] steentjes) {
		if (steentjes[0] != 0) {
			getrokkenSteentje1.setVisible(true);
			getrokkenSteentje1.getStyleClass().clear();
			getrokkenSteentje1.getStyleClass().add("button-" + steentjes[0]);
		} else
			getrokkenSteentje1.setVisible(false);

		if (steentjes[1] != 0) {
			getrokkenSteentje2.setVisible(true);
			getrokkenSteentje2.getStyleClass().clear();
			getrokkenSteentje2.getStyleClass().add(String.format("button-" + steentjes[1]));
		} else
			getrokkenSteentje2.setVisible(false);
		if (steentjes[2] != 0) {
			getrokkenSteentje3.setVisible(true);
			getrokkenSteentje3.getStyleClass().clear();
			getrokkenSteentje3.getStyleClass().add(String.format("button-" + steentjes[2]));
		} else
			getrokkenSteentje3.setVisible(false);

	}

	private void refreshBord() {
		bord = new GridPane();
		bord.setLayoutX(20);
		bord.setLayoutY(45);
		bord.setHgap(5);
		bord.setVgap(5);
		bord.setLayoutY(100);
		// bord.getChildren().clear();// reset gridpane
		List<List<String[]>> spelBord = dc.geefSpelBord();
		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 15; y++) {
				Button btn = new Button();
				String[] vakje = spelBord.get(x).get(y);
				btn.setMinWidth(50);
				btn.setMinHeight(50);
				btn.setStyle("-fx-background-color: " + geefKleur(vakje[0]));
				if (vakje[0] == "muur")
					btn.setVisible(false);
				btn.getStyleClass().add("button-" + vakje[1]);
				btn.setOnAction(this::vakjeKlik);
				bord.add(btn, x, y);
			}
		}
		this.getChildren().add(bord);
	}

	public void vakjeKlik(ActionEvent e) {
		Button btn = (Button) e.getSource();
		dc.opVakjeGeklikt(GridPane.getColumnIndex(btn), GridPane.getRowIndex(btn));
		refreshBord();
	}

	private void refreshScoreBlad(List<List<Integer>> scoreBlad) {
		ArrayList<String> namenRijen = new ArrayList<>(Arrays.asList("x2", "10 (1pt)", "11 (2pt)", "12 (4pt)", "Bonus",
				resourceBundle.getString("totalScore")));
		gridScore = new GridPane();
		gridScore.setLayoutX(1050);
		gridScore.setLayoutY(250);
		int teller = 0;
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 24; y++) {
				if (y < 23) {
					TextField nameInput = new TextField();
					nameInput.setEditable(false);
					nameInput.setMaxWidth(120);
					nameInput.setMaxHeight(15);
					nameInput.setAlignment(Pos.BASELINE_RIGHT);
					if (y == 0) {
						nameInput.setStyle("-fx-background-color: #E1E1E1");
						nameInput.setText(namenRijen.get(teller));
						nameInput.setAlignment(Pos.BASELINE_CENTER);
						teller++;
						gridScore.add(nameInput, x, y);
						continue;
					}
					if (y == 22) {
						if (x == 0 || x == 1 || x == 2 || x == 3 || x == 4) {
							nameInput.setVisible(false);
						}
						if (x == 5) {
							nameInput.getStyleClass().add("-fx-text-box-border: black; -fx-border-radius:0px");
						}
					}
					if (y < scoreBlad.get(x).size()) {
						if (x == 0) {
							nameInput.setText("X");
						} else if (x == 1) {
							nameInput.setText(scoreBlad.get(x).get(y).toString());
							System.out.println(scoreBlad.get(x).get(y));
							String output = "";
							for (int i = 0; i < scoreBlad.get(1).get(y); i++) {
								output += "X";
							}
							nameInput.setText(output);
						} else if (x == 2) {
							int ber = scoreBlad.get(2).get(y) / 2;
							String output = "";
							for (int i = 0; i < ber; i++) {
								output += "X";
							}
							nameInput.setText(output);

						} else if (x == 3) {
							int ber = scoreBlad.get(3).get(y) / 4;
							String output = "";
							for (int i = 0; i < ber; i++) {
								output += "X";
							}
							nameInput.setText(output);
						}

						else {
							nameInput.setText(scoreBlad.get(x).get(y) == 0 ? "" : scoreBlad.get(x).get(y).toString());
						}
					}
					gridScore.add(nameInput, x, y);
				}

			}
		}
		this.getChildren().addAll(gridScore);
	}

	public void eindeSpel(String gebruikersNaam) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(gebruikersNaam + " " + resourceBundle.getString("endcurrentgame"));
		alert.showAndWait();
		BeginScherm bs = new BeginScherm(dc);
		Scene scene = new Scene(bs, 770, 461);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Zatre - Main");
		stage.setResizable(false);
		stage.show();
	}

	private String geefKleur(String vakje) {
		if (vakje == "wit")
			return "#ffffff";
		else if (vakje == "grijs")
			return "#8D8D8D";
		else
			return "#F4F4F4";
	}

	private void buttonFullScreenPushed(ActionEvent event) {
		Stage stage = (Stage) (this.getScene().getWindow());
		stage.setFullScreen(true);
	}

	private void buttonFullScreenWindowedPushed(ActionEvent event) {
		Stage stage = (Stage) (this.getScene().getWindow());
		stage.setFullScreen(false);
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

	private void buttonStopPushed(ActionEvent event) {
		Alert alertExit = new Alert(Alert.AlertType.CONFIRMATION);
		alertExit.setHeaderText(resourceBundle.getString("stopcurrentgamemessage"));
		Optional<ButtonType> result = alertExit.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			BeginScherm bs = new BeginScherm(dc);
			Scene scene = new Scene(bs, 770, 461);
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Zatre - Main");
			stage.setResizable(false);
			stage.show();
		}
	}

	private void buttonDutchPushed(ActionEvent event) {

		Alert alertToDo = new Alert(Alert.AlertType.WARNING);
//		alertToDo.setHeaderText("U heeft de taal Nederlands geselecteerd.");
//		alertToDo.showAndWait();
		resourceBundle = ResourceBundle.getBundle("res.bundle", locale_nl_NL);
		resetText();

	}

	private void buttonEnglishPushed(ActionEvent event) {
//		Alert alertToDo = new Alert(Alert.AlertType.WARNING);
//		alertToDo.setHeaderText("You have selected the language English.");
//		alertToDo.showAndWait();
		resourceBundle = ResourceBundle.getBundle("res.bundle", locale_en_EN);
		resetText();
	}
}