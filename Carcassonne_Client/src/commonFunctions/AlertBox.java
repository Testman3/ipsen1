package commonFunctions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

/**
 * Deze class zorgt voor de styling en het geluidseffect van al onze alertboxen
 */
public class AlertBox {
	private Alert alert;
	private AudioClip errorSound = new AudioClip(Paths.get("Sounds/Error.WAV").toUri().toString());

	public AlertBox(String reason){
		alert = new Alert(Alert.AlertType.ERROR, reason, ButtonType.OK) ;
		DialogPane alertPane = alert.getDialogPane();
		alertPane.getStylesheets().add("style.css");
		alertPane.getStyleClass().add("alertBox");
		alertPane.setMinWidth(500);
		errorSound.play();
		alert.showAndWait();
	}
}
