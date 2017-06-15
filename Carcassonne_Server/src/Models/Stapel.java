package Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import Controllers.JsonKaarten;

/**
 * Created by Marti on 8-6-2017.
 */
public class Stapel {

	ArrayList<Tile> kaartenOver;

	Tile beginTile;

	//De tile die deze beurt getrokken is
	Tile turnTile;

	public Stapel(){
		kaartenOver = JsonKaarten.getAllKaarten();
		beginTile = kaartenOver.get(7);
		System.out.println(kaartenOver.size());
		Collections.shuffle(kaartenOver);
	}

	public int getKaartenOver() {
		return kaartenOver.size();
	}

	public void pakKaart(){
		turnTile = kaartenOver.get(0);
		kaartenOver.remove(0);
	}

	public Tile getTurnTile() {
		return turnTile;
	}
	public Tile getBeginTile() { return beginTile;}
}
