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
		
		Registry registry;
		registry = LocateRegistry.getRegistry("127.0.0.1");
		System.out.println("Getting the Lobby stub from registry");
		rmiStub = (RMIInterface) registry.lookup("RMI");
		
		updatePlayerList();
		
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
				spelerID = rmiStub.getPlayerID(naamSpeler);
				System.out.println("Gejoined als "+ naamSpeler +", index in ArrayList = " + spelerID);
				updatePlayerList();
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
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

	
	public void updatePlayerList(){
		try {
			playerList.setText(rmiStub.playerList());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
