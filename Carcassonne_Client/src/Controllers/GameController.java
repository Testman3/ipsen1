package Controllers;

import Models.GameClient;
import Models.Horige;
import Models.RMIInterface;
import javafx.stage.FileChooser;
import java.io.File;
import java.rmi.RemoteException;

/**
 * Deze class regelt een aantal functies met betrekking tot het pakken en plaatsen van kaarten
 */
public class GameController {

	GameClient gameClientModel;
	RMIInterface rmiStub;

	public GameController(GameClient gameClientModel){
		this.gameClientModel = gameClientModel;
	}

	/**
	 * Deze functie zorgt ervoor dat de kaart uit het gameClientModel gepakt wordt (roept pakKaart() aan in het gameClientModel)
	 */
	public void klikPakKaart() {
		gameClientModel.pakKaart();
	}
	public void klikDraaiKaart() {
		gameClientModel.draaiKaart();}
	public void klikPlaatsHorige(Horige.Posities positie){
		gameClientModel.plaatsHorige(positie);
	}
	public void klikBeeindigbeurt() {
		gameClientModel.beeindigBeurt();
	}
	/**
	 * Deze functie zorgt ervoor dat de kaart in het gameClientModel (GameClient) geplaatst wordt
	 * @param x
	 * geef de x co-ordinaat van de kaart mee
	 * @param y
	 * geef de y co-ordinaat van de kaart mee
	 */
	public void klikPlaatsKaart(int x, int y) {
		gameClientModel.plaatsKaart(x, y);
	}

	/**
	 * Deze functie
	 * @return gameClientModel
	 */
	public GameClient getGameClientModel() {
		return gameClientModel;
	}


	/**
	 * Deze functie zort ervoor dat de Filebrowser wordt geopend.
	 */
	public void saveFileBrowser(){
		FileChooser fileChooser = new FileChooser();

		//title of window
		fileChooser.setTitle("Save Game");
		//extension filter json
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Carcassonne", "*.json"));
		File file = fileChooser.showSaveDialog(gameClientModel.getGameScene().getController().getGameStage());
		// File handeling in class filemanager
//		fileManager.saveGame();
		try {
			gameClientModel.RmiStub.saveFile(file.getPath());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * roept de functie saveFile(path) aan.
	 * @param path
	 * Geef het path van de safefile mee in de vorm van een String
	 */
	public void getSavefile(String path){
		try {
			rmiStub.saveFile(path);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
