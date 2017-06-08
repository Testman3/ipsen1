package Views;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Controllers.LobbyController;
import Controllers.MenuController;
import commonFunctions.SceneInitialiser;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class LobbyScene extends Scene implements SceneInitialiser {

	private Button leaveGame;
	private Button startGame;
	private Label[] spelers = new Label[5];
	private boolean enableThread;

	private VBox playerBox;
	private HBox knoppenBox;

	private BorderPane lobbyPane;
	MenuController controller;
	LobbyController lobbyController;

	Thread lobbyThread;
	ArrayList<String> allenamen;

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


		knoppenBox = new HBox(35);
		playerBox = new VBox();
		knoppenBox.setPadding(new Insets(50,0,0,0));
		playerBox.setId("schild");

		for (int i = 0; i < spelers.length; i++) {
			spelers[i] = new Label();
			spelers[i].setId("standardLabel");
			playerBox.getChildren().add(spelers[i]);
		}

		leaveGame = new Button("Leave Game");
		leaveGame.setId("standardLabel");
		startGame = new Button("Start game");
		startGame.setId("standardLabel");

		knoppenBox.getChildren().addAll(startGame, leaveGame);
		knoppenBox.setAlignment(Pos.CENTER);

		playerBox.getChildren().add(knoppenBox);
		playerBox.setAlignment(Pos.CENTER);

		lobbyPane.setCenter(playerBox);


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
			}

			for (int i = 0; i < spelers.length; i++) {
				if(allenamen.size() <= i ){
					spelers[i].setText("Leeg");
				} else {
					spelers[i].setText(allenamen.get(i));
				}
			}
		});
	}

}