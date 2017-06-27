package Controllers;

import Models.*;

import java.util.ArrayList;

/**
 * Deze class regelt alle functies die betrekking hebben tot het bord
 */
public class BordController {
	private int stopgetal = 0;
	private PuntenTeller puntenTeller;
	public Stapel kaartenStapel;
	public Bord bord;
	private boolean gameVoorbij = false;

	/**
	 * Maak een bordcontroller aan met een nieuw bord en een nieuwe kaarten stapel
	 *
	 * @param spelerList Geef een ArrayList van alle spelers mee
	 */
	public BordController(ArrayList<Speler> spelerList) {
		puntenTeller = new PuntenTeller();
		kaartenStapel = new Stapel();
		bord = new Bord(spelerList);
		bord.plaatsKaartCheat(50, 50, kaartenStapel.getBeginTile());
	}

	public BordController(ArrayList<Speler> spelerList, Tile[][] tiles, ArrayList<Tile> currentStapel) {
		puntenTeller = new PuntenTeller();
		kaartenStapel = new Stapel(currentStapel);
		bord = new Bord(spelerList, tiles);
		bord.plaatsLoadKaart(tiles);
	}

	/**
	 * Deze functie zorgt ervoor dat er een kaart van de stapel wordt gepakt
	 *
	 * @param spelerNaam Geef de naam van de speler mee die de kaart wil pakken in de vorm van een String
	 * @return Wanneer de speler aan de beurt is krijgt hij de imageview van de volgende kaart in de stapel
	 * wanneer de speler niet aan de beurt is krijgt hij geen imageview terug van de volgende kaart
	 */
	public String pakKaartvanStapel(String spelerNaam) {
		if (bord.isSpelerBeurt(spelerNaam)) {
			kaartenStapel.pakKaart();
			if (bord.isKaartPlaatsbaar(kaartenStapel.getTurnTile())) {
				return kaartenStapel.getTurnTile().getImageID();
			}
			System.out.println("Er is een onplaatsbare kaart gevonden! Namelijk kaart ID " + kaartenStapel.getTurnTile().getImageID());

			kaartenStapel.stopLaatsteKaartTerug();
			return pakKaartvanStapel(spelerNaam);

		}
		return null;
	}

	/**
	 * Deze functie roept eerst de functie checkKaartFit aan in bord, als hij true is wordt de functie
	 * plaatsKaart aangeroepen in bord
	 *
	 * @param x De x co-ordinaat van de kaart
	 * @param y De y co-ordinaat van de kaart
	 * @return True als de kaart succesvol is geplaatst, false als de kaart niet succesvol is geplaatst
	 */
	boolean plaatsKaart(int x, int y) {
		if (bord.checkKaartFit(x, y, kaartenStapel.getTurnTile())) {
			kaartenStapel.getTurnTile().plaats(x, y);
			bord.plaatsKaart(x, y, kaartenStapel.getTurnTile());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Deze functie zorgt ervoor dat een horige neergezet wordt
	 *
	 * @param positie Geef de positie mee
	 * @return True als de horige is geplaatst, false als dit niet het geval is
	 */
	boolean plaatsHorige(Horige.Posities positie) {
		kaartenStapel.getTurnTile().plaatsHorige(positie, bord.getSpelerBeurt());
		System.out.println("Horige wordt geplaatst - bordcontroller");
		volgendeBeurt();
		return true;
	}

	/**
	 * Deze functie roept de functie draaiKaart aan in de kaartenStapel
	 *
	 * @param speler Geef de naam van de speler mee in de vorm van een String
	 * @return Geeft true terug als de kaart is gedraaid, false als dit niet is gebeurt
	 */
	boolean draaiKaart(String speler) {
		if (kaartenStapel.getTurnTile() == bord.getLaatstGeplaatst()) {
			return false;
		}
		if (!bord.isSpelerBeurt(speler)) {
			return false;
		} else {
			kaartenStapel.getTurnTile().draaiKaart();
			return true;
		}
	}

	/**
	 * Deze functie zorgt voor het beeindigen van de beurt van de speler
	 *
	 * @param spelernaam Geef de spelernaam mee in de vorm van een String
	 */
	public void beeindigBeurt(String spelernaam) {
		if (bord.isSpelerBeurt(spelernaam)) {
			System.out.println(spelernaam + " heeft beurt beindigt");
			volgendeBeurt();
		}
	}

	public void volgendeBeurt() {
		puntenTeller.BerekenPunten(kaartenStapel.getTurnTile(), bord);
		bord.geefSpelerBeurt();
		isGameVoorbij();
	}

	public void volgendeBeurtNaLeave(String naam) {
		if (bord.isSpelerBeurt(naam)) {
			bord.geefSpelerBeurt();
		}

	}

	public void isGameVoorbij() {
		if (kaartenStapel.getKaartenOver() == stopgetal) {
			gameVoorbij = true;
		}
	}

	public boolean getIsGameVoorbij() {
		return gameVoorbij;
	}


	public void setStopgetal() {
		this.stopgetal = kaartenStapel.getKaartenOver() - 1;
	}
}
