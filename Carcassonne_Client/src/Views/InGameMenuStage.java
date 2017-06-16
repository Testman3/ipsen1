package Views;

import Controllers.LobbyController;
import Controllers.MenuController;
import Models.GameClient;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import commonFunctions.SmartLabel;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class InGameMenuStage extends Stage implements SceneInitialiser{
	private StackPane menuPane;
    private MenuController menuController;
    private SmartButton[] knoppen = new SmartButton[5];
    private Stage stage;
    private VBox allMenuItemsVBox;
    private SmartLabel titel;
    private Scene menuScene;
    private ImageView backgroud;
    private GameScene gameScene;
    private LobbyController lobbyController;
    private AudioClip ding = new AudioClip(Paths.get("Sounds/ding.wav").toUri().toString());

    //settings
	private StackPane settingsPane;
    private Scene settingsScene;
	private VBox buttonVBox;
	private HBox soundBox;
	private HBox spraakBox;
	private HBox fullscreenBox;
	private SmartLabel titelSettings;
	private SmartLabel sounds;
	private SmartLabel spraak;
	private SmartLabel fullscreen;
	private CheckBox soundCheckBox;
	private CheckBox spraakCheckBox;
	private CheckBox fullscreenCheckBox;
	private SmartButton backtoMenu;


	/**
	 * Constructor van de ingamemenustage
	 * @param controller
	 * Geef MenuController mee
	 * @param scene
	 * Geef GameScene mee
	 */
	public InGameMenuStage(MenuController controller, GameScene scene, LobbyController lobbyController){
    	this.gameScene = scene;
        this.menuController = controller;
        this.lobbyController = lobbyController;
        initGui();
    }

    @Override
    public void initGui() {
		backgroud = new ImageView();
        menuPane = new StackPane();
        menuPane.setId("inGameMenu");
		stage = new Stage(StageStyle.TRANSPARENT);
		allMenuItemsVBox = new VBox();
		menuScene = new Scene(menuPane);
		menuScene.setFill(null);

        //Css toevoegen
        menuPane.getStylesheets().add("style.css");

        backgroud.setFitHeight(800);
        backgroud.setFitWidth(600);
        backgroud.setId("BackgroundMenu");

        //Tekst in titel zetten.
        titel = new SmartLabel("Menu");
        titel.setId("title");

        //Titel toevoegen aan Box
        allMenuItemsVBox.getChildren().add(titel);

        //Maken Knoppen
        for (int i = 0; i < knoppen.length; i++) {
            knoppen[i] = new SmartButton();
            knoppen[i].setId("standardLabel");
            allMenuItemsVBox.getChildren().add(knoppen[i]);
        }

        //Tekst toevoegen
        knoppen[0].setText("Spel opslaan");
        knoppen[1].setText("Instellingen");
        knoppen[2].setText("Handleiding");
        knoppen[3].setText("Spel afsluiten");
        knoppen[4].setText("Terug naar spel");

        allMenuItemsVBox.setAlignment(Pos.CENTER);
		//Vbox toevoegen aan pane en background op empty
        menuPane.getChildren().addAll(backgroud, allMenuItemsVBox);
		menuPane.setBackground(Background.EMPTY);

		//Toevoegen aan stage
        stage.setScene(menuScene);
        stage.initOwner(menuController.getGameStage());
        stage.initModality(Modality.APPLICATION_MODAL);

        //Functie InitAction aanroepen
        initAction();
        initSettings();
    }

    private void initSettings(){

		settingsPane = new StackPane();
		settingsPane.getStylesheets().add("style.css");


		buttonVBox = new VBox();
		soundBox = new HBox(5);
		spraakBox = new HBox(5);
		fullscreenBox = new HBox(5);

		soundCheckBox = new CheckBox();
		spraakCheckBox = new CheckBox();
		fullscreenCheckBox = new CheckBox();

		soundCheckBox.setId("checkBox");
		soundCheckBox.setSelected(true);
		spraakCheckBox.setId("checkBox");
		fullscreenCheckBox.setId("checkBox");

		titel = new SmartLabel("Instellingen");
		sounds = new SmartLabel("Geluid");
		spraak = new SmartLabel("Spraakondersteuning");
		fullscreen = new SmartLabel("Fullscreen");
		backtoMenu = new SmartButton("Terug naar menu");

		titel.setId("title");
		sounds.setId("standardLabel");
		spraak.setId("standardLabel");
		fullscreen.setId("standardLabel");

		soundBox.getChildren().addAll(sounds, soundCheckBox);
		spraakBox.getChildren().addAll(spraak, spraakCheckBox);
		fullscreenBox.getChildren().addAll(fullscreen, fullscreenCheckBox);

		buttonVBox.getChildren().addAll(titel, soundBox, spraakBox, fullscreenBox, backtoMenu);

		settingsPane.getChildren().add(buttonVBox);

		buttonVBox.setAlignment(Pos.CENTER);
		soundBox.setAlignment(Pos.CENTER);
		spraakBox.setAlignment(Pos.CENTER);
		fullscreenBox.setAlignment(Pos.CENTER);

		settingsScene = new Scene(settingsPane);

	}

    @Override
    public void initAction(){

    	//Spel opslaan
		knoppen[0].setOnAction(event -> {
			gameScene.gameController.saveFileBrowser();
		});

		//Instellingen
		knoppen[1].setOnAction(event -> {
			stage.setScene(settingsScene);
		});

		//Handleiding
        File handleidingDoc = new File("Handleiding.html");
        knoppen[2].setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(handleidingDoc.toURI());
            } catch (IOException e1) {
                System.out.println("");
            }
        });

        //Spel afsluiten
		knoppen[3].setOnAction(event -> {
			Platform.runLater(() -> {
				Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION);

					ding.play();
					exitConfirmation.showAndWait().ifPresent(response -> {
						if (response == ButtonType.OK) {
							try {
								lobbyController.getRmiStub().removePlayer(menuController.getSpelernaam());
								System.exit(0);
							} catch (RemoteException e) {
							e.printStackTrace();}
						}
						else if (response == ButtonType.CANCEL){
							System.out.println("Canceled");
						}
					});
			});
		});

		//Terug naar spel
        knoppen[4].setOnAction(event -> {
            menuController.hideInGameMenu();
        });

    }

	/**
	 * Haalt de menuStage op.
	 * @return Geeft de stage terug
	 */
	public Stage getMenuStage(){
        return  this.stage;
    }
}
