package Controllers;

import Views.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MenuController {

	Stage gameStage;

	private EndGameScene endGameScene;
	private GameScene gameScene;
	private LobbyScene lobbyScene;
	private MenuViewScene menuViewScene;
	private PreLobbyScene preLobbyScene;
	private SettingsScene settingsScene;
	private CreditsScene creditsScene;

	private String spelernaam;

	public static File loadedFile;

	public MenuController(Stage gameStage) {

		LobbyController lobbyController = new LobbyController();


		this.gameStage = gameStage;
		endGameScene = new EndGameScene(this);
		gameScene = new GameScene(this);
		lobbyScene = new LobbyScene(this, lobbyController);
		menuViewScene = new MenuViewScene(this);
		preLobbyScene = new PreLobbyScene(this, lobbyController);
		settingsScene = new SettingsScene(this);
		creditsScene = new CreditsScene(this);

		setMenuViewScene();
		//setGameScene();
		gameStage.show();
		gameStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
	}


	/**
	 * Button action method om de speler terug in het hoofd menu te krijgen
	 */
	public void backToMainMenu(){
		getGameStage().setScene(getMenuViewScene());
	}

	/**
	 * Buttion action method om de speler naar het spel einde scherm te laten stellen
	 */
	public void setEndGameScene(){
		gameStage.setScene(endGameScene);
	}
	public void setGameScene(){
		gameStage.setScene(gameScene);

	//	gameStage.setFullScreen(SettingsScene.fullScreen);
	}

	public void setLobbyScene(){
		gameStage.setScene(lobbyScene);
		getLobbyScene().Join();
	}

	public void setMenuViewScene(){
		gameStage.setScene(menuViewScene);
	}

	public void setPreLobbyScene(){
		gameStage.setScene(preLobbyScene);

	}
	public void setSettingsScene(){

		gameStage.setScene(settingsScene);
	}
	
	public void setCreditsScene(){

		gameStage.setScene(creditsScene);
	}




	public Stage getGameStage() {
		return gameStage;
	}

	public EndGameScene getEndGameScene() {
		return endGameScene;
	}

	public GameScene getGameScene() {
		return gameScene;
	}

	public LobbyScene getLobbyScene() {
		return lobbyScene;
	}

	public MenuViewScene getMenuViewScene() {
		return menuViewScene;
	}

	public PreLobbyScene getPreLobbyScene() {
		return preLobbyScene;
	}

	public SettingsScene getSettingsScene() {
		return settingsScene;
	}
	
	public CreditsScene getCreditsScene() {
		return creditsScene;
	}


	public void updatePlayerList() {
		// TODO Auto-generated method stub

	}


	public String getSpelernaam() {
		return spelernaam;
	}


	public void setSpelernaam(String spelernaam) {
		this.spelernaam = spelernaam;
	}

	public File openFileBrowser(){
		FileChooser fileChooser = new FileChooser();
		//title of window
		fileChooser.setTitle("Laadgame");
		//extension filter json
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Carcassonne", "*.json"));

		// return file
		return fileChooser.showOpenDialog(gameStage);
	}





}
