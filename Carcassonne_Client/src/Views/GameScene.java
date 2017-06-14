package Views;

import Controllers.GameController;
import Controllers.MenuController;
import Models.GameClient;
import Models.RMIInterface;
import Models.Speler;
import Models.TileStump;
import commonFunctions.SmartLabel;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GameScene extends Scene {


	int sceneHeight = (int) getHeight();
	int sceneWidth = (int) getWidth();

	MenuController controller;
	public MenuController getController() { return controller;}
	GameController gameController;

	BorderPane mainPane;

	Pane tilesPane;
	HBox test;

	TileView[][] tileViews;
	HorigeView[] horigeViews;
	SpelerView[] playerViews;


	public ImageView ShowKaart;


	String kaartPlaatsId;
	private double scale;

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
			System.out.println("COORDS + " + verticaal.getLayoutX() + " " + verticaal.getLayoutY());
		});

		setOnScroll(event -> {
			if (event.getDeltaY() > 0) {
				if (scale < 10) {
					scale = verticaal.getScaleX() * 1.05;
					verticaal.setScaleY(scale);
					verticaal.setScaleX(scale);
					verticaal.setLayoutX(verticaal.getLayoutX() / verticaal.getScaleX());
					verticaal.setLayoutY(verticaal.getLayoutY() / verticaal.getScaleY());
				}
			}
			if (event.getDeltaY() < 0) {
				if (scale > 0.8) {
					scale = verticaal.getScaleX() * 0.95;
					verticaal.setScaleY(scale);
					verticaal.setScaleX(scale);
					verticaal.setLayoutX(verticaal.getLayoutX() / verticaal.getScaleX());
					verticaal.setLayoutY(verticaal.getLayoutY() / verticaal.getScaleY());

				}
			}
		});

	}


	/**
	 * Plaatst previews om een tile heen, deze methode mag alleen gerunt worden nadat er een tile geplaatst is
	 *
	 * @param x
	 * @param y
	 */
	public void addTilePreviews(int x, int y) {
		addTilePreview(x - 1, y);
		addTilePreview(x + 1, y);
		addTilePreview(x, y + 1);
		addTilePreview(x, y - 1);
	}

	/**
	 * Plaatst 1 preview, deze methode mag niet zomaar gerunnt worden
	 *
	 * @param x
	 * @param y
	 */
	private void addTilePreview(int x, int y) {
		if (x < 0 || y < 0) {
			return;
		}
		//Als de tile "Empty" is dan moet er een kaarPreview komen, empty houdt in dat er geen geplaatste tile in zit
		//Deze check is nodig om ervoor te zorgen dat tiles waar al een kaart in zit niet overschreven worden.
		if (tileViews[x][y].getimgId().contains("Empty")) {
			tileViews[x][y].setKaartId("KaartPreview");
		}

	}

	public void init() {

		mainPane = new BorderPane();
		tilesPane.getChildren().add(mainPane);
		mainPane.setId("test");
		mainPane.setPrefSize(1280, 720);
		mainPane.setMaxSize(1280,720);
		mainPane.setMinSize(1280,720);
		mainPane.setPickOnBounds(false);

		VBox links = new VBox(5);
		links.setPickOnBounds(false);
		//links.setPadding(new Insets(0, 0, 0, 20));

		HBox onder = new HBox();
		onder.setPickOnBounds(false);
		onder.getChildren().add(links);
		onder.setPadding(new Insets(0, 0, 0, 0));

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

		menuButton = new Button("Menu");
		menuButton.minHeightProperty().bind(heightProperty().multiply(0.2));
		menuButton.minWidthProperty().bind(widthProperty().multiply(0.11));
		menuButton.setId("standardLabel");
		links.getChildren().add(menuButton);

		playerViews = new SpelerView[5];
		for (int i = 0; i < 5; i++) {
			playerViews[i] = new SpelerView();
			playerViews[i].setMinSize(150,70);
			playerViews[i].setMaxSize(150,70);
			//playerViews[i].maxHeightProperty().bind(heightProperty().multiply(0.1));
			//playerViews[i].maxWidthProperty().bind(widthProperty().multiply(0.1));
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

		//links.getChildren().add(onder);
		//Spane.setLeft(onder);
		mainPane.setBottom(onder);
		KaartenLeft = new SmartLabel("Kaarten over: 72");
		links.getChildren().add(KaartenLeft);
		KaartenLeft.setId("standardLabel");


	//		mainPane.setCenter(ingamePane);
	}

	public void plaatsKaart(GameClient client, String id, int x, int y) {
		ShowKaart.setId("Kaartview");
		try {
			tileViews[x][y].laatHorigePreviewZien(RmiStub.getHorigePosities());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ShowKaart.setRotate(0);

	}

	public void showKaart(GameClient client) {
		ShowKaart.setId(client.kaartPlaatsId);

		

	}
	int kaartenOver = 0;
	ArrayList<Speler> alleSpelers = null;
	public void updateView(GameClient client) {
		TileStump stump = null;

		try {
			//Haalt het Tilestump object uit de server om hem vervolgens in de client te kunnen plaatsen
			stump = client.getTile();
			alleSpelers = RmiStub.getPlayerListObject();
			kaartenOver = RmiStub.getKaartenLeft();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		tileViews[stump.getX()][stump.getY()].setRotation(stump.getRotation());
		tileViews[stump.getX()][stump.getY()].setKaartId(stump.getId());
		addTilePreviews(stump.getX(), stump.getY());
		System.out.println(stump.getX() + " " + stump.getY() + " " + stump.getRotation());
		Platform.runLater(() -> {
		KaartenLeft.setText("Kaarten over " + kaartenOver);
			for (int i = 0; i < playerViews.length ; i++) {
				if(i == alleSpelers.size()){
					return;
				}
				playerViews[i].setNaam(alleSpelers.get(i).getNaam());
				playerViews[i].setPunten(alleSpelers.get(i).getPunten());
			}
		});
	}


}