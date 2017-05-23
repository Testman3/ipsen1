package views;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import controllers.MenuController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MenuView extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	private Stage stage;
	private Scene scene;

	@Override
	public void start(Stage primaryStage) throws Exception {

		stage = primaryStage;

		VBox box = new VBox(10);

		/*
		 * 0 = New game 1 = Laden game 2 = Gebruiksaanwijzing 3 = About 4 =
		 * Instellingen 5 = Spel verlaten
		 */
		Button[] knoppen = new Button[6];
		MenuController controller = new MenuController();

		for (int i = 0; i < knoppen.length; i++) {
			knoppen[i] = new Button();
			box.getChildren().add(knoppen[i]);
		}

		box.setAlignment(Pos.CENTER);
		scene = new Scene(box, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();

		knoppen[0].setText("Nieuw spel");
		knoppen[0].setOnAction(e -> primaryStage.setScene(controller.setPreLobbyGame(this)));

		// Maakt een variabele aan die naar het handleiding document verwijst,
		// wanneer je op de handleiding knop drukt wordt het html doc geopend
		// in het default programma voor het openen van .html

		File handleidingDoc = new File("Handleiding.html");
		knoppen[2].setText("Gebruiksaanwijzing");
		knoppen[2].setOnAction(e -> {
			try {
				Desktop.getDesktop().browse(handleidingDoc.toURI());
			} catch (IOException e1) {
				System.out.println(e1);
			}
		});
		
		knoppen[5].setText("Spel afsluiten");
		knoppen[5].setOnAction(e -> System.exit(0));

	}

	public Stage getStage() {
		return stage;
	}

	public Scene getScene() {
		return scene;
	}

}
