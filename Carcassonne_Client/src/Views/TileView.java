package Views;

import Models.Horige;
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
	ImageView horigeView;

	int x;
	int y;

	public TileView(int x, int y, GameScene scene){
		minHeight(90);
		minWidth(90);
		maxHeight(90);
		maxWidth(90);
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
			System.out.println("Pane coords " + getLayoutX() + " " + getLayoutY());
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

	public void setRotation(int rotation ){
		view.setRotate(rotation);
	}
	public void setKaartId(String Id) {
		kaartId = Id;
		view.setId(kaartId);
		System.out.println("Coord of tile " + getLayoutX() + " " + getLayoutY() );
	}

	public void plaatsHorige(Horige horige){
	Platform.runLater(() -> {
		horigeView = new ImageView();
		horigeView.setId(horige.getSpeler().getHorigeKleur());
		horigeView.setFitHeight(20);
		horigeView.setFitWidth(20);
		getChildren().add(horigeView);
		horigeView.setLayoutX(horige.getPositie().getX());
		horigeView.setLayoutY(horige.getPositie().getY());
		System.out.println("Dit wordt gerunt!");
	});
	}
	public void laatHorigePreviewZien(Horige.Posities[] horigenZijdes) {

		Platform.runLater(() -> {
			System.out.println("Horige views length " + horigenZijdes.length);
			ImageView[] horigeViews = new ImageView[horigenZijdes.length];
			for (int i = 0; i < horigenZijdes.length; i++) {
				ImageView horigeView = new ImageView();
				horigeView.setFitHeight(20);
				horigeView.setFitWidth(20);
				horigeView.setId("horigePreview");

				getChildren().add(horigeView);
				horigeView.setLayoutX(horigenZijdes[i].getX());
				horigeView.setLayoutY(horigenZijdes[i].getY());

				horigeViews[i] = horigeView;
				final Horige.Posities pos = horigenZijdes[i];
				horigeView.setOnMouseClicked(e ->{
					for (int j = 0; j < horigeViews.length; j++) {
						for (ImageView horige: horigeViews) {
							getChildren().remove(horige);
						}
						scene.gameController.klikPlaatsHorige(pos);
					}
				});
			}
		});
	}
}
