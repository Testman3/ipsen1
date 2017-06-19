package Models;

import Controllers.JsonKaarten;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Marti on 8-6-2017.
 */
public class Stapel {

	Tile[] kaartenOver;

	Tile beginTile;

	//De tile die deze beurt getrokken is
	Tile turnTile;

	int kaartenGepakt = 0;

	public Stapel(){
		ArrayList<Tile> kaartenOverList = JsonKaarten.getAllKaarten();
		Collections.shuffle(kaartenOverList);
		kaartenOver = new Tile[kaartenOverList.size()];
		kaartenOver = kaartenOverList.toArray(kaartenOver);

		beginTile = kaartenOverList.get(7);
		System.out.println(kaartenOverList.size());
	}

	public Stapel(ArrayList<Tile> kaartenOver){
		ArrayList<Tile> kaartenOverList = JsonKaarten.getAllKaarten();

		beginTile = kaartenOverList.get(7);
		System.out.println(kaartenOverList.size());
		Collections.shuffle(kaartenOverList);
		System.out.println("Saved kaarten over: " + kaartenOverList.size());

	}


	public int getKaartenOver() {
		return kaartenOver.length;
	}

	public void pakKaart(){
		turnTile = kaartenOver[kaartenGepakt];
		kaartenOver[kaartenGepakt] = null;
		kaartenGepakt++;

	}

	public Tile getTurnTile() {
		return turnTile;
	}
	public Tile getBeginTile() { return beginTile;}
}
