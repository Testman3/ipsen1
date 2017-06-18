package Views;

import Controllers.MenuController;
import commonFunctions.SceneInitialiser;
import commonFunctions.SmartButton;
import commonFunctions.SmartLabel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import static Views.MenuViewScene.mediaPlayer;

/**
 * Deze class zorgt voor een correcte weergave van de SettingsScene
 */
public class SettingsScene extends Scene implements SceneInitialiser {

	//Settings vars
	private BorderPane mainPane;
	private VBox buttonVBox;
	private HBox soundBox;
	private HBox spraakBox;
	private HBox fullscreenBox;
	private Label titel;
	private SmartLabel sounds;
	private SmartLabel spraak;
	private SmartLabel fullscreen;
	private CheckBox soundCheckBox;
	private CheckBox spraakCheckBox;
	private CheckBox fullscreenCheckBox;
	private SmartButton backToHome;
	private MenuController controller;

	//De drie beschikbare menu opties
	public static boolean optieSpreken = false;
	public static boolean optieFullscreen = false;
	public static boolean optieGeluid = true;

	/**
	 * Constructor van SettingsScene
	 *
	 * @param controller Geef MenuController mee
	 */
	public SettingsScene(MenuController controller) {
		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();

		this.controller = controller;

		initGui();
	}

	public void initGui() {


		backToHome = new SmartButton("Terug naar hoofdmenu");
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
		soundCheckBox.setSelected(true);
		spraakCheckBox.setId("checkBox");
		fullscreenCheckBox.setId("checkBox");

		titel = new Label("Instellingen");
		sounds = new SmartLabel("Geluid");
		spraak = new SmartLabel("Spraakondersteuning");
		fullscreen = new SmartLabel("Fullscreen in-game");

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

		optieSpreken = spraakCheckBox.isSelected();
		optieFullscreen = fullscreenCheckBox.isSelected();
		optieGeluid = soundCheckBox.isSelected();

		initAction();
		;

	}

	public void initAction() {

		spraakCheckBox.setOnAction(e -> {
			optieSpreken = spraakCheckBox.isSelected();
		});

		soundCheckBox.setOnAction(e -> {
			optieGeluid = soundCheckBox.isSelected();

			if (!mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING) && SettingsScene.optieGeluid) {
				mediaPlayer.setVolume(0.75);
				mediaPlayer.play();
			} else if (optieGeluid == false) {
				mediaPlayer.stop();
			}
		});

		fullscreenCheckBox.setOnAction(e -> {
			optieFullscreen = fullscreenCheckBox.isSelected();
		});

		backToHome.setOnAction(e -> {
			controller.backToMainMenu();
		});

	}

}