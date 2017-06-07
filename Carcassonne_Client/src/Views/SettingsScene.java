package Views;

import commonFunctions.SceneInitialiser;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SettingsScene extends Scene implements SceneInitialiser {

	//Settings vars
	private BorderPane mainPane;
	private VBox buttonVBox;
	private Label titel = new Label("Instellingen");
	private Label sounds = new Label("Geluid");
	private Label spraak = new Label("Spraakondersteuning");
	private Label fullscreen = new Label("Fullscreen");

	//spraak
	public static boolean optieSpreken = false;

	public SettingsScene(){
		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();

		init();
	}


	public void init(){
		buttonVBox = new VBox();


		buttonVBox.setId("schild");
		mainPane.getStylesheets().add("style.css");
		mainPane.setId("mainBackground");
		titel.setId("titel");
		sounds.setId("sounds");
		spraak.setId("spraak");
		fullscreen.setId("fullscreen");

		buttonVBox.getChildren().addAll(titel, sounds, spraak, fullscreen);


		mainPane.setCenter(buttonVBox);
		buttonVBox.setAlignment(Pos.CENTER);


	}
	public void buttonInit(){

	}

}
