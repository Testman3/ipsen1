package Views;

import commonFunctions.SmartButton;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


/**
 * Created by Justin on 13-6-2017.
 */
public class inGamePane extends BorderPane {

	SmartButton instellingen = new SmartButton("Instelingen");
	SmartButton handleiding = new SmartButton("Spelregels");
	SmartButton opslaan = new SmartButton("Spel opslaan");
	SmartButton terugNaarSpel = new SmartButton("Terug naar spel");
	SmartButton leaveGame = new SmartButton("Spel verlaten");

	VBox knopBox = new VBox();

	public inGamePane(){
		this.getStylesheets().add("style.css");
		this.setId("schild");
		instellingen.setId("standardLabel");
		handleiding.setId("standardLabel");
		opslaan.setId("standardLabel");
		terugNaarSpel.setId("standardLabel");
		leaveGame.setId("standardLabel");

		knopBox.getChildren().addAll(instellingen, handleiding, opslaan, terugNaarSpel, leaveGame);
		knopBox.setAlignment(Pos.CENTER);
		this.setCenter(knopBox);
	}


}
