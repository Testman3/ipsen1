package Views;

import Controllers.MenuController;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import commonFunctions.SmartLabel;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InGameMenuStage extends Stage implements SceneInitialiser{
    private MenuController menuController;
    private BorderPane menuPane;
    private SmartLabel test;
    private SmartButton button;
    private Stage stage;
    private Scene menu;

    public InGameMenuStage(MenuController controller){
        this.menuController = controller;

        initGui();
    }

    @Override
    public void initGui() {
        test = new SmartLabel("Hallo");
        button = new SmartButton("Knopie");
        menuPane = new BorderPane();

        menuPane.setCenter(button);
        menuPane.setTop(test);

       // menuController.getGameStage().mainPane.setEffect(new GaussianBlur());

        stage = new Stage(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(menuPane, Color.TRANSPARENT));
//        stage.initOwner(menuController.getGameStage());
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setScene(new Scene(menuPane, Color.TRANSPARENT));

        initAction();
    }

    @Override
    public void initAction() {


    }
    public Stage getMenuStage(){
        return  this.stage;
    }
}
