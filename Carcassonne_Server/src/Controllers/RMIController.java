package Controllers;

import Models.*;
import commonFunctions.Point;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Deze class zorgt voor alle kernspelfunctionaliteiten en functies op de server
 */
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
			if (speler.getNaam().contains(naam)) {
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
			Spelernamen.add(speler.getNaam());
		}

		return Spelernamen;
	}

	public ArrayList<Integer> getPlayerScore(){
		ArrayList<Integer> spelerScore = new ArrayList<>();
		for(Speler speler : alleSpelers){
			spelerScore.add(speler.getPunten());
		}

		return spelerScore;
	}

	@Override
	public ArrayList<Speler> getPlayerListObject() throws RemoteException {
		return serverManager.bordController.bord.getAlleSpelers();
	}

	@Override
	public boolean checkContains(String naam) throws RemoteException {
		for (Speler speler : alleSpelers) {
			if (speler.getNaam().contains(naam)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public void startenGame() throws RemoteException {
		serverManager.startGame(alleSpelers);
	}

	// Overload file laden
	@Override
	public void startenGame(File jsonFile) throws RemoteException {
		// File Loaden
		File importedFile = jsonFile;
		// File inhoud lezen
		Tile[][] alleKaarten = FileManager.loadBordKaartenJSON(importedFile);
		ArrayList<Speler> spelers = FileManager.loadAlleSpelersJSON(importedFile);
		serverManager.startGame(spelers, alleKaarten);
    }

    @Override
	public boolean isGameStarted() throws RemoteException {
		return ServerManager.gameStarted;

	}

	@Override
	public String pakKaart(String spelerNaam) throws RemoteException {
		return serverManager.bordController.pakKaartvanStapel(spelerNaam);

	}


	public boolean plaatsKaart(int x, int y) throws RemoteException {
		return serverManager.bordController.plaatsKaart(x,y);
	}

	public boolean draaiKaart(String naam) {
		return serverManager.bordController.draaiKaart(naam);
	}

	@Override
	public String getPlayerBeurt() throws RemoteException {
		return serverManager.bordController.bord.getSpelerBeurt().getNaam();
	}

	public TileStump getPlacedKaart() throws RemoteException {
		return new TileStump(
				serverManager.bordController.bord.getLaatstGeplaatst().getX(),
				serverManager.bordController.bord.getLaatstGeplaatst().getY(),
				serverManager.bordController.bord.getLaatstGeplaatst().getKaartId(),
				serverManager.bordController.bord.getLaatstGeplaatst().getRotation(),
				serverManager.bordController.bord.getLaatstGeplaatst().getGeplaatsteHorigePositie()
		);
	}

	//
	public TileStump[] getPlacedKaartList() throws RemoteException {
		ArrayList<Tile> tile = serverManager.bordController.bord.getPlacedTiles();
		TileStump[] tileStumpLoad = new TileStump[tile.size()];
		for (int i = 0; i < tile.size(); i++) {
			tileStumpLoad[i] = new TileStump(
					tile.get(i).getX(),
					tile.get(i).getY(),
					tile.get(i).getKaartId(),
					tile.get(i).getRotation(),
					tile.get(i).getGeplaatsteHorigePositie()
			);
		}
		return tileStumpLoad;
	}

	public boolean getLoadedGame(){
		return ServerManager.gameLoaded;
	}



	@Override
	public int getBeurt() throws RemoteException {
		return serverManager.bordController.bord.gameBeurt();
	}

	@Override
	public int getKaartenLeft() throws RemoteException {
		return serverManager.bordController.kaartenStapel.getKaartenOver();
	}

	@Override
	public File saveFile(String path) {
		FileManager manager = new FileManager(serverManager);
		return manager.saveGame(path);
	}

	public boolean plaatsHorige(Horige.Posities posities) throws RemoteException {
		return serverManager.bordController.plaatsHorige(posities);
	}

	@Override
	public Horige.Posities[] getHorigePosities() throws RemoteException {
		return serverManager.bordController.kaartenStapel.getTurnTile().getHorigenZijdes();
	}

	@Override
	public void beeindigenBeurt(String spelerNaam) throws RemoteException {
		serverManager.bordController.beeindigBeurt(spelerNaam);
	}

	@Override
	public ArrayList<Point> getHorigeToRemove() throws RemoteException {
		return serverManager.bordController.bord.getVerwijderHorigeDezeRonde();
	}

	@Override
	public boolean getisEindeSpel() throws RemoteException {
		return serverManager.bordController.getIsGameVoorbij();
	}

	@Override
	public void leaveGame(String naam) throws RemoteException {

		serverManager.bordController.volgendeBeurtNaLeave(naam);

	}

	@Override
	public int getAvailableHorige(String naam) throws RemoteException {
		return serverManager.bordController.bord.getSpelerHorige(naam);
	}
}
