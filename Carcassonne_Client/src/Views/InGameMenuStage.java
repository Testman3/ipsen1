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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    private GameClient gameClient;

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
    }

    @Override
    public void initAction(){

    	//Spel opslaan
		knoppen[0].setOnAction(event -> {
			gameScene.gameController.saveFileBrowser();
		});

		//Instellingen
		knoppen[1].setOnAction(event -> {
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

					exitConfirmation.showAndWait().ifPresent(response -> {
						if (response == ButtonType.OK) {
							try {
								gameClient.getRmiStub().beeindigenBeurt(menuController.getSpelernaam());
								gameClient.getRmiStub().removePlayer(menuController.getSpelernaam());
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
