package filebrowser;

import java.io.File;
import java.io.IOException;

import javafx.stage.FileChooser;

public class fileManager {

	public static void saveGame(String path){
		File file = new File(path);
		System.out.print(file.getAbsoluteFile());
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}
	
}
