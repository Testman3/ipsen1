package Views;

import Models.Horige;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Deze class verzorgt de correcte weergave van alle Tiles
 */
public class TileView extends Pane {

	//The size of a tile in pixels
	final int SIZE_X = 90;
	final int SIZE_Y = 90;
	private GameScene scene;
	private String kaartId;
	private ImageView view;
	private ImageView horigeView;
	ImageView[] horigePreviews;

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
	TileView(int x, int y, GameScene scene){
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
	String getimgId() {
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
	void setRotation(int rotation){
		view.setRotate(rotation);
	}

	/**
	 * Deze functie stlet het ID van de kaart in
	 * @param Id
	 * Geef het id van de kaart mee in de vorm van een String
	 */
	void setKaartId(String Id) {
		kaartId = Id;
		view.setId(kaartId);
		System.out.println("Coord of tile " + getLayoutX() + " " + getLayoutY() );
	}

	/**
	 * Deze functie zorgt voor het neerzetten van de horige op een tile
	 * @param horige
	 * Geef de horige mee die geplaatst gaat worden
	 */
	void plaatsHorige(Horige horige){
		Platform.runLater(() -> {
			horigeView = new ImageView();
			horigeView.setId(horige.getSpeler().getHorigeKleur());
			horigeView.setFitHeight(20);
			horigeView.setFitWidth(20);
			getChildren().add( horigeView);
			horigeView.setLayoutX(horige.getPositie().getX());
			horigeView.setLayoutY(horige.getPositie().getY());
			System.out.println("Dit wordt gerunt!");
		});
	}

	public void verwijderHorige() {
		Platform.runLater(() -> {
			//getChildren().remove(horigeView);
			try {
				//System.out.println("" + this.getChildren());
				//TODO FIX THIS SHIT
				//this.getChildren().get(0).setId("");
				this.getChildren().remove(1);
			}catch (java.lang.IndexOutOfBoundsException e){
				//Doe niks wanneer de fout wordt opgevangen
			}
		});
		verwijderHorigePreviews();
	}

	public void verwijderHorigePreviews() {
		if(horigePreviews == null){
			return;
		}
		for (ImageView view : horigePreviews) {
			getChildren().remove(view);
		}
	}
	/**
	 * Deze functie zorgt voor het laten zien van de horige preview op de kaarten
	 * @param horigenZijdes
	 * Geef de zijden mee waarop een horige kan staan
	 */
	void laatHorigePreviewZien(Horige.Posities[] horigenZijdes) {

		Platform.runLater(() -> {
			System.out.println("Horige views length " + horigenZijdes.length);
			horigePreviews = new ImageView[horigenZijdes.length];

			for (int i = 0; i < horigenZijdes.length; i++) {
				horigePreviews[i] = new ImageView();
				horigePreviews[i].setFitHeight(20);
				horigePreviews[i].setFitWidth(20);
				horigePreviews[i].setId("horigePreview");

				getChildren().add(horigePreviews[i]);
				horigePreviews[i].setLayoutX(horigenZijdes[i].getX());
				horigePreviews[i].setLayoutY(horigenZijdes[i].getY());

				final Horige.Posities pos = horigenZijdes[i];
				horigePreviews[i].setOnMouseClicked(e ->{
						verwijderHorigePreviews();
						System.out.println("Horige geplaatst door " + scene.gameController.getModel().spelerNaam);
						scene.gameController.klikPlaatsHorige(pos);
				});
			}
		});
	}
}
