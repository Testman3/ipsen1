package Views;


import Controllers.LobbyController;
import Controllers.MenuController;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import commonFunctions.SmartLabel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class NaamSelecteerScene extends Scene implements SceneInitialiser{
	//Setting vars
	private MenuController controller;
	BorderPane mainPane;
	private SmartButton backToHome;
	private SmartLabel credits;
	private SmartLabel[] labels = new SmartLabel[5];
	private VBox alleElementen;
	private VBox alleNamen;
	private ChoiceBox naamDropDown;
	private SmartButton continueButton;

	private LobbyScene lobbyScene;

	/**
	 * Constructor van de NaamSelecteerScene
	 * @param controller
	 * Geef MenuController mee
	 */
	public NaamSelecteerScene(MenuController controller) {

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
		naamDropDown = new ChoiceBox();;
		naamDropDown.setId("menuKnop");
		continueButton = new SmartButton("Doorgaan");
		continueButton.setId("dropDown");

		mainPane.getStylesheets().add("style.css");
		mainPane.setId("mainBackground");

		backToHome = new SmartButton("Terug naar Hoofdmenu");
		backToHome.setId("terugNaarMenu");

		credits = new SmartLabel("Credits");
		credits.setId("credits");

		alleNamen.getChildren().addAll(naamDropDown, continueButton);
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

	public void fillDropDown(ArrayList<String> spelerNamen){
	for(int i = 0; i < spelerNamen.size(); i++){
		naamDropDown.getItems().add(spelerNamen.get(i));
	}
		naamDropDown.getSelectionModel().selectFirst();
	}

	public void join(LobbyScene scene, ArrayList<String> spelerNamen ){
		this.lobbyScene = scene;
		fillDropDown(spelerNamen);
	}

}
