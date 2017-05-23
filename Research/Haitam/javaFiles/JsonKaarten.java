

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonKaarten {

	public JsonKaarten() {
		//open for edits
	}

	/**
	* Get json file met kaarten data en parse alle data naar een TegelData array
	* @return ArrayList<TegelData> - alle kaarten van carcassonne
	* @throws FileNotFoundException
	*/
	public ArrayList<TegelData> getAllKaarten() {

		ArrayList<TegelData> alleKaarten =  new ArrayList<TegelData>();

		JSONParser parser = new JSONParser();
		Object obj = null;


		try {
			// Get file json file (in map json)
			obj = parser.parse(new FileReader("json/alleGoeieKaarten.json"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
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
			Tegel.ZijdeType noordZijde = Tegel.ZijdeType.valueOf((String)jsonNumber.get("noordZijde"));
			Tegel.ZijdeType oostZijde  = Tegel.ZijdeType.valueOf((String)jsonNumber.get("oostZijde"));
			Tegel.ZijdeType zuidZijde  = Tegel.ZijdeType.valueOf((String)jsonNumber.get("zuidZijde"));
			Tegel.ZijdeType westZijde  = Tegel.ZijdeType.valueOf((String)jsonNumber.get("westZijde"));

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
			TegelData data = new TegelData(imageId, noordZijde, oostZijde, zuidZijde, westZijde, noordEinde, oostEinde, zuidEinde, westEinde, heeftKlooster, heeftBonus);
			alleKaarten.add(data);

			// Debugging info (overwrite in future with debugging function)
			boolean debug = false;

			if (debug){
				System.out.println("noord zijde: " + noordZijde);
				System.out.println("oost zijde: " + oostZijde);
				System.out.println("zuid zijde: " + zuidZijde);
				System.out.println("west zijde: " + westZijde);
				System.out.println("noord einde: " + noordEinde);
				System.out.println("oost einde: " + oostEinde);
				System.out.println("zuid einde: " + zuidEinde);
				System.out.println("west einde: " + westEinde);
				System.out.println("heeftKlooster:" + heeftKlooster);
				System.out.println("heeftBonus:" + heeftBonus);
				System.out.println("image_id: " + imageId);
				System.out.println("\n");
			}

		}
		return alleKaarten;
	}

}