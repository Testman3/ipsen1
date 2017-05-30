package Controllers;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Models.RMIInterface;


public class GameManager {

	public static void main(String[] args){
		runServer();
	}



	/**
	 * Start de gameserver en initialiseert de RMIcontroller,
	 * om vervolgens connectie te kunnen maken met de clients
	 * 
	 */
	public static void runServer() {
		try {

			RMIController RMIimlp = new RMIController();
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
}
