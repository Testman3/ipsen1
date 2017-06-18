package Views;

import Controllers.GameController;
import Controllers.MenuController;
import Models.GameClient;
import Models.RMIInterface;
import Models.Speler;
import Models.TileStump;
import commonFunctions.Point;
import commonFunctions.SmartButton;
import commonFunctions.SmartLabel;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Deze class zorgt ervoor dat het daadwerkelijke speelbord goed wordt weergegeven.
 */
public class GameScene extends Scene {

	private MenuController controller;
	public GameController gameController;
	public BorderPane mainPane;
	private Pane tilesPane;
	private HBox test;
	private TileView[][] tileViews;
	private HorigeView[] horigeViews;
	private HorigeView[] horigeViewsInvisible;
	private SpelerView[] playerViews;
	private ImageView ShowKaart;
	private String kaartPlaatsId;
	private double scale;
	public RMIInterface RmiStub;
	private SmartLabel KaartenLeft;
	private SmartButton menuButton;
	private Point laatstGeplaatstLocatie;
	private SmartButton eindigButton;
	private SmartButton draaiButton;
	private HBox onderkant;
	private VBox links;
	private VBox rest;
	private HBox onderkantElement;
	private HBox horigeBox;
	private StackPane draaiBackground;
	private StackPane eindigBeurtBackground;
	private ImageView draaiImage;
	private ImageView eindigBeurtImage;
	private int breedte;
	private int hoogte;
	private String spelerKleur;


	/**
	 * Constructor van de GameScene
	 *
	 * @param menuController Geef MenuController mee
	 */
	public GameScene(MenuController menuController, int breedte, int hoogte) {
		//	super(new Pane(), 1280, 720);
		super(new Pane(), breedte, hoogte);
		getStylesheets().add("style.css");
		this.breedte = breedte;
		this.hoogte = hoogte;

		tilesPane = (Pane) this.getRoot();

		this.controller = menuController;
		createTileGrid(100, 100);

		init();
	}

	private void init() {
		// new shit
		mainPane = new BorderPane();
		links = new VBox(6);
		onderkant = new HBox(20);
		draaiButton = new SmartButton("Draaien");
		eindigButton = new SmartButton("Beëindig beurt");
		menuButton = new SmartButton("Menu");
		playerViews = new SpelerView[5];
		ShowKaart = new ImageView();
		horigeBox = new HBox(10);
		KaartenLeft = new SmartLabel("Stapel: 72");
		onderkantElement = new HBox(100);
		rest = new VBox();
		horigeViews = new HorigeView[7];
		draaiBackground = new StackPane();
		eindigBeurtBackground = new StackPane();
		draaiImage = new ImageView();
		eindigBeurtImage = new ImageView();
		
		// id
		mainPane.setId("uiBackground");
		draaiButton.setId("inGameKnoppen");
		draaiImage.setId("draaiImage");
		eindigBeurtImage.setId("eindigBeurtImage");
		eindigButton.setId("inGameKnoppen");
		menuButton.setId("menuKnop");
		ShowKaart.setId("Kaartview");
		KaartenLeft.setId("standardLabel");
		horigeBox.setId("horigeBox");
		links.setId("gameLinks");

		//setup Menubutton
		menuButton.setAlignment(Pos.BOTTOM_CENTER);


		//Size BorderPane
		mainPane.setPrefSize(1280, 720);
		mainPane.setMaxSize(1280, 720);
		mainPane.setMinSize(1280, 720);

		//size draaiButton
		draaiImage.setFitHeight(65);
		draaiImage.setFitWidth(200);

		//size eindigButton
		eindigBeurtImage.setFitHeight(65);
		eindigBeurtImage.setFitWidth(265);

		//Set PickOnBounds
		mainPane.setPickOnBounds(false);
		links.setPickOnBounds(false);
		onderkant.setPickOnBounds(false);
		rest.setPickOnBounds(false);
		onderkantElement.setPickOnBounds(false);

		// Set padding
		onderkant.setPadding(new Insets(0, 0, 0, 0));

		//Prop
		menuButton.minHeightProperty().bind(heightProperty().multiply(0.2));
		menuButton.minWidthProperty().bind(widthProperty().multiply(0.11));
		ShowKaart.fitHeightProperty().bind(heightProperty().multiply(0.2));
		ShowKaart.fitWidthProperty().bind(widthProperty().multiply(0.11));

		//setAlignment
		links.setAlignment(Pos.CENTER);
		rest.setAlignment(Pos.BOTTOM_CENTER);
		onderkantElement.setAlignment(Pos.CENTER);
		horigeBox.setAlignment(Pos.BOTTOM_CENTER);

		//GetChilderen
		draaiBackground.getChildren().addAll(draaiImage, draaiButton);
		eindigBeurtBackground.getChildren().addAll(eindigBeurtImage, eindigButton);
		links.getChildren().add(menuButton);

		//Speler Borden
		for (int i = 0; i < 5; i++) {
			playerViews[i] = new SpelerView();
			playerViews[i].setMinSize(150, 70);
			playerViews[i].setMaxSize(150, 70);
			//playerViews[i].maxHeightProperty().bind(heightProperty().multiply(0.1));
			//playerViews[i].maxWidthProperty().bind(widthProperty().multiply(0.1));
			links.getChildren().add(playerViews[i]);
		}

		links.getChildren().add(ShowKaart);
		links.getChildren().add(KaartenLeft);
		tilesPane.getChildren().add(mainPane);

		//Horige
		for (int i = 0; i < 7; i++) {
			horigeViews[i] = new HorigeView();
			horigeViews[i].setId("horigeZwart");
			horigeViews[i].fitHeightProperty().bind(heightProperty().multiply(0.07));
			horigeViews[i].fitWidthProperty().bind(horigeViews[i].fitHeightProperty());
			horigeBox.getChildren().add(horigeViews[i]);
		}

		onderkantElement.getChildren().add(draaiBackground);
		onderkantElement.getChildren().add(horigeBox);
		onderkantElement.getChildren().add(eindigBeurtBackground);
		rest.getChildren().add(onderkantElement);
		onderkant.getChildren().add(links);
		onderkant.getChildren().add(rest);

		mainPane.setBottom(onderkant);

		initAction();

	}

