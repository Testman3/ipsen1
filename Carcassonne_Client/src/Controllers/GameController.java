package Controllers;

import Models.GameClient;
import Models.RMIInterface;

/**
 * Created by Marti on 11-6-2017.
 */
public class GameController {

	GameClient model;
	RMIInterface rmiStub;

	public GameController(GameClient model){
		this.model = model;
	}

	public void klikPakKaart() {
		model.pakKaart();
	}
	public void klikPlaatsKaart(int x, int y) {
		model.plaatsKaart(x, y);
	}
	public GameClient getModel() {
		return model;
	}
}
