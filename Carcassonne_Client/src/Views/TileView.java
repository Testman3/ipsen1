package Views;

import Models.Horige;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.rmi.RemoteException;

/**
 * Deze class verzorgt de correcte weergave van alle Tiles
 */
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

	/**
	 * Constructor van TileView
	 * @param x
	 * x co-ordinaat
	 * @param y
	 * y co-ordinaat
	 * @param scene
	 * Geef de GameScene mee
	 */
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

	/**
	 * Deze functie haalt het image id op
	 * @return het id van de imageview in de vorm van een String
	 */
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

	/**
	 * Deze functie stelt de rotatie in van de kaart
	 * @param rotation
	 * Geef een int mee om de gradatie van rotatie aan te geven
	 */
	public void setRotation(int rotation ){
		view.setRotate(rotation);
	}

	/**
	 * Deze functie stlet het ID van de kaart in
	 * @param Id
	 * Geef het id van de kaart mee in de vorm van een String
	 */
	public void setKaartId(String Id) {
		kaartId = Id;
		view.setId(kaartId);
		System.out.println("Coord of tile " + getLayoutX() + " " + getLayoutY() );
	}

	/**
	 * Deze functie zorgt voor het laten zien van de horige preview op de kaarten
	 * @param horigenZijdes
	 * Geef de zijden mee waarop een horige kan staan
	 */
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
