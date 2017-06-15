package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Speler implements Serializable {

	private String naam;

	private boolean beurt = false;

	private int punten;

	private ArrayList<Horige> horigeBeschikbaar;
	private ArrayList<Horige> horigeGebruikt;

	private String horigeKleur;

	public Speler(String naam){
		Random rand = new Random();
		punten = rand.nextInt(250);
		this.naam = naam;

		horigeBeschikbaar = new ArrayList<>();
		horigeGebruikt = new ArrayList<>();

		for (int i = 0; i < 7 ; i++) {
			horigeBeschikbaar.add(new Horige(this));
		}

	}

	public Speler(String naam, boolean beurt, int punten){
		this.naam = naam;
		this.beurt = beurt;
		this.punten = punten;
	}

	public String getHorigeKleur(){
		return horigeKleur;
	}
	public void setHorigeKleur(String kleur){
		this.horigeKleur = kleur;
	}

	public Horige getBeschikbareHorige() {
		if(horigeBeschikbaar.isEmpty()){
			return null;
		}
		Horige pak = horigeBeschikbaar.get(0);
		horigeBeschikbaar.remove(pak);
		horigeGebruikt.add(pak);
		return pak;

	}

	public void getHorigeTerug(Horige horige) {
		if(!horigeGebruikt.contains(horige)){
			System.out.println("LET OP ============================================================= LET OP ");
			System.out.println("Horige die de speler terug krijgt staat niet in de array 'horigeGebruikt'. " +
					"			Als je deze error ziet is er iets fout!");
			return;
		}
		horigeGebruikt.remove(horige);
		horigeBeschikbaar.add(horige);
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
