package Views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BordView extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane mainPane = new BorderPane();
		Scene mainScene = new Scene(mainPane, 1280, 720);
		mainPane.getStylesheets().add("Views/markup.css");
		mainPane.setId("hallo");
		




		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
