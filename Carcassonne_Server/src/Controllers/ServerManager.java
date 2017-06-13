package Controllers;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Models.RMIInterface;
import Models.Speler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ServerManager extends Application{
	ServerManager manager;

	public static void main(String[] args){
		launch(args);
		//manager.runServer();

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

	public void startGame(ArrayList<Speler> spelerList) {
		bordController = new BordController(spelerList);
		gameStarted = true;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		TextArea consoleOutput = new TextArea();
		PrintStream ps = new PrintStream(
				new OutputStream() {
					@Override
					public void write(int b) throws IOException {
						consoleOutput.setText(consoleOutput.getText() + (char) b);
					}
				}
		);
		System.setOut(ps);
		BorderPane mainPane = new BorderPane();

		mainPane.setCenter(consoleOutput);


		Scene mainScene = new Scene(mainPane, 600, 300);

		primaryStage.setScene(mainScene);
		primaryStage.show();
		manager = new ServerManager();
		manager.runServer();
	}
}
