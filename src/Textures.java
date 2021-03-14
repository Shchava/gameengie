import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class Textures {
	private static Textures[] values = {
		new Textures("/textures/wall2.png"),	//1
		new Textures("/textures/wall3.png")		//2
	};
	//wall1("/textures/wall2.png"),wall3("/textures/wall3.png");
	
	Image img;
	PixelReader reader;
	Textures(String dir){
		img=new Image(dir);
		reader=img.getPixelReader(); 
	}
	PixelReader getPixelReader(){
		return reader;
	}
	public static Textures getTextureByID(int ID){
		if(ID == 0){
		return values[ID];}
		else{
			return values[ID-1];
		}
	}
}
