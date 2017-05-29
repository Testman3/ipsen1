package Controllers;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Views.EndGameScene;
import Views.GameScene;
import Views.LobbyScene;
import Views.MenuViewScene;
import Views.PreLobbyScene;
import Views.SettingsScene;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import Models.RMIInterface;

public class MenuController {

	Stage gameStage;

	EndGameScene endGameScene;
	GameScene gameScene;
	LobbyScene lobbyScene;
	MenuViewScene menuViewScene;
	PreLobbyScene preLobbyScene;
	SettingsScene settingsScene;

	private boolean ableToConnect;

	public RMIInterface RMIstub;

	public MenuController(Stage gameStage) {


		this.gameStage = gameStage;
		endGameScene = new EndGameScene();
		gameScene = new GameScene();
		lobbyScene = new LobbyScene(this);
		menuViewScene = new MenuViewScene(this);
		preLobbyScene = new PreLobbyScene(this);
		settingsScene = new SettingsScene();

		setMenuViewScene();
		gameStage.show();
	}


	public void connectToServer(String ip, String naam) throws RemoteException {
		if (!validateIP(ip)) {
			Alert alert = new Alert(AlertType.ERROR, "Dit is niet een geldig IP adres", ButtonType.OK);
			alert.showAndWait();
		} else {
			System.out.println("Getting access to the registry");
			Registry registry;

			try {
				registry = LocateRegistry.getRegistry(ip);
				System.out.println("Getting the Lobby stub from registry");
				RMIstub = (RMIInterface) registry.lookup("Lobby");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}

			if (RMIstub.checkContains(naam)) {
				Alert alert = new Alert(AlertType.ERROR, "Deze naam bestaat al in de lobby!", ButtonType.OK);
				alert.showAndWait();
				naam = "";
				RMIstub = null;
				registry = null;
				ableToConnect = false;
			} else {

				System.out.println("Joining the game as " + naam);
				//System.out.println(RMIstub.getPlayerList());
				ableToConnect = true;

}
		}
	}

	private boolean validateIP(final String ip) {
		Pattern pattern;
		Matcher matcher;
		String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		pattern = Pattern.compile(IPADDRESS_PATTERN);
		matcher = pattern.matcher(ip);
		return matcher.matches();
	}


	public boolean canConnect(){
		if (ableToConnect){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Button action method om de speler terug in het hoofd menu te krijgen
	 */
	public void backToMainMenu(){
		getGameStage().setScene(getMenuViewScene());
	}

	public void setEndGameScene(){
		gameStage.setScene(endGameScene);
	}
	public void setGameScene(){
		gameStage.setScene(gameScene);
	}

	public void setLobbyScene(){
		gameStage.setScene(lobbyScene);
	}

	public void setMenuViewScene(){
		gameStage.setScene(menuViewScene);
	}

	public void setPreLobbyScene(){
		gameStage.setScene(preLobbyScene);
	}
	public void setSettingsScene(){
		gameStage.setScene(settingsScene);
	}




	public Stage getGameStage() {
		return gameStage;
	}

	public EndGameScene getEndGameScene() {
		return endGameScene;
	}

	public GameScene getGameScene() {
		return gameScene;
	}

	public LobbyScene getLobbyScene() {
		return lobbyScene;
	}

	public MenuViewScene getMenuViewScene() {
		return menuViewScene;
	}

	public PreLobbyScene getPreLobbyScene() {
		return preLobbyScene;
	}

	public SettingsScene getSettingsScene() {
		return settingsScene;
	}


	public void updatePlayerList() {
		// TODO Auto-generated method stub

	}






}
