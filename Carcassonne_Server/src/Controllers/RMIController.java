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
		System.out.println("Player " + naam + " has joined the game");
	}

	@Override
	public void removePlayer(String naam) {

		for (Player player : allePlayers) {
			if(player.naam.contains(naam)){
				System.out.println("Player " + naam + " has left the game");
				allePlayers.remove(player);
				return;
			}
		}

	}

	@Override
	public ArrayList<String> getPlayerList() {
		ArrayList<String> Spelernamen = new ArrayList<String>();
		for (Player player : allePlayers) {
			Spelernamen.add(player.naam);
		}
		return Spelernamen;
	}

	@Override
	public boolean checkContains(String naam) throws RemoteException {
		for (Player player : allePlayers) {
			if(player.naam.contains(naam)){
				return true;
			}
		}
		return false;

	}

	@Override
	public void startenGame() throws RemoteException {
		ServerManager.gameStarted = true;
	}

	@Override
	public boolean isGameStarted() throws RemoteException {
		System.out.println(ServerManager.gameStarted);
		return ServerManager.gameStarted;

	}
}
