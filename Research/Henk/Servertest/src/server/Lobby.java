package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Lobby extends Remote
{
	public String playerList() throws RemoteException;
	public void addPlayer(String naam) throws RemoteException;
	public void removePlayer(String naam) throws RemoteException;
	
}
