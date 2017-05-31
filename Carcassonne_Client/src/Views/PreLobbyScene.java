package Views;

import java.nio.file.Paths;
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
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import Controllers.MenuController;

public class PreLobbyScene extends Scene{

	private AudioClip clickSound = new AudioClip(Paths.get("Sounds/Snd_Click.wav").toUri().toString());
	int maxTextFieldWidth = 400;
	int maxButtonWidth = 400;
	String playerName = "Testspeler";

	private MenuController controller;

	BorderPane mainPane;



	public PreLobbyScene(MenuController controller) {

		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();
		this.controller = controller;

		try {
			init();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void init() throws RemoteException {
		mainPane.getStylesheets().add("style.css");
		mainPane.setId("mainBackground");

		TextField naamVeld = new TextField("Player1");
		naamVeld.setMaxWidth(maxTextFieldWidth);
		naamVeld.setId("textVelden");
		TextField ipVeld = new TextField("127.0.0.1");
		ipVeld.setMaxWidth(maxTextFieldWidth);
		ipVeld.setId("textVelden");

		VBox joinViewButtons = new VBox(10);

		Button addPlayer = new Button("Join Game");
		addPlayer.setMaxWidth(maxButtonWidth);
		addPlayer.setId("menuKnoppen");
		Button leaveGame = new Button("Leave Game");
		leaveGame.setMaxWidth(maxButtonWidth);
		leaveGame.setId("menuKnoppen");
		Button backToHome = new Button("Terug naar Hoofdmenu");
		backToHome.setMaxWidth(maxButtonWidth);
		backToHome.setId("menuKnoppen");

		Label playersLabel = new Label("Players in this game: ");
		playersLabel.setFont(new Font("CALIBRI", 20));



		joinViewButtons.getChildren().addAll(naamVeld, ipVeld, addPlayer, backToHome);
		joinViewButtons.setAlignment(Pos.CENTER);

		backToHome.setOnAction(e -> {
			clickSound.play();
			controller.backToMainMenu();
		});
		addPlayer.setOnAction(e -> {
			clickSound.play();
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
					e1.printStackTrace();
				}
				controller.updatePlayerList();
			}
			else {
				System.out.println("Kan niet connecten dus switcht de scene niet");
			}

		});

		mainPane.setCenter(joinViewButtons);

	}


}

