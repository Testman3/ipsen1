package Views;

import Controllers.MenuController;
import Models.Tile;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import commonFunctions.SmartLabel;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InGameMenuStage extends Stage implements SceneInitialiser{
    private MenuController menuController;
    private BorderPane menuPane;
    private SmartButton[] knoppen = new SmartButton[5];
    private Stage stage;
    private Scene menu;
    private VBox allMenuItemsVBox;
    private SmartLabel titel;
    private Scene menuScene;

    public InGameMenuStage(MenuController controller){
        this.menuController = controller;
        initGui();
    }

    @Override
    public void initGui() {
		
        menuPane = new BorderPane();
        menuPane.setId("inGameMenu");
		stage = new Stage(StageStyle.TRANSPARENT);
		allMenuItemsVBox = new VBox();
		menuScene = new Scene(menuPane);
		menuScene.setFill(null);

        //Css toevoegen
        menuPane.getStylesheets().add("style.css");

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

		//Vbox toevoegen aan pane en background op empty
        menuPane.setCenter(allMenuItemsVBox);
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

        File handleidingDoc = new File("Handleiding.html");
        knoppen[2].setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(handleidingDoc.toURI());
            } catch (IOException e1) {
                System.out.println(e1);
            }
        });

        knoppen[4].setOnAction(event -> {
            menuController.hideInGameMenu();
        });



    }

    public Stage getMenuStage(){
        return  this.stage;
    }
}
