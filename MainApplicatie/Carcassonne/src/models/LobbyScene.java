package models;

import controllers.MenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import views.MenuView;

public class LobbyScene extends Scene{
	private Label player1;
	private Label playersLabel;
	private HBox playerBox;
	private Button leaveGame;
	
	private FlowPane lobbyPane;
	private MenuView view = new MenuView();
	private MenuController controller = new MenuController();
	
	
	public LobbyScene(MenuView menu){
		super(new FlowPane(), 400, 400);
		view = menu;
		lobbyPane = (FlowPane) this.getRoot();
		init(lobbyPane);
		
	}
	
	
	
	private void init(FlowPane pane){
		
		playersLabel = new Label("Players in this game: ");
		playersLabel.setFont(new Font("CALIBRI", 20));
		player1 = new Label();
		player1.setText("empty");
		player1.setFont(new Font("CALIBRI", 15));
		player1.setAlignment(Pos.CENTER);
		playerBox = new HBox();
		leaveGame = new Button("Leave Game");
		leaveGame.setMaxWidth(200);
		
		
		playerBox.getChildren().addAll(playersLabel, player1);
		lobbyPane.getChildren().addAll(playerBox, leaveGame);
		
		leaveGame.setOnAction(e -> {
			view.getStage().setScene(controller.setPreLobbyGame(view));
			controller.removePlayer(controller.getCurrentSpeler());
		});
	}
	
	public void setPlayerList(String spelers){
		player1.setText(spelers);
		System.out.println("WERK KUT DING");
	}

}
