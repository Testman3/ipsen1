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

/**
 * Deze class zorgt ervoor dat de CreditsScene goed wordt weergegeven.
 */
public class CreditsScene extends Scene implements SceneInitialiser {

	//Setting vars
	private MenuController controller;
	private int maxButtonWidth = 800;
	BorderPane mainPane;

	private SmartButton backToHome;
	private SmartLabel[] labels = new SmartLabel[5];

	private VBox creditText;
	private VBox creditsNamen;
	private VBox backToHomeButton;
	private VBox alles;

	private Label credits;

	/**
	 * Constructor van de creditsscene
	 * @param controller
	 * Geef MenuController mee
	 */
	public CreditsScene(MenuController controller) {

		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();
		this.controller = controller;

			initGui();
	}


	@Override
	public void initGui() {
		mainPane.getStylesheets().add("style.css");
		mainPane.setId("mainBackground");

		backToHome = new SmartButton("Terug naar Hoofdmenu");
		backToHome.setMaxWidth(maxButtonWidth);
		backToHome.setId("terugNaarMenu");

		creditText = new VBox();
		creditsNamen = new VBox(10);
		backToHomeButton = new VBox();
		alles = new VBox();

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

		credits = new SmartLabel();
		credits.setText("Credits");
		credits.setId("credits");

		creditsNamen.setAlignment(Pos.CENTER);
		backToHomeButton.setAlignment(Pos.CENTER);

		creditText.getChildren().add(credits);
		creditText.setAlignment(Pos.CENTER);

		backToHomeButton.getChildren().add(backToHome);

		alles.setId("schild");
		alles.getChildren().setAll(creditText, creditsNamen, backToHomeButton);

		mainPane.setCenter(alles);

		initAction();
	}

	@Override
	public void initAction() {
		backToHome.setOnAction(e -> {
		controller.backToMainMenu();
		});
	}

}