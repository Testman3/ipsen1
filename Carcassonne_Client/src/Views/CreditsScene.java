package Views;

import java.nio.file.Paths;
import java.rmi.RemoteException;

import Controllers.MenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

public class CreditsScene extends Scene {

	private MenuController controller;
	private int maxButtonWidth = 400;
	private AudioClip clickSound = new AudioClip(Paths.get("Sounds/Snd_Click.wav").toUri().toString());
	
	BorderPane mainPane;
	
	public CreditsScene(MenuController controller) {

		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();
		this.controller = controller;

		try {
			init();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws RemoteException {
		
		mainPane.getStylesheets().add("style.css");
		Button backToHome = new Button("Terug naar Hoofdmenu");
		backToHome.setMaxWidth(maxButtonWidth);
		backToHome.setId("menuKnoppen");
		backToHome.setOnAction(e -> {
			clickSound.play();
			controller.backToMainMenu();			
		});
		
		VBox creditText = new VBox();
		VBox creditsNamen = new VBox(10);
		VBox backToHomeButton = new VBox();
		
		backToHomeButton.getChildren().add(backToHome);
		
		Label[] labels = new Label[5];
		
		/*
		 * 0 = Martijn 1 = Jusin 2 = Raymon 3 = Haitam 4 = Henk
		 */
		
		for (int i = 0; i < labels.length; i++ ){
			labels[i] = new Label();
			labels[i].setId("labelStyle");
			creditsNamen.getChildren().add(labels[i]);
		}
		
		labels[0].setText("Martijn van Adrichem");
		labels[1].setText("Justin Moor");
		labels[2].setText("Raymon Haalebos");
		labels[3].setText("Haitam el Attar");
		labels[4].setText("Henk van Overbeek");
		Label credits = new Label();
		credits.setText("Credits");
		credits.setId("creditStyle");
		
		creditsNamen.setAlignment(Pos.CENTER);
		backToHomeButton.setAlignment(Pos.CENTER);
		credits.setAlignment(Pos.CENTER);
		
		creditText.getChildren().add(credits);
		creditText.setAlignment(Pos.CENTER);
		
		mainPane.setTop(creditText);
		mainPane.setCenter(creditsNamen);
		mainPane.setBottom(backToHomeButton);
		
		
	}
	
	
}