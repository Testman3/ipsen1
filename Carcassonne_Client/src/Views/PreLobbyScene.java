package Views;


import java.rmi.RemoteException;
import commonFunctions.SceneInitialiser;
import Controllers.LobbyController;
import commonFunctions.SmartButton;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import Controllers.MenuController;
/**
 * Deze class zorgt voor een goede weergave van de PreLobbyScene
 */
public class PreLobbyScene extends Scene implements SceneInitialiser{

	//Setting variables
	private int maxTextFieldWidth = 400;
	private int maxButtonWidth = 400;
	private String playerName = "Testspeler";
	private MenuController controller;
	private BorderPane mainPane;
	private LobbyController lobbyController;
	private TextField naamVeld;
	private TextField ipVeld;
	private VBox joinViewButtons;
	private SmartButton addPlayer;
	private SmartButton leaveGame;
	private SmartButton backToHome;
	private Label playersLabel;

	/**
	 * Constructor van PreLobbyScene
	 * @param controller
	 * Geef MenuController mee
	 * @param lobbyController
	 * Geef LobbyController mee
	 */
	public PreLobbyScene(MenuController controller, LobbyController lobbyController) {

		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();
		this.controller = controller;
		this.lobbyController = lobbyController;
		initGui();
	}

	@Override
	public void initGui() {
		mainPane.getStylesheets().add("style.css");
		mainPane.setId("mainBackground");

		naamVeld = new TextField("Player1");
		naamVeld.setMaxWidth(maxTextFieldWidth);
		naamVeld.setId("tekstInvoer");
		ipVeld = new TextField("127.0.0.1");
		ipVeld.setMaxWidth(maxTextFieldWidth);
		ipVeld.setId("tekstInvoer");

		joinViewButtons = new VBox(10);
		joinViewButtons.setId("schild");

		addPlayer = new SmartButton("Join Game");
		addPlayer.setMaxWidth(maxButtonWidth);
		addPlayer.setId("standardLabel");

		leaveGame = new SmartButton("Leave Game");
		leaveGame.setMaxWidth(maxButtonWidth);
		leaveGame.setId("standardLabel");

		backToHome = new SmartButton("Terug naar Hoofdmenu");
		backToHome.setMaxWidth(maxButtonWidth);
		backToHome.setId("standardLabel");

		playersLabel = new Label("Players in this game: ");
		playersLabel.setFont(new Font("CALIBRI", 20));

		joinViewButtons.getChildren().addAll(naamVeld, ipVeld, addPlayer, backToHome);
		joinViewButtons.setAlignment(Pos.CENTER);

		mainPane.setCenter(joinViewButtons);

		initAction();
	}

	@Override
	public void initAction() {
		//Action voor de Terug naar Hoogdmenu knop
		backToHome.setOnAction(e -> {
			controller.backToMainMenu();
		});

		//Action voor de Join game knop
		addPlayer.setOnAction((ActionEvent e) -> {
			try {
				lobbyController.connectToServer(ipVeld.getText(), naamVeld.getText());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}

			if (lobbyController.canConnect()){
				controller.setLobbyScene();
				try {
					lobbyController.RMIstub.addPlayer(naamVeld.getText());
					controller.setSpelernaam(naamVeld.getText());

				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			else {
				System.out.println("Kan niet connecten dus switcht de scene niet");
			}

		});
	}

}