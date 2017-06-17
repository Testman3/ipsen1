package Models;

import java.io.Serializable;

public class Horige implements Serializable {

	Speler speler;
	Posities positie;

	public Horige(){

	}

	public Horige(Speler speler){
		this.speler = speler;
	}

	public void setPositie(Posities positie){
		this.positie = positie;
	}

	public void setSpeler(Speler speler){
		this.speler = speler;
	}


	public Posities getPositie() {
		return positie;
	}
	public Speler getSpeler(){
		return speler;
	}

	public String getSpelerString(){
		return speler.getNaam();
	}


	public String getPositieString(){
		return positie.toString();
	}

	public enum Posities {

		NOORD(40,0),
		OOST(70,40),
		ZUID(40,70),
		WEST(0,40),
		MIDDEN(40,40);

		int x;
		int y;

		private Posities(int x, int y){
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
}
