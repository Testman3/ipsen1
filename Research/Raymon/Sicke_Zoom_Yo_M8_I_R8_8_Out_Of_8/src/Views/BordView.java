package Views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BordView extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane mainPane = new BorderPane();

		mainPane.getStylesheets().add("Views/markup.css");
		mainPane.setId("hallo");

		HBox stommeKaarten = new HBox();
		HBox stommeKaarten2 = new HBox();

		Pane kaart1 = new Pane();
		Pane kaart2 = new Pane();
		Pane kaart3 = new Pane();
		Pane kaart4 = new Pane();

		kaart1.setId("kaart1");
		kaart2.setId("kaart2");
		kaart3.setId("kaart3");
		kaart4.setId("kaart4");

		kaart1.setMinWidth(50);
		kaart1.setMinHeight(50);
		kaart2.setMinWidth(50);
		kaart2.setMinHeight(50);
		kaart3.setMinWidth(50);
		kaart3.setMinHeight(50);
		kaart4.setMinWidth(50);
		kaart4.setMinHeight(50);

	stommeKaarten.getChildren().addAll(kaart1, kaart2);
	stommeKaarten2.getChildren().addAll(kaart3, kaart4);
	mainPane.setLeft(stommeKaarten);
	mainPane.setRight(stommeKaarten2);
		Scene mainScene = new Scene(mainPane, 1280, 720);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
