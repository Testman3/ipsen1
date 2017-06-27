package Controllers;

import Models.RMIInterface;
import commonFunctions.AlertBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Deze class is verantwoordelijk voor de interacties tussen de lobby / prelobby en de RMI server
 */
public class LobbyController {

	private boolean ableToConnect = false;

	public RMIInterface RMIstub;

	/**
	 * Probeert de speler te laten verbinden met de rmi server
	 * @param ip   ip adres in de vorm van een String
	 * @param naam gebruikersnaam in de vorm van een String
	 * @throws RemoteException RemoteException wordt gegooid wanneer er een fout zit in de verbinding met RMI
	 */
	public void connectToServer(String ip, String naam) throws RemoteException {

		AlertBox alert;

		if (!validateIP(ip)) {
			alert = new AlertBox("Dit is niet een geldig IP adres");
			return;
		}

		System.out.println("Getting access to the registry");
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(ip);
			System.out.println("Getting the Lobby stub from registry");
			RMIstub = (RMIInterface) registry.lookup("Lobby");
			ableToConnect = true;
		} catch (ConnectException e) {
			alert = new AlertBox("Server niet bereikbaar!");
			return;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			alert = new AlertBox("Server niet bereikbaar!");
			return;
		}

		if(naam.length() > 15){
			ableToConnect = false;
			alert = new AlertBox("Je naam mag niet langer zijn dan 15 tekens!");
			return;
		} else if(naam.length() < 2){
			ableToConnect = false;
			alert = new AlertBox("Je naam mag niet korter zijn dan 2 tekens!");
			return;
		}else {
			if (controleerNaam(naam)) {
				alert = new AlertBox("Deze naam bestaat al in de lobby!");
				naam = "";
				RMIstub = null;
				registry = null;
				ableToConnect = false;
				return;
			} else if (getRmiStub().isGameStarted()) {
				ableToConnect = false;
				alert = new AlertBox( "Er is al een spelsessie gestart!");
				return;
			} else if (getRmiStub().getPlayerList().size() == 5) {
				ableToConnect = false;
				alert = new AlertBox("De lobby zit vol!");
				return;
			}
		}
	}

	/**
	 * Geeft terug of het spel geladen is of niet
	 * @return true / false
	 */
	public boolean isGameLoaded() {
		try {
			return RMIstub.getLoadedGame();
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Controleert of de meegegeven naam al bestaat in de server
	 * @param naam
	 * Geef de gewenste naam mee
	 * @return true als de naam nog niet bestaat, false wanneer de naam al bestaat
	 */
	private boolean controleerNaam(String naam) {
		if (RMIstub == null) {
			//Speler is niet connected met de RMI, dus de naam kan niet gechecked worden
			return false;
		}
		boolean naamCheck = false;
		try {
			naamCheck = RMIstub.checkContains(naam);
		} catch (RemoteException e) {
			System.out.println("Er is een RMI fout opgetreden!");
		}
		return naamCheck;
	}

	/**
	 * Controleert of de opgegeven String een geldig IP format is
	 *
	 * @param ip Geef het ip adres mee in de vorm van een String
	 * @return Geeft true terug als het ip valide is, en false als dit niet het geval is
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
		if (ableToConnect) {
			return true;
		}
		return false;
	}

	/**
	 * Update de naam naar de geselecteerde naam uit het dropdown menu wanneer er een spel geladen wordt
	 * @param old oude naam
	 * @param niew geselecteerde naam
	 */
	public void updateNaam(String old, String niew){
		try {
			RMIstub.updateSpelerNaam(old, niew);
		} catch (RemoteException e) {
			System.out.println("Er is een RMI fout opgetreden!");
		}
	}
	public RMIInterface getRmiStub() {
		return RMIstub;
	}
}
