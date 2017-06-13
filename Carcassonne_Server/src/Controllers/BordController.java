package Controllers;

import Models.Speler;
import Models.Stapel;
import Models.Bord;

import java.util.ArrayList;

/**
 * Created by Marti on 8-6-2017.
 */
public class BordController {

	Stapel kaartenStapel;
	Bord bord;

	public BordController(ArrayList<Speler> spelerList) {
		kaartenStapel = new Stapel();
		bord = new Bord(spelerList);
		bord.plaatsKaartCheat(5,5,kaartenStapel.getBeginTile());
	}

	public String pakKaartvanStapel(String spelerNaam) {
		if (bord.isSpelerBeurt(spelerNaam)) {
			kaartenStapel.pakKaart();

			return kaartenStapel.getTurnTile().getImageID();
		}
		return null;
	}

	public boolean checkKaartFit(int x, int y) {
		return bord.checkKaartFit(x, y, kaartenStapel.getTurnTile());
	}

	public boolean plaatsKaart(int x, int y) {
	if(bord.checkKaartFit(x,y,kaartenStapel.getTurnTile())){
		kaartenStapel.getTurnTile().plaats(x,y);
		bord.plaatsKaart(x,y,kaartenStapel.getTurnTile());
		return true;
	} else {
		return false;
	}
	}

	public void draaiKaart(){
		kaartenStapel.getTurnTile().draaiKaart();
	}
}
