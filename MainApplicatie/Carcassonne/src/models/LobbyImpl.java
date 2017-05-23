package models;

import java.rmi.RemoteException;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;

import controllers.MenuController;
/////////////////////////////////////
//Geschreven door Henk van Overbeek//
/////////////////////////////////////

@SuppressWarnings("unused")
public class LobbyImpl implements LobbyInterface{
	
	MenuController controller = new MenuController();

	private ArrayList<String> playerArr = new ArrayList<String>();
	//private String[] playerArr = new String[6];
	//private int playerCount = 0;
	@Override
	public String playerList() throws RemoteException {
		String spelerLijst ="";
		
		for (int i = 0; i < playerArr.size(); i++) {
			spelerLijst = spelerLijst + playerArr.get(i) + " ";
		}
		
		return spelerLijst;
	}

	@Override
	public void addPlayer(String playerNaam) throws RemoteException {
		playerArr.add(playerNaam);
		MenuController.updatePlayerList();
		System.out.println(playerNaam + " has entered the game");
	}

	@Override
	public void removePlayer(String playerNaam) throws RemoteException {
		playerArr.remove(playerNaam);
		MenuController.updatePlayerList();
		System.out.println(playerNaam + " has left the game");
		//GameClient.updatePlayerList();
	}


}
