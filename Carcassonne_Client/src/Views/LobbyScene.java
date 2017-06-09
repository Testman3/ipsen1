package Views;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Controllers.LobbyController;
import Controllers.MenuController;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class LobbyScene extends Scene implements SceneInitialiser {

	private SmartButton leaveGame;
	private SmartButton startGame;
	private Label[] spelers;
	private boolean enableThread;

	private VBox completeBox;
	private HBox knoppenBox;

	private HBox box1;

	private VBox horigenBox;
	private VBox spelerBox;

	private ImageView [] horigen;

	private BorderPane lobbyPane;
	private MenuController controller;
	private LobbyController lobbyController;

	private Thread lobbyThread;
	private ArrayList<String> allenamen;

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
		spelerBox = new VBox(15);
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

		knoppenBox.getChildren().addAll(leaveGame, startGame);
		knoppenBox.setAlignment(Pos.CENTER);

		completeBox.getChildren().add(knoppenBox);
		completeBox.setAlignment(Pos.CENTER);

		lobbyPane.setCenter(completeBox);


		initAction();

	}

	public void initAction() {

		startGame.setOnAction(e -> {
			try {
				lobbyController.RMIstub.startenGame();
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

	public void Update() {
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
				controller.getGameScene().setRmiStub(lobbyController.getRmiStub());
				enableThread = false;
			}

			for (int i = 0; i < spelers.length; i++) {
				if (allenamen.size() <= i) {
					spelers[i].setText("Leeg");
				} else {
					spelers[i].setText(allenamen.get(i));
				}
			}
		});
	}


}