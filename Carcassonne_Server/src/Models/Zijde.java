package Models;

public class Zijde {

	public enum ZijdeType {
		KASTEEL,
		WEG,
		GRAS,
		KLOOSTER,
		LEEG
	}

	ZijdeType zijde;
	boolean isEinde;
	Horige horigeSpeler;

	/**
	 * Maak Zijde aan
	 * @param zijde ZijdeType object,
	 * @param isEinde boolean,
	 */
	public Zijde(ZijdeType zijde, boolean isEinde){
		this.zijde = zijde;
		this.isEinde = isEinde;
	}

	/**
	 * Maak Zijde aan
	 * @param zijde ZijdeType object,
	 * @param isEinde boolean,
	 * @param horigeSpeler Horige object
	 */
	public Zijde(ZijdeType zijde, boolean isEinde, Horige horigeSpeler){
		this.zijde = zijde;
		this.isEinde = isEinde;
		this.horigeSpeler = horigeSpeler;
	}

	public void setHorigeSpeler(Horige horige){
		this.horigeSpeler = horige;

	}

	public Horige getHorigeSpeler() {
		return horigeSpeler;
	}

	public ZijdeType getZijde(){
		return zijde;
	}

	public boolean getIsEinde() {
		return isEinde;
	}

	public boolean isEinde() {
		return isEinde;
	}


}
