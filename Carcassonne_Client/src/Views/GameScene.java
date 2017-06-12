package Views;

import Controllers.GameController;
import Controllers.MenuController;
import Controllers.RMIController;
import Models.GameClient;
import Models.RMIInterface;
import Models.TileStump;
import commonFunctions.SmartLabel;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import Models.TileStump;
import commonFunctions.SmartLabel;

public class GameScene extends Scene {

	int sceneHeight = (int) getHeight();
	int sceneWidth = (int) getWidth();

	MenuController controller;
	GameController gameController;

	BorderPane mainPane;

	Pane tilesPane;
	HBox test;

	TileView[][] tileViews;
	HorigeView[] horigeViews;
	ImageView[] playerViews;


	public ImageView ShowKaart;


	String kaartPlaatsId;

	public RMIInterface RmiStub;

	SmartLabel KaartenLeft;
	Button menuButton;

	public GameScene(MenuController menuController) {
		//	super(new Pane(), 1280, 720);
		super(new Pane(), 1280, 720);
		getStylesheets().add("style.css");
		tilesPane = (Pane) this.getRoot();
		this.controller = menuController;

		createTileGrid(100, 100);

		init();



	}

	/**
	 * Creates the tile grid in the game client
	 *
	 * @param sizeX the x size of the grid (x amount of tiles)
	 * @param sizeY the y size of the grid (y amount of tiles)
	 */
	public void createTileGrid(int sizeX, int sizeY) {
		tileViews = new TileView[sizeX][sizeY];
		VBox verticaal = new VBox();
		for (int y = 0; y < sizeY; y++) {
			HBox horizontal = new HBox();
			verticaal.getChildren().add(horizontal);
			for (int x = 0; x < sizeX; x++) {
				TileView tileView = new TileView(x, y, this);
				tileViews[x][y] = tileView;
				horizontal.getChildren().add(tileView);
			}
		}
		tilesPane.getChildren().add(verticaal);
		tilesPane.setId("hallo");

		//Verplaatsen over de map met W A S D keys, Speed is de snelheid dat je verplaatst.
		int speed = 20;
		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.W) {
				verticaal.setLayoutY(verticaal.getLayoutY() + speed);
			} else if (e.getCode() == KeyCode.A) {
				verticaal.setLayoutX(verticaal.getLayoutX() + speed);
			} else if (e.getCode() == KeyCode.S) {
				verticaal.setLayoutY(verticaal.getLayoutY() - speed);
			} else if (e.getCode() == KeyCode.D) {
				verticaal.setLayoutX(verticaal.getLayoutX() - speed);
			}
		});

	}


	/**
	 * Plaatst previews om een tile heen, deze methode mag alleen gerunt worden nadat er een tile geplaatst is
	 * @param x
	 * @param y
	 */
	public void addTilePreviews(int x, int y) {
		addTilePreview(x - 1, y);
		addTilePreview(x + 1, y);
		addTilePreview(x, y + 1);
		addTilePreview(x, y-1);
	}

	/**
	 * Plaatst 1 preview, deze methode mag niet zomaar gerunnt worden
	 * @param x
	 * @param y
	 */
	private void addTilePreview(int x, int y) {
		if(x < 0 || y < 0){
			return;
		}
		//Als de tile "Empty" is dan moet er een kaarPreview komen, empty houdt in dat er geen geplaatste tile in zit
		//Deze check is nodig om ervoor te zorgen dat tiles waar al een kaart in zit niet overschreven worden.
		if (tileViews[x][y].getId().contains("Empty")) {
			tileViews[x][y].setId("KaartPreview");
		}

	}

	public void init() {

		mainPane = new BorderPane();
		tilesPane.getChildren().add(mainPane);
		mainPane.setId("test");
		mainPane.setPrefSize(1280, 720);
		mainPane.setPickOnBounds(false);

		VBox links = new VBox(sceneHeight * 0.0);
		links.setPickOnBounds(false);
		links.setPadding(new Insets(0, 0, 0, 20));

		menuButton = new Button("Menu");
		menuButton.minHeightProperty().bind(heightProperty().multiply(0.2));
		menuButton.minWidthProperty().bind(widthProperty().multiply(0.11));
		menuButton.setId("standardLabel");
		links.getChildren().add(menuButton);

		playerViews = new ImageView[5];
		for (int i = 0; i < 5; i++) {
			playerViews[i] = new ImageView();
			playerViews[i].setFitHeight(sceneHeight * 0.1);
			playerViews[i].maxHeight(100);
			playerViews[i].prefHeight(100);
			playerViews[i].fitHeightProperty().bind(heightProperty().multiply(0.1));
			playerViews[i].fitWidthProperty().bind(widthProperty().multiply(0.11));
			playerViews[i].setId("Speler");
			links.getChildren().add(playerViews[i]);
		}

		ShowKaart = new ImageView();
		ShowKaart.fitHeightProperty().bind(heightProperty().multiply(0.2));
		ShowKaart.fitWidthProperty().bind(widthProperty().multiply(0.11));
		ShowKaart.setId("Kaartview");
		links.getChildren().add(ShowKaart);
		ShowKaart.setOnMouseClicked(e -> {
			gameController.klikPakKaart();
		});

		HBox onder = new HBox();
		onder.setPickOnBounds(false);
		onder.getChildren().add(links);
		onder.setPadding(new Insets(0, 0, 0, 0));
		//links.getChildren().add(onder);
		//Spane.setLeft(onder);
		mainPane.setBottom(onder);
		 KaartenLeft = new SmartLabel("Kaarten over: 72");
		links.getChildren().add(KaartenLeft);
		KaartenLeft.setId("standardLabel");

		for (int i = 0; i < 7; i++) {
			ImageView horige = new ImageView();
			horige.fitHeightProperty().bind(heightProperty().multiply(0.07));
			horige.fitWidthProperty().bind(horige.fitHeightProperty());
			horige.setId("Horige");
			onder.getChildren().add(horige);
		}

		Button button = new Button("Draaien");
		button.setId("standardLabel");
		onder.getChildren().add(button);
		button.setOnAction(e -> {
			try {
				RmiStub.draaiKaart();
				ShowKaart.setRotate(ShowKaart.getRotate() + 90);

			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

	}

	public void plaatsKaart(GameClient client, String id, int x, int y) {
		ShowKaart.setId("Kaartview");
		ShowKaart.setRotate(0);

	}

	public void showKaart(GameClient client) {
		ShowKaart.setId(client.kaartPlaatsId);

	}
	public void updateView(GameClient client) {
		TileStump stump = null;
		try {
			//Haalt het Tilestump object uit de server om hem vervolgens in de client te kunnen plaatsen
			stump = client.getTile();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		tileViews[stump.getX()][stump.getY()].setRotate(stump.getRotation());
		tileViews[stump.getX()][stump.getY()].setKaartId(stump.getId());
		addTilePreviews(stump.getX(), stump.getY());
		System.out.println(stump.getX() + " " + stump.getY() + " " + stump.getRotation());
	}



}