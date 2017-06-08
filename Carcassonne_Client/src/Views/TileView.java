package Views;

import javafx.scene.image.ImageView;

public class TileView extends ImageView {

	//The size of a tile in pixels
	final int SIZE_X = 90;
	final int SIZE_Y = 90;

	GameScene scene;

	String kaartId;

	int x;
	int y;

	public TileView(int x, int y, GameScene scene){
		this.x = x;
		this.y = y;
		this.scene = scene;
		setFitHeight(90);
		setFitWidth(90);
		minHeight(90);
		minWidth(90);
		setPosition();
		setId("Empty");
		setOnMouseClicked(e -> {
			System.out.println("Clicked on " + x +  " " + y);
			if(scene.kaartPlaatsId != "") {
				System.out.println(scene.kaartPlaatsId);
				setId(scene.kaartPlaatsId);
				scene.kaartPlaatsId = "";
			}
			scene.addPreviews(x, y);
		});
	}

	/**
	 * Sets the posiion of the tile in the grid
	 */
	private void setPosition(){
		setLayoutX(x * SIZE_X);
		setLayoutY(y * SIZE_Y);
	}


}
