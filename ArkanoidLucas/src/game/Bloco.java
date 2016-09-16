package game;

import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bloco extends Sprite{
	private boolean alive = true;

	public Bloco(){
		super(25, 5, Color.RED);
	
	}
	public void draw(Canvas canvas) {
		if(alive)
		super.draw(canvas);
	}
	
	public Rect getBounds() {
		return super.getBounds();
	}
	
	//==============SE BATEU NO BLOCO, APAGA============================
		public boolean colidiu(Bola bola){
			if (!alive) return false;
			Point posicaoBola = bola.getPosition();
			double tamanhoX = super.getPosition().x + super.getWidth();
			double tamanhoY = super.getPosition().y + super.getHeight();
			if (super.getPosition().x <= posicaoBola.x && tamanhoX >= posicaoBola.x)
				if (super.getPosition().y <= posicaoBola.y && tamanhoY >= posicaoBola.y){
					alive = false;
					return true;
				}
			return false;
		}
	
	
	
	
	
}
