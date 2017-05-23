package models;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class StartenGame extends Scene {

	Pane pane;

	public StartenGame() {
		
		super(new Pane(), 400, 400);
		pane = (Pane) this.getRoot();
		init(pane);
	}
	
	private void init (Pane pane){
		Button start = new Button("Start");
		pane.getChildren().add(start);
	}
}
