package Models;

public class Speler {

	public String naam;

	private boolean beurt = false;

	private int punten;


	public Speler(String naam){

		this.naam = naam;
	}

	public Speler(String naam, boolean beurt, int punten){
		this.naam = naam;
		this.beurt = beurt;
		this.punten = punten;
	}

	public String getNaam() {
		return naam;
	}

	public void setBeurt(boolean beurt){
		this.beurt = beurt;
	}

	public boolean getBeurt(){
		return this.beurt;
	}

	public int getPunten(){
		return this.punten;
	}





}
