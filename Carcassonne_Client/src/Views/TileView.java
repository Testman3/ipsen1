package Views;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.rmi.RemoteException;

public class TileView extends Pane {

	//The size of a tile in pixels
	final int SIZE_X = 90;
	final int SIZE_Y = 90;

	GameScene scene;

	String kaartId;

	ImageView view;

	int x;
	int y;

	public TileView(int x, int y, GameScene scene){
		this.x = x;
		this.y = y;
		this.scene = scene;
		view = new ImageView();
		view.setFitHeight(90);
		view.setFitWidth(90);
		view.minHeight(90);
		view.minWidth(90);
		setPosition();
		view.setId("Empty");
		view.setOnMouseClicked(e -> {
			System.out.println("Clicked on " + x +  " " + y);
			scene.gameController.klikPlaatsKaart(x,y);
		});
		getChildren().add(view);
		minHeight(90);
		minWidth(90);
	}


	public String getimgId() {
		return view.getId();
	}
	/**
	 * Sets the posiion of the tile in the grid
	 */
	private void setPosition(){
		setLayoutX(x * SIZE_X);
		setLayoutY(y * SIZE_Y);
	}

	public void setKaartId(String Id) {
		kaartId = Id;
		view.setId(kaartId);
		laatHorigePreviewZien();
	}

	public void laatHorigePreviewZien() {

		Platform.runLater(() -> {
		double[][] posities = {{40,0},{0,40},{40,70},{70,40}};

		for(int i = 0; i < 4; i++){
			ImageView horigeView = new ImageView();
			horigeView.setFitHeight(20);
			horigeView.setFitWidth(20);
			horigeView.setId("horigePreview");
			getChildren().add(horigeView);
			horigeView.setLayoutX(posities[i][0]);
			horigeView.setLayoutY(posities[i][1]);
		}});

	}

}
