package commonFunctions;

public interface SceneInitialiser {

	/**
	 * Deze functie zorgt er voor dat ale GUI elementen worden geinitialiseerd.
	 */
	 public void initGui();

	/**
	 * In deze functie worden alle acties gedefinieerd die deze class moet hebben. Dit zullen voornamelijk
	 * button actions en andere javafx events zijn
	 */
	 public void initAction();

}