package Models;

/**
 * Created by Marti on 8-6-2017.
 */
public class Tile {

	String imageID;

	Zijde noordZijde;
	Zijde oostZijde;
	Zijde zuidZijde;
	Zijde westZijde;

	int x;
	int y;

	int rotation;

	boolean heeftKlooster;
	boolean heeftBonus; //Of kasteel tile blauw schild heeft

	Horige[] horigenZijdes;

	public Tile(String imageId, Zijde noordZijde, Zijde oostZijde, Zijde zuidZijde, Zijde westZijde, boolean heeftKlooster, boolean heeftBonus, Horige[] horigen) {
		this.imageID = imageId;
		this.noordZijde = noordZijde;
		this.oostZijde = oostZijde;
		this.zuidZijde = zuidZijde;
		this.westZijde = westZijde;
		this.heeftKlooster = heeftKlooster;
		this.heeftBonus = heeftBonus;
		this.horigenZijdes = horigen;
	}

	public void plaats(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public String getImageID() {
		return imageID;
	}
	public void draaiKaart() {

		Zijde noordZijdetmp = noordZijde;
		Zijde oostZijdetmp = oostZijde;
		Zijde zuidZijdetmp = zuidZijde;
		Zijde westZijdetmp = westZijde;



		noordZijde = westZijdetmp;
		westZijde = zuidZijdetmp;
		zuidZijde = oostZijdetmp;
		oostZijde = noordZijdetmp;

		rotation += 90;
		if(rotation == 360){
			rotation = 0;
		}
	}

	public int getX(){
		return x;
	}

	public int getY() {
		return y;
	}
	public int getRotation() {
		return rotation;
	}
	public String getKaartId() {
		return imageID;
	}
}
