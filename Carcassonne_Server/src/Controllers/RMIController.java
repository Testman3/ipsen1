package Controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Models.Speler;
import Models.RMIInterface;
import Models.TileStump;

public class RMIController implements RMIInterface {

	public static ArrayList<Speler> alleSpelers = new ArrayList<Speler>();

	public ServerManager serverManager;


	public RMIController(ServerManager manager) {
		serverManager = manager;
	}

	@Override
	public void addPlayer(String naam) {
		Speler speler = new Speler(naam);
		alleSpelers.add(speler);
		System.out.println("Speler " + naam + " has joined the game");
	}

	@Override
	public void removePlayer(String naam) {

		for (Speler speler : alleSpelers) {
			if (speler.naam.contains(naam)) {
				System.out.println("Speler " + naam + " has left the game");
				alleSpelers.remove(speler);
				return;
			}
		}

	}

	@Override
	public ArrayList<String> getPlayerList() {
		ArrayList<String> Spelernamen = new ArrayList<String>();
		for (Speler speler : alleSpelers) {
			Spelernamen.add(speler.naam);
		}

		return Spelernamen;
	}

	@Override
	public boolean checkContains(String naam) throws RemoteException {
		for (Speler speler : alleSpelers) {
			if (speler.naam.contains(naam)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public void startenGame() throws RemoteException {
		serverManager.startGame(alleSpelers);
	}

	@Override
	public boolean isGameStarted() throws RemoteException {
		System.out.println(ServerManager.gameStarted);
		return ServerManager.gameStarted;

	}

	@Override
	public String pakKaart(String spelerNaam) throws RemoteException {
		return serverManager.bordController.pakKaartvanStapel(spelerNaam);

	}


	public boolean plaatsKaart(int x, int y) throws RemoteException {
		return serverManager.bordController.plaatsKaart(x,y);
	}

	public void draaiKaart() {
		serverManager.bordController.draaiKaart();
	}

	@Override
	public String getPlayerBeurt() throws RemoteException {
		return serverManager.bordController.bord.getSpelerBeurt().naam;
	}

	@Override
	public int getKaartX() throws RemoteException {
		return serverManager.bordController.bord.getLaatstGeplaatst().getX();
	}

	@Override
	public int getKaartY() throws RemoteException {
		return serverManager.bordController.bord.getLaatstGeplaatst().getY();
	}

	@Override
	public String getKaartId() throws RemoteException {
		return serverManager.bordController.bord.getLaatstGeplaatst().getKaartId();
	}

	@Override

	public int getKaartRotation() throws RemoteException {
		return serverManager.bordController.bord.getLaatstGeplaatst().getRotation();
	}

	public TileStump getPlacedKaart() throws RemoteException {
		return new TileStump(
				serverManager.bordController.bord.getLaatstGeplaatst().getX(),
				serverManager.bordController.bord.getLaatstGeplaatst().getY(),
				serverManager.bordController.bord.getLaatstGeplaatst().getKaartId(),
				serverManager.bordController.bord.getLaatstGeplaatst().getRotation()

		);
	}

	@Override
	public int getBeurt() throws RemoteException {
		return serverManager.bordController.bord.gameBeurt();
	}
}
