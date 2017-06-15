package Models;

import Views.GameScene;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GameClient {

	AudioClip meepMerp = new AudioClip(Paths.get("Sounds/meepMerp.mp3").toUri().toString());

	GameScene view;
	public GameScene getGameScene(){return view;}

	Thread gameThread;

	private boolean enableThread = true;

	public String spelerNaam;
	public String kaartPlaatsId = "";

	String spelerBeurt = "";
	int beurt = 0;

	public RMIInterface RmiStub;

	private boolean kaartGepakt = false;
	private boolean kaartGeplaatst = false;
	ArrayList<Point2D> verwijderHorige;

	public GameClient(GameScene view) {
		this.view = view;

	}


	/**
	 * Deze functie MOET gerunt worden als de speler de game joint, dit start de thread en set de spelernaam.
	 *
	 * @param spelerNaam Geef de spelernaam mee in de vorm van een String
	 */
	public void Join(String spelerNaam) {
		this.spelerNaam = spelerNaam;

		gameThread = new Thread(() -> {
			while (enableThread == true) {
				Update();
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		gameThread.start();

	}

	public void beeindigBeurt() {
		if(kaartGeplaatst == true){
			try {
				RmiStub.beeindigenBeurt(spelerNaam);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Pak een kaart van de stapel, dit kan alleen als de speler aan de beurt is
	 */
	public void pakKaart() {
		try {
			if (kaartGepakt) {
				meepMerp.play();
				return;
			}
			if(kaartGepakt == false) {
				String id = RmiStub.pakKaart(spelerNaam);
				kaartGepakt = true;
				kaartPlaatsId = id;
				view.showKaart(this);
				return;
			}

		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Plaats de kaart in de view
	 *
	 * @param x coordinaat
	 * @param y coordinaat
	 */
	public void plaatsKaart(int x, int y) {
		try {
			if (kaartGepakt && RmiStub.plaatsKaart(x, y)) {
				view.plaatsKaart(this, x, y);
				kaartGeplaatst = true;
				kaartPlaatsId = "";
			}
			else {
				meepMerp.play();
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	public void draaiKaart() {
		try {
		if(RmiStub.draaiKaart(spelerNaam)){
			view.DraaiKaart();
		}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void plaatsHorige(Horige.Posities posities) {
		try {
			RmiStub.plaatsHorige(posities);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	public void setRmiStub(RMIInterface rmiController) {
		RmiStub = rmiController;
		view.RmiStub = rmiController;
	}
		/**
		 * De client wordt elke x ms geupdate, als de beurt op de server hoger is dan de beurt op de client betekent dat en
		 * speler klaar is met zijn beurt, en het spelbord geupdate moet worden.
		 */
	public void Update() {

		try {
			if (beurt != RmiStub.getBeurt()) {
				view.updateView(this);
				kaartGepakt = false;
				kaartGeplaatst = false;
				beurt = RmiStub.getBeurt();
				ArrayList<Point2D> verwijderHorige = RmiStub.getHorigeToRemove();
			}
			if (RmiStub.getKaartenLeft() <= 0) {
				enableThread = false;

				Platform.runLater(() -> {
					view.getController().getEndGameScene().join(RmiStub);
					view.getController().setEndGameScene();
					for (Point2D point: verwijderHorige) {
						view.removeHorige((int)point.getX(), (int)point.getY());
					}
				});


			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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


