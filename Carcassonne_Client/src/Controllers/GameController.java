package Controllers;

import Models.FileManager;
import Models.GameClient;
import Models.RMIInterface;
import javafx.stage.FileChooser;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Created by Marti on 11-6-2017.
 */
public class GameController {

	GameClient model;
	RMIInterface rmiStub;

	public GameController(GameClient model){
		this.model = model;
	}

	public void klikPakKaart() {
		model.pakKaart();
	}
	public void klikPlaatsKaart(int x, int y) {
		model.plaatsKaart(x, y);
	}
	public GameClient getModel() {
		return model;
	}



	public void saveFileBrowser(){
		FileManager fileManager = new FileManager();
		FileChooser fileChooser = new FileChooser();

		//title of window
		fileChooser.setTitle("Save Game");
		//extension filter json
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Carcassonne", "*.json"));
		File file = fileChooser.showSaveDialog(model.getGameScene().getController().getGameStage());
		// File handeling in class filemanager
//		fileManager.saveGame();
		try {
			model.RmiStub.saveFile(file.getPath());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void createFile(String naam, JSONObject object){

		try {
			//new file
			File newTextFile = new File(naam);

			//create writer
			FileWriter fw = new FileWriter(newTextFile);

			//Write to json file
			fw.write(object.toJSONString());

			//close writer
			fw.close();
		} catch (IOException iox) {
			//do stuff with exception
			iox.printStackTrace();
		}
	}
}
