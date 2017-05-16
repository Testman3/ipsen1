package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import server.Lobby;

@SuppressWarnings("unused")

public class GameClient extends Application{

	String connectAdress = "127.0.0.1";
	String localAddress = "127.0.0.1";
	String remoteAddress = "149.210.245.145";
    static Lobby lobbyStub;
    String playerName ="Testspeler";
    static Label player1;
    Thread viewUpdater;
	ViewThread t = new ViewThread();
	Thread thread = new Thread(t);

	
	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage mainStage) throws Exception 
	{
		FlowPane mainPane = new FlowPane();
		TextField naamVeld = new TextField();
		
		Scene mainScene = new Scene(mainPane, 400, 400);
		Button addPlayer = new Button("Join Game");
		Button leaveGame = new Button("Leave Game");
		Button localHost = new Button("Localhost");
		Button remoteServer = new Button("Remote Server");
		
		FlowPane lobbyPane = new FlowPane();
		
		Label serverIP = new Label(connectAdress);
		
		Label playersLabel = new Label("Players in this game: ");
		playersLabel.setFont(new Font("Arial", 20));

		player1 = new Label();
		player1.setText("empty");
		player1.setFont(new Font("Arial", 15));
		player1.setAlignment(Pos.CENTER);
		
				
		HBox playerBox = new HBox();
		playerBox.getChildren().addAll(playersLabel, player1);
		lobbyPane.getChildren().addAll(playerBox, leaveGame);
		
		Scene lobbyScene = new Scene(lobbyPane, 400, 400);

		
		mainPane.getChildren().setAll(naamVeld, addPlayer, localHost, remoteServer, serverIP);
		naamVeld.setAlignment(Pos.TOP_CENTER);
		
		mainStage.setScene(mainScene);
		mainStage.setTitle("Carcassonne Client");
		mainStage.show();
	
		addPlayer.setOnAction(e -> 
		{
			try {				
				System.out.println("Getting access to the registry");
				Registry registry = LocateRegistry.getRegistry(connectAdress); // if server on another machine: provide that machine's IP address. Default port  1099	
				System.out.println("Getting the Lobby stub from registry");
	            lobbyStub = (Lobby) registry.lookup("Lobby"); // get remote Calculator object from registry
	           
				playerName = naamVeld.getText();
				lobbyStub.addPlayer(playerName);
				System.out.println("Joining the game as " + playerName);
				System.out.println(lobbyStub.playerList());
				updatePlayerList();
				
				thread.start();
				
				//ViewThread.main(null);
				mainStage.setScene(lobbyScene);

				
				//Zorgt ervoor dat de speler word verwijderd uit de spelerslijst wanneer
				//het speelvenster wordt gesloten
				mainStage.setOnCloseRequest(e3 -> {
				try {
					ViewThread.kill();
					lobbyStub.removePlayer(playerName);
					System.exit(0);
				} catch (RemoteException e1) {e1.printStackTrace();}});
				
				
			} catch (RemoteException e1) {e1.printStackTrace();} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		leaveGame.setOnAction(e -> 
		{
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
		
		localHost.setOnAction(e -> 
		{
			connectAdress = localAddress;
			serverIP.setText(connectAdress);
		});
		
		remoteServer.setOnAction(e -> 
		{
			connectAdress = remoteAddress;
			serverIP.setText(connectAdress);
		});
		
		
	}
	


	public static void updatePlayerList()
	{
		try {
			Platform.runLater(() -> {
				try {
					player1.setText(lobbyStub.playerList());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			System.out.println(lobbyStub.playerList());
			
			
			} catch (RemoteException e) {e.printStackTrace(); 
			
		}
	}
}
