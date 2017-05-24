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

	private ArrayList<Speler> playerArr = new ArrayList<Speler>();
	//Speler speler;

	@Override
	public String playerList() throws RemoteException {
		String spelerLijst ="";
		
		for (int i = 0; i < playerArr.size(); i++) {
			spelerLijst = spelerLijst + playerArr.get(i).naam + " ";
		}
		
		return spelerLijst;
	}

	@Override
	public void addPlayer(Speler speler) throws RemoteException {
		playerArr.add(speler);
		//this.speler = speler;
		//controller.updatePlayerList();
		System.out.println(speler.naam + " has entered the game");
	}

	@Override
	public void removePlayer(Speler spelerToBeRemoved) throws RemoteException {
		//System.out.println(this.speler.naam + " has left the game");
		playerArr.remove(spelerToBeRemoved);
		//controller.updatePlayerList();
		
		//GameClient.updatePlayerList();
	}


}
