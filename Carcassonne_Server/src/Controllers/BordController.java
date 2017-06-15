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

	Stapel kaartenStapel;
	public Bord bord;

	/**
	 * Maak een bordcontroller aan met een nieuw bord en een nieuwe kaarten stapel
	 * @param spelerList
	 * Geef een ArrayList van alle spelers mee
	 */
	public BordController(ArrayList<Speler> spelerList) {
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
	public String pakKaartvanStapel(String spelerNaam) {
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
	public boolean plaatsKaart(int x, int y) {
	if(bord.checkKaartFit(x,y,kaartenStapel.getTurnTile())){
		kaartenStapel.getTurnTile().plaats(x,y);
		bord.plaatsKaart(x,y,kaartenStapel.getTurnTile());
		return true;
	} else {
		return false;
	}
	}

	public boolean plaatsHorige(Horige.Posities positie){
		kaartenStapel.getTurnTile().plaatsHorige(positie, bord.getSpelerBeurt());
		System.out.println("Horige wordt geplaatst - bordcontroller");
		bord.geefSpelerBeurt();
		return true;
	}
	/**
	 * Deze functie roept de functie draaiKaart aan in de kaartenStapel
	 */
	public boolean draaiKaart(String speler){
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

	public void beeindigBeurt(String spelernaam) {
		if(bord.isSpelerBeurt(spelernaam)){
			bord.geefSpelerBeurt();
			System.out.println("==@@@@@@@@@@@@@@@@@@@Speler heeft beurt beindigt");
		}
	}
}
