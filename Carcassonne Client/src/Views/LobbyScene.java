package Views;

import Controllers.MenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class LobbyScene extends Scene{
	private Label player1;
	private Label playersLabel;
	private HBox playerBox;
	private Button leaveGame;

	private FlowPane lobbyPane;
	MenuController controller;


	public LobbyScene(MenuController controller){
		super(new FlowPane(), 400, 400);
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
		// TODO fix this
		});
	}

	public void setPlayerList(String spelers){
		player1.setText(spelers);
		System.out.println("WERK KUT DING");
	}

}