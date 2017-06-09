package Views;

import Controllers.MenuController;
import Controllers.RMIController;
import Models.RMIInterface;
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

public class GameScene extends Scene {

	int sceneHeight = (int) getHeight();
	int sceneWidth = (int) getWidth();

	MenuController controller;
	BorderPane Spane;

	Pane tilesPane;
	HBox test;

	TileView[][] tileViews;

	RMIInterface RmiStub;

	Thread gameThread;

	private boolean enableThread;

	 public ImageView ShowKaart;

	private String spelerNaam;
	public String kaartPlaatsId = "";

	public GameScene(MenuController menuController) {
		//	super(new Pane(), 1280, 720);
		super(new Pane(), 1280, 720);
		getStylesheets().add("style.css");
		tilesPane = (Pane) this.getRoot();
		this.controller = menuController;

		//Spane.getChildren().add(tilesPane);
		createTileGrid(100, 100);
		tileViews[10][10].setKaartId("Kaart_04");
		addPreviews(10,10);
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
		//tilesPane.getChildren().add(verticaal);
		for (int y = 0; y < sizeX; y++) {
			HBox horizontal = new HBox();
			verticaal.getChildren().add(horizontal);
			for (int x = 0; x < sizeY; x++) {
				TileView tileView = new TileView(x, y, this);
				tileViews[x][y] = tileView;
				horizontal.getChildren().add(tileView);
			}
		}

		tilesPane.getChildren().add(verticaal);
		tilesPane.setId("hallo");
		//verticaal.setLayoutX(sceneWidth * 0.149);
		//verticaal.setLayoutY(0);

		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.W) {
				verticaal.setLayoutY(verticaal.getLayoutY() + 10);
			} else if (e.getCode() == KeyCode.A) {
				verticaal.setLayoutX(verticaal.getLayoutX() + 10);
			} else if (e.getCode() == KeyCode.S) {
				verticaal.setLayoutY(verticaal.getLayoutY() - 10);
			} else if (e.getCode() == KeyCode.D) {
				verticaal.setLayoutX(verticaal.getLayoutX() - 10);
			}
		});

	}

	public void addPreviews(int x, int y) {
		addPreview(x - 1, y);
		addPreview(x + 1, y);
		addPreview(x, y + 1);
		addPreview(x, y-1);
	}

	public void addPreview(int x, int y) {
		if (tileViews[x][y].getId().contains("Empty")) {
			tileViews[x][y].setId("KaartPreview");
		}
	}

	public void init() {

		//	 primaryStage.initStyle(StageStyle.UNDECORATED);

		//	Font.loadFont(main.class.getResource("Enchanted Land.otf").toExternalForm(), 10)


		//primaryStage.setFullScreen(true);

		//scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		Spane = new BorderPane();

		tilesPane.getChildren().add(Spane);
		Spane.setId("test");
		Spane.setPrefSize(1280, 720);
		Spane.setPickOnBounds(false);
		Spane.setOnMouseClicked(e -> {
			System.out.println("biep");
		});
		VBox links = new VBox(sceneHeight * 0.0);
		links.setPadding(new Insets(0, 0, 0, 20));

		Spane.setLeft(links);

		ImageView imgView = new ImageView();
		imgView.fitHeightProperty().bind(heightProperty().multiply(0.2));
		imgView.fitWidthProperty().bind(widthProperty().multiply(0.11));
		//imgView.setId("Speler");
		links.getChildren().add(imgView);

		for (int i = 0; i < 5; i++) {
			imgView = new ImageView();
			//	imgView.setFitHeight(sceneHeight * 0.1);
			imgView.maxHeight(100);
			imgView.prefHeight(100);
			imgView.fitHeightProperty().bind(heightProperty().multiply(0.1));
			imgView.fitWidthProperty().bind(widthProperty().multiply(0.11));
			imgView.setId("Speler");
			links.getChildren().add(imgView);
		}

		ShowKaart = new ImageView();
		ShowKaart.fitHeightProperty().bind(heightProperty().multiply(0.2));
		ShowKaart.fitWidthProperty().bind(widthProperty().multiply(0.11));
		ShowKaart.setId("Kaartview");

		links.getChildren().add(ShowKaart);
		ShowKaart.setOnMouseClicked(e -> {

			try {
				String id = RmiStub.pakKaart(controller.getSpelernaam());
				ShowKaart.setId(id);
				kaartPlaatsId = id;
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		HBox onder = new HBox();
		onder.setPadding(new Insets(0, 0, 0, 0));
		//links.getChildren().add(onder);
		Spane.setBottom(onder);

		Text KaartenLeft = new Text("Kaarten over: 999");
		links.getChildren().add(KaartenLeft);
		KaartenLeft.setId("KaartenLeft");

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

	public void setRmiStub(RMIInterface rmiController) {
		 RmiStub = rmiController;
	}

	public void Join(String spelerNaam) {

		gameThread = new Thread( () -> {
			while(enableThread == true){
				Update();
				System.out.println("Running...");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		gameThread.start();

	}


	public void Update() {




		Platform.runLater(() -> {
			});
	}


}