package Views;

import java.nio.file.Paths;
import java.rmi.RemoteException;

import commonFunctions.SceneInitialiser;
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
import javafx.scene.text.Text;

public class PreLobbyScene extends Scene implements SceneInitialiser{

	//Importing audioclip for click sound
	private AudioClip clickSound = new AudioClip(Paths.get("Sounds/Snd_Click.wav").toUri().toString());

	//Setting variables
	private int maxTextFieldWidth = 400;
	private int maxButtonWidth = 400;
	private String playerName = "Testspeler";
	private MenuController controller;
	private BorderPane mainPane;

	private TextField naamVeld;
	private TextField ipVeld;

	private VBox joinViewButtons;

	private Button addPlayer;
	private Button leaveGame;
	private Button backToHome;

	private Label playersLabel;

	@Override
	public void initGui() {
		mainPane.getStylesheets().add("style.css");
		mainPane.setId("mainBackground");

		naamVeld = new TextField("Player1");
		naamVeld.setMaxWidth(maxTextFieldWidth);
		naamVeld.setId("standardLabel");
		ipVeld = new TextField("127.0.0.1");
		ipVeld.setMaxWidth(maxTextFieldWidth);
		ipVeld.setId("standardLabel");

		joinViewButtons = new VBox(10);
		joinViewButtons.setId("schild");

		addPlayer = new Button("Join Game");
		addPlayer.setMaxWidth(maxButtonWidth);
		addPlayer.setId("standardLabel");

		leaveGame = new Button("Leave Game");
		leaveGame.setMaxWidth(maxButtonWidth);
		leaveGame.setId("standardLabel");

		backToHome = new Button("Terug naar Hoofdmenu");
		backToHome.setMaxWidth(maxButtonWidth);
		backToHome.setId("standardLabel");

		playersLabel = new Label("Players in this game: ");
		playersLabel.setFont(new Font("CALIBRI", 20));

		joinViewButtons.getChildren().addAll(naamVeld, ipVeld, addPlayer, backToHome);
		joinViewButtons.setAlignment(Pos.CENTER);

		mainPane.setCenter(joinViewButtons);

		InitAction();
	}

	@Override
	public void InitAction() {
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
	}

	public PreLobbyScene(MenuController controller) {

		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();
		this.controller = controller;
		initGui();
	}

}

