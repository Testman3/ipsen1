package controllers;

import javafx.scene.Scene;
import models.LobbyScene;
import models.PreLobbyScene;
import views.MenuView;

public class MenuController {

	private PreLobbyScene preLobby;
	private MenuView menu;
	private LobbyScene lobby;

	public Scene setPreLobbyGame(MenuView view) {
		preLobby = new PreLobbyScene(view);
		return preLobby;
	}

	public Scene setMenuScene() {
		menu = new MenuView();
		return menu.getScene();
	}
	
	public Scene setLobbyScene(MenuView view){
		lobby = new LobbyScene(view);
		return lobby;
	}

}
