package Models;

import java.io.Serializable;
import java.util.Random;

public class Speler implements Serializable {

	private String naam;

	private boolean beurt = false;

	private int punten;


	public Speler(String naam){
		Random rand = new Random();
		punten = rand.nextInt(250);
		
		this.naam = naam;
	}

	public Speler(String naam, boolean beurt, int punten){
		this.naam = naam;
		this.beurt = beurt;
		this.punten = punten;
	}

	public void setNaam(String naam){
		this.naam = naam;
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

	public void setPunten(int punten){
		this.punten = punten;
	}

	public int getPunten(){
		return this.punten;
	}





}
