package Models;

/**
 * Created by Marti on 8-6-2017.
 */
public class Zijde {

	public enum ZijdeType {
		KASTEEL,
		WEG,
		GRAS,
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
}
