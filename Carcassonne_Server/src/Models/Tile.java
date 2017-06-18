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
	Zijde middenZijde;

	int x;
	int y;

	int rotation;

	boolean heeftKlooster;

	boolean heeftBonus; //Of kasteel tile blauw schild heeft

	Horige.Posities[] horigenZijdes;

	public Tile(){

	}

	public Tile(String imageId, Zijde noordZijde, Zijde oostZijde, Zijde zuidZijde, Zijde westZijde, boolean heeftKlooster, boolean heeftBonus, Horige.Posities[] horigen) {
		this.imageID = imageId;
		this.noordZijde = noordZijde;
		this.oostZijde = oostZijde;
		this.zuidZijde = zuidZijde;
		this.westZijde = westZijde;
		this.heeftKlooster = heeftKlooster;
		this.heeftBonus = heeftBonus;
		this.horigenZijdes = horigen;
		if(heeftKlooster){
		middenZijde = new Zijde(Zijde.ZijdeType.KLOOSTER, true);
		}
	}

	public void plaatsHorige(Horige.Posities posititie, Speler speler){
		if(posititie == Horige.Posities.NOORD){
			noordZijde.setHorigeSpeler(speler.getBeschikbareHorige());
			noordZijde.getHorigeSpeler().setPositie(Horige.Posities.NOORD);
		} else if(posititie == Horige.Posities.OOST){
			oostZijde.setHorigeSpeler(speler.getBeschikbareHorige());
			oostZijde.getHorigeSpeler().setPositie(Horige.Posities.OOST);
		} else if(posititie == Horige.Posities.ZUID){
			zuidZijde.setHorigeSpeler(speler.getBeschikbareHorige());
			zuidZijde.getHorigeSpeler().setPositie(Horige.Posities.ZUID);
		} else if(posititie == Horige.Posities.WEST){
			westZijde.setHorigeSpeler(speler.getBeschikbareHorige());
			westZijde.getHorigeSpeler().setPositie(Horige.Posities.WEST);
		} else if(posititie == Horige.Posities.MIDDEN){
			middenZijde.setHorigeSpeler(speler.getBeschikbareHorige());
			middenZijde.getHorigeSpeler().setPositie(Horige.Posities.MIDDEN);
		}
	}

	public void plaats(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Horige getGeplaatsteHorigePositie(){
		if(noordZijde.horigeSpeler != null){
			return noordZijde.horigeSpeler;
		} else if(oostZijde.horigeSpeler != null){
			return oostZijde.horigeSpeler;
		} else if(zuidZijde.horigeSpeler != null){
			return zuidZijde.horigeSpeler;
		} else if(westZijde.horigeSpeler != null){
			return westZijde.horigeSpeler;
		} else if(middenZijde != null && middenZijde.horigeSpeler != null){
			return middenZijde.horigeSpeler;
		}
		return null;
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

		for (int i = 0; i < horigenZijdes.length; i++) {

			if(horigenZijdes[i] == Horige.Posities.MIDDEN){
				continue;
			}
			Horige.Posities old = horigenZijdes[i];

			if(horigenZijdes[i] == Horige.Posities.NOORD){
				horigenZijdes[i] = Horige.Posities.OOST;
			} else 	if(horigenZijdes[i] == Horige.Posities.OOST){
				horigenZijdes[i] = Horige.Posities.ZUID;
			} else 	if(horigenZijdes[i] == Horige.Posities.ZUID){
				horigenZijdes[i] = Horige.Posities.WEST;
			} else 	if(horigenZijdes[i] == Horige.Posities.WEST){
				horigenZijdes[i] = Horige.Posities.NOORD;
			}

			//horigenZijdes[i] =  Horige.Posities.values()[(horigenZijdes[i].ordinal() + 1) % Horige.Posities.values().length - 1];

			System.out.println("Old" + old + " New " + horigenZijdes[i]);
		}


		rotation += 90;
		if(rotation == 360){
			rotation = 0;
		}
	}

	public int getX(){
		return x;
	}
	public boolean getKlooster(){return this.heeftKlooster;};
	public int getY() {
		return y;
	}
	public int getRotation() {
		return rotation;
	}
	public String getKaartId() {
		return imageID;
	}

	public Horige.Posities[] getHorigenZijdes() { return horigenZijdes; }

	public boolean getHeeftBonus(){
		return heeftBonus;
	}

	public Zijde getNoordZijde() {
		return noordZijde;
	}

	public Zijde getOostZijde() {
		return oostZijde;
	}

	public Zijde getZuidZijde() {
		return zuidZijde;
	}

	public Zijde getWestZijde() {
		return westZijde;
	}

	public Zijde getMiddenZijde() {
		return middenZijde;
	}

	public boolean getHeeftKlooster() { return heeftKlooster; }

	public Horige.Posities[] getHorigePositie(int counter){
		for (int i = 0; 1 < horigenZijdes.length; i++){

		}
		return this.horigenZijdes;
	}


	public void setImageID(String imageID) {
		this.imageID = imageID;
	}

	public void setNoordZijde(Zijde noordZijde) {
		this.noordZijde = noordZijde;
	}

	public void setOostZijde(Zijde oostZijde) {
		this.oostZijde = oostZijde;
	}

	public void setZuidZijde(Zijde zuidZijde) {
		this.zuidZijde = zuidZijde;
	}

	public void setWestZijde(Zijde westZijde) {
		this.westZijde = westZijde;
	}

	public void setMiddenZijde(Zijde middenZijde) {
		this.middenZijde = middenZijde;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public void setHeeftKlooster(boolean heeftKlooster) {
		this.heeftKlooster = heeftKlooster;
	}

	public void setHeeftBonus(boolean heeftBonus) {
		this.heeftBonus = heeftBonus;
	}

	public void setHorigenZijdes(Horige.Posities[] horigenZijdes) {
		this.horigenZijdes = horigenZijdes;
	}
}
