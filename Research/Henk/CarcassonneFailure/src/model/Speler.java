package model;

import java.io.Serializable;

public class Speler implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int punten;
	private String naam;
	private int aantalHorigen;
	private Horige[] horigenLijst = new Horige[5];
	
	public Speler(String naam){
		this.naam = naam;
	}

	public int getPunten() {
		return punten;
	}

	public void setPunten(int punten) {
		this.punten = punten;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public int getAantalHorigen() {
		return aantalHorigen;
	}

	public void setAantalHorigen(int aantalHorigen) {
		this.aantalHorigen = aantalHorigen;
	}

	public Horige[] getHorigenLijst() {
		return horigenLijst;
	}

	public void setHorigenLijst(Horige[] horigenLijst) {
		this.horigenLijst = horigenLijst;
	}
	
}
