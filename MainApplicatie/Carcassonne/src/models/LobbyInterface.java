package models;

import java.rmi.Remote;
import java.rmi.RemoteException;
/////////////////////////////////////
//Geschreven door Henk van Overbeek//
/////////////////////////////////////

public interface LobbyInterface extends Remote{
	public String playerList() throws RemoteException;
	public void addPlayer(Speler speler) throws RemoteException;
	public void removePlayer(Speler speler) throws RemoteException;
	
}
