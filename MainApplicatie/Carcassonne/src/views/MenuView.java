package views;

import controllers.MenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuView extends Application {
	public static Button handleidingButton = new Button("Handleiding");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
		public void start(Stage mainStage) throws Exception {
			
			Pane mainPane = new Pane();
			Scene mainScene = new Scene(mainPane, 400, 400);
			mainPane.getChildren().setAll(handleidingButton);
			
			mainStage.setScene(mainScene);
			mainStage.show();
			
			MenuController.openHandleiding();
		
	}

}
