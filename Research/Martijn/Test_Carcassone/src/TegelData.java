
import javafx.scene.image.Image;

public class TegelData {

	String imageId;
	Tegel.ZijdeType NoordZijde = Tegel.ZijdeType.LEEG;
	Tegel.ZijdeType OostZijde  = Tegel.ZijdeType.LEEG;
	Tegel.ZijdeType ZuidZijde  = Tegel.ZijdeType.LEEG;
	Tegel.ZijdeType WestZijde  = Tegel.ZijdeType.LEEG;

	boolean noordZijdeEinde;
	boolean oostZijdeEinde;
	boolean zuidZijdeEinde;
	boolean westZijdeEinde;

	boolean heeftKlooster;
	boolean heeftBonus;


	public int rotation = 0;

	public TegelData(String imageId, Tegel.ZijdeType noordZijde, Tegel.ZijdeType oostZijde, Tegel.ZijdeType zuidZijde,
			Tegel.ZijdeType westZijde, boolean noordZijdeEinde, boolean oostZijdeEinde, boolean zuidZijdeEinde,
			boolean westZijdeEinde, boolean heeftKlooster, boolean heeftBonus) {
		super();
		this.imageId = imageId;
		NoordZijde = noordZijde;
		OostZijde = oostZijde;
		ZuidZijde = zuidZijde;
		WestZijde = westZijde;
		this.noordZijdeEinde = noordZijdeEinde;
		this.oostZijdeEinde = oostZijdeEinde;
		this.zuidZijdeEinde = zuidZijdeEinde;
		this.westZijdeEinde = westZijdeEinde;
		this.heeftKlooster = heeftKlooster;
		this.heeftBonus = heeftBonus;
		this.rotation = 0;
	}



	public void rotate() {
		rotation += 90;
		if(rotation % 360 == 0){
			rotation = 0;
		}
		NoordZijde = WestZijde;
		WestZijde = ZuidZijde;
		ZuidZijde = OostZijde;
		OostZijde = NoordZijde;
		test.instance.getTegel(19, 19).imageview.setRotate(rotation);
	}

}
