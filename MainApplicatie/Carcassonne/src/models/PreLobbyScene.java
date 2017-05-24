package models;

import java.rmi.RemoteException;

import controllers.MenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import views.MenuView;

public class PreLobbyScene extends Scene {

	int maxTextFieldWidth = 200;
	int maxButtonWidth = 200;
	static LobbyInterface lobbyStub;
	String playerName = "Testspeler";
	Speler speler;
	

	BorderPane mainPane;
	private MenuView view = new MenuView();
	private MenuController controller = new MenuController();

	public PreLobbyScene(MenuView menu) {

		super(new BorderPane(), 400, 400);
		view = menu;
		mainPane = (BorderPane) this.getRoot();
		init(mainPane);
	}

	private void init(BorderPane pane) {

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

		backToHome.setOnAction(e -> view.getStage().setScene(view.getScene()));
		addPlayer.setOnAction(e -> {
			controller.connectToServer(ipVeld.getText(), naamVeld.getText());
			
			if (controller.canConnect()){
				view.getStage().setScene(controller.setLobbyScene(view));
				speler = new Speler(naamVeld.getText());
				controller.addPlayer(speler);
				try {
					controller.updatePlayerList();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				System.out.println("Kan niet connecten dus switcht de scene niet");
			}

		});
		
		
		

		// mainPane.getChildren().setAll(lobbyButtons);
		pane.setTop(joinViewButtons);

	}
	

}
