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

	Tile test1;
	Tile test2;

	public Stapel(){
		kaartenOver = JsonKaarten.getAllKaarten();
		test1 = kaartenOver.get(0);
		test2 = kaartenOver.get(1);

		beginTile = kaartenOver.get(7);
		System.out.println(kaartenOver.size());
		Collections.shuffle(kaartenOver);
	}

	public void compareTests() {
		System.out.println("=======COMPARING 2 TEST CARDS");
		System.out.println("INDEX 0:  test 1 " + test1.getHorigenZijdes()[0].toString() + " EN test 2 " + test2.getHorigenZijdes()[0].toString());
		System.out.println("INDEX 1:  test 1 " + test1.getHorigenZijdes()[1].toString() + " EN test 2 " + test2.getHorigenZijdes()[1].toString());
		System.out.println("Zijdes check! Noord test 1 " + test1.noordZijde.zijde.toString() + " EN test 2 " + test2.noordZijde.zijde.toString());
		System.out.println("Zijdes check! Ooost test 1 " + test1.oostZijde.zijde.toString() + " EN test 2 " + test2.oostZijde.zijde.toString());
		System.out.println("Zijdes check! zuid test 1 " + test1.zuidZijde.zijde.toString() + " EN test 2 " + test2.zuidZijde.zijde.toString());
		System.out.println("Zijdes check! west test 1 " + test1.westZijde.zijde.toString() + " EN test 2 " + test2.westZijde.zijde.toString());

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
