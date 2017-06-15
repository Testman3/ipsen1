package Views;

import commonFunctions.SmartLabel;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Stack;

/**
 * Deze class regelt de juiste weergave van de spelernamen en punten in de ui bij het spelbord
 */
public class SpelerView extends StackPane {

	private SmartLabel text_Naam;
	private SmartLabel text_Punten;
	private  ImageView achterkant;

	/**
	 * Constructor van SpelerView
	 */
	public SpelerView(){

	VBox texts = new VBox();
	texts.setAlignment(Pos.CENTER);
	text_Punten = new SmartLabel();
	text_Naam = new SmartLabel();
	texts.getChildren().addAll(text_Naam,text_Punten);
	achterkant = new ImageView();

		text_Naam.setId("standardLabel");
		text_Punten.setId("standardLabel");
		achterkant.setId("Speler");
		achterkant.fitHeightProperty().bind(this.heightProperty());
		achterkant.fitWidthProperty().bind(this.widthProperty());

		getChildren().addAll(achterkant, texts);
	}

	/**
	 * Deze functie stelt de spelernaam in
	 * @param naam
	 * Geef spelernaam mee in de vorm van een String
	 */
	public void setNaam(String naam ){
	text_Naam.setText(naam);
	}

	/**
	 * Deze functie stelt het aantal punten in
	 * @param punten
	 * Geef een int mee met aantal punten
	 */
	public void setPunten(int punten){
		text_Punten.setText(" " + punten);
	}

}
