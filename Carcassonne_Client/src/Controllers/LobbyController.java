package Controllers;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Views.LobbyScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import Models.RMIInterface;

/**
 * Deze class is verantwoordelijk voor de interacties tussen de lobby / prelobby en de RMI server
 */
public class LobbyController {


	private boolean ableToConnect = false;

	public RMIInterface RMIstub;

	/**
	 * Probeert de speler te laten verbinden met de rmi server
	 *
	 * @param ip
	 * ip adres in de vorm van een String
	 * @param naam
	 * gebruikersnaam in de vorm van een String
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er een fout zit in de verbinding met RMI
	 */
	public void connectToServer(String ip, String naam) throws RemoteException {

		Alert alert;

		if (!validateIP(ip)) {
			alert = new Alert(AlertType.ERROR, "Dit is niet een geldig IP adres", ButtonType.OK);
			alert.showAndWait();
		} else {
			System.out.println("Getting access to the registry");
			Registry registry;

			try {
				registry = LocateRegistry.getRegistry(ip);
				System.out.println("Getting the Lobby stub from registry");
				RMIstub = (RMIInterface) registry.lookup("Lobby");
				ableToConnect = true;
			} catch (ConnectException e) {
				alert = new Alert(AlertType.ERROR, "Server niet bereikbaar!", ButtonType.OK);
				alert.showAndWait();
				ableToConnect = false;
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				alert = new Alert(AlertType.ERROR, "Server niet bereikbaar!", ButtonType.OK);
				alert.showAndWait();
				ableToConnect = false;
			}

			if (controleerNaam(naam)) {
				alert = new Alert(AlertType.ERROR, "Deze naam bestaat al in de lobby!", ButtonType.OK);
				alert.showAndWait();
				naam = "";
				RMIstub = null;
				registry = null;
				ableToConnect = false;
			} else {
				System.out.println("Joining the game as " + naam);
			}
		}
	}


	private boolean controleerNaam(String naam) {
		if (RMIstub == null) {
			//Speler is niet connected met de RMI, dus de naam kan niet gechecked worden
			return false;
		}
		boolean naamCheck = false;
		try {
			naamCheck = RMIstub.checkContains(naam);
		} catch (RemoteException e) {
		}
		return naamCheck;
	}

	/**
	 * Controleert of de opgegeven String een geldig IP format is
	 *
	 * @param ip
	 * @return
	 */
	private boolean validateIP(final String ip) {
		Pattern pattern;
		Matcher matcher;
		String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		pattern = Pattern.compile(IPADDRESS_PATTERN);
		matcher = pattern.matcher(ip);
		return matcher.matches();
	}


	/**
	 * Deze functie wordt gebruikt bij het verbinding maken met de rmi server.
	 *
	 * @return true / false
	 */
	public boolean canConnect() {
		if (ableToConnect) return true;
		else return false;
	}


	public void gameStarten() {

	}

	public RMIInterface getRmiStub() {
		return RMIstub;
	}
}
