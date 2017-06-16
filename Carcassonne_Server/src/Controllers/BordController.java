package Controllers;

import Models.Horige;
import Models.Speler;
import Models.Stapel;
import Models.Bord;

import java.util.ArrayList;

/**
 * Deze class regelt alle functies die betrekking hebben tot het bord
 */
public class BordController {

	PuntenTeller puntenTeller;
	Stapel kaartenStapel;
	public Bord bord;
	private boolean gameVoorbij = false;

	/**
	 * Maak een bordcontroller aan met een nieuw bord en een nieuwe kaarten stapel
	 * @param spelerList
	 * Geef een ArrayList van alle spelers mee
	 */
	public BordController(ArrayList<Speler> spelerList) {
		puntenTeller = new PuntenTeller();
		kaartenStapel = new Stapel();
		bord = new Bord(spelerList);
		bord.plaatsKaartCheat(5,5,kaartenStapel.getBeginTile());
	}

	/**
	 * Deze functie zorgt ervoor dat er een kaart van de stapel wordt gepakt
	 * @param spelerNaam
	 * Geef de naam van de speler mee die de kaart wil pakken in de vorm van een String
	 * @return Wanneer de speler aan de beurt is krijgt hij de imageview van de volgende kaart in de stapel
	 * wanneer de speler niet aan de beurt is krijgt hij geen imageview terug van de volgende kaart
	 */
	String pakKaartvanStapel(String spelerNaam) {
		if (bord.isSpelerBeurt(spelerNaam)) {
			kaartenStapel.pakKaart();
			return kaartenStapel.getTurnTile().getImageID();
		}
		return null;
	}

	/**
	 * Roept de functie checkKaartFit aan in het bord
	 * @param x
	 * De x co-ordinaat van de kaart
	 * @param y
	 * De y co-ordinaat van de kaart
	 * @return True als de kaart past op de meegegeven locatie, false als de kaart niet past
	 */
	public boolean checkKaartFit(int x, int y) {
		return bord.checkKaartFit(x, y, kaartenStapel.getTurnTile());
	}

	/**
	 * Deze functie roept eerst de functie checkKaartFit aan in bord, als hij true is wordt de functie
	 * plaatsKaart aangeroepen in bord
	 * @param x
	 * De x co-ordinaat van de kaart
	 * @param y
	 * De y co-ordinaat van de kaart
	 * @return True als de kaart succesvol is geplaatst, false als de kaart niet succesvol is geplaatst
	 */
	boolean plaatsKaart(int x, int y) {
	if(bord.checkKaartFit(x,y,kaartenStapel.getTurnTile())){
		kaartenStapel.getTurnTile().plaats(x,y);
		bord.plaatsKaart(x,y,kaartenStapel.getTurnTile());
		return true;
	} else {
		return false;
	}
	}

	/**
	 * Deze functie zorgt ervoor dat een horige neergezet wordt
	 * @param positie
	 * Geef de positie mee
	 * @return
	 * True als de horige is geplaatst, false als dit niet het geval is
	 */
	boolean plaatsHorige(Horige.Posities positie){
		kaartenStapel.getTurnTile().plaatsHorige(positie, bord.getSpelerBeurt());
		System.out.println("Horige wordt geplaatst - bordcontroller");
		volgendeBeurt();
		return true;
	}
	/**
	 * Deze functie roept de functie draaiKaart aan in de kaartenStapel
	 * @param speler
	 * Geef de naam van de speler mee in de vorm van een String
	 * @return
	 * Geeft true terug als de kaart is gedraaid, false als dit niet is gebeurt
	 */
	boolean draaiKaart(String speler){
		if(kaartenStapel.getTurnTile() == bord.getLaatstGeplaatst()){
			return false;
		}
		if(!bord.isSpelerBeurt(speler)){
			return false;
		} else {
			kaartenStapel.getTurnTile().draaiKaart();
			return true;
		}
	}

	/**
	 * Deze functie zorgt voor het beeindigen van de beurt van de speler
	 * @param spelernaam
	 * Geef de spelernaam mee in de vorm van een String
	 */
	void beeindigBeurt(String spelernaam) {
		if(bord.isSpelerBeurt(spelernaam)){
			System.out.println("==@@@@@@@@@@@@@@@@@@@Speler heeft beurt beindigt");
			volgendeBeurt();
		}
	}

	public void volgendeBeurt() {
		puntenTeller.BerekenPunten(kaartenStapel.getTurnTile(), bord);
		bord.geefSpelerBeurt();
		isGameVoorbij();
	}

	public void isGameVoorbij() {
		if(kaartenStapel.getKaartenOver() == 0){
			gameVoorbij = true;
		}
	}

	public boolean getIsGameVoorbij(){
		return gameVoorbij;
	}
}
