package Views;

import Controllers.GameController;
import Controllers.LobbyController;
import Controllers.MenuController;
import Models.GameClient;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Deze class zorgt voor een juiste weergave van de LobbyScene
 */
public class LobbyScene extends Scene implements SceneInitialiser {

	private SmartButton leaveGame;
	private static SmartButton startGame;
	private Label[] spelers;
	private boolean enableThread;

	private VBox completeBox;
	private static HBox knoppenBox;

	private HBox box1;

	private VBox horigenBox;
	private VBox spelerBox;

	private ImageView[] horigen;

	private BorderPane lobbyPane;
	MenuController controller;
	LobbyController lobbyController;

	private Thread lobbyThread;
	ArrayList<String> allenamen;

	/**
	 * Constructor van de LobbyScene
	 * @param controller
	 * Geef de MenuController mee
	 * @param lobbyController
	 * Geef de lobbyController mee
	 */
	public LobbyScene(MenuController controller, LobbyController lobbyController) {
		super(new BorderPane(), 1280, 720);
		lobbyPane = (BorderPane) this.getRoot();

		this.controller = controller;
		this.lobbyController = lobbyController;

		initGui();
	}

	public void initGui() {

		lobbyPane.getStylesheets().add("style.css");
		lobbyPane.setId("mainBackground");

		box1 = new HBox(35);
		spelerBox = new VBox();
		horigenBox = new VBox(15);
		spelers = new Label[5];

		horigen = new ImageView[5];
		horigen[0] = new ImageView("Horige_Rood.png");
		horigen[1] = new ImageView("Horige_Blauw.png");
		horigen[2] = new ImageView("Horige_Geel.png");
		horigen[3] = new ImageView("Horige_Groen.png");
		horigen[4] = new ImageView("Horige_Zwart.png");

		knoppenBox = new HBox(60);
		completeBox = new VBox();
		knoppenBox.setPadding(new Insets(50, 0, 0, 0));
		box1.setPadding(new Insets(60, 0, 0, 0));
		completeBox.setId("schild");

		for (int i = 0; i < spelers.length; i++) {
			spelers[i] = new Label();
			spelers[i].setId("standardLabel");

			horigen[i].setFitHeight(50);
			horigen[i].setFitWidth(50);
			spelerBox.getChildren().addAll(spelers[i]);
			horigenBox.getChildren().add(horigen[i]);

		}

		box1.getChildren().addAll(spelerBox, horigenBox);
		completeBox.getChildren().add(box1);
		box1.setAlignment(Pos.CENTER);


		leaveGame = new SmartButton("Leave Game");
		leaveGame.setId("standardLabel");
		startGame = new SmartButton("Start game");
		startGame.setId("standardLabel");

		knoppenBox.getChildren().addAll(leaveGame);
		knoppenBox.setAlignment(Pos.CENTER);

		completeBox.getChildren().add(knoppenBox);
		completeBox.setAlignment(Pos.CENTER);

		lobbyPane.setCenter(completeBox);


		initAction();

	}

	public void initAction() {

		startGame.setOnAction(e -> {
			try {
				if(controller.loadedFile == null) {
					lobbyController.RMIstub.startenGame();
				} else {
					lobbyController.RMIstub.startenGame(controller.loadedFile);
				}

			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		leaveGame.setOnAction(e -> {

			try {
				lobbyController.RMIstub.removePlayer(controller.getSpelernaam());
				System.out.println(controller.getSpelernaam());


			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			controller.setPreLobbyScene();
			enableThread = false;
		});

	}

	/**
	 * Deze functie wordt aangeroepen wanneer je deze scene inkomt. er worden een aantal variabelen goedgezet, en de
	 * thread runt de Update functie elke 500 Ms
	 */
	public void Join() {
		enableThread = true;
		lobbyThread = new Thread(() -> {
			while (enableThread == true) {
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
		lobbyThread.start();

	}

	boolean starten = false;

	/**
	 * Deze functie update alle elementen in de lobby voor de client
	 */
	private void Update() {
		allenamen = new ArrayList<String>();

		try {
			allenamen = lobbyController.RMIstub.getPlayerList();
			if (lobbyController.RMIstub.isGameStarted()) {
				starten = true;
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		Platform.runLater(() -> {
			if (starten) {
				controller.setGameScene();
				GameClient client = new GameClient(controller.getGameScene());
				GameController Gamecontroller = new GameController(client);
				client.Join(controller.getSpelernaam());
				client.setRmiStub(lobbyController.getRmiStub());
				controller.getGameScene().gameController = Gamecontroller;
				enableThread = false;
			}

			for (int i = 0; i < spelers.length; i++) {
				if (allenamen.size() <= i) {
					spelers[i].setText("Leeg");
				} else {
					if (i == 0) {
						spelers[i].setText(allenamen.get(i) + (" (Host)"));
					} else {
						spelers[i].setText(allenamen.get(i));
					}
				}

			}
			//Controleer of de gekoppelde speler op de eerste plaats in de spelerlijst staat, en of hij nog geen stargame knop heeft
			//Als dit het geval is wordt de start game knop toegevoegd aan de hbox in de lobbyscene
			try {
				if (lobbyController.getRmiStub().getPlayerList().get(0).contains(controller.getSpelernaam()) && !knoppenBox.getChildren().contains(startGame)) {

					setAbleToStartGame();
				}

				//Als de gekoppelde speler niet meer op de eerste plaats staat maar nog wel de startgame knop heeft
				//verwijderen we de startgame knop voor de gekoppelde speler uit de hbox in lobbyscene
				else if (!lobbyController.getRmiStub().getPlayerList().get(0).contains(controller.getSpelernaam()) && knoppenBox.getChildren().contains(startGame)) {
					knoppenBox.getChildren().remove(startGame);
				}
			}catch (java.rmi.RemoteException e1){e1.printStackTrace();}
		});
	}

	/**
	 * Deze functie plaatst een startGame knop in de lobby voor de speler die de lobby leader is
	 */
	public static void setAbleToStartGame() {
		knoppenBox.getChildren().add(startGame);
	}

}