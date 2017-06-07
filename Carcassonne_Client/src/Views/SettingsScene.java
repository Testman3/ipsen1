package Views;

import Controllers.MenuController;
import commonFunctions.SceneInitialiser;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsScene extends Scene implements SceneInitialiser {

	//Settings vars
	private BorderPane mainPane;
	private VBox buttonVBox;
	private HBox soundBox;
	private HBox spraakBox;
	private HBox fullscreenBox;

	private Label titel;
	private Label sounds;
	private Label spraak;
	private Label fullscreen;

	private CheckBox soundCheckBox;
	private CheckBox spraakCheckBox;
	private CheckBox fullscreenCheckBox;

	private Button backToHome;
	private MenuController controller;

	//spraak
	public static boolean optieSpreken = false;
	public static boolean fullScreen = false;

	public SettingsScene(MenuController controller) {
		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();

		this.controller = controller;

		initGui();
		InitAction();
	}


	public void initGui() {

		backToHome = new Button("Terug naar hoofdmenu");
		backToHome.setId("standardLabel");

		mainPane.getStylesheets().add("style.css");
		mainPane.setId("mainBackground");

		buttonVBox = new VBox();
		soundBox = new HBox(5);
		spraakBox = new HBox(5);
		fullscreenBox = new HBox(5);

		buttonVBox.setId("schild");

		soundCheckBox = new CheckBox();
		spraakCheckBox = new CheckBox();
		fullscreenCheckBox = new CheckBox();

		soundCheckBox.setId("checkBox");
		spraakCheckBox.setId("checkBox");
		fullscreenCheckBox.setId("checkBox");

		titel = new Label("Instellingen");
		sounds = new Label("Geluid");
		spraak = new Label("Spraakondersteuning");
		fullscreen = new Label("Fullscreen");

		titel.setId("title");
		sounds.setId("standardLabel");
		spraak.setId("standardLabel");
		fullscreen.setId("standardLabel");

		soundBox.getChildren().addAll(sounds, soundCheckBox);
		spraakBox.getChildren().addAll(spraak, spraakCheckBox);
		fullscreenBox.getChildren().addAll(fullscreen, fullscreenCheckBox);

		buttonVBox.getChildren().addAll(titel, soundBox, spraakBox, fullscreenBox, backToHome);


		mainPane.setCenter(buttonVBox);

		buttonVBox.setAlignment(Pos.CENTER);
		soundBox.setAlignment(Pos.CENTER);
		spraakBox.setAlignment(Pos.CENTER);
		fullscreenBox.setAlignment(Pos.CENTER);

	}
	public void InitAction() {

		spraakCheckBox.setOnAction(e -> {
			optieSpreken = spraakCheckBox.isSelected();
		});

		fullscreenCheckBox.setOnAction(e -> {
			fullScreen = fullscreenCheckBox.isSelected();
		});

		backToHome.setOnAction(e -> {
			controller.backToMainMenu();
		});

	}

}
