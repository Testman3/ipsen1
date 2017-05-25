package controller;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import model.RMIInterface;

public class GameManager {

	public GameManager(){
		try {
			RMIController controller = new RMIController(); // create calculator and treat as Calculator
			RMIInterface rmiSkeleton = 	(RMIInterface) UnicastRemoteObject.exportObject(controller, 0); // cast to remote object
			System.out.println("RMI skeleton created");
			Registry registry = LocateRegistry.createRegistry(1099); // default port 1099 // run RMI registry on local host
			System.out.println("RMI Registry starter");
			registry.rebind("RMI", rmiSkeleton); // bind rmiskeleton to RMI registry
			System.out.println("RMI skeleton bound");
			System.out.println("Server running...");

			// if you'd like to run rmiregistry from the commend line
			//	run it from the project's bin directory, so rmiregistry can find the necessary classes

		} catch (Exception e) {
			System.out.println("EXCEPTION: " + e);
		}
	}

	private void runServer(){

	}



	public static void main(String[] args) {
		new GameManager().runServer();

	}
}
