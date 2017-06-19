package Models;

import java.io.Serializable;

/**
 * Created by Marti on 11-6-2017.
 */
public class TileStump implements Serializable {

	private int x;
	private int y;
	private String id;
	private int rotation;
	private Horige horige;

	/**
	 * Constructor van TileStump
	 * @param x x-coordinaat van de tile
	 * @param y y-coordinaat van de tile
	 * @param id de css-tileId
	 * @param rotation de rotatie van de tile
	 * @param horige
	 */
	public TileStump(int x, int y, String id, int rotation, Horige horige){
		this.x = x;
		this.y = y;
		this.id = id;
		this.rotation = rotation;
		this.horige = horige;
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
	public Horige getGeplaatsteHorige() { return horige; }
}
