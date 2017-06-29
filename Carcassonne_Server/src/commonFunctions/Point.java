package commonFunctions;

import java.io.Serializable;

/**
 * Deze class is een simpele container voor een x- en y positie
 */
public class Point implements Serializable {

	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return x;
	}

	public int getY() {
		return y;
	}
}
