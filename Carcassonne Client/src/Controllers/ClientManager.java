package Controllers;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClientManager extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		runClient(primaryStage);

	}
	/**
	 * Start de client van de speler
	 */
	public static void runClient(Stage stage) {
		MenuController menucontroller = new MenuController(stage);
	}
}

