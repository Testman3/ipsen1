package models;


import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class LobbyScene extends Scene{

	
	BorderPane mainPane;

	public LobbyScene() {
		
		super(new BorderPane(), 400, 400);
		mainPane = (BorderPane) this.getRoot();
		init(mainPane);
	}
	
	private void init (BorderPane pane){
		
		
	}
}
