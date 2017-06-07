package filebrowser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.stage.FileChooser;

public class fileManager {

	public static void saveGame(String path){
		//Create json Object 
		JSONObject obj = new JSONObject();
	
		obj.put("Name", "crunchify.com");
		obj.put("Author", "App Shah");
 
		// add Array in json
		JSONArray company = new JSONArray();
		company.add("Compnay: eBay");
		company.add("Compnay: Paypal");
		company.add("Compnay: Google");
		
		//Add array in list
		obj.put("Company List", company);

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
	
}
