package Models;

import Views.GameScene;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import Models.TileStump;

import java.rmi.RemoteException;

/**
 * Created by Marti on 11-6-2017.
 */
public class GameClient {

	GameScene view;

	Thread gameThread;

	private boolean enableThread = true;

	private String spelerNaam;
	public String kaartPlaatsId = "";

	String spelerBeurt = "";

	RMIInterface RmiStub;

	public GameClient(GameScene view){
		this.view = view;

	}
	public void Join(String spelerNaam) {
	this.spelerNaam = spelerNaam;

		gameThread = new Thread( () -> {
			while(enableThread == true){
				Update();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		gameThread.start();

	}

	public void setRmiStub(RMIInterface rmiController) {
		RmiStub = rmiController;
		view.RmiStub = rmiController;
	}


	public void Update() {
		try {
			System.out.println("Spelerbeurt naam " + spelerBeurt + " en rmi naam " + RmiStub.getPlayerBeurt());
			if (!spelerBeurt.contains(RmiStub.getPlayerBeurt())) {
				System.out.println("Spelerbeurt komt niet overen met RMI beurt ==================================");
				view.updateView(this);
				spelerBeurt = RmiStub.getPlayerBeurt();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

		public void getPlacedTile() throws RemoteException {
		int kaartX = RmiStub.getKaartX();
		int kaartY = RmiStub.getKaartY();
		String kaartId = RmiStub.getKaartId();
		int kaartRotation = RmiStub.getKaartRotation();
	}

	public TileStump getTile() throws RemoteException {
		try {
			return RmiStub.getPlacedKaart();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}


