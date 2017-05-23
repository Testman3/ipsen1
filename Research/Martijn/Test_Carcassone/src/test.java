import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

import com.sun.javafx.sg.prism.NodeEffectInput;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class test extends Application{

	 public static void main(String[] args) {
		launch(args);
	}

	 public static test instance;

	 Tegel[][] alleTegels;
	ArrayList<TegelData> alleTegelData;
	public TegelData currentTegel;

	@Override
	public void start(Stage primaryStage) throws Exception {
			CreateTiles();
			instance = this;
			VBox vbox = new VBox(1);
			alleTegels = new Tegel[20][20];


		for (int i = 0; i < 20; i++) {

			HBox hbox = new HBox(1);
			vbox.getChildren().add(hbox);

			for (int j = 0; j < 20; j++) {
				ImageView imageView = new ImageView();
				Tegel tegel = new Tegel(j,i, imageView);
				alleTegels[j][i] = tegel;
				hbox.getChildren().add(imageView);
			}
		}

		Button button = new Button("Rotate");
		button.setOnAction(e -> currentTegel.rotate());
		vbox.getChildren().add(button);
		currentTegel = randomTegel();
		Scene scene = new Scene(vbox, 1000,1000);

		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.W){
				vbox.setLayoutY(vbox.getLayoutY() + 10);
			} else 	if(e.getCode() == KeyCode.A){
				vbox.setLayoutX(vbox.getLayoutX() + 10);
			} else 	if(e.getCode() == KeyCode.S){
				vbox.setLayoutY(vbox.getLayoutY() - 10);
			} else 	if(e.getCode() == KeyCode.D){
				vbox.setLayoutX(vbox.getLayoutX() - 10);
			}


		});

		//scene.setOnScroll(e -> vbox.setLayoutY(vbox.getLayoutY() +  e.getDeltaY()));
		primaryStage.setScene(scene);
		primaryStage.show();
		scene.getStylesheets().add("style.css");
	}

	public int getMapWidth(){
		return alleTegels[0].length;
	}
	public int getMapHeight() {
		return alleTegels[1].length;
	}

	public Tegel getTegel(int x, int y){

		if(x > getMapWidth() || y > getMapHeight() || x < 0 || y < 0){
			return null;
		}
		return alleTegels[x][y];
	}

	public TegelData randomTegel() {
		Random rand = new Random();

		return alleTegelData.get(rand.nextInt(alleTegelData.size()));
	}

	public void CreateTiles() {
		alleTegelData = new ArrayList<TegelData>();

	}

}
