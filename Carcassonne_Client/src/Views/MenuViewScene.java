package Views;

import Controllers.MenuController;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import static javafx.scene.media.MediaPlayer.INDEFINITE;

/**
 * Deze class zorgt ervoor dat de MenuViewScene goed wordt weergegeven.
 */
public class MenuViewScene extends Scene implements SceneInitialiser{

	private static Media backgroundMuziek = new Media(Paths.get("Sounds/BackgroundMusic.mp3").toUri().toString());
	static MediaPlayer mediaPlayer = new MediaPlayer(backgroundMuziek);
	private BorderPane mainPane;
	private Label titel = new Label("Carcassonne");
	private VBox buttonVBox = new VBox();
	private MenuController controller;
	// alle hoofdmenu buttons (smartButton = button met spraak)
	private SmartButton[] knoppen = new SmartButton[6];

	/**
	 * Constructor van de MenuViewScene
	 * @param controller
	 * Geef MenuController mee
	 */
	public MenuViewScene(MenuController controller){
		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();
		this.controller = controller;

		initGui();
	}

	public void initGui() {
		buttonVBox.setId("schild");
		mainPane.getStylesheets().add("style.css");
		mainPane.setId("mainBackground");
		titel.setId("title");

		buttonVBox.getChildren().add(titel);

		mainPane.setCenter(buttonVBox);
		buttonVBox.setAlignment(Pos.CENTER);

		initAction();
	}

	public void initAction(){
			mediaPlayer.setCycleCount(INDEFINITE);
		if (!mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING) && SettingsScene.optieGeluid ){
			mediaPlayer.setVolume(0.2);
			mediaPlayer.play();
		}

		/*
		 * 0 = New game 1 = Laden game
		 * 2 = Gebruiksaanwijzing
		 * 3 = About
		 * 4 = Instellingen
		 * 5 = Spel verlaten
		 */

		for (int i = 0; i < knoppen.length; i++) {
			knoppen[i] = new SmartButton();
			knoppen[i].setId("standardLabel");
			buttonVBox.getChildren().add(knoppen[i]);
		}

		knoppen[0].setText("Nieuw spel");
		knoppen[0].setOnAction(e -> {
			controller.setPreLobbyScene();
		});

		knoppen[1].setText("Laden spel");
		knoppen[1].setOnAction(e -> {
			controller.setPreLobbyScene();
			controller.loadedFile = controller.openFileBrowser();
		});

		// Maakt een variabele aan die naar het handleiding document verwijst,
		// wanneer je op de handleiding knop drukt wordt het html doc geopend
		// in het default programma voor het openen van .html

		File handleidingDoc = new File("Handleiding.html");
		knoppen[2].setText("Spelregels");
		knoppen[2].setOnAction(e -> {
			try {
				Desktop.getDesktop().browse(handleidingDoc.toURI());
			} catch (IOException e1) {
				System.out.println("File not Found");
			}
		});

		knoppen[3].setText("Credits");
		knoppen[3].setOnAction(e -> {
			controller.setCreditsScene();

		});

		knoppen[4].setText("Instellingen");
		knoppen[4].setOnAction(e -> {
			controller.setSettingsScene();

		});

		//Try Catch blok is hier nodig omdat anders het geluid niet af speelt voordat het programma wordt gesloten
		//Er word precies zo lang gewacht als het geluid lang is
		knoppen[5].setText("Spel afsluiten");
		knoppen[5].setOnAction(e -> {
			try {
				Thread.sleep(142);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		});


	}

}