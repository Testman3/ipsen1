package controllers;

import javafx.scene.Scene;
import models.LobbyScene;
import views.MenuView;

public class MenuController {

	private LobbyScene lobby;
	private MenuView menu;

	public Scene setLobbyScene(MenuView view) {
		lobby = new LobbyScene(view);
		return lobby;
	}

	public Scene setMenuScene() {
		menu = new MenuView();
		return menu.getScene();
	}

}
