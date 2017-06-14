package Models;

public class Horige {

	public enum Posities {

		NOORD(40,0),
		OOST(40,70),
		ZUID(70,40),
		WEST(0,40),
		MIDDEN(40,40);

		int x;
		int y;

		private Posities(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
}
