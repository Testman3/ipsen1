package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
	
	public void pakKaart() throws RemoteException;
	public void plaatsKaart() throws RemoteException;
	public void plaatsHorigen() throws RemoteException;
	public void volgendeTurn() throws RemoteException;
	
	/**
	 * Voegt de meegegeven speler toe aan de ArrayList op de server
	 * @param speler
	 * @throws RemoteException
	 */
	
	public void addPlayer(Speler speler) throws RemoteException;
	
	/**
	 * Zet het object op de meegegeven index op null (Verwijderd de overeenkomde speler uit de ArrayList op de server)
	 * @param id
	 * @throws RemoteException
	 */
	
	public void removePlayer(int id) throws RemoteException;
	
	/**
	 * Geeft een String terug met alle spelernamen in de ArrayList gescheiden met een spatie
	 * @return String met spelernamen in server's ArrayList
	 * @throws RemoteException
	 */
	
	public String playerList() throws RemoteException;
	
	/**
	 * Geeft het indexnummer terug van de speler met de meegegeven naam
	 * @param naam
	 * @return playerID op basis van meegegeven naam
	 * @throws RemoteException
	 */
	
	public int getPlayerID(String naam) throws RemoteException;

}
