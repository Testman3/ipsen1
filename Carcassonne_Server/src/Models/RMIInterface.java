package Models;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIInterface extends Remote {


	/**
	 * Deze functie zorgt ervoor dat er een nieuw spelerobject wordt aangemaakt met de meegegeven naam, 
	 * die vervolgens in een arraylist op de server wordt geplaatst
	 * @param naam
	 * @throws RemoteException
	 */
	public void addPlayer(String naam) throws RemoteException;
	/**
	 * Deze functie zorgt ervoor dat het spelerobject in de arraylist dat overeenkomt met de
	 * meegegeven naam uit de arraylist wordt verwijderd.
	 * @param naam
	 * @throws RemoteException
	 */
	public void removePlayer(String naam) throws RemoteException;
	/**
	 * Deze functie controleert of de meegegeven naam reeds voorkomt in de arraylist met spelers
	 * @param naam
	 * @return true / false
	 * @throws RemoteException
	 */
	public boolean checkContains(String naam) throws RemoteException;
	/**
	 * Deze functie geeft een ArrayList terug met de namen van alle spelers aanwezig in de ArrayList op de server
	 * @return ArrayList<String>
	 * @throws RemoteException
	 */
	public ArrayList<String> getPlayerList() throws RemoteException;

	/**
	 * Deze functie start de game, wordt gerunt als de speler op starten drukt in de lobby
	 * @throws RemoteException
	 */
	public void startenGame() throws RemoteException;


	/**
	 * Checkt of de game al gestart is
	 * @return isStarted
	 * @throws RemoteException
	 */
	public boolean isGameStarted() throws RemoteException;


	/**
	 *
 	 * @param spelerNaam
	 * @return ID van de kaart die gepakt is
	 * @throws RemoteException
	 */
	public String pakKaart(String spelerNaam) throws RemoteException;

	public boolean plaatsKaart(int x, int y) throws RemoteException;

	public void draaiKaart() throws RemoteException;

	public String getPlayerBeurt() throws RemoteException;

	public int getKaartX() throws RemoteException;

	public int getKaartY() throws RemoteException;

	public String getKaartId() throws RemoteException;

	public int getKaartRotation() throws RemoteException;

	public TileStump getPlacedKaart() throws RemoteException;

}
