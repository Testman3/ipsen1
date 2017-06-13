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
 * Created by Marti on 13-6-2017.
 */
public class SpelerView extends StackPane {

	private SmartLabel text_Naam;
	private SmartLabel text_Punten;
	private  ImageView achterkant;

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
		achterkant.setId("Speler");
		achterkant.setFitWidth(200);
		achterkant.setFitHeight(100);
		getChildren().addAll(achterkant, texts);
	}



	public void setNaam(String naam ){
	text_Naam.setText(naam);
	}
	public void setPunten(int punten){
		text_Punten.setText(" " + punten);
	}


}
