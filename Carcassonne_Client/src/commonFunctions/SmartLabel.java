package commonFunctions;

import com.sun.speech.freetts.*;

import Views.SettingsScene;
import javafx.css.PseudoClass;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Label;

public class SmartLabel extends Label {

	// Stem type van speech (Kevin)
	private static final String VOICENAME="kevin16";

	//tts libary voice settings
	private Voice voice;
	private VoiceManager vm = VoiceManager.getInstance();


	// Overload constructs
	public SmartLabel(String text) {
		super(text);
		voice = vm.getVoice(VOICENAME);
		voice.allocate();

		// Onhover talk text
		if (SettingsScene.optieSpreken) {
			this.setOnMouseEntered(e -> {
				System.out.println("HOVER : " + this.getText());
				talk(this.getText());
			});
		}
		initialize();
	}

	public SmartLabel() {
		voice = vm.getVoice(VOICENAME);
		voice.allocate();

		// Onhover talk text
		if (SettingsScene.optieSpreken) {
			this.setOnMouseEntered(e -> {
				System.out.println("HOVER : " + this.getText());
				talk(this.getText());
			});
		}

		initialize();
	}

	// Lees label text voor (speech)
	// @param String text - label tekst
	// @throws Exception
	public void talk(String text) {
		try{
			voice.speak(text);
		}catch(Exception e){
			System.out.println("FOUT: " + e);
		}
	}

}
