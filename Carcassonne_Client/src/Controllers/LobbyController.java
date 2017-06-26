package Controllers;

import Models.RMIInterface;
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

	private AudioClip errorSound = new AudioClip(Paths.get("Sounds/Error.WAV").toUri().toString());


	private boolean ableToConnect = false;

	public RMIInterface RMIstub;

	/**
	 * Probeert de speler te laten verbinden met de rmi server
	 *
	 * @param ip   ip adres in de vorm van een String
	 * @param naam gebruikersnaam in de vorm van een String
	 * @throws RemoteException RemoteException wordt gegooid wanneer er een fout zit in de verbinding met RMI
	 */
	public void connectToServer(String ip, String naam) throws RemoteException {

		Alert alert;

		if (!validateIP(ip)) {
			alert = new Alert(AlertType.ERROR, "Dit is niet een geldig IP adres", ButtonType.OK);

			///////////////////////////////////////////////////////////
			//Style voorbeeld Alert boxen, moet per new Alert voor nu//
			///////////////////////////////////////////////////////////
			DialogPane alertPane = alert.getDialogPane();
			alertPane.getStylesheets().add("style.css");
			alertPane.getStyleClass().add("alertBox");

			errorSound.play();
			alert.showAndWait();
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
			alert = new Alert(AlertType.ERROR, "Server niet bereikbaar!", ButtonType.OK);
			errorSound.play();
			alert.showAndWait();
			ableToConnect = false;
			return;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			alert = new Alert(AlertType.ERROR, "Server niet bereikbaar!", ButtonType.OK);
			errorSound.play();
			alert.showAndWait();
			ableToConnect = false;
			return;
		}

		if(naam.length() > 10){
			ableToConnect = false;
			alert = new Alert(AlertType.ERROR, "De naam mag niet langer zijn dan 10 tekens!", ButtonType.OK);
			errorSound.play();
			alert.showAndWait();
			return;
		} else if(naam.length() < 2){
			ableToConnect = false;
			alert = new Alert(AlertType.ERROR, "De naam mag niet korter zijn dan 2 tekens!", ButtonType.OK);
			errorSound.play();
			alert.showAndWait();
			return;
		}else {
			if (controleerNaam(naam)) {
				alert = new Alert(AlertType.ERROR, "Deze naam bestaat al in de lobby!", ButtonType.OK);
				errorSound.play();
				alert.showAndWait();
				naam = "";
				RMIstub = null;
				registry = null;
				ableToConnect = false;
				return;
			} else if (getRmiStub().isGameStarted()) {
				ableToConnect = false;
				alert = new Alert(AlertType.ERROR, "Er is al een spelsessie gestart!", ButtonType.OK);
				errorSound.play();
				alert.showAndWait();
				return;
			} else if (getRmiStub().getPlayerList().size() == 5) {
				ableToConnect = false;
				alert = new Alert(AlertType.ERROR, "De lobby zit vol!", ButtonType.OK);
				errorSound.play();
				alert.showAndWait();
				return;
			}
		}

	}

	public boolean isGameLoaded() {
		try {
			return RMIstub.getLoadedGame();
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
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

	public RMIInterface getRmiStub() {
		return RMIstub;
	}
}
