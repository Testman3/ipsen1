package Views;

import javafx.scene.Scene;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import Controllers.MenuController;
import commonFunctions.SmartButton;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuViewScene extends Scene{

	private AudioClip clickSound = new AudioClip(Paths.get("Sounds/Snd_Click.wav").toUri().toString());
	VBox mainPane;
	private MenuController controller;

	// Smartbutton kan text uitspreken
	private SmartButton[] knoppen = new SmartButton[6];


	public MenuViewScene(MenuController controller){
		super(new VBox(), 400,400);
		mainPane = (VBox) this.getRoot();
		this.controller = controller;

		init();
	}

	// Init alle menu buttons
	public void initButtons(){
		/*
		 * 0 = New game 1 = Laden game
		 * 2 = Gebruiksaanwijzing
		 * 3 = About 4 = Instellingen
		 * 5 = Spel verlaten
		 */
		for (int i = 0; i < knoppen.length; i++) {
			knoppen[i] = new SmartButton();
			knoppen[i].setId("menuKnoppen");
			// Elke knop een klik sound
			knoppen[i].setOnAction(e -> {
				clickSound.play();
			});
			mainPane.getChildren().add(knoppen[i]);
		}
	}

	public void init() {
		mainPane.getStylesheets().add("style.css");


		// Init alle menu buttons
		initButtons();

		mainPane.setAlignment(Pos.CENTER);

		knoppen[0].setText("Nieuw spel");
		knoppen[0].setOnAction(e -> {
			controller.setPreLobbyScene();
		});

		knoppen[1].setText("Laad spel");

		// Maakt een variabele aan die naar het handleiding document verwijst,
		// wanneer je op de handleiding knop drukt wordt het html doc geopend
		// in het default programma voor het openen van .html
		File handleidingDoc = new File("Handleiding.html");
		knoppen[2].setText("Gebruiksaanwijzing");

		knoppen[2].setOnAction(e -> {
			try {
				Desktop.getDesktop().browse(handleidingDoc.toURI());
			} catch (IOException e1) {
				System.out.println(e1);
			}
		});

		knoppen[3].setText("Credits");
		knoppen[4].setText("Instellingen");

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