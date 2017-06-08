package Views;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Controllers.LobbyController;
import Controllers.MenuController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class LobbyScene extends Scene{
	private Label allePlayerNamen;
	private Label playersLabel;
	private HBox playerBox;
	private Button leaveGame;
	private Button startGame;
	private boolean enableThread;

	private FlowPane lobbyPane;
	MenuController controller;
	LobbyController lobbyController;

	Thread lobbyThread;
	ArrayList<String> allenamen;

	public LobbyScene(MenuController controller, LobbyController lobbyController){
		super(new FlowPane(), 400, 400);
		lobbyPane = (FlowPane) this.getRoot();
		init();
		this.controller = controller;
		this.lobbyController = lobbyController;
	}



	private void init(){

		playersLabel = new Label("Players in this game: ");
		playersLabel.setId("standardLabel");
		allePlayerNamen = new Label();
		allePlayerNamen.setText("empty");
		allePlayerNamen.setId("standardLabel");
		allePlayerNamen.setAlignment(Pos.CENTER);
		playerBox = new HBox();
		leaveGame = new Button("Leave Game");
		leaveGame.setMaxWidth(200);
		startGame = new Button("Start game");

		startGame.setOnAction(e -> {
			try {
				lobbyController.RMIstub.startenGame();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		playerBox.getChildren().addAll(playersLabel, allePlayerNamen);
		lobbyPane.getChildren().addAll(playerBox, leaveGame, startGame);

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
		lobbyThread = new Thread( () -> {
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
		lobbyThread.start();

	}

	boolean starten = false;

	public void Update() {
		allenamen = new ArrayList<String>();

		try {
		allenamen = lobbyController.RMIstub.getPlayerList();
		if(lobbyController.RMIstub.isGameStarted()){
			starten = true;
		}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}



		Platform.runLater(() -> {
			if(starten){
				controller.setGameScene();
			}
			allePlayerNamen.setText("");
		for (String string : allenamen) {
			allePlayerNamen.setText(allePlayerNamen.getText() + string);
		}});
	}
	public void setPlayerList(String spelers){
		allePlayerNamen.setText(spelers);
		System.out.println("WERK KUT DING");
	}

}