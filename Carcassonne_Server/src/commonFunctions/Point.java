package commonFunctions;

import java.io.Serializable;

/**
 * Created by Marti on 15-6-2017.
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
