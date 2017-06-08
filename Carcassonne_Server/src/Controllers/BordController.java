package Controllers;

import Models.Stapel;
import Models.Bord;
/**
 * Created by Marti on 8-6-2017.
 */
public class BordController {

	Stapel kaartenStapel;
	Bord bord;

	public BordController() {
		System.out.println("HALLLLLLLLLLLLLO");
		kaartenStapel = new Stapel();
		bord = new Bord();
	}

	public String pakKaartvanStapel(String spelerNaam) {
		if (!bord.isSpelerBeurt(spelerNaam)) {
			kaartenStapel.pakKaart();
			return kaartenStapel.getTurnTile().getImageID();
		}
		return null;
	}

}
