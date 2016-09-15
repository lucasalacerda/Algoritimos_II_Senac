package game;

import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bloco extends Sprite{
	
	public Bloco(){
		super(25, 5, Color.RED);

			setPosition(
					Resolution.MSX.width/10-10,
					Resolution.MSX.height-90
					);
		
		
	}
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	public Rect getBounds() {
		return super.getBounds();
	}
	
	//==============SE BATEU NO BLOCO, APAGA============================
		public boolean colidiu(Bola bola){
			
			Point posicaoBola = bola.getPosition();
			double tamanhoX = super.getPosition().x + super.getWidth();
			double tamanhoY = super.getPosition().y + super.getHeight();
			if (super.getPosition().x <= posicaoBola.x && tamanhoX >= posicaoBola.x)
				if (super.getPosition().y <= posicaoBola.y && tamanhoY >= posicaoBola.y){
					return true;
				}
			return false;
		}
	
	
	
	
	
}
