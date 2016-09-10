package game;

import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {

	private Bola bola;
	private Bloco bloco;
	private Paddle paddle;
	private boolean desenhaBloco=true;
	private int auxiliar = 0;
	
	private KeyboardAction moveBRight = new KeyboardAction() {
		public void handleEvent(){
			Point posicaoPaddle = paddle.getPosition();
			paddle.move(8,0);
			if(posicaoPaddle.x > Resolution.MSX.width-paddle.getWidth()){
				paddle.move(-8, 0);	
				}
			}
		};
	private KeyboardAction moveBLeft = new KeyboardAction() {
		public void handleEvent(){
			Point posicaoPaddle = paddle.getPosition();
			paddle.move(-8,0);
			if (posicaoPaddle.x < 0) {
			paddle.move(8, 0);	
			}
		}
		};
	

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		bola.draw(canvas);
		paddle.draw(canvas);
		if(desenhaBloco==true)
		bloco.draw(canvas);
	}

	@Override
	protected void setup() {
		
		setResolution(Resolution.MSX);
		setFramesPerSecond(80);
		bola = new Bola();
		
		bloco = new Bloco();
		
		paddle = new Paddle();
		
		bindKeyPressed("LEFT", moveBLeft);
			
		bindKeyPressed("RIGHT", moveBRight);
	}

	@Override
	protected void loop() {
		
		bola.move();
		
		colidiuParede(bola);
		colidiuPaddle(bola, paddle);
		colidiuBloco(bola, bloco);
		
		
		
			redraw();

		
	}
	//==============VERIFICA SE A BOLA BATEU NA PAREDE==================
	private void colidiuParede(Bola bola) {
		Point posicao = bola.getPosition();
		if (posicao.x < 0 || posicao.x >= Resolution.MSX.width-5){
			bola.invertHorizontal();
		}
		if (posicao.y < 0 || posicao.y >= Resolution.MSX.height-5) {
			bola.invertVertical();
		}	
	}
	
	//==============SE BATEU NO BLOCO, APAGA============================
	private void colidiuBloco(Bola bola, Bloco paddle){
		Point posicaoBola = bola.getPosition();
		
		if (colidiu(posicaoBola,paddle.getBounds())){
			desenhaBloco = false;
		}	
	}
	//=============SE BATEU NO PADDLE, INVERTE===========================
	private void colidiuPaddle(Bola bola, Paddle paddle){
		Point posicaoBola = bola.getPosition();
		
		if (colidiu(posicaoBola,paddle.getBounds())){
			bola.invertVertical();
		
		}
		
	}
	//=============VERIFICA SE A BOLA BATEU EM ALGO=======================
	private boolean colidiu(Point posicaoBola, Rect bounds){
	
		double maximoX = bounds.x + bounds.width;
		double maximoY = bounds.y + bounds.height;
		if (bounds.x <= posicaoBola.x && maximoX >= posicaoBola.x)
			if (bounds.y <= posicaoBola.y && maximoY >= posicaoBola.y)
				return true;
		return false;
	}

	
}




