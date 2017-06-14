package Models;

import java.io.Serializable;

/**
 * Created by Marti on 11-6-2017.
 */
public class TileStump implements Serializable {

	int x;
	int y;
	String id;
	int rotation;
	Horige.Posities[] horigenZijdes;

	public TileStump(int x, int y, String id, int rotation){
		this.x = x;
		this.y = y;
		this.id = id;
		this.rotation = rotation;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getId() {
		return id;
	}
	public int getRotation() {
		return rotation;
	}
	public 	Horige.Posities[] getHorigenZijdes() { return horigenZijdes;}
}
