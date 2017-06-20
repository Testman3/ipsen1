package Models;

import Controllers.JsonKaarten;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Stapel klass met standaard gevuld met 72 kaarten
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

	public Stapel(ArrayList<Tile> kaartenOver){
		this.kaartenOver = kaartenOver;
		System.out.println("Saved kaarten over: " + kaartenOver.size());
		Collections.shuffle(kaartenOver);
	}


	public int getKaartenOver() {
		return kaartenOver.size();
	}

	public void pakKaart(){
		turnTile = kaartenOver.get(0);
		kaartenOver.remove(0);
	}

	public void stopLaatsteKaartTerug() {
		kaartenOver.add(turnTile);

	}
	public Tile getTurnTile() {
		return turnTile;
	}
	public Tile getBeginTile() { return beginTile;}
}
