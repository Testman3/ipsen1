package Models;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Deze interface zorgt ervoor dat alle server based functies worden geïmplementeerd in de klassen waar ze nodig zijn
 */
public interface RMIInterface extends Remote {

	/**
	 * Deze functie zorgt ervoor dat er een nieuw spelerobject wordt aangemaakt met de meegegeven naam, 
	 * die vervolgens in een arraylist op de server wordt geplaatst
	 * @param naam
	 * Geef de gebruikersnaam van de speler mee
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public void addPlayer(String naam) throws RemoteException;

	/**
	 * Deze functie zorgt ervoor dat het spelerobject in de arraylist dat overeenkomt met de
	 * meegegeven naam uit de arraylist wordt verwijderd.
	 * @param naam
	 * Geef de gebruikersnaam van de speler mee
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public void removePlayer(String naam) throws RemoteException;

	/**
	 * Deze functie controleert of de meegegeven naam reeds voorkomt in de arraylist met spelers
	 * @param naam
	 * Geef de gebruikersnaam van de speler mee
	 * @return true / false
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public boolean checkContains(String naam) throws RemoteException;

	/**
	 * Deze functie geeft een ArrayList terug met de namen van alle spelers aanwezig in de ArrayList op de server
	 * @return ArrayList<String>
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public ArrayList<String> getPlayerList() throws RemoteException;

	public ArrayList<Integer> getPlayerScore() throws RemoteException;

	/**
	 *
	 * @return ArrayList<Speler>
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public ArrayList<Speler> getPlayerListObject() throws RemoteException;

	/**
	 * Deze functie start de game, wordt gerunt als de speler op starten drukt in de lobby
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public void startenGame() throws RemoteException;

	/**
	 * Deze functie start het spel met een opgeslagen spel
	 * @param jsonFile
	 * Geef een Json-based save file mee
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public void startenGame(File jsonFile) throws RemoteException;

	/**
	 * Checkt of de game al gestart is
	 * @return isStarted
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public boolean isGameStarted() throws RemoteException;

	/**
	 *
 	 * @param spelerNaam
	 * Geef de gebruikersnaam van de speler mee
	 * @return ID van de kaart die gepakt is
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public String pakKaart(String spelerNaam) throws RemoteException;

	/**
	 *
	 * @param x
	 * Geef de X co-ordinaat mee waar de kaart op geplaatst moet worden
	 * @param y
	 * Geef de y co-ordinaat mee waar de kaar op geplaatst moet worden
	 * @return
	 * Geeft true terug als de kaart geplaatst is, geeft false terug als de kaart niet geplaatst
	 * kan worden op de meegegeven co-ordinaten
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public boolean plaatsKaart(int x, int y) throws RemoteException;

	/**
	 * Draait de kaart die de speler gepakt heeft met 90 graden
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public boolean draaiKaart(String naam) throws RemoteException;

	/**
	 * Returnt welke speler er nu aan de beurt is
	 * @return De spelernaam
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public String getPlayerBeurt() throws RemoteException;

	/**
	 * Returnt een data-klasse met de informatie over de laatst geplaatste kaart.
	 * @return de tilestump
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public TileStump getPlacedKaart() throws RemoteException;

	/**
	 * Geeft een int van welke beurt het spel op dit moment is
	*  @return beurt
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public int getBeurt() throws RemoteException;

	/**
	 * Returnt hoeveel kaarten er nog in de pot zitten.
 	 * @return
	 * Geeft terug hoeveel kaarten er nog over zijn in de stapel
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public int getKaartenLeft() throws RemoteException;

	/**
	 * Opslaan van de game staat
	 */
	public void saveFile(String path) throws RemoteException;



	public boolean plaatsHorige(Horige.Posities posities) throws RemoteException;

	public 	Horige.Posities[] getHorigePosities() throws RemoteException;

	public void beeindigenBeurt(String spelerNaam) throws RemoteException;

}
