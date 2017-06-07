package Controllers;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClientManager extends Application {

	static MenuController menucontroller;

	public static void main(String[] args) {
		//Dit is de main, de launch is niet wit!
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
		menucontroller = new MenuController(stage);
	}
}

