package controllers;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import models.LobbyInterface;
import models.LobbyScene;
import models.PreLobbyScene;
import models.Speler;
import views.MenuView;

public class MenuController {

	private PreLobbyScene preLobby;
	private MenuView menu;
	private  LobbyScene lobby;
	private  LobbyInterface lobbyStub;
	private boolean ableToConnect;
	Speler speler;
	
	public Scene setPreLobbyGame(MenuView view) {
		preLobby = new PreLobbyScene(view);
		return preLobby;
	}

	public Scene setMenuScene() {
		menu = new MenuView();
		return menu.getScene();
	}

	public Scene setLobbyScene(MenuView view) {
		lobby = new LobbyScene(view);
		return lobby;
	}

	public void connectToServer(String ip, String naam) {
		if (!validateIP(ip)) {
			Alert alert = new Alert(AlertType.ERROR, "Dit is niet een geldig IP adres", ButtonType.OK);
			alert.showAndWait();
		} else {
			System.out.println("Getting access to the registry");
			Registry registry;

			try {
				registry = LocateRegistry.getRegistry(ip);
				System.out.println("Getting the Lobby stub from registry");
				lobbyStub = (LobbyInterface) registry.lookup("Lobby");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}

			try {
				if (lobbyStub.playerList().contains(naam)) {
					Alert alert = new Alert(AlertType.ERROR, "Deze naam bestaat al in de lobby!", ButtonType.OK);
					alert.showAndWait();
					naam = "";
					lobbyStub = null;
					registry = null;
					ableToConnect = false;
				} else {
					
					System.out.println("Joining the game as " + naam);
					System.out.println(lobbyStub.playerList());
					ableToConnect = true;

			}} catch (RemoteException e) {
				e.printStackTrace();
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

	public void updatePlayerList() throws RemoteException {

				lobby.setPlayerList(lobbyStub.playerList());
				
		};

		
	public void addPlayer(Speler incomingSpeler){
		try {
			speler = new Speler(incomingSpeler.naam);
			lobbyStub.addPlayer(speler);
			System.out.println("Dit zou de naam van de speler moeten zijn: " + speler.getnaam());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void removePlayer(Speler spelerToBeRemoved){
		System.out.println("Removing " + spelerToBeRemoved.getnaam());
		//lobbyStub.removePlayer(this.speler.naam);
	}

	public boolean canConnect(){
		if (ableToConnect){
			return true;
		} else {
			return false;
		}
	}
	
	public Speler getCurrentSpeler(){
		return this.speler;
	}
}
