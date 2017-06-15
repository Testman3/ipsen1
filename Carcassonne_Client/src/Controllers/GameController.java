package Controllers;

import Models.GameClient;
import Models.Horige;
import Models.RMIInterface;

/**
 * Deze class regelt een aantal functies met betrekking tot het pakken en plaatsen van kaarten
 */
public class GameController {

	GameClient model;
	RMIInterface rmiStub;

	public GameController(GameClient model){
		this.model = model;
	}

	/**
	 * Deze functie zorgt ervoor dat de kaart uit het model gepakt wordt (roept pakKaart() aan in het model)
	 */
	public void klikPakKaart() {
		model.pakKaart();
	}
	public void klikDraaiKaart() {model.draaiKaart();}
	public void klikPlaatsHorige(Horige.Posities positie){
		model.plaatsHorige(positie);
	}
	/**
	 * Deze functie zorgt ervoor dat de kaart in het model (GameClient) geplaatst wordt
	 * @param x
	 * geef de x co-ordinaat van de kaart mee
	 * @param y
	 * geef de y co-ordinaat van de kaart mee
	 */
	public void klikPlaatsKaart(int x, int y) {
		model.plaatsKaart(x, y);
	}

	/**
	 * Deze functie
	 * @return model
	 */
	public GameClient getModel() {
		return model;
	}
}
