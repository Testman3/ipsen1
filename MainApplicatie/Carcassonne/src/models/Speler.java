package models;

import java.io.Serializable;

public class Speler implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public String naam;
public int punten;
public int aantalHorigen;

public Speler(String naam){
	this.naam = naam;
	}

public String getnaam(){
	return this.naam;
}

}
