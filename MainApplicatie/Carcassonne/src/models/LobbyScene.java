package models;

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
import views.Lobby;
import views.MenuView;
import views.ViewThread;

public class LobbyScene extends Scene {

	int maxTextFieldWidth = 200;
	int maxButtonWidth = 200;
	String localAddress = "127.0.0.1";
	String remoteAddress = "149.210.245.145";
	static Lobby lobbyStub;
	String playerName = "Testspeler";
	static Label player1;
	ViewThread t = new ViewThread();
	Thread thread = new Thread(t);

	BorderPane mainPane;
	private MenuController controller = new MenuController();
	private MenuView view = new MenuView();

	public LobbyScene(MenuView menu) {

		super(new BorderPane(), 400, 400);
		view = menu;
		mainPane = (BorderPane) this.getRoot();
		init(mainPane);
	}

	private void init(BorderPane pane) {

		TextField naamVeld = new TextField("Player1");
		naamVeld.setMaxWidth(maxTextFieldWidth);
		TextField ipVeld = new TextField(localAddress);
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

		player1 = new Label();
		player1.setText("empty");
		player1.setFont(new Font("CALIBRI", 15));
		player1.setAlignment(Pos.CENTER);

		playerBox.getChildren().addAll(playersLabel, player1);
		lobbyPane.getChildren().addAll(playerBox, leaveGame);

		joinViewButtons.getChildren().addAll(naamVeld, ipVeld, addPlayer, localHost, remoteServer, backToHome);
		joinViewButtons.setAlignment(Pos.CENTER);

		backToHome.setOnAction(e -> view.getStage().setScene(view.getScene()));

		// mainPane.getChildren().setAll(lobbyButtons);
		pane.setTop(joinViewButtons);

	}
}
