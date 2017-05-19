package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.helpers.ValidationEventImpl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import server.Lobby;
/////////////////////////////////////
//Geschreven door Henk van Overbeek//
/////////////////////////////////////

@SuppressWarnings("unused")

public class GameClient extends Application{

	int maxTextFieldWidth = 200;
	int maxButtonWidth = 200;
	String localAddress = "127.0.0.1";
	String remoteAddress = "149.210.245.145";
	static Lobby lobbyStub;
	String playerName ="Testspeler";
	static Label player1;
	Thread viewUpdater;
	ViewThread t = new ViewThread();
	Thread thread = new Thread(t);


	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		BorderPane mainPane = new BorderPane();
		TextField naamVeld = new TextField("Player1");
			naamVeld.setMaxWidth(maxTextFieldWidth);
		TextField ipVeld = new TextField(localAddress);
			ipVeld.setMaxWidth(maxTextFieldWidth);
		VBox joinViewButtons = new VBox(10);
		HBox playerBox = new HBox();
		Scene mainScene = new Scene(mainPane, 400, 400);
		
		//Define required buttons
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
		playersLabel.setFont(new Font("Arial", 20));

		player1 = new Label();
			player1.setText("empty");
			player1.setFont(new Font("Arial", 15));
			player1.setAlignment(Pos.CENTER);

		playerBox.getChildren().addAll(playersLabel, player1);
		lobbyPane.getChildren().addAll(playerBox, leaveGame);
		
		joinViewButtons.getChildren().addAll(naamVeld, ipVeld, addPlayer, localHost, remoteServer, backToHome);
		joinViewButtons.setAlignment(Pos.CENTER);

		Scene lobbyScene = new Scene(lobbyPane, 400, 400);

		//mainPane.getChildren().setAll(lobbyButtons);
		mainPane.setTop(joinViewButtons);

		mainStage.setScene(mainScene);
		mainStage.setTitle("Carcassonne Client");
		mainStage.show();

		addPlayer.setOnAction(e -> {
			try {
				if (!validateIP(ipVeld.getText())) {
					Alert alert = new Alert(AlertType.ERROR, "Dit is niet een geldig IP adres", ButtonType.OK);
					alert.showAndWait();
				} else {
					System.out.println("Getting access to the registry");
					Registry registry = LocateRegistry.getRegistry(ipVeld.getText()); // if server on another machine: provide that machine's IP address. Default port  1099
					System.out.println("Getting the Lobby stub from registry");
					lobbyStub = (Lobby) registry.lookup("Lobby"); // get remote Calculator object from registry
						
					playerName = naamVeld.getText();
					
				if (lobbyStub.playerList().contains(playerName)){
					Alert alert = new Alert(AlertType.ERROR, "Deze naam bestaat al in de lobby!", ButtonType.OK);
					alert.showAndWait();
					playerName = "";
					lobbyStub = null;
					registry = null;
				} else {
					lobbyStub.addPlayer(playerName);
					System.out.println("Joining the game as " + playerName);
					System.out.println(lobbyStub.playerList());
					updatePlayerList();
					thread.start();				
				//ViewThread.main(null);
				mainStage.setScene(lobbyScene);
				}
		}

				//Zorgt ervoor dat de speler word verwijderd uit de spelerslijst wanneer
				//het speelvenster wordt gesloten, en dat de NullPointerException
				//Wordt afgehandeld als de speler geen verbinding heeft
				mainStage.setOnCloseRequest(e3 -> {
				try {
					ViewThread.kill();
					lobbyStub.removePlayer(playerName);
					System.exit(0);
				} catch (RemoteException e1) {
					e1.printStackTrace();
					} catch (NullPointerException e1) {
					System.out.println("\nPlayer heeft geen verbinding en probeert zich te verwijderen");
					}});


			} catch (RemoteException e1) {e1.printStackTrace();} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		

		leaveGame.setOnAction(e ->{
			mainStage.setScene(mainScene);		
			
			try {
				lobbyStub.removePlayer(playerName);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.exit killt alle gerelateerde processen, hierdoor sluit het programma volledig
			System.exit(0);
		});
		
		//
		localHost.setOnAction(e -> {
			ipVeld.setText(localAddress);
		});
		
		//
		remoteServer.setOnAction(e -> {
			ipVeld.setText(remoteAddress);
		});
		
	}
	
	//Deze methode wordt gebruikt om de playerlist voor de client te updaten
	//Deze methode wordt elke halve seconde aangeroepen door ViewThread
	public static void updatePlayerList(){
		Platform.runLater(() -> {
			try {
				player1.setText(lobbyStub.playerList());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		//System.out.println(lobbyStub.playerList());
	}


	/**
	 * Blabla
	 * @param ip
	 * @return
	 */
	public boolean validateIP(final String ip) {
			Pattern pattern;
			Matcher matcher;
			String IPADDRESS_PATTERN
					= "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
			pattern = Pattern.compile(IPADDRESS_PATTERN);
			matcher = pattern.matcher(ip);
			return matcher.matches();
	}
}
