package Models;

import Controllers.BordController;

public class Bord {

	Speler spelerBeurt;

	public Bord() {
		geefSpelerBeurt();
	}

	public Speler getSpelerBeurt() {

		return spelerBeurt;
	}

	public void geefSpelerBeurt() {
		Speler speler = new Speler("Speler1");
		spelerBeurt = speler;
	}
	/**
	 * returnt of de speler aan de beurt is
	 * @param spelerNaam
	 * @return
	 */
	public boolean isSpelerBeurt(String spelerNaam) {
		return spelerBeurt.naam == spelerNaam;
	}

}
