package Controllers;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Models.RMIInterface;


public class ServerManager {

	public static void main(String[] args){
		ServerManager manager = new ServerManager();
		manager.runServer();
	}


	public static boolean gameStarted = false;
	BordController bordController;
	/**
	 * Start de gameserver en initialiseert de RMIcontroller,
	 * om vervolgens connectie te kunnen maken met de clients
	 * 
	 */
	public void runServer() {
		try {

			RMIController RMIimlp = new RMIController(this);
			RMIInterface rmiSkeleton = 	(RMIInterface) UnicastRemoteObject.exportObject(RMIimlp, 0);
			System.out.println("rmiSkeleton created");
			Registry registry = LocateRegistry.createRegistry(1099);
			System.out.println("RMI Registry starter");
			registry.rebind("Lobby", rmiSkeleton);
			System.out.println("rmiSkeleton bound");
			System.out.println("Server running...");

		} catch (Exception e) {
			System.out.println("EXCEPTION: " + e);
		}
	}

	public void startGame() {
		bordController = new BordController();
		gameStarted = true;
	}
}
