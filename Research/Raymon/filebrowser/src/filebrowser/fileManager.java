package filebrowser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javafx.stage.FileChooser;

public class fileManager {

	public static void saveGame(String path){
		
		//Create json Object from getAll() 
		JSONObject obj = getAll();
	
		//Make File
		try {
			//new file
            File newTextFile = new File(path);
            
            //create writer
            FileWriter fw = new FileWriter(newTextFile);
            
            //Write to json file
            fw.write( obj.toJSONString());
            
            //close writer
            fw.close();
        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
	
	}
	
	//Return JsonObject met alle spel data
	public static JSONObject getAll(){
		
		//Aanmaken van spelerlijst voor test
		ArrayList<Speler> spelerlijst = new ArrayList<Speler>();
		
		//Toevoegen Spelers voor test
		spelerlijst.add(new Speler(false, 900, "Raymon"));
		spelerlijst.add(new Speler(false, 1892, "Henk"));
		spelerlijst.add(new Speler(false, 736, "Justin"));
		spelerlijst.add(new Speler(false, 289, "Haitam"));
		spelerlijst.add(new Speler(true, 1028, "Martijn"));
		
		//Json objecten 
		JSONObject gameData = new JSONObject();
		JSONObject Speler = new JSONObject();
		JSONArray Spelers = new JSONArray();

		
		//loop voor toevoegen spelers van spelerlijst
		for (int i = 0; i < spelerlijst.size(); i++) {
			
			//toevoegen spelernaam  aan Speler json object
			Speler.put("Spelernaam", spelerlijst.get(i).getSpelernaam());
			
			//toevoegen Punten  aan Speler json object
			Speler.put("Punten", spelerlijst.get(i).getPunten());
			
			//toevoegen Beurt aan Speler json object
			Speler.put("Beurt", spelerlijst.get(i).getIsAanDeBeurt());
			
			//toevoegen aan Array
			Spelers.add(Speler);
		}
		
		//Array spelers toevoegen aan object voor print
		gameData.put("Spelers", Spelers);
		
		//Jsonobject return
		return gameData;
		}
	
} 
