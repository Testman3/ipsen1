package speech;

import com.sun.speech.freetts.*;
import javafx.scene.control.Label;

public class Praten extends Label {

	// Stem type van speech (spreeker Kevin)
	private static final String VOICENAME="kevin16";

	private Voice voice;
	private VoiceManager vm = VoiceManager.getInstance();


	public Praten(String text){
		super(text);
		voice = vm.getVoice(VOICENAME);
		voice.allocate();

		// Onhover talk text
		this.setOnMouseEntered(e -> {
			System.out.println("HOVER");
			talk(text);
		});
	}

	// Lees label text voor (speech)
	// @param String text - label tekst
	// @throws Exception
	public void talk(String text){
		try{
			voice.speak(text);
		}catch(Exception e){
			System.out.println("FOUT: " + e);
		}
	}

}
