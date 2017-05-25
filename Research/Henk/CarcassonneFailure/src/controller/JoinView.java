package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.RMIInterface;
import model.Speler;

public class JoinView extends Application{
	
	Label playerList;
	Speler speler = new Speler("");
	RMIInterface rmiStub;
	private int spelerID;
	String naamSpeler = "";

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage mainStage) throws Exception {
		
		//Maak alle benodigde javafx dingen aan
		BorderPane mainPane = new BorderPane();
		Scene mainScene = new Scene(mainPane, 400, 400);
		Button addPlayer = new Button("Add Player");
		Button removePlayer = new Button("Remove Player");
		
		TextField spelerNaam = new TextField();
		
		playerList = new Label("Empty");
		
		HBox buttonBox = new HBox(10);
		buttonBox.getChildren().addAll(addPlayer, removePlayer, spelerNaam);
		
		HBox spelerLijst = new HBox(10);
		spelerLijst.getChildren().addAll(playerList);
		
		mainPane.setCenter(buttonBox);
		mainPane.setBottom(spelerLijst);
		
		
		mainStage.setScene(mainScene);
		mainStage.show();
		
		//Maak meteen verbinding met de gameserver zodra de applicatie is opgestart
		Registry registry;
		registry = LocateRegistry.getRegistry("127.0.0.1");
		System.out.println("Getting the Lobby stub from registry");
		rmiStub = (RMIInterface) registry.lookup("RMI");
		
		//Update meteen de playerlist zodat je kan zien welke spelers er aanwezig zijn in de arraylist
		updatePlayerList();
		
		
		//Zorgt er voor dat de addplayer knop een speler aanmaakt met de ingevoerde naam en toevoegd aan de arraylist op de server.
		addPlayer.setOnAction(e -> {
			naamSpeler = spelerNaam.getText();
			
			try {
				if (rmiStub.playerList().contains(naamSpeler))
				{
					System.out.println("Bestaat al");
				}
				
				else{
				speler.setNaam(naamSpeler);
				rmiStub.addPlayer(speler);
				//Stelt spelerID in aan de hand van de locatie van de speler in de arraylist
				spelerID = rmiStub.getPlayerID(naamSpeler);
				System.out.println("Gejoined als "+ naamSpeler +", index in ArrayList = " + spelerID);
				updatePlayerList();
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		
		//zorgt ervoor dat de aangemaakte speler uit de arraylist verwijderd kan worden aan de hand van het playerid
		//zoals opgehaald met de addplayer knop
		removePlayer.setOnAction(e -> {
			try {
				rmiStub.removePlayer(spelerID);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			updatePlayerList();
		});
		
		
	}

	
	//Update het lokale label door de method playerList aan te roepen op de server.
	public void updatePlayerList(){
		try {
			playerList.setText(rmiStub.playerList());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
