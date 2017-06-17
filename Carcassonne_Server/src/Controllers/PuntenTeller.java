package Controllers;

import Models.*;
import com.sun.jndi.ldap.Ber;
import commonFunctions.Point;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import Models.Zijde.ZijdeType;

/**
 * Deze class zorgt voor het berekenen van alle punten
 */
public class PuntenTeller {

	/**
	 * Deze functie berekent punten
	 * @param tile
	 * Geef de gewenste tile mee
	 * @param bord
	 * Geef het bord mee
	 */
	public void BerekenPunten(Tile tile, Bord bord){
		System.out.println("=================================BEREKENEN PUNTEN======================");
		ArrayList<Tile> alleBuren = new ArrayList<>();

		//Kijk om je heen, zit er ergens een klooster?
		int x = tile.getX();
		int y = tile.getY();
		Tile noord = bord.getTile(x, y-1);
		Tile oost = bord.getTile(x+1, y );
		Tile zuid = bord.getTile(x, y+1 );
		Tile west = bord.getTile(x-1, y );
		Tile noordOost = bord.getTile(x+1, y-1 );
		Tile zuidOost = bord.getTile(x+1, y+1 );
		Tile zuidWest = bord.getTile(x-1, y+1 );
		Tile noordWest = bord.getTile(x-1, y-1 );

		alleBuren.addAll(Arrays.asList(tile, noord, oost, zuid, west, noordOost, zuidOost, zuidWest, noordWest));
		for (Tile buur:alleBuren) {
			if(buur == null){
				continue;
			}
			if(buur.getHeeftKlooster()){
				System.out.println("Klooster berekenen!");
				//Er zit een klooster, kijk of deze is afgebouwd
				BerekenKloosters(buur, bord);
			}
		}

		if(tile.getNoordZijde().getZijde() == ZijdeType.WEG && tile.getNoordZijde().getIsEinde()){
			berekenWegen(bord.getTile(tile.getX(), tile.getY() - 1), bord);
		}
		if(tile.getOostZijde().getZijde() == ZijdeType.WEG && tile.getOostZijde().getIsEinde()){
			berekenWegen(bord.getTile(tile.getX() + 1, tile.getY()), bord);
		}
		if(tile.getZuidZijde().getZijde() == ZijdeType.WEG && tile.getZuidZijde().getIsEinde()){
			berekenWegen(bord.getTile(tile.getX(), tile.getY() + 1), bord);
		}
		if(tile.getWestZijde().getZijde() == ZijdeType.WEG && tile.getWestZijde().getIsEinde()){
			berekenWegen(bord.getTile(tile.getX() - 1, tile.getY()), bord);
		}
		if(tile.getNoordZijde().getZijde() == ZijdeType.WEG && !tile.getNoordZijde().getIsEinde() ||
			tile.getOostZijde().getZijde() == ZijdeType.WEG && !tile.getOostZijde().getIsEinde() ||
			tile.getZuidZijde().getZijde() == ZijdeType.WEG && !tile.getZuidZijde().getIsEinde() ||
			tile.getWestZijde().getZijde() == ZijdeType.WEG && !tile.getWestZijde().getIsEinde()){
				berekenWegen(tile, bord);
		}

		System.out.println("==========================EINDE BEREKENEN PUNTEN==============================");
	}

	/**
	 * Deze functie berekent de punten voor kloosters
	 * @param tile
	 * Geef de gewenste tile mee
	 * @param bord
	 * Geef het bord mee
	 */
	public void BerekenKloosters(Tile tile, Bord bord) {

		if(tile.getMiddenZijde().getHorigeSpeler() == null ){
			System.out.println("Geen horige op tile " + tile.getX() + " " + tile.getY());
			return;
		}

		ArrayList<Tile> alleBuren = new ArrayList<>();
		int x = tile.getX();
		int y = tile.getY();
		Tile noord = bord.getTile(x, y-1);
		Tile oost = bord.getTile(x+1, y );
		Tile zuid = bord.getTile(x, y+1 );
		Tile west = bord.getTile(x-1, y );
		Tile noordOost = bord.getTile(x+1, y-1 );
		Tile zuidOost = bord.getTile(x+1, y+1 );
		Tile zuidWest = bord.getTile(x-1, y+1 );
		Tile noordWest = bord.getTile(x-1, y-1 );
		alleBuren.addAll(Arrays.asList(noord, oost, zuid, west, noordOost, zuidOost, zuidWest, noordWest));

		for (Tile buur:alleBuren) {
			if(buur == null){
				System.out.println("Klooster op positie " + x + " " + y + " is niet af!");
				return;
			}
		}
		geefPunten(tile.getMiddenZijde().getHorigeSpeler().getSpeler(), 9);
		tile.getMiddenZijde().getHorigeSpeler().getSpeler().getHorigeTerug(tile.getMiddenZijde().getHorigeSpeler());
		bord.verwijderHorige(new Point(x,y));

	}

	private enum entryZijde{
		NOORD,
		OOST,
		ZUID,
		WEST
	}


