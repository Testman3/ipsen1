package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.Lobby;

public class GameClient extends Application{

    static Lobby lobbyStub;
    String playerName ="Testspeler";
    static Label player1;
    Label player2;
    Label player3;

	
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
		
		FlowPane lobbyPane = new FlowPane();
		
		player1 = new Label();
		player1.setText("empty");
		VBox playerBox = new VBox();
		playerBox.getChildren().addAll(player1);
		lobbyPane.getChildren().addAll(playerBox);
		
		Scene lobbyScene = new Scene(lobbyPane, 400, 400);

		
		mainPane.getChildren().setAll(naamVeld, addPlayer);
		naamVeld.setAlignment(Pos.TOP_CENTER);
		
		mainStage.setScene(mainScene);
		mainStage.setTitle("Carcassonne Client");
		mainStage.show();
	
		addPlayer.setOnAction(e -> 
		{
			try {				
				System.out.println("Getting access to the registry");
				Registry registry = LocateRegistry.getRegistry("127.0.0.1"); // if server on another machine: provide that machine's IP address. Default port  1099	
				System.out.println("Getting the Lobby stub from registry");
	            lobbyStub = (Lobby) registry.lookup("Lobby"); // get remote Calculator object from registry
	           
				playerName = naamVeld.getText();
				lobbyStub.addPlayer(playerName);
				System.out.println("Joining the game as " + playerName);
				System.out.println(lobbyStub.playerList());
				updatePlayerList();

				mainStage.setScene(lobbyScene);
				
				//Zorgt ervoor dat de speler word verwijderd uit de spelerslijst wanneer
				//het speelvenster wordt gesloten
				mainStage.setOnCloseRequest(e3 -> {
				try {
					lobbyStub.removePlayer(playerName);
				} catch (RemoteException e1) {e1.printStackTrace();}});
				
				
			} catch (RemoteException e1) {e1.printStackTrace();} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
	}
	
	public static void updatePlayerList()
	{
		try {
			player1.setText(lobbyStub.playerList());
			} catch (RemoteException e) {e.printStackTrace();
		}
	}
}
