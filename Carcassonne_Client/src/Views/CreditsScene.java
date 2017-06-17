package Views;

import Controllers.MenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import commonFunctions.*;

import javax.swing.text.html.ImageView;

/**
 * Deze class zorgt ervoor dat de CreditsScene goed wordt weergegeven.
 */
public class CreditsScene extends Scene implements SceneInitialiser {

	//Setting vars
	private MenuController controller;
	BorderPane mainPane;
	private SmartButton backToHome;
	private SmartLabel credits;
	private SmartLabel[] labels = new SmartLabel[5];
	private VBox alleElementen;
	private VBox alleNamen;


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

		alleElementen = new VBox(10);
		alleNamen= new VBox(5);
		alleElementen.setId("schild");

		mainPane.getStylesheets().add("style.css");
		mainPane.setId("mainBackground");

		backToHome = new SmartButton("Terug naar Hoofdmenu");
		backToHome.setId("terugNaarMenu");

		credits = new SmartLabel("Credits");
		credits.setId("credits");





		/*
		 * 0 = Martijn 1 = Jusin 2 = Raymon 3 = Haitam 4 = Henk
		 */

		for (int i = 0; i < labels.length; i++ ){
			labels[i] = new SmartLabel();
			labels[i].setId("creditsLabel");
			alleNamen.getChildren().add(labels[i]);
		}

		labels[0].setText("Martijn van Adrichem");
		labels[1].setText("Justin Moor");
		labels[2].setText("Raymon Haalebos");
		labels[3].setText("Haitam el Attar");
		labels[4].setText("Henk van Overbeek");

		alleNamen.setAlignment(Pos.CENTER);



		alleElementen.getChildren().add(credits);
		alleElementen.getChildren().add(alleNamen);
		alleElementen.getChildren().add(backToHome);
		alleElementen.setAlignment(Pos.CENTER);


		mainPane.setCenter(alleElementen);


		initAction();
	}

	@Override
	public void initAction() {
		backToHome.setOnAction(e -> {
		controller.backToMainMenu();
		});
	}

}