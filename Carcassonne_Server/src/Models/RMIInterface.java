package Models;

import com.sun.org.apache.regexp.internal.RE;

import java.io.File;
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

	public ArrayList<Integer> getPlayerScore() throws RemoteException;

	public ArrayList<Speler> getPlayerListObject() throws RemoteException;


	/**
	 * Deze functie start de game, wordt gerunt als de speler op starten drukt in de lobby
	 * @throws RemoteException
	 */
	public void startenGame() throws RemoteException;

	public void startenGame(File jsonFile) throws RemoteException;


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

	/**
	 *
	 * @param x
	 * @param y
	 * @return
	 * @throws RemoteException
	 */
	public boolean plaatsKaart(int x, int y) throws RemoteException;

	/**
	 * Draait de kaart die de speler gepakt heeft met 90 graden
	 * @throws RemoteException
	 */
	public void draaiKaart() throws RemoteException;

	/**
	 * Returnt welke speler er nu aan de beurt is
	 * @return De spelernaam
	 * @throws RemoteException
	 */
	public String getPlayerBeurt() throws RemoteException;

	/**
	 * Returnt een data-klasse met de informatie over de laatst geplaatste kaart.
	 * @return de tilestump
	 * @throws RemoteException
	 */
	public TileStump getPlacedKaart() throws RemoteException;

	/**
	 * Geeft een int van welke beurt het spel op dit moment is
	*  @return beurt
	 * @throws RemoteException
	 */
	public int getBeurt() throws RemoteException;

	/**
	 * Returnt hoeveel kaarten er nog in de pot zitten.
 	 * @return
	 * @throws RemoteException
	 */
	public int getKaartenLeft() throws RemoteException;


}
