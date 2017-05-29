package Controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Models.Player;
import Models.RMIInterface;

public class RMIController implements RMIInterface {

public	ArrayList<Player> allePlayers = new ArrayList<Player>();

	@Override
	public void addPlayer(String naam) {
		Player player = new Player(naam);
		allePlayers.add(player);
		System.out.println("Player " + naam + " was added");
	}

	@Override
	public void removePlayer(String naam) {

		for (Player player : allePlayers) {
			if(player.naam == naam){
				allePlayers.remove(player);
			}
		}

	}

	@Override
	public ArrayList<Player> getPlayerList() {
		return allePlayers;
	}

	@Override
	public boolean checkContains(String naam) throws RemoteException {
		for (Player player : allePlayers) {
			if(player.naam == naam){
				return true;
			}
		}
		return false;

	}

}
