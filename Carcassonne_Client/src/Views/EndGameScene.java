package Views;

import Controllers.LobbyController;
import Controllers.MenuController;
import Models.Speler;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import commonFunctions.SmartLabel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.rmi.RemoteException;
import java.util.ArrayList;


public class EndGameScene extends Scene implements SceneInitialiser {

	private BorderPane mainPane;
	private MenuController controller;
	private LobbyController lobbyController;

	private VBox allesContainer;
	private SmartLabel titel;
	private SmartLabel[] spelers;
	private SmartButton exit;

	private ArrayList<Speler> spelerObj;


	public EndGameScene(MenuController controller) {
		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();
		this.controller = controller;

		initGui();
	}

	@Override
	public void initGui() {

		mainPane.getStylesheets().add("style.css");
		allesContainer = new VBox();
		spelers = new SmartLabel[5];
		exit = new SmartButton("Spel verlaten");

		titel = new SmartLabel("Score:");
		titel.setId("title");

		mainPane.setId("exitBackground");

		for (int i = 0; i < spelers.length; i++) {
			spelers[i] = new SmartLabel();
			spelers[i].setId("standardLabel");
			allesContainer.getChildren().add(spelers[i]);
		}

		mainPane.getChildren().add(allesContainer);

		setScoreboard();
		initAction();

	}

	@Override
	public void initAction() {

		exit.setOnAction(e -> {
			try {
				lobbyController.RMIstub.removePlayer(controller.getSpelernaam());

			} catch (RemoteException b) {

			}
			controller.backToMainMenu();
		});

	}

	private void setScoreboard(){

		spelerObj = new ArrayList<>();


		try {
			spelerObj = lobbyController.getRmiStub().getPlayerListObject();

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		//Bubblesort om arraylist met spelers te sorteren op de hoogte van hun score
		//Index van hun score en naam veranderd naar de hoogte van hun score.
		int tempScore;
		String tempNaam;

		for (int i = 0; i < spelerObj.size(); i++) {
			for (int j = 1; j < spelerObj.size() - i; j++) {
				if (spelerObj.get(j - 1).getPunten() > spelerObj.get(j).getPunten()) {
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
			spelers[i].setText(spelerObj.get(i).getNaam());
		}

	}

}
