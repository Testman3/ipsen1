package Models;

/**
 * Created by Marti on 8-6-2017.
 */
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

	public Zijde(ZijdeType zijde, boolean isEinde){
		this.zijde = zijde;
		this.isEinde = isEinde;
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


	public ZijdeType getZijde() {
		return zijde;
	}

	public boolean isEinde() {
		return isEinde;
	}


}
