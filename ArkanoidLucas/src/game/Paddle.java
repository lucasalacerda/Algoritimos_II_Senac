package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;

public class Paddle extends Sprite{

	public Paddle(Image paddle){
		super(paddle);
		setPosition(
				Resolution.MSX.width/2-5,
				Resolution.MSX.height-10
				);
		
	}
	
	public Rect getBounds() {
		return super.getBounds();
	}
	
	public void colidiuPaddle(Bola bola){
		
		Point posicaoBola = bola.getPosition();
		double tamanhoX = super.getPosition().x + super.getWidth();
		double tamanhoY = super.getPosition().y + super.getHeight();
		if (super.getPosition().x <= posicaoBola.x && tamanhoX >= posicaoBola.x)
			if (super.getPosition().y <= posicaoBola.y && tamanhoY >= posicaoBola.y){
				bola.invertVertical();

			}		
	}

	public void posicionaPaddle() {
		super.setPosition(
				Resolution.MSX.width/2-5,
				Resolution.MSX.height-10
				);		
	}
	
	
}
