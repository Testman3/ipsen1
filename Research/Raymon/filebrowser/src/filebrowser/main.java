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
		// TODO Auto-generated method stub
		Button idk = new Button("Save game");
		idk.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Carcassonne", "*.json")
					);
			fileChooser.setTitle("Save Game");
			File fle = fileChooser.showSaveDialog(primaryStage);
			
			String path = fle.getPath();
			fileManager.saveGame(path);
			
			
		});
		
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
