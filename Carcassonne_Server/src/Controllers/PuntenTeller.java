package Controllers;

import Models.Bord;
import Models.Speler;
import Models.Tile;
import com.sun.jndi.ldap.Ber;
import commonFunctions.Point;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Marti on 15-6-2017.
 */
public class PuntenTeller {

	public void BerekenPunten(Tile tile, Bord bord){
		System.out.println("=================================BEREKENEN PUNTEN======================");
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

		alleBuren.addAll(Arrays.asList(tile, noord, oost, zuid, west, noordOost, zuidOost, zuidWest, noordWest));
		for (Tile buur:alleBuren) {
			if(buur == null){
				continue;
			}
			if(buur.getHeeftKlooster()){
				System.out.println("Klooster berekenen!");
				BerekenKloosters(buur, bord);
			}
		}
		System.out.println("==========================EINDE BEREKENEN PUNTEN==============================");
	}

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

	public void geefPunten(Speler speler, int hoeveelheid){
		speler.geefPunten(9);
		System.out.println("Punten gegeven aan speler " + speler.getNaam() + " ( " + speler.getPunten() + " punten)");
	}
}
