package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
	
	public void pakKaart() throws RemoteException;
	public void plaatsKaart() throws RemoteException;
	public void plaatsHorigen() throws RemoteException;
	public void volgendeTurn() throws RemoteException;
	public void addPlayer(Speler speler) throws RemoteException;
	public void removePlayer(int id) throws RemoteException;
	public String playerList() throws RemoteException;
	public int getPlayerID(String naam) throws RemoteException;

}
