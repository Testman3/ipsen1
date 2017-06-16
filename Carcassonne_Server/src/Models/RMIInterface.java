package Models;

import commonFunctions.Point;

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
	 * @return ArrayList(String)
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public ArrayList<String> getPlayerList() throws RemoteException;

	/**
	 * Deze functie haalt de playerscores op
	 * @return
	 * Er wordt een arraylist(int) teruggegeven die de scores van alle spelers bevat
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public ArrayList<Integer> getPlayerScore() throws RemoteException;

	/**
	 * Deze functie vraagt een arraylist van spelers op
	 * @return ArrayList(Speler)
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
	 * Deze functie zorgt voor het pakken van een kaart uit de stapel
 	 * @param spelerNaam
	 * Geef de gebruikersnaam van de speler mee
	 * @return ID van de kaart die gepakt is
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public String pakKaart(String spelerNaam) throws RemoteException;

	/**
	 * Deze functie zorgt voor het plaatsen van een kaart op het speelveld
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
	 * @param naam
	 * Geef de naam van de speler mee in de vorm van een String
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 * @return
	 * Geeft een true terug wanneer de kaart is gedraait, een false als dit niet is gelukt
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
	 * Geeft terug hoeveel kaarten er nog over zijn in de stapel
	 * @return
	 * Geeft een int terug met het aantal kaarten dat nog in de stapel zit
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public int getKaartenLeft() throws RemoteException;

	/**
	 * Opslaan van de game staat
	 * @param path
	 * Geef het path mee in de vorm van een String
	 * @return
	 * placeholder
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public File saveFile(String path) throws RemoteException;

	/**
	 * Deze functie zorgt ervoor dat een horige geplaatst wordt op het speelbord
	 * @param posities
	 * Geef de positie mee
	 * @return
	 * Geeft true terug wanneer de horige succesvol geplaatst is, en false wanneer dit niet zo is
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public boolean plaatsHorige(Horige.Posities posities) throws RemoteException;

	/**
	 * Deze functie vraagt de positie op van een horige
	 * @return
	 * ArrayList met alle horige posities
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public 	Horige.Posities[] getHorigePosities() throws RemoteException;

	/**
	 * Deze functie zorgt ervoor dat de speler zijn beurt kan beeindigen
	 * @param spelerNaam
	 * Geef de spelernaam mee in de vorm van een String
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets mis gaat met de RMI verbinding
	 */
	public void beeindigenBeurt(String spelerNaam) throws RemoteException;

	public ArrayList<Point> getHorigeToRemove() throws RemoteException;

	public boolean getisEindeSpel() throws RemoteException;

	public void leaveGame(String naam) throws RemoteException;
}
