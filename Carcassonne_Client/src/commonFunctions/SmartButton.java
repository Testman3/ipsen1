package commonFunctions;

import Controllers.ClientManager;
import com.sun.speech.freetts.*;

import Views.SettingsScene;
import javafx.css.PseudoClass;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

/**
 * Deze class extend de standaard Button class uit javafx om spraakondersteuning en geluidseffecten toe te voegen
 */
public class SmartButton extends Button {

	//Importeer de audioclip te gebruiken voor alle knoppen
	private AudioClip clickSound = new AudioClip(Paths.get("Sounds/Snd_Click.wav").toUri().toString());

	// Stem type van speech (Kevin)
	private static final String VOICENAME="kevin16";

	//tts libary voice settings
	private Voice voice;
	private VoiceManager vm = VoiceManager.getInstance();


	// Overload constructs
	public SmartButton(String text) {
		super(text);
		voice = vm.getVoice(VOICENAME);
		if(voice != null){
			voice.allocate();
		}

		//Speel het clickSound af wanneer er met de muis op een knop gedrukt wordt
		this.setOnMousePressed (e -> {
			clickSound.play();
		});

		// Onhover talk text
		this.setOnMouseEntered(e -> {
			if(ClientManager.debug == true)
				System.out.println("HOVER : " + this.getText());

			talk(this.getText());
		});

		initialize();

	}

	public SmartButton() {
		voice = vm.getVoice(VOICENAME);

		if(voice != null){
			voice.allocate();
		}

		//Speel het clickSound af wanneer er met de muis op een knop gedrukt wordt
		this.setOnMousePressed(e-> {

			clickSound.play();
		});

		 //Onhover talk text
		this.setOnMouseEntered(e -> {
			if(ClientManager.debug == true)
				System.out.println("HOVER : " + this.getText());

			talk(this.getText());

			initialize();
		});
	}
	/**
	* Lees label text voor (speech)
	* @param text
	 * Geef de tekst mee in de vorm van een String die uitgesproken zal gaan worden
	*/
	public void talk(String text) {
		if (SettingsScene.optieSpreken) {
			try {
				voice.speak(text);
			} catch (Exception e) {
				System.out.println("FOUT: " + e);
			}
		}
	}

	// Button funct en vars (niet belangrijk)
	private void initialize() {
		getStyleClass().setAll(DEFAULT_STYLE_CLASS);
		setAccessibleRole(AccessibleRole.BUTTON);
		setMnemonicParsing(true);     // enable mnemonic auto-parsing by default
	}

	private static final String DEFAULT_STYLE_CLASS = "button";
	private static final PseudoClass PSEUDO_CLASS_DEFAULT = PseudoClass.getPseudoClass("default");
	private static final PseudoClass PSEUDO_CLASS_CANCEL = PseudoClass.getPseudoClass("cancel");

}