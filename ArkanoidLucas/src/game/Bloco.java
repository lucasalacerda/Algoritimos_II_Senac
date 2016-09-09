package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bloco extends Sprite{
	
		
	public Bloco(){
		super(25,7,Color.RED);
		setPosition(
				Resolution.MSX.width/10-10,
				Resolution.MSX.height-90
				);
	}
	public Rect getBounds() {
		return super.getBounds();
	}
	
}
