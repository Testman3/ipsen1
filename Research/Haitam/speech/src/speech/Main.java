package speech;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
        launch(args);
    }


	@Override
	public void start(Stage primaryStage) {
		System.out.println("Test");
		primaryStage.setTitle("Hello World!");
		Button btn = new Button();
		Praten tekts = new Praten("Hi alles goed, spel starten");
		Label teksttt = new Label("TEST");
		btn.setText("Say 'Hello World'");

		StackPane root = new StackPane();
		root.getChildren().add(tekts);
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}

}
