package Controllers;

import Models.*;
import com.sun.jndi.ldap.Ber;
import commonFunctions.Point;
import javafx.geometry.Point2D;

import java.util.*;

import Models.Zijde.ZijdeType;

/**
 * Deze class zorgt voor het berekenen van alle punten
 */
public class PuntenTeller {

	/**
	 * Deze functie berekent punten
	 *
	 * @param tile Geef de gewenste tile mee
	 * @param bord Geef het bord mee
	 */
	public void BerekenPunten(Tile tile, Bord bord) {
		bord.getVerwijderHorigeDezeRonde().clear();
		System.out.println("=================================BEREKENEN PUNTEN======================");
		ArrayList<Tile> alleBuren = new ArrayList<>();

		//Kijk om je heen, zit er ergens een klooster?
		int x = tile.getX();
		int y = tile.getY();
		Tile noord = bord.getTile(x, y - 1);
		Tile oost = bord.getTile(x + 1, y);
		Tile zuid = bord.getTile(x, y + 1);
		Tile west = bord.getTile(x - 1, y);
		Tile noordOost = bord.getTile(x + 1, y - 1);
		Tile zuidOost = bord.getTile(x + 1, y + 1);
		Tile zuidWest = bord.getTile(x - 1, y + 1);
		Tile noordWest = bord.getTile(x - 1, y - 1);

		alleBuren.addAll(Arrays.asList(tile, noord, oost, zuid, west, noordOost, zuidOost, zuidWest, noordWest));
		for (Tile buur : alleBuren) {
			if (buur == null) {
				continue;
			}
			if (buur.getHeeftKlooster()) {
				System.out.println("Klooster berekenen!");
				//Er zit een klooster, kijk of deze is afgebouwd
				BerekenKloosters(buur, bord);
			}
		}
		System.out.println("==============BEREKENEN WEGEN============");

		if (tile.getNoordZijde().getZijde() == ZijdeType.WEG && tile.getNoordZijde().getIsEinde()) {
			berekenWegen(tile, bord, entryPoint.ZUID);
		}
		if (tile.getOostZijde().getZijde() == ZijdeType.WEG && tile.getOostZijde().getIsEinde()) {
			berekenWegen(tile, bord, entryPoint.WEST);
		}
		if (tile.getZuidZijde().getZijde() == ZijdeType.WEG && tile.getZuidZijde().getIsEinde()) {
			berekenWegen(tile, bord, entryPoint.NOORD);
		}
		if (tile.getWestZijde().getZijde() == ZijdeType.WEG && tile.getWestZijde().getIsEinde()) {
			berekenWegen(tile, bord, entryPoint.OOST);
		}
		if (tile.getNoordZijde().getZijde() == ZijdeType.WEG && !tile.getNoordZijde().getIsEinde() ||
				tile.getOostZijde().getZijde() == ZijdeType.WEG && !tile.getOostZijde().getIsEinde() ||
				tile.getZuidZijde().getZijde() == ZijdeType.WEG && !tile.getZuidZijde().getIsEinde() ||
				tile.getWestZijde().getZijde() == ZijdeType.WEG && !tile.getWestZijde().getIsEinde()) {
			berekenWegen(tile, bord, entryPoint.GEEN);
		}
		System.out.println("==============BEREKENEN KASTELEN============");
		if (tile.getNoordZijde().getZijde() == ZijdeType.KASTEEL && tile.getNoordZijde().getIsEinde()) {
			berekenKastelen(tile, bord, entryPoint.ZUID);
		}
		if (tile.getOostZijde().getZijde() == ZijdeType.KASTEEL && tile.getOostZijde().getIsEinde()) {
			berekenKastelen(tile, bord, entryPoint.WEST);
		}
		if (tile.getZuidZijde().getZijde() == ZijdeType.KASTEEL && tile.getZuidZijde().getIsEinde()) {
			berekenKastelen(tile, bord, entryPoint.NOORD);
		}
		if (tile.getWestZijde().getZijde() == ZijdeType.KASTEEL && tile.getWestZijde().getIsEinde()) {
			berekenKastelen(tile, bord, entryPoint.OOST);
		}
		if (tile.getNoordZijde().getZijde() == ZijdeType.KASTEEL && !tile.getNoordZijde().getIsEinde() ||
				tile.getOostZijde().getZijde() == ZijdeType.KASTEEL && !tile.getOostZijde().getIsEinde() ||
				tile.getZuidZijde().getZijde() == ZijdeType.KASTEEL && !tile.getZuidZijde().getIsEinde() ||
				tile.getWestZijde().getZijde() == ZijdeType.KASTEEL && !tile.getWestZijde().getIsEinde()) {
			berekenKastelen(tile, bord, entryPoint.GEEN);
		}

			System.out.println("==========================EINDE BEREKENEN PUNTEN==============================");
	}

