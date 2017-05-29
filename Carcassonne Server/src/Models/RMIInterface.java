package Models;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIInterface extends Remote {


	public void addPlayer(String naam) throws RemoteException;
	public void removePlayer(String naam) throws RemoteException;
	public boolean checkContains(String naam) throws RemoteException;
	public ArrayList<Player> getPlayerList() throws RemoteException;

}