	public void initAction() {

		draaiButton.setOnAction(e -> {
			gameController.klikDraaiKaart();
		});

		eindigButton.setOnAction(e -> {
			gameController.klikBeeindigbeurt();
		});

		ShowKaart.setOnMouseClicked(e -> {
			gameController.klikPakKaart();
		});

		menuButton.setOnAction(event -> {
			controller.showInGameMenu();
		});
	}

	/**
	 * Creates the tile grid in the game client
	 *
	 * @param sizeX the x size of the grid (x amount of tiles)
	 * @param sizeY the y size of the grid (y amount of tiles)
	 */
	private void createTileGrid(int sizeX, int sizeY) {
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
		tilesPane.setId("spelBordBackground");

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
			} else if (e.getCode() == KeyCode.P) { // Get data
				gameController.saveFileBrowser();
			}
		});

		//Zoom Functie(Scrol event)
		tilesPane.setOnScroll(e -> {
			System.out.println("Werkt");
			e.consume();

			if (e.getDeltaY() == 0) {
				return;
			}

			double scaleFactor = (e.getDeltaY() > 0) ? 1.1 : (1 / 1.1);
			
			tilesPane.setScaleX(tilesPane.getScaleX() * scaleFactor);
			tilesPane.setScaleY(tilesPane.getScaleY() * scaleFactor);

			if (tilesPane.getScaleX() < 1.0) {
				tilesPane.setScaleX(1.0);
			}

			if (tilesPane.getScaleY() < 1.0) {
				tilesPane.setScaleY(1.0);
			}

			if (tilesPane.getScaleX() > 6.0) {
				tilesPane.setScaleX(6.0);
			}

			if (tilesPane.getScaleY() > 6.0) {
				tilesPane.setScaleY(6.0);
			}

		});
	}


	/**
	 * Plaatst previews om een tile heen, deze methode mag alleen gerunt worden nadat er een tile geplaatst is
	 *
	 * @param x x co-ordinaat
	 * @param y y co-ordinaat
	 */
	private void addTilePreviews(int x, int y) {
		addTilePreview(x - 1, y);
		addTilePreview(x + 1, y);
		addTilePreview(x, y + 1);
		addTilePreview(x, y - 1);
	}

	/**
	 * Plaatst 1 preview, deze methode mag niet zomaar gerunt worden
	 *
	 * @param x x co-ordinaat
	 * @param y y co-ordinaat
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

	/**
	 * Deze methode laat de kaart draaien
	 */
	public void DraaiKaart() {
		if (ShowKaart.getId().equals("Kaartview")) {
			return;
		}
		ShowKaart.setRotate(ShowKaart.getRotate() + 90);
	}

	/**
	 * Deze functie plaatst de kaart
	 *
	 * @param client Placeholder
	 * @param x      x co-ordinaat
	 * @param y      y co-ordinaat
	 */
	public void plaatsKaart(GameClient client, int x, int y) {
		ShowKaart.setId("Kaartview");
		try {
			TileStump stump = client.getTile();
			laatstGeplaatstLocatie = new Point(stump.getX(), stump.getY());
			tileViews[stump.getX()][stump.getY()].setRotation(stump.getRotation());
			tileViews[stump.getX()][stump.getY()].setKaartId(stump.getId());
			tileViews[x][y].laatHorigePreviewZien(RmiStub.getHorigePosities());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ShowKaart.setRotate(0);

	}

	public void verwijdwerHorigePreviews() {
		tileViews[laatstGeplaatstLocatie.getX()][laatstGeplaatstLocatie.getY()].verwijderHorigePreviews();
	}

	/**
	 * Deze functie laat de neergelegde kaart zien
	 *
	 * @param client Geef GameClient mee
	 */
	public void showKaart(GameClient client) {
		if (client.kaartPlaatsId == null) {
			ShowKaart.setId("Kaartview");
		} else
			ShowKaart.setId(client.kaartPlaatsId);
	}

	public void removeHorige(int x, int y) {
		tileViews[x][y].verwijderHorige();
	}

	int kaartenOver = 0;
	ArrayList<Speler> alleSpelers = null;

	/**
	 * Deze functie zorgt ervoor dat de view wordt geüpdatet
	 *
	 * @param client Geef GameClient mee
	 */
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
		if (stump.getGeplaatsteHorige() != null) {
			tileViews[stump.getX()][stump.getY()].plaatsHorige(stump.getGeplaatsteHorige());
			System.out.println("Horige is niet null!");
		}

		addTilePreviews(stump.getX(), stump.getY());
		System.out.println(stump.getX() + " " + stump.getY() + " " + stump.getRotation());
		Platform.runLater(() -> {
			KaartenLeft.setText("Stapel: " + kaartenOver);
			for (int i = 0; i < playerViews.length; i++) {
				if (i == alleSpelers.size()) {
					playerViews[i].setNaam("");
					playerViews[i].setPunten("");
					return;
				}
				if(alleSpelers.get(i).getNaam().equals(client.getSpelerBeurt())){
					playerViews[i].setHighlighted();
				} else {
					playerViews[i].setUnHighlighted();
				}
				playerViews[i].setNaam(alleSpelers.get(i).getNaam());
				playerViews[i].setPunten("" + alleSpelers.get(i).getPunten());


			}
		});
	}

	public MenuController getController() {
		return controller;
	}

	public void setSceneBlur() {
		GaussianBlur blur = new GaussianBlur();
		this.mainPane.setEffect(blur);
		tilesPane.setEffect(blur);
	}

	public void hideSceneBlur() {
		this.mainPane.setEffect(null);
		tilesPane.setEffect(null);
	}

	public void setPlayerBeurtKleur() {

	}

	public void setHorigeKleur(int spelerNummer){
		switch (spelerNummer) {
			case 0:
				spelerKleur = "horigeRood";
				break;
			case 1:
				spelerKleur = "horigeBlauw";
				break;
			case 2:
				spelerKleur = "horigeGroen";
				break;
			case 3:
				spelerKleur = "horigeGeel";
				break;
			case 4:
				spelerKleur = "horigePaars";
				break;
			default: spelerKleur = "horigeZwart";
		}

		for (int i = 0; i< 7 ; i++){
			horigeViews[i].setId(spelerKleur);
		}
	}

	/**
	 * Deze functie zorgt ervoor dat een horige de used texture gebruikt in de ui wanneer hij is geplaatst
	 */
	public void setHorigeUsed(){
		for (int i = 0; i < 7; i++){
			if (!horigeViews[i].getId().equals("horigeUsed")){
				horigeViews[i].setId("horigeUsed");
				break;
			}
		}
	}

	public void setHorigeReturned(){
		for (int i = 7; i > 0; i--){
			if (horigeViews[i].getId().equals("horigeUsed")) {
				horigeViews[i].setId(spelerKleur);
				break; }
			}
	}

	public void switchFullScreenMode(){
		controller.getGameStage().setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		controller.getGameStage().setFullScreenExitHint(null);
		controller.getGameStage().setFullScreen(SettingsScene.optieFullscreen);
		if (SettingsScene.optieFullscreen == true){
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			double schermBreedte = screenSize.getWidth();
			double schermHoogte = screenSize.getHeight();

			mainPane.setMinSize(schermBreedte, schermHoogte);
			getStylesheets().add("FullscreenStyle.css");
			getStylesheets().remove("style.css");
		}else {
			getStylesheets().add("style.css");
			getStylesheets().remove("FullscreenStyle.css");
			mainPane.setMinSize(breedte, hoogte);
		}
	}
}