package Models;

import java.util.ArrayList;
import Controllers.JsonKaarten;

/**
 * Created by Marti on 8-6-2017.
 */
public class Stapel {

	ArrayList<Tile> kaartenOver;

	//De tile die deze beurt getrokken is
	Tile turnTile;

	public void Stapel(){
	kaartenOver = JsonKaarten.getAllKaarten();

	}

	public int getKaartenOver() {
		return kaartenOver.size();
	}

	public void pakKaart(){

	}

}