	/**
	 * Deze functie berekent de punten voor kloosters
	 *
	 * @param tile Geef de gewenste tile mee
	 * @param bord Geef het bord mee
	 */
	public void BerekenKloosters(Tile tile, Bord bord) {

		if (tile.getMiddenZijde().getHorigeSpeler() == null) {
			System.out.println("Geen horige op tile " + tile.getX() + " " + tile.getY());
			return;
		}

		ArrayList<Tile> alleBuren = new ArrayList<>();
		int x = tile.getX();
		int y = tile.getY();
		Tile noord = bord.getTile(x, y - 1);
		Tile oost = bord.getTile(x + 1, y);
		Tile zuid = bord.getTile(x, y + 1);
		Tile west = bord.getTile(x - 1, y);
		Tile noordOost = bord.getTile(x + 1, y - 1);
		Tile zuidOost = bord.getTile(x + 1, y + 1);
		Tile zuidWest = bord.getTile(x - 1, y + 1);
		Tile noordWest = bord.getTile(x - 1, y - 1);
		alleBuren.addAll(Arrays.asList(noord, oost, zuid, west, noordOost, zuidOost, zuidWest, noordWest));

		for (Tile buur : alleBuren) {
			if (buur == null) {
				System.out.println("Klooster op positie " + x + " " + y + " is niet af!");
				return;
			}
		}
		geefPunten(tile.getMiddenZijde().getHorigeSpeler().getSpeler(), 9);
		tile.getMiddenZijde().getHorigeSpeler().getSpeler().getHorigeTerug(tile.getMiddenZijde().getHorigeSpeler());
		bord.verwijderHorige(new Point(x, y));

	}



	/**
	 * Deze funcite berekent de punten voor wegen
	 *
	 * @param tile Geef de gewenste tile mee
	 * @param bord Geef het bord mee
	 */
	int eindes = 0;
	Tile startTile;
	boolean startTileFoundTwice = false;

	public void berekenWegen(Tile tile, Bord bord, entryPoint entry) {
		ArrayList<Tile> wegNetwerk = new ArrayList<>();
		ArrayList<Horige> horigeInNetwerk = new ArrayList<Horige>();
		eindes = 0;
		startTile = tile;
		startTileFoundTwice = false;

		berekenWeg(tile, entry, bord, wegNetwerk, horigeInNetwerk);

		/*
		for (int i = 0; i < wegNetwerk.size(); i++) {
			if(wegNetwerk.get(i).getGeplaatsteHorigePositie() != null){
				//FIXME op dit moment worden ALLE horige toegevoegd, ook de horige die op een klooster of kasteel staan!
				horigeInNetwerk.add(wegNetwerk.get(i).getGeplaatsteHorigePositie());
			}
		}
		*/
		if(eindes >= 2 || startTileFoundTwice) {
			if(startTileFoundTwice) {
				System.out.println("start tile 2x gevonden !!!!!");
			}if(eindes >= 2){
				System.out.println("Genoeg eindes gevonden!" + eindes );
			}
				ArrayList<Speler> puntenSpelers = calculateplayerWithMostHorige(bord, horigeInNetwerk);
			for (int h = 0; h < puntenSpelers.size(); h++) {
				geefPunten(horigeInNetwerk.get(h).getSpeler(), wegNetwerk.size());
				System.out.println("Speler " + horigeInNetwerk.get(h).getSpeler() + "  heeft voor het afmaken van een weg " + wegNetwerk.size() + " punten geschreven");

			}

		} else {
			System.out.println("Er waren niet genoeg eindes! namelijk " + eindes);
		}
	}

