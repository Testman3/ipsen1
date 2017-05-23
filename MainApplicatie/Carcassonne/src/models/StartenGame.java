package models;

import controllers.MenuController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import views.MenuView;

public class StartenGame extends Scene {

	VBox box;
	Pane pane;
	Label naam;
	Label ip;
	Button next;
	MenuController controller = new MenuController();
	MenuView view = new MenuView();

	public StartenGame(MenuView menu) {
		
		super(new Pane(), 400, 400);
		view = menu;
		pane = (Pane) this.getRoot();
		init(pane);
	}
	
	private void init (Pane pane){
		box = new VBox(10);
		naam = new Label("Naam: ");
		ip = new Label("IP: ");
		next = new Button("Gereed");
		box.getChildren().addAll(naam, ip, next);
		pane.getChildren().add(box);
		
		next.setOnAction(e -> view.getStage().setScene(controller.setLobbyScene()));
		
	}
}
