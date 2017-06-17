package Views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Paths;

/**
 * Deze class zorgt voor het laten zien van de horigen
 */
public class HorigeView extends ImageView {
	int arrayListPositie;

	public HorigeView(){
	}



	public void setVisible(){
		setVisible(true);
	}

	public void setInvisible(){
		setVisible(false);
	}

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