	private enum entryPoint {
		NOORD,
		OOST,
		ZUID,
		WEST,
		GEEN
	}
	/**
	 * Deze functie berekend wat een weg is
	 *
	 * @param tile    Geef de tile mee
	 * @param bord    Geef het bord mee
	 * @param netwerk Geef een arraylist van tiles mee
	 * @param eindes  Geef het aantal eindes mee
	 */
	public void berekenWeg(Tile tile, entryPoint entry, Bord bord, ArrayList<Tile> netwerk, ArrayList<Horige> horige) {

		if (tile == null) {
			return;
		}

		if (netwerk.contains(tile)) {
			if(tile==startTile){
				startTileFoundTwice = true;
			}
			return;
		}

		System.out.println("Bereken tile " + tile.getX() + " " + tile.getY() + " met entry point " + entry.toString());
		addToNetwork(netwerk, tile);

		if (eindes == 2) {
			return;
		}

		if (entry == entryPoint.NOORD) {
			if (tile.getZuidZijde().isEinde()) {
				eindes++;
				if (tile.getZuidZijde().getHorigeSpeler() != null)
						addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
					Tile nextTile = bord.getTile(tile.getX(), tile.getY() + 1);
					berekenWeg(nextTile, entryPoint.ZUID, bord, netwerk, horige);
				return;
			} else {
				if (tile.getZuidZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				}
			}
			if (tile.getOostZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getOostZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() + 1, tile.getY());
				berekenWeg(nextTile, entryPoint.OOST, bord, netwerk, horige);
				return;

			} else if (tile.getNoordZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getNoordZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() - 1);
				berekenWeg(nextTile, entryPoint.NOORD, bord, netwerk, horige);
				return;

			} else if (tile.getWestZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getWestZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() - 1, tile.getY());
				berekenWeg(nextTile, entryPoint.WEST, bord, netwerk, horige);
				return;
			}
		}
		if (entry == entryPoint.OOST) {
			if (tile.getWestZijde().isEinde()) {
				eindes++;
				if (tile.getWestZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() - 1, tile.getY());
				berekenWeg(nextTile, entryPoint.WEST, bord, netwerk, horige);
				return;
			} else {
				if (tile.getWestZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				}
			}
			if (tile.getOostZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getOostZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() + 1, tile.getY());
				berekenWeg(nextTile, entryPoint.OOST, bord, netwerk, horige);
				return;

			} else if (tile.getNoordZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getNoordZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() - 1);
				berekenWeg(nextTile, entryPoint.NOORD, bord, netwerk, horige);
				return;

			} else if (tile.getZuidZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getZuidZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() + 1);
				berekenWeg(nextTile, entryPoint.ZUID, bord, netwerk, horige);
				return;
			}
		}
		if (entry == entryPoint.ZUID) {
			if (tile.getNoordZijde().isEinde()) {
				eindes++;
				if (tile.getNoordZijde().getHorigeSpeler() != null)
						addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
					Tile nextTile = bord.getTile(tile.getX(), tile.getY() - 1);
					berekenWeg(nextTile, entryPoint.NOORD, bord, netwerk, horige);
				return;
			} else {
				if (tile.getNoordZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				}
			}
			if (tile.getOostZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getOostZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() + 1, tile.getY());
				berekenWeg(nextTile, entryPoint.OOST, bord, netwerk, horige);
				return;

			} else if (tile.getZuidZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getZuidZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() + 1);
				berekenWeg(nextTile, entryPoint.ZUID, bord, netwerk, horige);
				return;

			} else if (tile.getWestZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getWestZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() - 1, tile.getY());
				berekenWeg(nextTile, entryPoint.WEST, bord, netwerk, horige);
				return;
			}
		}
		if (entry == entryPoint.WEST) {
			if (tile.getOostZijde().isEinde()) {
				eindes++;
				if (tile.getOostZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() + 1, tile.getY());
				berekenWeg(nextTile, entryPoint.OOST, bord, netwerk, horige);
				return;
			} else {
				if (tile.getOostZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				}
			}
			if (tile.getZuidZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getZuidZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() + 1);
				berekenWeg(nextTile, entryPoint.ZUID, bord, netwerk, horige);
				return;

			} else if (tile.getNoordZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getNoordZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() - 1);
				berekenWeg(nextTile, entryPoint.NOORD, bord, netwerk, horige);
				return;

			} else if (tile.getWestZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getWestZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() - 1, tile.getY());
				berekenWeg(nextTile, entryPoint.WEST, bord, netwerk, horige);
				return;
			}
		}
		if (entry == entryPoint.GEEN) {

			if (tile.getOostZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getOostZijde().isEinde()) {
					System.out.println("Einde gevonden");
					eindes++;
				}
				if (tile.getOostZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				}
				Tile nextTile = bord.getTile(tile.getX() + 1, tile.getY());
				berekenWeg(nextTile, entryPoint.OOST, bord, netwerk, horige);
				System.out.println("biep1");
			}
			if (tile.getNoordZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getNoordZijde().isEinde()) {
					System.out.println("Einde gevonden");
					eindes++;
				}
				if (tile.getNoordZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				}
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() - 1);
				berekenWeg(nextTile, entryPoint.NOORD, bord, netwerk, horige);
				System.out.println("biep2");

			}
			if (tile.getZuidZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getZuidZijde().isEinde()) {
					System.out.println("Einde gevonden");
					eindes++;
				}
				if (tile.getZuidZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				}
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() + 1);
				berekenWeg(nextTile, entryPoint.ZUID, bord, netwerk, horige);
				System.out.println("biep3");

			}
			if (tile.getWestZijde().getZijde() == ZijdeType.WEG) {
				if (tile.getWestZijde().isEinde()) {
					System.out.println("Einde gevonden");
					eindes++;
				}
				if (tile.getWestZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				}
				Tile nextTile = bord.getTile(tile.getX() - 1, tile.getY());
				berekenWeg(nextTile, entryPoint.WEST, bord, netwerk, horige);
				System.out.println("biep4");
			}
		}
	}

	////EINDE BEREKENEN WEGN/////
	public void berekenKastelen(Tile tile, Bord bord, entryPoint entry) {
		ArrayList<Tile> wegNetwerk = new ArrayList<>();
		ArrayList<Horige> horigeInNetwerk = new ArrayList<Horige>();
		eindes = 0;
		startTile = tile;
		startTileFoundTwice = false;

		berekenWeg(tile, entry, bord, wegNetwerk, horigeInNetwerk);

		/*
		for (int i = 0; i < wegNetwerk.size(); i++) {
			if(wegNetwerk.get(i).getGeplaatsteHorigePositie() != null){
				//FIXME op dit moment worden ALLE horige toegevoegd, ook de horige die op een klooster of kasteel staan!
				horigeInNetwerk.add(wegNetwerk.get(i).getGeplaatsteHorigePositie());
			}
		}
		*/
		if(eindes >= 2 || startTileFoundTwice) {
			if(startTileFoundTwice) {
				System.out.println("start tile 2x gevonden !!!!!");
			}if(eindes >= 2){
				System.out.println("Genoeg eindes gevonden!" + eindes );
			}
			ArrayList<Speler> puntenSpelers = calculateplayerWithMostHorige(bord, horigeInNetwerk);
			for (int h = 0; h < puntenSpelers.size(); h++) {
				geefPunten(horigeInNetwerk.get(h).getSpeler(), wegNetwerk.size() * 2);
				System.out.println("Speler " + horigeInNetwerk.get(h).getSpeler() + "  heeft voor het afmaken van een kasteel " + wegNetwerk.size()  * 2 + " punten geschreven");

			}

		} else {
			System.out.println("Er waren niet genoeg eindes voor het kasteel! namelijk " + eindes);
		}
	}

	/**
	 * Deze functie berekend wat een weg is
	 *
	 * @param tile    Geef de tile mee
	 * @param bord    Geef het bord mee
	 * @param netwerk Geef een arraylist van tiles mee
	 * @param eindes  Geef het aantal eindes mee
	 */
	public void berekenKasteel(Tile tile, entryPoint entry, Bord bord, ArrayList<Tile> netwerk, ArrayList<Horige> horige) {

		if (tile == null) {
			return;
		}

		if (netwerk.contains(tile)) {
			if(tile==startTile){
				startTileFoundTwice = true;
			}
			return;
		}

		System.out.println("Bereken Kasteel " + tile.getX() + " " + tile.getY() + " met entry point " + entry.toString());
		addToNetwork(netwerk, tile);

		if (eindes == 2) {
			return;
		}

		if (entry == entryPoint.NOORD) {
			if (tile.getZuidZijde().isEinde()) {
				eindes++;
				if (tile.getZuidZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() + 1);
				berekenWeg(nextTile, entryPoint.ZUID, bord, netwerk, horige);
				return;
			} else {
				if (tile.getZuidZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				}
			}
			if (tile.getOostZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getOostZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() + 1, tile.getY());
				berekenWeg(nextTile, entryPoint.OOST, bord, netwerk, horige);

			} else if (tile.getNoordZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getNoordZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() - 1);
				berekenWeg(nextTile, entryPoint.NOORD, bord, netwerk, horige);

			} else if (tile.getWestZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getWestZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() - 1, tile.getY());
				berekenWeg(nextTile, entryPoint.WEST, bord, netwerk, horige);
			}
		}
		if (entry == entryPoint.OOST) {
			if (tile.getWestZijde().isEinde()) {
				eindes++;
				if (tile.getWestZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() - 1, tile.getY());
				berekenWeg(nextTile, entryPoint.WEST, bord, netwerk, horige);
				return;
			} else {
				if (tile.getWestZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				}
			}
			if (tile.getOostZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getOostZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() + 1, tile.getY());
				berekenWeg(nextTile, entryPoint.OOST, bord, netwerk, horige);

			} else if (tile.getNoordZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getNoordZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() - 1);
				berekenWeg(nextTile, entryPoint.NOORD, bord, netwerk, horige);

			} else if (tile.getZuidZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getZuidZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() + 1);
				berekenWeg(nextTile, entryPoint.ZUID, bord, netwerk, horige);
			}
		}
		if (entry == entryPoint.ZUID) {
			if (tile.getNoordZijde().isEinde()) {
				eindes++;
				if (tile.getNoordZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() - 1);
				berekenWeg(nextTile, entryPoint.NOORD, bord, netwerk, horige);
				return;
			} else {
				if (tile.getNoordZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				}
			}
			if (tile.getOostZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getOostZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() + 1, tile.getY());
				berekenWeg(nextTile, entryPoint.OOST, bord, netwerk, horige);

			} else if (tile.getZuidZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getZuidZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() + 1);
				berekenWeg(nextTile, entryPoint.ZUID, bord, netwerk, horige);

			} else if (tile.getWestZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getWestZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() - 1, tile.getY());
				berekenWeg(nextTile, entryPoint.WEST, bord, netwerk, horige);
			}
		}
		if (entry == entryPoint.WEST) {
			if (tile.getOostZijde().isEinde()) {
				eindes++;
				if (tile.getOostZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() + 1, tile.getY());
				berekenWeg(nextTile, entryPoint.OOST, bord, netwerk, horige);
				return;
			} else {
				if (tile.getOostZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				}
			}
			if (tile.getZuidZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getZuidZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() + 1);
				berekenWeg(nextTile, entryPoint.ZUID, bord, netwerk, horige);

			} else if (tile.getNoordZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getNoordZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() - 1);
				berekenWeg(nextTile, entryPoint.NOORD, bord, netwerk, horige);

			} else if (tile.getWestZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getWestZijde().getHorigeSpeler() != null)
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				Tile nextTile = bord.getTile(tile.getX() - 1, tile.getY());
				berekenWeg(nextTile, entryPoint.WEST, bord, netwerk, horige);
			}
		}
		if (entry == entryPoint.GEEN) {

			if (tile.getOostZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getOostZijde().isEinde()) {
					System.out.println("Einde gevonden");
					eindes++;
				}
				if (tile.getOostZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getOostZijde().getHorigeSpeler());
				}
				Tile nextTile = bord.getTile(tile.getX() + 1, tile.getY());
				berekenWeg(nextTile, entryPoint.OOST, bord, netwerk, horige);
				System.out.println("biep1");
			}
			if (tile.getNoordZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getNoordZijde().isEinde()) {
					System.out.println("Einde gevonden");
					eindes++;
				}
				if (tile.getNoordZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getNoordZijde().getHorigeSpeler());
				}
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() - 1);
				berekenWeg(nextTile, entryPoint.NOORD, bord, netwerk, horige);
				System.out.println("biep2");

			}
			if (tile.getZuidZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getZuidZijde().isEinde()) {
					System.out.println("Einde gevonden");
					eindes++;
				}
				if (tile.getZuidZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getZuidZijde().getHorigeSpeler());
				}
				Tile nextTile = bord.getTile(tile.getX(), tile.getY() + 1);
				berekenWeg(nextTile, entryPoint.ZUID, bord, netwerk, horige);
				System.out.println("biep3");

			}
			if (tile.getWestZijde().getZijde() == ZijdeType.KASTEEL) {
				if (tile.getWestZijde().isEinde()) {
					System.out.println("Einde gevonden");
					eindes++;
				}
				if (tile.getWestZijde().getHorigeSpeler() != null) {
					addHorigeToNetwork(horige, tile.getWestZijde().getHorigeSpeler());
				}
				Tile nextTile = bord.getTile(tile.getX() - 1, tile.getY());
				berekenWeg(nextTile, entryPoint.WEST, bord, netwerk, horige);
				System.out.println("biep4");
			}
		}
	}
	public void addToNetwork(ArrayList<Tile> netwerk, Tile tile) {
		if (tile == null) {
			return;
		}
		if (netwerk.contains(tile)) {
			System.out.println("Tile zit al in het netwerk!");
			return;
		}
		netwerk.add(tile);
		System.out.println("Tile " + tile.getX() + " " + tile.getY() + "toegevoegd aan netwerk");

	}

	public void addHorigeToNetwork(ArrayList<Horige> Horige, Horige horige) {
		if (horige == null) {
			return;
		}
		if (!Horige.contains(horige)) {
			System.out.println("Horige toegevoegd aan het netwerk! ( van speler " + horige.getSpeler().getNaam() + ")");
			Horige.add(horige);
		}

	}

	public ArrayList<Speler> calculateplayerWithMostHorige(Bord bord, ArrayList<Horige> networkHorige) {
		ArrayList<Speler> alleSpelers = bord.getAlleSpelers();
		spelerToHorige[] spelersToHorigen = new spelerToHorige[alleSpelers.size()];

		for (int i = 0; i < alleSpelers.size(); i++) {
			spelersToHorigen[i] = new spelerToHorige(alleSpelers.get(i));
		}

		for (Horige horige:networkHorige) {
			for (int i = 0; i < spelersToHorigen.length; i++) {
				if (spelersToHorigen[i].speler.getNaam().equals(horige.getSpeler().getNaam())) {
					spelersToHorigen[i].aantalHorige++;
				}
			}
		}

		int meesteHorige = 1;

		for (spelerToHorige sth:spelersToHorigen){
			if(sth.aantalHorige > meesteHorige)
				meesteHorige = sth.aantalHorige;
		}
		ArrayList<Speler> spelersDiePuntenKrijgen = new ArrayList<>();

		for (spelerToHorige sth:spelersToHorigen){
			if(sth.aantalHorige == meesteHorige){
				spelersDiePuntenKrijgen.add(sth.speler);
			}

		}

		return spelersDiePuntenKrijgen;
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
 class spelerToHorige {
	public Speler speler;
	public int aantalHorige = 0;

	public spelerToHorige(Speler speler){
		this.speler = speler;
	}

	@Override
	 public boolean equals(Object other){
		Speler speler = (Speler) other;
		return this.speler == speler;
	}
}