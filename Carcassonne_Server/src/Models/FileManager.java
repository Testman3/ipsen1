package Models;

import Controllers.ServerManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

	private ServerManager manager;

	public FileManager(ServerManager manager){
		this.manager = manager;
	}

	//Maak JSON object aan van save game
	public File saveGame(String naam) {
		//JSON OB - save all data inside
		JSONObject object = new JSONObject();
		// Save all players information in the json object
		object.put("Spelers", saveAlleSpelersJSON());
		// Get alle BordGegevens (kaarten en horigen)
		object.put("Kaarten", saveAlleKaartenJSON());
		// Current stapel
		object.put("CurrentStapel", saveStapelKaartenJSON());
		return createFile(naam, object);
	}

	private JSONArray saveAlleSpelersJSON(){
		JSONArray spelerInJSON = new JSONArray();
		// Get alle Spelers
		ArrayList<Speler> spelerLijst = manager.bordController.bord.getAlleSpelers();

		for (int i = 0; i < spelerLijst.size() ; i++) {
			JSONObject speler = new JSONObject();
			speler.put("spelerNaam", spelerLijst.get(i).getNaam());
			speler.put("beurt", spelerLijst.get(i).getBeurt());
			speler.put("punten", spelerLijst.get(i).getPunten());

			spelerInJSON.add(speler);
		}
		return spelerInJSON;
	}

	private JSONArray saveAlleKaartenJSON() {
		Tile[][] alleTiles = manager.bordController.bord.getAlleTiles();
		JSONArray alleKaartenBijElkaar = new JSONArray();

		for(int Y= 0; Y < 100; Y++){
			//y
			for(int X =0; X < 100; X++){
				if( alleTiles[X][Y] != null) {
					JSONObject kaarten = new JSONObject();
					kaarten.put("isLeeg", false);

					System.out.println("IMage id: " + alleTiles[X][Y].getImageID());

					kaarten.put("imageId", alleTiles[X][Y].getImageID());
					// Noord Zijde
					kaarten.put("noordZijde", alleTiles[X][Y].getNoordZijde().getZijde().toString());
					kaarten.put("noordEinde", alleTiles[X][Y].getNoordZijde().isEinde());
					if(alleTiles[X][Y].getNoordZijde().getHorigeSpeler() != null) {
						kaarten.put("noordZijdeHorige", alleTiles[X][Y].getNoordZijde().getHorigeSpeler().getSpelerString());
					}
					// Oost Zijde
					kaarten.put("oostZijde", alleTiles[X][Y].getOostZijde().getZijde().toString());
					kaarten.put("oostEinde", alleTiles[X][Y].getOostZijde().isEinde());
					if(alleTiles[X][Y].getOostZijde().getHorigeSpeler() != null) {
						kaarten.put("oostZijdeHorige", alleTiles[X][Y].getOostZijde().getHorigeSpeler().getSpelerString());
					}
					// Zuid Zijde
					kaarten.put("zuidZijde", alleTiles[X][Y].getZuidZijde().getZijde().toString());
					kaarten.put("zuidEinde", alleTiles[X][Y].getZuidZijde().isEinde());
					if(alleTiles[X][Y].getZuidZijde().getHorigeSpeler() != null) {
						kaarten.put("zuidZijdeHorige", alleTiles[X][Y].getZuidZijde().getHorigeSpeler().getSpelerString());
					}
					// West zijde
					kaarten.put("westZijde", alleTiles[X][Y].getWestZijde().getZijde().toString());
					kaarten.put("westEinde", alleTiles[X][Y].getWestZijde().isEinde());
					if(alleTiles[X][Y].getWestZijde().getHorigeSpeler() != null) {
						kaarten.put("westZijdeHorige", alleTiles[X][Y].getWestZijde().getHorigeSpeler().getSpelerString());
					}

					// Midden zijde
					if(alleTiles[X][Y].getMiddenZijde() != null) {
						kaarten.put("middenZijde", alleTiles[X][Y].getMiddenZijde().getZijde().toString());
						kaarten.put("middenZijdeHorige", alleTiles[X][Y].getMiddenZijde().getHorigeSpeler().getSpelerString());
					} else {
						kaarten.put("middenZijde", false);
						kaarten.put("middenZijdeHorige", false);
					}
					//x en y
					kaarten.put("x", alleTiles[X][Y].getX());
					kaarten.put("y", alleTiles[X][Y].getY());
					// Rotation
					kaarten.put("rotation", alleTiles[X][Y].getRotation());
					// Klooster
					kaarten.put("heeftKlooster", alleTiles[X][Y].getKlooster());
					// Heeft bonus
					kaarten.put("heeftBonus", alleTiles[X][Y].getHeeftBonus());

					// Horige Positie (enum to string)
					Horige.Posities[] horigePosities = alleTiles[X][Y].getHorigenZijdes();
					JSONArray JSONHorigeArr = new JSONArray();

					for(int i = 0; i < horigePosities.length; i++){
						JSONHorigeArr.add(horigePosities[i].toString());
					}

					kaarten.put("horigePosities", JSONHorigeArr);
					alleKaartenBijElkaar.add(kaarten);
				} else {
					JSONObject kaarten = new JSONObject();
					kaarten.put("isLeeg", true);
					alleKaartenBijElkaar.add(kaarten);
				}
			}
		}
		return alleKaartenBijElkaar;
	}

	private JSONArray saveStapelKaartenJSON(){
		JSONArray stapelKaartenJSON = new JSONArray();
		ArrayList<Tile> currentStapel = manager.bordController.kaartenStapel.kaartenOver;

		for(int i = 0; i < currentStapel.size(); i++){
			JSONObject kaart = new JSONObject();

			// Zijdes
			// Noord
			kaart.put("noordZijde", currentStapel.get(i).getNoordZijde().getZijde().toString());
			kaart.put("noordEinde", currentStapel.get(i).getNoordZijde().isEinde());
			// Oost
			kaart.put("oostZijde", currentStapel.get(i).getOostZijde().getZijde().toString());
			kaart.put("oostEinde", currentStapel.get(i).getOostZijde().isEinde());
			// Zuid
			kaart.put("zuidZuid", currentStapel.get(i).getZuidZijde().getZijde().toString());
			kaart.put("zuidZuid", currentStapel.get(i).getZuidZijde().isEinde());
			// West
			kaart.put("westZuid", currentStapel.get(i).getWestZijde().getZijde().toString());
			kaart.put("westZuid", currentStapel.get(i).getWestZijde().isEinde());
			// Heeft klooster
			kaart.put("heeftKlooster", currentStapel.get(i).getHeeftKlooster());
			// Heeft bonus
			kaart.put("heeftBonus", currentStapel.get(i).getHeeftBonus());
			// Image id
			kaart.put("image_id", currentStapel.get(i).getImageID());

			// Horige Positie (enum to string)
			Horige.Posities[] horigePosities = currentStapel.get(i).getHorigenZijdes();
			JSONArray JSONHorigeArr = new JSONArray();

			for(int h = 0; h < horigePosities.length; h++){
				JSONHorigeArr.add(horigePosities[h].toString());
			}

			kaart.put("horigePosities", JSONHorigeArr);
			stapelKaartenJSON.add(kaart);

		}
	return stapelKaartenJSON;
	}

	public File createFile(String naam, JSONObject object){

		try {
			//new file
			File newTextFile = new File(naam);

			//create writer
			FileWriter fw = new FileWriter(newTextFile);

			//Write to json file
			fw.write(object.toJSONString());

			//close writer
			fw.close();
			return newTextFile;
		} catch (IOException iox) {
			//do stuff with exception
			iox.printStackTrace();
		}
		return null;
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

	//Return JsonObject met alle spel data TEST!!! (maken file met alle spelers) - niet nodig maar handig voor later
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
