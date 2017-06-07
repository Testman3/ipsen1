package filebrowser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class main extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//New button for save game
		Button idk = new Button("Save game");
		
		idk.setOnAction(e -> {
			// open window explorer
			FileChooser fileChooser = new FileChooser();
			
			// show json in explorer
			fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Carcassonne", "*.json")
			);
			
			// explorer window name
			fileChooser.setTitle("Save Game");
			// show explorer window
			File fle = fileChooser.showSaveDialog(primaryStage);
			//get path of selected file
			String path = fle.getPath();
			// File handeling in class fileManager
			fileManager.saveGame(path);
		});
		
		//new button for load game
		Button openen = new Button("Laad spel");
		openen.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Laadgame");
			File fle = fileChooser.showOpenDialog(primaryStage);
		});
		
		HBox pane = new HBox(10);
		pane.getChildren().addAll(idk, openen);
		Scene scene = new Scene(pane);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