	/**
	 * Deze funcite berekent de punten voor wegen
	 * @param tile
	 * Geef de gewenste tile mee
	 * @param bord
	 * Geef het bord mee
	 */
	public void berekenWegen(Tile tile, Bord bord) {
		ArrayList<Tile> wegNetwerk = new ArrayList<>();
		ArrayList<Horige> horigeInNetwerk = new ArrayList<Horige>();
		int eindes = 0;
		berekenWeg(tile, bord, wegNetwerk, eindes);

		for (int i = 0; i < wegNetwerk.size(); i++) {
			if(wegNetwerk.get(i).getGeplaatsteHorigePositie() != null){
				//FIXME op dit moment worden ALLE horige toegevoegd, ook de horige die op een klooster of kasteel staan!
				horigeInNetwerk.add(wegNetwerk.get(i).getGeplaatsteHorigePositie());
			}
		}
		for (int h = 0; h < horigeInNetwerk.size() ; h++) {
			geefPunten(horigeInNetwerk.get(h).getSpeler(), wegNetwerk.size());
			System.out.println("Speler " + horigeInNetwerk.get(h).getSpeler() + "  heeft voor het afmaken van een weg " + wegNetwerk.size() + " punten geschreven");
			System.out.println("Eindes " + eindes );
		}
	}

	/**
	 * Deze functie berekend wat een weg is
	 * @param tile
	 * Geef de tile mee
	 * @param bord
	 * Geef het bord mee
	 * @param netwerk
	 * Geef een arraylist van tiles mee
	 * @param eindes
	 * Geef het aantal eindes mee
	 */
	public void berekenWeg(Tile tile, Bord bord, ArrayList<Tile> netwerk, int eindes){

		if(netwerk.contains(tile)){
			return;
		}

		if(tile == null){
			return;
		}

		if(eindes == 2){
			return;
		}

		addToNetwork(netwerk,tile);

		if(tile.getNoordZijde().getZijde() == ZijdeType.WEG) {
			if(!tile.getNoordZijde().getIsEinde()){
				Tile nextTile = bord.getTile(tile.getX(),tile.getY() - 1);
				berekenWeg(nextTile,bord,  netwerk, eindes);
			} else {
				eindes++;
				return;
			};
		}
		if(tile.getOostZijde().getZijde() == ZijdeType.WEG) {
			if(!tile.getOostZijde().getIsEinde()){
				Tile nextTile = bord.getTile(tile.getX() + 1,tile.getY() );
				berekenWeg(nextTile,bord,netwerk, eindes);
			} else {
				eindes++;
				return;
			}
		}
		if(tile.getZuidZijde().getZijde() == ZijdeType.WEG) {
			if(!tile.getZuidZijde().getIsEinde()){
				Tile nextTile = bord.getTile(tile.getX(),tile.getY() + 1);
				berekenWeg(nextTile,bord,netwerk, eindes);
			} else {
				eindes++;
				return;
			}
		}
		if(tile.getWestZijde().getZijde() == ZijdeType.WEG) {
			if(!tile.getWestZijde().getIsEinde()){
				Tile nextTile = bord.getTile(tile.getX() - 1 ,tile.getY());
				berekenWeg(nextTile,bord,netwerk, eindes);
			} else {
				eindes++;
				return;
			}
		}
	}

//	public void berekenZijde(Tile tile,Bord bord, entryZijde zijde,  ArrayList<Tile> netwerk, int eindes){
//		if(zijde == entryZijde.NOORD && ) {
//			if(!tile.getNoordZijde().getIsEinde()){
//				Tile nextTile = bord.getTile(tile.getX(),tile.getY() - 1);
//				berekenWeg(nextTile,bord,netwerk, eindes);
//			} else eindes++;
//		}
//		if(tile.getOostZijde().getZijde() == ZijdeType.WEG) {
//			if(!tile.getOostZijde().getIsEinde()){
//				Tile nextTile = bord.getTile(tile.getX() + 1,tile.getY() );
//				berekenWeg(nextTile,bord,netwerk, eindes);
//			} else eindes++;
//		}
//		if(tile.getZuidZijde().getZijde() == ZijdeType.WEG) {
//			if(!tile.getZuidZijde().getIsEinde()){
//				Tile nextTile = bord.getTile(tile.getX(),tile.getY() + 1);
//				berekenWeg(nextTile,bord,netwerk, eindes);
//			} else eindes++;
//		}
//		if(tile.getWestZijde().getZijde() == ZijdeType.WEG) {
//			if(!tile.getWestZijde().getIsEinde()){
//				Tile nextTile = bord.getTile(tile.getX() - 1 ,tile.getY());
//				berekenWeg(nextTile,bord,netwerk, eindes);
//			} else eindes++;
//		}
//	}

	public void addToNetwork(ArrayList<Tile> netwerk, Tile tile){
		if(tile==null){
			return;
		}
		if(netwerk.contains(tile)){
			System.out.println("Tile zit al in het netwerk!");
			return;
		}
		netwerk.add(tile);

	}

	/**
	 * Deze functie geeft de punten aan de speler
	 * @param speler
	 * Geef de speler mee die de punten moet krijgen
	 * @param hoeveelheid
	 * Geef de hoeveelheid punten mee die de speler moet krijgen
	 */
	public void geefPunten(Speler speler, int hoeveelheid){
		speler.geefPunten(hoeveelheid);
		System.out.println("Punten gegeven aan speler " + speler.getNaam() + " ( " + speler.getPunten() + " punten)");
	}

}
