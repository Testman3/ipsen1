package Views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Paths;

/**
 * Deze class zorgt voor het laten zien van de horigen
 */
public class HorigeView extends ImageView {
	int arrayListPositie;

	/**
	 * Lege contructor, HorigeView wordt eigenlijk alleen gebruikt als image container dmv het toewijzen van een
	 * id voor CSS
	 */
	HorigeView(){
	}

	/**
	 * Deze functie wordt niet gebruikt geloof ik
	 * @param spelerKleur
	 * Geef de spelerkleur mee in de vorm van een String
	 */
	public void setHorigeKleur(String spelerKleur) {
		switch (spelerKleur) {

			case "horigeRood":
				this.setId("horigeGroen");
			case "horigeBlauw":
				this.setId("horigeBlauw");
			case "horigeGroen":
				this.setId("horigeGroen");
			case "horigeGeel":
				this.setId("horigeGeel");
			case "horigePaars":
				this.setId("horigePaars");
		}
	}
}