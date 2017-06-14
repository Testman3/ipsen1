package Models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.ArrayList;

public class FileManager {

	public static void saveGame(String naam) {
		JSONObject object;
		//createFile(naam, object);
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

	public static void loadGame(File load) {

		Alert alert;
		ArrayList<Speler> spelerLijst = new ArrayList<Speler>();
		JSONParser parser = new JSONParser();
		Object obj = null;

		try {
			// Get file json file (selected in filebrowser)
			obj = parser.parse(new FileReader(load));
			System.out.println("File Geladen");

			JSONObject jsonObject = (JSONObject) obj;

			// Get array in json file
			JSONArray numbers = (JSONArray) jsonObject.get("Spelers");

			for (Object number : numbers) {
				// Make json object
				JSONObject jsonNumber = (JSONObject) number;

				boolean isAanDeBeurt = (boolean) jsonNumber.get("Beurt");
				String naam = (String) jsonNumber.get("Spelernaam");
				Number punten = (Number) jsonNumber.get("Punten");
				int puntenInt = punten.intValue();


				Speler speler = new Speler(naam, isAanDeBeurt, puntenInt);
				spelerLijst.add(speler);

			}
			System(spelerLijst);

		} catch (FileNotFoundException e) {
			alert = new Alert(Alert.AlertType.ERROR, "Er is iets mis gegaan!", ButtonType.OK);
			alert.showAndWait();
		} catch (IOException e) {
			alert = new Alert(Alert.AlertType.ERROR, "Er is iets mis gegaan!", ButtonType.OK);
			alert.showAndWait();
		} catch (org.json.simple.parser.ParseException e) {
			alert = new Alert(Alert.AlertType.ERROR, "Er is iets mis gegaan!", ButtonType.OK);
			alert.showAndWait();
		} catch (NullPointerException e) {

		}

		// Convert the json file (String) to a JsonObject

	}

	//Test voor spelerlijst(load)
	public static void System(ArrayList<Speler> spelerlijst) {
		for (int j = 0; j < spelerlijst.size(); j++) {
			System.out.println("Naam: " + spelerlijst.get(j).getNaam());
			System.out.println("Punten: " + spelerlijst.get(j).getPunten());
			System.out.println("beurt: " + spelerlijst.get(j).getBeurt());
			System.out.println();
		}
	}

	//Return JsonObject met alle spel data TEST!!! (maken file met alle spelers)
	public static JSONObject getAll() {

		//Aanmaken van spelerlijst voor test
		ArrayList<Speler> spelerlijst = new ArrayList<Speler>();

		//Toevoegen Spelers voor test
		spelerlijst.add(new Speler("Raymon", false, 100));
		spelerlijst.add(new Speler("Henk", false, 120));
		spelerlijst.add(new Speler("Justin", false, 140));
		spelerlijst.add(new Speler("Haitam", false, 160));
		spelerlijst.add(new Speler("Martijn", true, 180));

		//Json objecten
		JSONObject gameData = new JSONObject();

		JSONArray Spelers = new JSONArray();


		//loop voor toevoegen spelers van spelerlijst
		for (int i = 0; i < spelerlijst.size(); i++) {

			JSONObject Speler = new JSONObject();

			//toevoegen spelernaam  aan Speler json object
			Speler.put("Spelernaam", spelerlijst.get(i).getNaam());

			//toevoegen Punten  aan Speler json object
			Speler.put("Punten", spelerlijst.get(i).getPunten());

			//toevoegen Beurt aan Speler json object
			Speler.put("Beurt", spelerlijst.get(i).getBeurt());

			//toevoegen aan Array
			Spelers.add(Speler);
		}

		//Array spelers toevoegen aan object voor print
		gameData.put("Spelers", Spelers);

		//Jsonobject return
		return gameData;
	}

}
