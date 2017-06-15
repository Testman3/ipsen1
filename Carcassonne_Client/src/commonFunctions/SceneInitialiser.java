package commonFunctions;

/**
 * Deze interface zorgt ervoor dat alle views zich houden aan de initGui + initAction methode die we hebben bedacht
 */
public interface SceneInitialiser {

	/**
	 * Deze functie zorgt er voor dat ale GUI elementen worden geinitialiseerd.
	 */
	 void initGui();

	/**
	 * In deze functie worden alle acties gedefinieerd die deze class moet hebben. Dit zullen voornamelijk
	 * button actions en andere javafx events zijn
	 */
	 void initAction();

}