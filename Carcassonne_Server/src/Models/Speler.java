package Models;

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Deze klasse zorgt voor data die de speler heeft.
 */
public class Speler implements Serializable {

	private String naam;
	private boolean beurt = false;
	private int punten;
	private ArrayList<Horige> horigeBeschikbaar;
	private ArrayList<Horige> horigeGebruikt;
	private String horigeKleur;

	public Speler(){

	}

	/**
	 * Maak speler aan
	 * @param naam spelernaam String
	 */
	public Speler(String naam){
		this.naam = naam;

		horigeBeschikbaar = new ArrayList<>();
		horigeGebruikt = new ArrayList<>();

		for (int i = 0; i < 7 ; i++) {
			horigeBeschikbaar.add(new Horige(this));
		}
	}

	// Overload voor saven game
	/**
	 * Maak speler
	 * @param naam naam van de type String
	 * @param punten aantal punten
	 * @param beurt spelerbeurt boolean
	 * @param horigeBeschikbaar horigeGebruikt beschikbaar int
	 * @param horigeGebruikt horigeGebruikt aantal int
	 * @param horigeKleur horigekleur String
	 */
	public Speler(String naam, int punten, boolean beurt, int horigeBeschikbaar, int horigeGebruikt, String horigeKleur){
		this.naam = naam;
		this.punten = punten;
		this.beurt = beurt;
		this.horigeBeschikbaar = new ArrayList<Horige>(horigeBeschikbaar);
		for(int i = 0; i < horigeBeschikbaar; i ++){
			this.horigeBeschikbaar.add(new Horige(this));
		}
		this.horigeGebruikt = new ArrayList<Horige>(horigeGebruikt);
	//	for(int i = 0; i < horigeGebruikt; i ++){
	//		this.horigeGebruikt.add(new Horige(this));
		//}
		this.horigeKleur = horigeKleur;
	}

	/**
	 * Maak Speler
	 * @param naam naam van de type String.
	 * @param beurt spelerbeurt boolean.
	 * @param punten aantal punten van de type int.
	 */
	public Speler(String naam, boolean beurt, int punten){
		this.naam = naam;
		this.beurt = beurt;
		this.punten = punten;
	}

	/**
	 * Geeft de horigekleur terug
	 * @return Return me de horigeKleur
	 */
	public String getHorigeKleur(){
		return horigeKleur;
	}

	/**
	 * Set horigekleur
	 * @param kleur
	 * Geeft kleur mee in de type String
	 */
	public void setHorigeKleur(String kleur){
		this.horigeKleur = kleur;
	}

	/**
	 * Krijg beschikbare horige
	 * @return Return file met JSON erin
	 */
	public Horige getBeschikbareHorige() {
		if(horigeBeschikbaar.isEmpty()){
			return null;
		}
		Horige pak = horigeBeschikbaar.get(0);
		horigeBeschikbaar.remove(pak);
		horigeGebruikt.add(pak);
		return pak;
	}

	/**
	 * Return speler horige
	 * @param horige horige
	 */
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

	/**
	 * Geeft de speler punten
	 * @param hoeveelheid de hoeveelheid punten die de speler verdient.
	 */
	public void geefPunten(int hoeveelheid) { punten += hoeveelheid;}

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

	public int getBeschikbareHorigeInt(){
		return this.horigeBeschikbaar.size();
	}

	public int getGebruikteHorigeInt(){
		return this.horigeGebruikt.size();
	}

	/**
	 * Deze functie zorgt dat de spelerdata terug wordt gegeven
	 * @return speler van de type JSONOBJECT
	 */
	public JSONObject getSpelerData(){

			JSONObject speler = new JSONObject();

			//toevoegen spelernaam  aan Speler json object
			speler.put("Spelernaam", this.getNaam());

			//toevoegen Punten  aan Speler json object
			speler.put("Punten", this.getPunten());

			//toevoegen Beurt aan Speler json object
			speler.put("Beurt", this.getBeurt());

			return speler;
	}

}


