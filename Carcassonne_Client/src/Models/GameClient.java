package Models;

import Views.GameScene;
import Views.SettingsScene;
import commonFunctions.Point;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GameClient {

	private AudioClip meepMerp = new AudioClip(Paths.get("Sounds/meepMerp.mp3").toUri().toString());
	private GameScene view;
	private Thread gameThread;

	private boolean enableThread = true;

	public String spelerNaam;
	public String kaartPlaatsId = "";

	private String spelerBeurt;

	//String spelerBeurt = "";
	private int beurt = 0;

	public RMIInterface RmiStub;

	private boolean kaartGepakt = false;
	private boolean kaartGeplaatst = false;
	private ArrayList<Point> verwijderHorige;

	public static AudioClip turnSound = new AudioClip(Paths.get("Sounds/battleHorn.mp3").toUri().toString());
	private AudioClip getCard = new AudioClip(Paths.get("Sounds/Shuffling_Cards.mp3").toUri().toString());

	private int aantalHorigeBeschikbaar;

	/**
	 * Constructor van GameClient
	 * @param view
	 * Geef de GameScene mee als view
	 */
	public GameClient(GameScene view) {
		this.view = view;
	}

	/**
	 * Deze functie MOET gerunt worden als de speler de game joint, dit start de thread en set de spelernaam.
	 * @param spelerNaam
	 * Geef de spelernaam mee in de vorm van een String
	 */
	public void Join(String spelerNaam) {
		this.spelerNaam = spelerNaam;

		gameThread = new Thread(() -> {
			while (enableThread == true) {
				Update();
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Thread Interrupted Exception");
				}
			}
		});
		try {
			if(RmiStub.getLoadedGame()) {
                try {
                    view.loadAlleTiles(getTileLoad());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		gameThread.start();

	}

	/**
	 * Deze functie zorgt ervoor dat de beurt van de speler beeindigt wordt
	 */
	public void beeindigBeurt() {
		if(kaartGeplaatst == true){
			try {
				RmiStub.beeindigenBeurt(spelerNaam);
				view.verwijdwerHorigePreviews();
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
				getCard.play();
				return;
			}

		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Plaats de kaart in de view
	 * @param x coordinaat
	 * @param y coordinaat
	 */
	public void plaatsKaart(int x, int y) {
		try {
			if (kaartGepakt && !kaartGeplaatst && RmiStub.plaatsKaart(x, y)) {
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

	/**
	 * Deze functie zorgt voor het draaien van een kaart
	 */
	public void draaiKaart() {
		try {
		if(RmiStub.draaiKaart(spelerNaam)){
			view.DraaiKaart();
		}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plaats horige op positie
	 * @param posities
	 * Geef de positie mee van de horige
	 */
	public void plaatsHorige(Horige.Posities posities) {
		try {
			RmiStub.plaatsHorige(posities);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set RmiStub
	 * @param rmiController
	 * geef een RMIInterface mee.
	 */
	public void setRmiStub(RMIInterface rmiController) {
		RmiStub = rmiController;
		view.RmiStub = rmiController;
	}

	/**
	 * Deze functie geeft de RMIStub terug
	 * @return RmiStub
	 * De RMIStub wordt teruggegeven
	 */
	public RMIInterface getRmiStub(){
		return RmiStub;
	}

		/**
		 * De client wordt elke x ms geīüpdatet, als de beurt op de server hoger is dan de beurt op de client betekent dat en
		 * speler klaar is met zijn beurt, en het spelbord geüpdatet moet worden.
		 */
	private void Update() {

		try {
			if (beurt != RmiStub.getBeurt()) {
				view.updateView(this);
				kaartGepakt = false;
				kaartGeplaatst = false;
				beurt = RmiStub.getBeurt();
				verwijderHorige = RmiStub.getHorigeToRemove();
				spelerBeurt = RmiStub.getPlayerBeurt();
				aantalHorigeBeschikbaar = RmiStub.getAvailableHorige(spelerNaam);

				System.out.println(aantalHorigeBeschikbaar);

			if(SettingsScene.optieGeluid) {
				if (spelerBeurt.equals(spelerNaam)) {
					turnSound.setVolume(0.5);
					turnSound.play();
				}
			}
			}

			if(verwijderHorige != null) {
				for (Point point : verwijderHorige) {
					view.removeHorige(point.getX(), point.getY());
				}
			}

			if (RmiStub.getisEindeSpel()) {
				enableThread = false;

				Platform.runLater(() -> {
					view.getController().getEndGameScene().join(RmiStub);
					view.getController().setEndGameScene();
				});

				//Één van deze is nuttig
				view.updateView(this);

			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		getGameScene().updateHorigenInUi(this);
		//System.out.println("Horigen in UI geüpdatet");
	}

	/**
	 * Deze functie geeft de laast geplaatste kaart terugg
	 * @return
	 * Geeft laatst geplaatste kaart terug mits hij verbinding kan maken met de server. anders geeft hij null terug.
	 * @throws RemoteException
	 * RemoteException wordt gegooid wanneer er iets fout is met de RMI verbinding
	 */
	public TileStump getTile() throws RemoteException {
		try {
			return RmiStub.getPlacedKaart();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	private TileStump[] getTileLoad() throws RemoteException {
		try {
			return RmiStub.getPlacedKaartList();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Deze functie geeft de view terug.
	 * @return view
	 * Geeft GameScene view terug
	 */
	public GameScene getGameScene(){return view;}

	/**
	 * Deze functie geeft de spelernaam terug
	 * @return spelerNaam
	 * Geeft de spelernaam terug in de vorm van een String
	 */
	public String getSpelerBeurt() { return spelerBeurt; }

	public String getSpelerNaam(){
	return spelerNaam;
	}

	public int getAantalHorigeBeschikbaar() {
		return aantalHorigeBeschikbaar;
	}

}

