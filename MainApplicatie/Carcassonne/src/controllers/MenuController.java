package controllers;

import javafx.scene.Scene;
import models.LobbyScene;
import models.StartenGame;
import views.MenuView;



public class MenuController {
	
	private StartenGame test;
	private LobbyScene lobby;
	
	public Scene setNewGame(MenuView view){
		test = new StartenGame(view);
		return test;
	}
	
	public Scene setLobbyScene(){
		lobby = new LobbyScene();
		return lobby;
	}
	
}
