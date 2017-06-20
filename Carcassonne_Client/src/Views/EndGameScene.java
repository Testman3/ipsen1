package Views;

import Controllers.MenuController;
import Models.RMIInterface;
import Models.Speler;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import commonFunctions.SmartLabel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import static Views.MenuViewScene.mediaPlayer;
import static Models.GameClient.turnSound;

/**
 * Deze class zorgt ervoor dat de EndGameScene goed wordt weergegeven.
 */
public class EndGameScene extends Scene implements SceneInitialiser {

	private BorderPane mainPane;
	private MenuController controller;
	private RMIInterface RMIstub;
	private VBox allesContainer;
	private SmartLabel titel;
	private SmartLabel[] spelers;
	private SmartButton exit;
	private ArrayList<Speler> spelerObj;
	private Boolean setScorebord = false;
	private Media victoryMusic = new Media(Paths.get("Sounds/JellyfishJam.mp3").toUri().toString());
	private MediaPlayer musicPlayer = new MediaPlayer(victoryMusic);
	private Media lossMusic = new Media(Paths.get("Sounds/YouLost.mp3").toUri().toString());
	private MediaPlayer lossMusicPlayer = new MediaPlayer(lossMusic);


	/**
	 * Constructor van de EndGameScene
	 * @param controller
	 * Geef MenuController mee
	 */
	public EndGameScene(MenuController controller) {
		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();
		this.controller = controller;

		initGui();
	}

	/**
	 * Initialiseert alle grafische elementen
	 */
	@Override
	public void initGui() {
		mainPane.getStylesheets().add("style.css");
		allesContainer = new VBox();
		spelers = new SmartLabel[5];
		exit = new SmartButton("Spel verlaten");
		exit.setId("standardLabel");

		titel = new SmartLabel("Score:");
		titel.setId("title");

		mainPane.setId("exitBackground");
		allesContainer.setId("schild");

		allesContainer.getChildren().add(titel);

		for (int i = 0; i < spelers.length; i++) {
			spelers[i] = new SmartLabel();
			spelers[i].setId("standardLabel");
			allesContainer.getChildren().add(spelers[i]);
		}

		allesContainer.getChildren().add(exit);
		allesContainer.setAlignment(Pos.CENTER);

		mainPane.setCenter(allesContainer);


		initAction();

	}

	/**
	 * Initialiseert de functionaliteit van de grafische elementen
	 * Zorgt ervoor zodra je de applicatie sluit, dat dit op de correctie manier gebeurd.
	 */
	@Override
	public void initAction() {
		exit.setOnAction(e -> {
			System.exit(0);
		});

	}

	/**
	 * Deze functie zorgt ervoor dat de verbing van de server er is waarna de functie setScoreboard wordt aangeroepen.
	 * @param stub
	 * De RMIInterface wordt meegegeven
	 */
	public void join(RMIInterface stub){
		RMIstub = stub;
		setScoreboard();
	}

	/**
	 * Deze functie controleert of de speler gewonnen heeft (speler index 0 in de array) en
	 * speelt de muziek af die bij de winnaar hoort.
	 */
	public void music(){
		if (controller.getSpelernaam().equals(spelerObj.get(0).getNaam()) && SettingsScene.optieGeluid){
			turnSound.stop();
			mediaPlayer.stop();
			musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			musicPlayer.setVolume(0.25);
			musicPlayer.setRate(1.10);
			musicPlayer.play();
		}else if (SettingsScene.optieGeluid){
			turnSound.stop();
			mediaPlayer.stop();
			lossMusicPlayer.play();
		}
		}



	/**
	 * Deze functie zorgt ervoor dat de spelers worden gesorteerd op score, waarna ze op het scherm zullen verschijnen.
	 */
	private void setScoreboard(){
		if (setScorebord == false)
		try {
			spelerObj = RMIstub.getPlayerListObject();

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		//Bubblesort om arraylist met spelers te sorteren op de hoogte van hun score
		//Index van hun score en naam veranderd naar de hoogte van hun score.
		int tempScore;
		String tempNaam;

		for (int i = 0; i < spelerObj.size(); i++) {
			for (int j = 1; j < spelerObj.size() - i; j++) {
				if (spelerObj.get(j - 1).getPunten() < spelerObj.get(j).getPunten()) {
					tempScore = spelerObj.get(j - 1).getPunten();
					tempNaam = spelerObj.get(j - 1).getNaam();

					spelerObj.get(j - 1).setPunten(spelerObj.get(j).getPunten());
					spelerObj.get(j - 1).setNaam(spelerObj.get(j).getNaam());

					spelerObj.get(j).setPunten(tempScore);
					spelerObj.get(j).setNaam(tempNaam);

				}
			}
		}

		for(int i = 0; i < spelerObj.size(); i++){
			spelers[i].setText((i + 1) + ". " + spelerObj.get(i).getNaam() + "  " + spelerObj.get(i).getPunten());
		}
		setScorebord = true;
		music();

	}

}
