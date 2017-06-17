package Views;

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
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
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
    private ImageView background;
    private GameScene gameScene;
    private GameClient gameClient;
    private AudioClip ding = new AudioClip(Paths.get("Sounds/ding.wav").toUri().toString());

    //settings
	private StackPane settingsPane;
    private Scene settingsScene;
	private VBox buttonVBox;
	private HBox soundBox;
	private HBox spraakBox;
	private HBox fullscreenBox;
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
	public InGameMenuStage(MenuController controller, GameScene scene, GameClient gameClient){
    	this.gameScene = scene;
        this.menuController = controller;
        this.gameClient = gameClient;
        initGui();
		initSettings();
    }

    @Override
    public void initGui() {
		background = new ImageView();
        menuPane = new StackPane();
        menuPane.setId("inGameMenu");
		stage = new Stage(StageStyle.TRANSPARENT);
		allMenuItemsVBox = new VBox();
		menuScene = new Scene(menuPane);
		menuScene.setFill(null);

        //Css toevoegen
        menuPane.getStylesheets().add("style.css");

        background.setFitHeight(800);
        background.setFitWidth(600);
        background.setId("BackgroundMenu");

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
        menuPane.getChildren().addAll(background, allMenuItemsVBox);
		menuPane.setBackground(Background.EMPTY);

		//Toevoegen aan stage
        stage.setScene(menuScene);
        stage.initOwner(menuController.getGameStage());
        stage.initModality(Modality.APPLICATION_MODAL);

        //Functie InitAction aanroepen
        initAction();
    }

    private void initSettings(){

		background = new ImageView();
		settingsPane = new StackPane();
		buttonVBox = new VBox();
		soundBox = new HBox(5);
		spraakBox = new HBox(5);
		fullscreenBox = new HBox(5);
		soundCheckBox = new CheckBox();
		spraakCheckBox = new CheckBox();
		fullscreenCheckBox = new CheckBox();
		titel = new SmartLabel("Instellingen");
		sounds = new SmartLabel("Geluid");
		spraak = new SmartLabel("Spraakondersteuning");
		fullscreen = new SmartLabel("Fullscreen");
		backtoMenu = new SmartButton("Terug naar menu");

		//Setup Css
		settingsPane.getStylesheets().add("style.css");

		//set Id
		background.setId("BackgroundMenu");
		soundCheckBox.setId("checkBox");
		spraakCheckBox.setId("checkBox");
		fullscreenCheckBox.setId("checkBox");
		titel.setId("title");
		sounds.setId("standardLabel");
		spraak.setId("standardLabel");
		fullscreen.setId("standardLabel");
		backtoMenu.setId("standardLabel");

		//set sound checkbox
		System.out.println("geluid = " + SettingsScene.optieGeluid );
		soundCheckBox.setSelected(SettingsScene.optieGeluid );
		spraakCheckBox.setSelected(SettingsScene.optieSpreken);
		fullscreenCheckBox.setSelected(SettingsScene.fullScreen);

		//Set Background Size
		background.setFitHeight(800);
		background.setFitWidth(600);

		//set Alignment
		soundBox.setAlignment(Pos.CENTER);
		spraakBox.setAlignment(Pos.CENTER);
		fullscreenBox.setAlignment(Pos.CENTER);
		buttonVBox.setAlignment(Pos.CENTER);
		buttonVBox.setAlignment(Pos.CENTER);
		soundBox.setAlignment(Pos.CENTER);
		spraakBox.setAlignment(Pos.CENTER);
		fullscreenBox.setAlignment(Pos.CENTER);

		//add Children
		soundBox.getChildren().addAll(sounds, soundCheckBox);
		spraakBox.getChildren().addAll(spraak, spraakCheckBox);
		fullscreenBox.getChildren().addAll(fullscreen, fullscreenCheckBox);
		buttonVBox.getChildren().addAll(titel, soundBox, spraakBox, fullscreenBox, backtoMenu);
		settingsPane.getChildren().addAll(background, buttonVBox);

		//Set Stackpane Background
		settingsPane.setBackground(Background.EMPTY);

		//Add to scene
		settingsScene = new Scene(settingsPane);
		settingsScene.setFill(null);

		initActionSettings();

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

								gameClient.getRmiStub().leaveGame(gameClient.getSpelerNaam());

								gameClient.getRmiStub().beeindigenBeurt(gameClient.getSpelerNaam());

								gameClient.getRmiStub().removePlayer(gameClient.getSpelerNaam());
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

    private void initActionSettings(){

    	//Geluid
		soundCheckBox.setOnAction(event -> {
			SettingsScene.optieGeluid = soundCheckBox.isSelected();
		});

		//Spraak
		spraakCheckBox.setOnAction(event -> {
			SettingsScene.optieSpreken = spraakCheckBox.isSelected();

		});

		//FullScreen
		fullscreenCheckBox.setOnAction(event -> {
			SettingsScene.fullScreen = fullscreenCheckBox.isSelected();
		});

    	//Terug naar menu
    	backtoMenu.setOnAction(event -> {
    		stage.setScene(menuScene);
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
