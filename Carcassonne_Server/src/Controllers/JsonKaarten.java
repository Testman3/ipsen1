package Controllers;

import Models.Horige;
import Models.Tile;
import Models.Zijde;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Deze class is verantwoordelijk voor Json interacties
 */
public class JsonKaarten {

	public JsonKaarten() {
		//open for edits
	}

	/**
	 * Get json file met kaarten data en parse alle data naar een TegelData array
	 * @return ArrayList (TegelData - alle kaarten van carcassonne)
	 */
	public static ArrayList<Tile> getAllKaarten() {

		ArrayList<Tile> alleKaarten =  new ArrayList<Tile>();

		JSONParser parser = new JSONParser();
		Object obj = null;

		// Debugging info (overwrite in future with debugging function)
		boolean debug = false;


		try {
			// Get file json file (in map json)
			File reader = new File("json/alleGoeieKaarten.json");
			System.out.println(reader.getAbsolutePath());
			obj = parser.parse(new FileReader(reader));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("FileNotFound");
		} catch (IOException e) {
			System.out.println("IO Error");
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			System.out.println("This shouldn't have happened");
			e.printStackTrace();
		}

		// Convert the json file (String) to a JsonObject
		JSONObject jsonObject = (JSONObject) obj;

		// Get array in json file
		JSONArray numbers = (JSONArray) jsonObject.get("alleKaarten");

		// Loop through all card data in json
		for (Object number : numbers) {
			// Make json object
			JSONObject jsonNumber = (JSONObject) number;

			// Get soort zijde
			Zijde.ZijdeType noordZijdeType = Zijde.ZijdeType.valueOf((String)jsonNumber.get("noordZijde"));
			Zijde.ZijdeType oostZijdeType  = Zijde.ZijdeType.valueOf((String)jsonNumber.get("oostZijde"));
			Zijde.ZijdeType zuidZijdeType  = Zijde.ZijdeType.valueOf((String)jsonNumber.get("zuidZijde"));
			Zijde.ZijdeType westZijdeType  = Zijde.ZijdeType.valueOf((String)jsonNumber.get("westZijde"));

			// Get data eindpunt
			boolean noordEinde = (boolean) jsonNumber.get("noordEinde");
			boolean oostEinde  = (boolean) jsonNumber.get("oostEinde");
			boolean zuidEinde  = (boolean) jsonNumber.get("zuidEinde");
			boolean westEinde  = (boolean) jsonNumber.get("westEinde");

			// Get json data heeftKlooster
			boolean heeftKlooster  = (boolean) jsonNumber.get("heeftKlooster");

			// Get json data heeftBonus
			boolean heeftBonus = (boolean) jsonNumber.get("heeftBonus");

			// Get json data image_id
			String imageId = (String) jsonNumber.get("image_id");
			// Parse all json data of one card to a TegelData object

			Number aantalKaarten = (Number) jsonNumber.get("aantalKaarten");

			//get JsonArray horigePosities
			JSONArray array = (JSONArray) jsonNumber.get("horigePosities");

			//new array
			String[] positie = new String[array.size()];
			Horige.Posities[] horigenPos = new Horige.Posities[array.size()];

			//all data to array
			for (int i = 0; i < array.size() ; i++) {
				positie[i] = (String)array.get(i);

				horigenPos[i] = Horige.Posities.valueOf((String)array.get(i));
				System.out.println("Kaart ENUM: " + horigenPos[i]);
			}

			//Horige.Posities[] posities = Horige.Posities.valueOf((String)jsonNumber.get("horigePosities"));

			for (int i = 0; i <  aantalKaarten.intValue(); i++){

				Zijde noordZijde = new Zijde(noordZijdeType, noordEinde);
				Zijde oostZijde = new Zijde(oostZijdeType, oostEinde);
				Zijde zuidZijde = new Zijde(zuidZijdeType, zuidEinde);
				Zijde westZijde = new Zijde(westZijdeType, westEinde);

				Horige.Posities[] pos = new Horige.Posities[array.size()];
					for (int j = 0; j < array.size() ; j++) {
					pos[j] = horigenPos[j];
				}
				Tile data = new Tile(imageId, noordZijde, oostZijde, zuidZijde, westZijde, heeftKlooster, heeftBonus, pos );
				alleKaarten.add(data);

				if (debug) {
					System.out.println("Voeg kaart toe ");
				}
			}

//			if (debug){
//				System.out.println("noord zijde: " + noordZijde);
//				System.out.println("oost zijde: " + oostZijde);
//				System.out.println("zuid zijde: " + zuidZijde);
//				System.out.println("west zijde: " + westZijde);
//				System.out.println("noord einde: " + noordEinde);
//				System.out.println("oost einde: " + oostEinde);
//				System.out.println("zuid einde: " + zuidEinde);
//				System.out.println("west einde: " + westEinde);
//				System.out.println("heeftKlooster:" + heeftKlooster);
//				System.out.println("heeftBonus:" + heeftBonus);
//				System.out.println("image_id: " + imageId);
//				System.out.println("\n");
//			}

		}
		return alleKaarten;
	}

}