package Views;

import Controllers.MenuController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameScene extends Scene{

	MenuController controller;
	BorderPane Spane;

	public GameScene(MenuController menuController){
		super(new BorderPane(), 1080, 720);
		Spane = (BorderPane) this.getRoot();
		this.controller = menuController;
		init();
	}


	public void init(){
		//	 primaryStage.initStyle(StageStyle.UNDECORATED);

		//	Font.loadFont(main.class.getResource("Enchanted Land.otf").toExternalForm(), 10);

			int sceneHeight = (int) getHeight();
			int sceneWidth = (int) getWidth();


	//primaryStage.setFullScreen(true);

	        //scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
			getStylesheets().add("style.css");
			Spane.setId("test");


			VBox links = new VBox(sceneHeight * 0.01);
			links.setPadding(new Insets(0, 0, 0, 20));

			Spane.setLeft(links);

			ImageView imgView = new ImageView();
			imgView.fitHeightProperty().bind(heightProperty().multiply(0.2));
			imgView.fitWidthProperty().bind(widthProperty().multiply(0.11));
			//imgView.setId("Player");
			links.getChildren().add(imgView);

			for (int i = 0; i < 5; i++) {
				 imgView = new ImageView();
			//	imgView.setFitHeight(sceneHeight * 0.1);
				 imgView.maxHeight(100);
				 imgView.prefHeight(100);
				imgView.fitHeightProperty().bind(heightProperty().multiply(0.1));
				imgView.fitWidthProperty().bind(widthProperty().multiply(0.11));
				imgView.setId("Player");
				links.getChildren().add(imgView);

			}


			 imgView = new ImageView();
				imgView.fitHeightProperty().bind(heightProperty().multiply(0.2));
				imgView.fitWidthProperty().bind(widthProperty().multiply(0.11));
			imgView.setId("Kaartview");
			links.getChildren().add(imgView);

					HBox onder = new HBox();
					onder.setPadding(new Insets(0, 0, 40, 0));
			links.getChildren().add(onder);


			Text KaartenLeft = new Text("Kaarten over: 999");
			onder.getChildren().add(KaartenLeft);
			KaartenLeft.setId("KaartenLeft");

			for (int i = 0; i < 7; i++) {
				ImageView horige = new ImageView();
				horige.fitHeightProperty().bind(heightProperty().multiply(0.07));
				horige.fitWidthProperty().bind(horige.fitHeightProperty());
				horige.setId("Horige");
				onder.getChildren().add(horige);
			}
	}
}
