package filebrowser;

public class Speler {
	
	String Spelernaam;
	int punten;
	boolean aanDeBeurt;
	
	//constructer
	Speler(boolean beurt, int punten, String spelernaam){
		this.aanDeBeurt = beurt;
		this.punten = punten;
		this.Spelernaam = spelernaam;
	}
	
	
	//setters en getters
	public String getSpelernaam() {
		return Spelernaam;
	}
	public void setSpelernaam(String spelernaam) {
		Spelernaam = spelernaam;
	}
	public int getPunten() {
		return punten;
	}
	public void setPunten(int punten) {
		this.punten = punten;
	}
	public boolean getIsAanDeBeurt() {
		return aanDeBeurt;
	}
	public void setAanDeBeurt(boolean aanDeBeurt) {
		this.aanDeBeurt = aanDeBeurt;
	}
	
	
	

}
