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
	private Bloco[] bloco;
	private Paddle paddle;
	private boolean desenhaBloco=true;
	private int tamanhoArrayBloco = 10;
	private int indiceBloco = 0;
	
	
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
			for(int i = 0; i < 10; i++){
				bloco[i].draw(canvas);
			}
			
			
	}

	@Override
	protected void setup() {
		
		setResolution(Resolution.MSX);
		setFramesPerSecond(80);
		
		criaBlocos();

		bola = new Bola();
			
		paddle = new Paddle();
		
		bindKeyPressed("LEFT", moveBLeft);
			
		bindKeyPressed("RIGHT", moveBRight);
	}

	@Override
	protected void loop() {
		
		bola.move();

		colidiuParede(bola);
		paddle.colidiuPaddle(bola);
		
		colidiuBloco();
		
		

		
		
		
		
			redraw();

		
	}
	
	private void criaBlocos(){
		bloco = new Bloco[tamanhoArrayBloco];
		for (int i = 0; i < tamanhoArrayBloco; i++) {
			bloco[i] = new Bloco();
			int x = (i%5)*26+1;
			int y = (i/5)*10+20;
			bloco[i].setPosition(new Point(x,y));
			
		}
		
	}
	private void colidiuBloco(){
		
		for (int i = 0; i < bloco.length; i++) {
			if(bloco[i].colidiu(bola)){
				bola.invertVertical();
				bloco[i].clear(Color.BLACK);
				
				
			}
		}
		
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
}




