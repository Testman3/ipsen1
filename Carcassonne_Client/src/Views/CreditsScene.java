package Views;

import java.nio.file.Paths;
import java.rmi.RemoteException;

import Controllers.MenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import commonFunctions.*;

public class CreditsScene extends Scene {

	private MenuController controller;
	private int maxButtonWidth = 800;
	private VBox buttonVBox = new VBox();
	
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
		mainPane.setId("mainBackground");

		SmartButton backToHome = new SmartButton("Terug naar Hoofdmenu");
		backToHome.setMaxWidth(maxButtonWidth);
		backToHome.setId("standardLabel");
		backToHome.setOnAction(e -> {
			controller.backToMainMenu();			
		});
		
		VBox creditText = new VBox();
		VBox creditsNamen = new VBox(10);
		VBox backToHomeButton = new VBox();
		VBox alles = new VBox();
		
		backToHomeButton.getChildren().add(backToHome);

		SmartLabel[] labels = new SmartLabel[5];
		
		/*
		 * 0 = Martijn 1 = Jusin 2 = Raymon 3 = Haitam 4 = Henk
		 */
		
		for (int i = 0; i < labels.length; i++ ){
			labels[i] = new SmartLabel();
			labels[i].setId("creditsLabel");
			creditsNamen.getChildren().add(labels[i]);
		}
		
		labels[0].setText("Martijn van Adrichem");
		labels[1].setText("Justin Moor");
		labels[2].setText("Raymon Haalebos");
		labels[3].setText("Haitam el Attar");
		labels[4].setText("Henk van Overbeek");
		Label credits = new SmartLabel();
		credits.setText("Credits");
		credits.setId("titel");

		creditsNamen.setAlignment(Pos.CENTER);
		backToHomeButton.setAlignment(Pos.CENTER);
		
		creditText.getChildren().add(credits);
		creditText.setAlignment(Pos.CENTER);

		alles.setId("schild");
		alles.getChildren().setAll(creditText, creditsNamen, backToHomeButton);

		mainPane.setCenter(alles);

	}
}
