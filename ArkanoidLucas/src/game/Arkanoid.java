package game;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {

	private Bola bola;
	private int tamanhoArrayBloco = 10;
	private Bloco[] blocoLinhaUm = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaDois = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaTres = new Bloco[tamanhoArrayBloco];
	private Paddle paddle;
	private int score = 0;
	private int vida = 3;
	private Image background;
	private Rect limits;

	
	//=======================MOVE PADDLE PARA A DIREITA==================================
	private KeyboardAction moveRight = new KeyboardAction() {
		public void handleEvent(){
			Point posicaoPaddle = paddle.getPosition();
			paddle.move(10,0);
			if(posicaoPaddle.x > Resolution.MSX.width-paddle.getWidth()){
				paddle.move(-10, 0);	
				}
			}
		};//=======FINAL MOVERIGHT
		
	//=======================MOVE PADDLE PARA A ESQUERDA==================================
	private KeyboardAction moveLeft = new KeyboardAction() {
		public void handleEvent(){
			Point posicaoPaddle = paddle.getPosition();
			paddle.move(-10,0);
			if (posicaoPaddle.x < 0) {
			paddle.move(10, 0);	
			}
		}
		};//=======FINAL MOVELEFT
	

	//=============================MÉTODO DRAW==================================
	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		canvas.drawImage(background, 0, 0);
		canvas.setForeground(Color.WHITE);
		canvas.putText(2, 2, 10, "SCORE:");
		canvas.putText(45, 2, 10, String.format("%05d", score));
		canvas.putText(110, 2, 10, "TENTATIVAS:");
		canvas.putText(185, 2, 10, String.valueOf(vida));



		
		bola.draw(canvas);
		paddle.draw(canvas);
			for(int i = 0; i < 10; i++){
				blocoLinhaUm[i].draw(canvas);
				blocoLinhaDois[i].draw(canvas);
				blocoLinhaTres[i].draw(canvas);

			}
	}//=======FINAL DRAW

	//=============================MÉTODO SETUP==================================
	@Override
	protected void setup() {
		carregaImagens();
		setResolution(Resolution.MSX);
		setFramesPerSecond(100);
		criaBlocos();

		bola = new Bola();
		paddle = new Paddle();
		
		bindKeyPressed("LEFT", moveLeft);
		bindKeyPressed("RIGHT", moveRight);
	}//=======FINAL SETUP
	
	//=============================MÉTODO LOOP==================================
	@Override
	protected void loop() {
		
		bola.move();
		paddle.colidiuPaddle(bola);
		bola.colidiuParede(bola);
		colidiuBloco(blocoLinhaUm);
		colidiuBloco(blocoLinhaDois);
		colidiuBloco(blocoLinhaTres);
		if(vida == 0){
			JOptionPane.showMessageDialog(null,"FIM DE JOGO", "MORREU DEMAIS",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		youLose();
		
			redraw();	
	}//=======FINAL LOOP
	
	//==========================CARREGA IMAGENS=======================================
	
	private void carregaImagens(){
		try {
			//limits = new Rect(0,0,Canvas.SCREEN_WIDTH,Canvas.SCREEN_HEIGHT);
			background = new Image("Imagens/background.jpg");
			background.resize(Resolution.MSX.width,Resolution.MSX.height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//=======================CRIACAO DAS TRES LINAHS DE BLOCOS========================
	private void criaBlocos(){
		for (int i = 0; i < tamanhoArrayBloco; i++) {
			int x = (i%10)*26+2;			
			blocoLinhaUm[i] = new Bloco();
			blocoLinhaUm[i].setPosition(new Point(x, 30));
			blocoLinhaDois[i] = new Bloco();
			blocoLinhaDois[i].setPosition(new Point(x,36));
			blocoLinhaTres[i] = new Bloco();
			blocoLinhaTres[i].setPosition(new Point(x,42));
					
		}
		
	}//=======FINAL CRIABLOCOS
	
	//==================VERIFICA SE BOLA COLIDIU COM ALGUM BLOCO======================
	private void colidiuBloco(Bloco[] bloco){
		
		for (int i = 0; i < tamanhoArrayBloco; i++) {
			if(bloco[i].colidiu(bola)){
				bola.invertVertical();
				score = score +100;
			}
		}
		
	}//=======FINAL CLIDIUBLOCO

	//=================================YOULOSE=========================================
	//VERIFICA SE BOLA FOI PARA O LIMITE DO CHÃO E VALIDA AS VIDAS RESTANTES
	private void youLose(){
		Point posicaoBola = bola.getPosition();
		if(posicaoBola.y >= Resolution.MSX.height-5){
			bola.setDx(0);
			bola.setDy(0);
			vida = vida-1;
			paddle.setPosition(
					Resolution.MSX.width/2-5,
					Resolution.MSX.height-10
					);
			bola.setPosition(
					Resolution.MSX.width/2-5,
					Resolution.MSX.height-11
					);
			bola.setDx(1.5);
			bola.setDy(1.5);
		}
			
	}
	
}//=======FINAL ARKANOID




