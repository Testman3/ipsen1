package Controllers;

import Views.SettingsScene;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ClientManager extends Application {

	static MenuController menucontroller;

	//Change debug to true, too see debug prints
	public static boolean debug = false;

	public static void main(String[] args) {
		//Dit is de main, de launch is niet wit!
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		//Als de username overeenkomt met overb wordt het onderstaande code blok NIET uitgevoerd
		if (!(System.getProperty("user.name").equals("overb")) || (System.getProperty("user.name").equals("Marti"))){
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		//System.out.println(System.getProperty("user.name"));
		//primaryStage.setX(primaryScreenBounds.getMinX());
		//primaryStage.setY(primaryScreenBounds.getMinY());
		//primaryStage.setWidth(primaryScreenBounds.getWidth());
		//primaryStage.setHeight(primaryScreenBounds.getHeight());

		//primaryStage.setResizable(false);
		}

		runClient(primaryStage);

	}
	/**
	 * Start de client van de speler
	 */
	public static void runClient(Stage stage) {
		menucontroller = new MenuController(stage);
	}
}

