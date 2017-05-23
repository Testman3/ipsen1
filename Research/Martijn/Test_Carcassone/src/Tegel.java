import java.time.format.TextStyle;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tegel {

	public enum ZijdeType {
		LEEG,
		KASTEEL,
		WEG,
		GRAS
	}

	 int x;
	 int y;
	 ImageView imageview;
	 boolean HasImage = false;

	 ZijdeType NoordZijde = ZijdeType.LEEG;
	 ZijdeType OostZijde  = ZijdeType.LEEG;
	 ZijdeType ZuidZijde  = ZijdeType.LEEG;
	 ZijdeType WestZijde  = ZijdeType.LEEG;



	 public Tegel(int x, int y, ImageView view) {
		this.x = x;
		this.y = y;
		this.imageview = view;
		 Image image1 = new Image(test.class.getResourceAsStream("LeegKaartje.png"));
		 imageview.setImage(image1);
		view.prefHeight(100);
		view.prefWidth(100);
		view.setOnMouseClicked(e -> {
			System.out.println("x: " + x + "y: " + y);

		if(!HasImage && checkTegelFit(test.instance.currentTegel)) {
				HasImage = true;
			  NoordZijde = test.instance.currentTegel.NoordZijde;
			  OostZijde  = test.instance.currentTegel.OostZijde;
			  ZuidZijde  = test.instance.currentTegel.ZuidZijde;
			  WestZijde  = test.instance.currentTegel.WestZijde;
			  imageview.setId(test.instance.currentTegel.imageId);
			  imageview.setRotate(test.instance.currentTegel.rotation);
			  test.instance.currentTegel.rotation = 0;
			  test.instance.currentTegel = test.instance.randomTegel();
			  test.instance.getTegel(0,19).imageview.setId(test.instance.randomTegel().imageId);
			}
		});
	}

	public boolean checkTegelFit(TegelData tegeldata) {

		Tegel noord = test.instance.getTegel(x, y - 1);
		Tegel oost = test.instance.getTegel(x + 1, y);
		Tegel zuid = test.instance.getTegel(x , y + 1);
		Tegel west = test.instance.getTegel(x - 1, y);

		if(noord != null){
			if(noord.ZuidZijde != ZijdeType.LEEG && noord.ZuidZijde != tegeldata.NoordZijde){
				System.out.println("Noordzijde komt niet overeen, want brontile: " + tegeldata.NoordZijde.toString()
				+ " en noordtile: " + noord.ZuidZijde);
				return false;
			}
		}
		if(oost != null){
			if(oost.WestZijde != ZijdeType.LEEG && oost.WestZijde != tegeldata.OostZijde){
				System.out.println("OostZijde komt niet overeen, want brontile: " + tegeldata.OostZijde.toString()
				+ " en oosttile: " + oost.WestZijde);
				return false;
			}
		}
		if(zuid != null){
			if(zuid.NoordZijde != ZijdeType.LEEG && zuid.NoordZijde != tegeldata.ZuidZijde){
				System.out.println("ZuidZijde komt niet overeen, want brontile: " + tegeldata.ZuidZijde.toString()
				+ " en zuidtile: " + zuid.NoordZijde);
				return false;
			}
		}
		if(west != null){
			if(west.OostZijde != ZijdeType.LEEG && west.OostZijde != tegeldata.WestZijde){
				System.out.println("WestZijde komt niet overeen, want brontile: " + tegeldata.WestZijde.toString()
				+ " en westtile: " + west.OostZijde);
				return false;
			}
		}
		return true;



	}



}
