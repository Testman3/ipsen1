package controllers;

import javafx.scene.Scene;
import models.StartenGame;



public class MenuController {
	
	private StartenGame test;
	
	public Scene setNewGame(){
		test = new StartenGame();
		return test;
	}
	
}
