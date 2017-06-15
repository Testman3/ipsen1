package Views;

import Controllers.MenuController;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import commonFunctions.SmartLabel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

	/**
	 * Constructor van de ingamemenustage
	 * @param controller
	 * Geef MenuController mee
	 * @param scene
	 * Geef GameScene mee
	 */
	public InGameMenuStage(MenuController controller, GameScene scene){
    	this.gameScene = scene;
        this.menuController = controller;
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
        knoppen[3].setText("Terug naar het hoofdmenu");
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
                System.out.println(e1);
            }
        });

        //Terug naar hoofdMenu
		knoppen[3].setOnAction(event -> {

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
