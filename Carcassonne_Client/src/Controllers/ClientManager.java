package Controllers;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Deze class zorgt ervoor dat de client applicatie opgestart wordt
 */
public class ClientManager extends Application {

	static MenuController menucontroller;

	//Change debug to true, too see debug prints
	public static boolean debug = false;

	/**
	 * Dit is de mainfunctie die de client opstart
	 * @param args
	 * verplicht argument voor de main functie
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Deze functie start het daadwerkele venster van de applicatie
	 * @param primaryStage
	 * Start met een stage (venster) met de naam primaryStage
	 * @throws Exception
	 * Generieke javafx Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		runClient(primaryStage);

	}

	/**
	 * Start de client van de speler
	 * @param stage
	 * Geef de mainStage mee als parameter
	 */
	private static void runClient(Stage stage) {
		menucontroller = new MenuController(stage);
	}
}

