package Views;

import java.rmi.RemoteException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import Controllers.MenuController;

public class PreLobbyScene extends Scene{

	int maxTextFieldWidth = 200;
	int maxButtonWidth = 200;
	String playerName = "Testspeler";

	private MenuController controller;

	BorderPane mainPane;



	public PreLobbyScene(MenuController controller) {

		super(new BorderPane(), 400, 400);
		mainPane = (BorderPane) this.getRoot();
		this.controller = controller;

		try {
			init();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init() throws RemoteException {

		TextField naamVeld = new TextField("Player1");
		naamVeld.setMaxWidth(maxTextFieldWidth);
		TextField ipVeld = new TextField("127.0.0.1");
		ipVeld.setMaxWidth(maxTextFieldWidth);

		VBox joinViewButtons = new VBox(10);
		HBox playerBox = new HBox();
		// Scene mainScene = new Scene(pane, 400, 400);

		Button addPlayer = new Button("Join Game");
		addPlayer.setMaxWidth(maxButtonWidth);
		Button leaveGame = new Button("Leave Game");
		leaveGame.setMaxWidth(maxButtonWidth);
		Button localHost = new Button("Localhost");
		localHost.setMaxWidth(maxButtonWidth);
		Button remoteServer = new Button("Remote Server");
		remoteServer.setMaxWidth(maxButtonWidth);
		Button backToHome = new Button("Terug naar Hoofdmenu");
		backToHome.setMaxWidth(maxButtonWidth);

		FlowPane lobbyPane = new FlowPane();

		Label playersLabel = new Label("Players in this game: ");
		playersLabel.setFont(new Font("CALIBRI", 20));

		lobbyPane.getChildren().addAll(playerBox, leaveGame);

		joinViewButtons.getChildren().addAll(naamVeld, ipVeld, addPlayer, localHost, remoteServer, backToHome);
		joinViewButtons.setAlignment(Pos.CENTER);

		backToHome.setOnAction(e -> controller.backToMainMenu());
		addPlayer.setOnAction(e -> {
			try {
				controller.connectToServer(ipVeld.getText(), naamVeld.getText());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}

			if (controller.canConnect()){
				controller.setLobbyScene();
				try {
					controller.RMIstub.addPlayer(naamVeld.getText());
					controller.setSpelernaam(naamVeld.getText());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				controller.updatePlayerList();
			}
			else {
				System.out.println("Kan niet connecten dus switcht de scene niet");
			}

		});



		// mainPane.getChildren().setAll(lobbyButtons);
		mainPane.setTop(joinViewButtons);

	}


}

