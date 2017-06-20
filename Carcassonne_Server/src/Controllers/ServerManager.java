package Controllers;

import Models.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Deze class zorgt voor het opstarten en draaien van de server en het consolescherm dat hier bij hoort
 */
public class ServerManager extends Application {
	ServerManager manager;

	Registry registry;
	RMIInterface rmiSkeleton;

	FileManager fileManager;

	/**
	 * Dit is de main methode :)
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public static boolean gameStarted = false;
	public static boolean gameLoaded = false;

	public BordController bordController;

	/**
	 * Start de gameserver en initialiseert de RMIcontroller,
	 * om vervolgens connectie te kunnen maken met de clients
	 */
	public void runServer() {
		try {

			System.out.println("Current Hostname/I.P Address: " + Inet4Address.getLocalHost());
			RMIController RMIimlp = new RMIController(this);
			rmiSkeleton = (RMIInterface) UnicastRemoteObject.exportObject(RMIimlp, 0);
			System.out.println("rmiSkeleton created");
			registry = LocateRegistry.createRegistry(1099);
			System.out.println("RMI Registry starter");
			registry.rebind("Lobby", rmiSkeleton);
			System.out.println("rmiSkeleton bound");
			System.out.println("Server running...");

		} catch (Exception e) {
			System.out.println("EXCEPTION: " + e);
		}
	}

	/**
	 * Deze functie maakt een nieuwe BordController aan en zet de gameStarted op true
	 * @param spelerList
	 * Geef een arrayList mee met speler objecten.
	 */
	public void startGame(ArrayList<Speler> spelerList) {
		bordController = new BordController(spelerList);
		gameStarted = true;
		gameLoaded = false;
	}

	/**
	 * Deze Functie maakt een nieuwe bordController aan en zet gameStarted en gameLoaded op true
	 * @param spelerList
	 * Geef een arrayList mee met speler objecten.
	 * @param tiles
	 * Geef een 2-dimensionale array mee met de type Tile.
	 */
	public void startGame(ArrayList<Speler> spelerList, Tile[][] tiles) {
		bordController = new BordController(spelerList, tiles);
		gameStarted = true;
		gameLoaded = true;
	}

	/**
	 * Deze functie laat de applicatie zien.
	 * @param primaryStage
	 * Geef een Stage mee.
	 * @throws Exception
	 * Weten we niet
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		TextArea consoleOutput = new TextArea();
		PrintStream ps = new PrintStream(
				new OutputStream() {
					@Override
					public void write(int b) throws IOException {
						Platform.runLater(() -> {
							consoleOutput.setText(consoleOutput.getText() + (char) b);
							consoleOutput.setScrollTop(Double.MAX_VALUE);
						});
					}
				}
		);
		System.setOut(ps);
		BorderPane mainPane = new BorderPane();
		//consoleOutput.setEditable(false);

		consoleOutput.setStyle("-fx-text-fill: lime;" +
				"-fx-control-inner-background: black;" + "-fx-font-family: monospace");

		mainPane.setCenter(consoleOutput);

		Scene mainScene = new Scene(mainPane, 600, 300);

		primaryStage.setScene(mainScene);
		primaryStage.getIcons().add(new javafx.scene.image.Image("Afbeeldingen/serverIcon.png"));
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> {

			registry = null;
			rmiSkeleton = null;
			System.exit(0);
		});
		primaryStage.setTitle("Server command prompt");

		manager = new ServerManager();
		manager.runServer();
	}
}
