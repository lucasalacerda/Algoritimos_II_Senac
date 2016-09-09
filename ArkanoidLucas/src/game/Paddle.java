package game;

import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Drawable;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Paddle extends Sprite{

	public Paddle(){
		super(25,5,Color.BLUE);
		setPosition(
				Resolution.MSX.width/2-5,
				Resolution.MSX.height-10
				);
		
	}
	
	public Rect getBounds() {
		return super.getBounds();
	}
	
	
}
