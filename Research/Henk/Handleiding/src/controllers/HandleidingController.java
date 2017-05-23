package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HandleidingController extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage mainStage) throws Exception {
		File handleidingDoc = new File("Index.html");
		Button handleiding = new Button("Handleiding");
		Pane mainPane = new Pane();
		Scene mainScene = new Scene(mainPane, 400, 400);
		mainPane.getChildren().setAll(handleiding);
		
		mainStage.setScene(mainScene);
		mainStage.show();
		
		handleiding.setOnAction(e -> {
			try {
				Desktop.getDesktop().browse(handleidingDoc.toURI());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Handleiding niet gevonden!");
			}
		});
		
		
	}

}
