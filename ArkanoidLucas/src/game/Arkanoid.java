package game;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {

	private Bola bola;
	private int tamanhoArrayBloco = 10;
	private Bloco[] blocoLinhaUm = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaDois = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaTres = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaUmEstagioDois = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaDoisEstagioDois = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaTresEstagioDois = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaUmEstagioTres = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaDoisEstagioTres = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaTresEstagioTres = new Bloco[tamanhoArrayBloco];
	private Bloco[] blocoLinhaQuatroEstagioTres = new Bloco[tamanhoArrayBloco];
	private Paddle paddle;
	private int score = 2900;
	private int hscore = 0;
	private int vida = 1;
	private Image paddleImage;
	private Image bgEstagioUm;
	private Image bgEstagioDois;
	private Image bgEstagioTres;
	private int estagioAtual = 0;
	private File soundFile;
	private final String cleanStage = "Audio/cleanStage.wav";
	private final String quebraBloco = "Audio/quebraBloco.wav";
	private final String gameOver = "Audio/gameOver.wav";
	private final String die = "Audio/die.wav";
	private Clip clip; 

	
	//=======================MOVE PADDLE PARA A DIREITA==================================
	private KeyboardAction moveRight = new KeyboardAction() {
		public void handleEvent(){
			Point posicaoPaddle = paddle.getPosition();
			if(posicaoPaddle.x > Resolution.MSX.width-30){
				paddle.move(0,0);
			}
			else{
				paddle.move(4, 0);
				}
			}
		};//=======FINAL MOVERIGHT
		
	//=======================MOVE PADDLE PARA A ESQUERDA==================================
	private KeyboardAction moveLeft = new KeyboardAction() {
		public void handleEvent(){
			Point posicaoPaddle = paddle.getPosition();
			if(posicaoPaddle.x < 2)
			paddle.move(0,0);
			else{
			paddle.move(-4, 0);	
			}
		}
		};//=======FINAL MOVELEFT
	

	//=============================MÉTODO DRAW==================================
	@Override
	protected void draw(Canvas canvas) {
		
		canvas.clear();
		if(estagioAtual == 0){
			canvas.drawImage(bgEstagioUm, 0, 0);	
		}
		if(estagioAtual == 1){
			canvas.drawImage(bgEstagioDois, 0, 0);
		}
		if(estagioAtual == 2){
			canvas.drawImage(bgEstagioTres, 0, 0);
		}
		gameOver();

		canvas.setForeground(Color.WHITE);
		canvas.putText(2, 2, 10, "SCORE:");
		canvas.putText(42, 2, 10, String.format("%05d", score));
		canvas.putText(85, 2, 10, "RECORD:");
		canvas.putText(132, 2, 10, String.format("%05d", hscore));
		canvas.putText(170, 2, 10, "TENTATIVAS:");
		canvas.putText(240, 2, 10, String.valueOf(vida));
		
		
		bola.draw(canvas);
		paddle.draw(canvas);
		
		if(estagioAtual == 0){
			desenhaBloco(blocoLinhaUm, canvas);
			desenhaBloco(blocoLinhaDois, canvas);
			desenhaBloco(blocoLinhaTres, canvas);
		}
		if(estagioAtual == 1){
			desenhaBloco(blocoLinhaUmEstagioDois, canvas);
			desenhaBloco(blocoLinhaDoisEstagioDois, canvas);
			desenhaBloco(blocoLinhaTresEstagioDois, canvas);
		}
		if(estagioAtual == 2){
			desenhaBloco(blocoLinhaUmEstagioTres, canvas);
			desenhaBloco(blocoLinhaDoisEstagioTres, canvas);
			desenhaBloco(blocoLinhaTresEstagioTres, canvas);
			desenhaBloco(blocoLinhaQuatroEstagioTres, canvas);

		}
		
		
			
	}//=======FINAL DRAW

	//=============================MÉTODO SETUP==================================
	@Override
	protected void setup() {
		
		carregaImagens();
		carregaAudio(cleanStage);
		clip.stop();
		setResolution(Resolution.MSX);
		setFramesPerSecond(100);
		criaBlocos();
		

		bola = new Bola();
		paddle = new Paddle(paddleImage);
		
		bindKeyPressed("LEFT", moveLeft);
		bindKeyPressed("RIGHT", moveRight);
	}//=======FINAL SETUP
	
	
	//=================================MÉTODO LOOP==================================
	
	
	@Override
	protected void loop() {
		
		bola.move();
		paddle.colidiuPaddle(bola);
		bola.colidiuParede(bola);
		
		youLose();

		if(estagioAtual == 0){
			colidiuBloco(blocoLinhaUm);
			colidiuBloco(blocoLinhaDois);
			colidiuBloco(blocoLinhaTres);
		}
		if(estagioAtual == 1){
			colidiuBloco(blocoLinhaUmEstagioDois);
			colidiuBloco(blocoLinhaDoisEstagioDois);
			colidiuBloco(blocoLinhaTresEstagioDois);
		}
		if(estagioAtual == 2){
			colidiuBloco(blocoLinhaUmEstagioTres);
			colidiuBloco(blocoLinhaDoisEstagioTres);
			colidiuBloco(blocoLinhaTresEstagioTres);
			colidiuBloco(blocoLinhaQuatroEstagioTres);

		}

		
		ConcluiEstagio();

		redraw();	
	}//========================================FINAL LOOP
	
	
	
	//=======================CRIACAO DAS TRES LINAHS DE BLOCOS========================
	
	private void criaBlocos(){
		for (int i = 0; i < tamanhoArrayBloco; i++) {
			int x = (i%10)*26+2;		
			
				blocoLinhaUm[i] = new Bloco(Color.RED, 1);
				blocoLinhaUm[i].setPosition(new Point(x, 30));
				blocoLinhaDois[i] = new Bloco(Color.RED, 1);
				blocoLinhaDois[i].setPosition(new Point(x,36));
				blocoLinhaTres[i] = new Bloco(Color.RED, 1);
				blocoLinhaTres[i].setPosition(new Point(x,42));
		
			
				blocoLinhaUmEstagioDois[i] = new Bloco(Color.CYAN, 1);
				blocoLinhaUmEstagioDois[i].setPosition(new Point(x, 30));
				blocoLinhaDoisEstagioDois[i] = new Bloco(Color.DARKGRAY, 2);
				blocoLinhaDoisEstagioDois[i].setPosition(new Point(x,36));
				blocoLinhaTresEstagioDois[i] = new Bloco(Color.CYAN, 1);
				blocoLinhaTresEstagioDois[i].setPosition(new Point(x,42));
		
				blocoLinhaUmEstagioTres[i] = new Bloco(Color.DARKGRAY, 2);
				blocoLinhaUmEstagioTres[i].setPosition(new Point(x, 30));
				blocoLinhaDoisEstagioTres[i] = new Bloco(Color.GREEN, 1);
				blocoLinhaDoisEstagioTres[i].setPosition(new Point(x,36));
				blocoLinhaTresEstagioTres[i] = new Bloco(Color.GREEN, 1);
				blocoLinhaTresEstagioTres[i].setPosition(new Point(x,42));
				blocoLinhaQuatroEstagioTres[i] = new Bloco(Color.DARKGRAY, 2);
				blocoLinhaQuatroEstagioTres[i].setPosition(new Point(x,48));
					
		}
		
	}//=======FINAL CRIABLOCOS
	
	
	//==================VERIFICA SE BOLA COLIDIU COM ALGUM BLOCO======================
	private void colidiuBloco(Bloco[] bloco){
		
		for (int i = 0; i < tamanhoArrayBloco; i++){
			
				if(bloco[i].colidiu(bola)){
					bola.invertVertical();
					bloco[i].clear(Color.GRAY);
					score = score +100;
					carregaAudio(quebraBloco);

					}
				
			}
			if(score > hscore){
			hscore = score;
			}
		}//=======FINAL CLIDIUBLOCO

	//===============================CONCLUIESTAGIO=================================
	//VALIDA OS PONTOS PARA AVANÇAR OS ESTAGIOS
	private void ConcluiEstagio(){
		if(estagioAtual == 0 && score == 3000){
			carregaAudio(cleanStage);
			JOptionPane.showMessageDialog(null, "Parabéns! Você completou o estágio!");
			bola.posicionaBola();
			paddle.posicionaPaddle();
			estagioAtual++;
			clip.stop();
		}
		else if(estagioAtual == 1 && score == 8000){
			carregaAudio(cleanStage);
			JOptionPane.showMessageDialog(null, "Parabéns! Você completou o estágio!");
			bola.posicionaBola();
			paddle.posicionaPaddle();
			estagioAtual++;
			clip.stop();
		}
		else if(estagioAtual == 2 && score == 16000){
			carregaAudio(cleanStage);
			JOptionPane.showMessageDialog(null, "Parabéns! Você completou o jogo!");
			System.exit(0);
		}	
	}//=========================FIM CONCLUIESTAGIO

	
	//=====================GAMEOVER===================================
	//VERIFICA SE NÃO HÁ MAIS VIDA
	private void gameOver(){
		if(vida == 0){
			carregaAudio(gameOver);
			if (JOptionPane.showConfirmDialog(null, "Deseja reiniciar?", "GAME OVER",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				bola.posicionaBola();
				clip.stop();
				paddle.posicionaPaddle();
				estagioAtual = 0;
				score = 0;
				vida = 3;
				criaBlocos();			
			} else {
				System.exit(0);
			}
		}
	}

	//=================================YOULOSE=========================================
	//VERIFICA SE BOLA FOI PARA O LIMITE DO CHÃO E VALIDA AS VIDAS RESTANTES
	private void youLose(){
		Point posicaoBola = bola.getPosition();
		if(posicaoBola.y >= Resolution.MSX.height-5){
			carregaAudio(die);
			bola.setDx(0);
			bola.setDy(0);
			vida = vida-1;
			bola.posicionaBola();
			paddle.posicionaPaddle();
		}
			
	}
	//================================DESENHABLOCO======================================
	//====================MÉTODO PARA DESENHAR OS BLOCOS CHAMADO NO DRAW================
	public void desenhaBloco(Bloco[] bloco, Canvas canvas){
			for(int i = 0; i < tamanhoArrayBloco; i++){
				bloco[i].draw(canvas);
		}
	}	
	
	//=====================>>>>>>>CARREGAAUDIO<<<<<<===============================
	//===========================CARREGA O AUDIO PASSANDO POR PARAMETRO UMA STRING
	private void carregaAudio(String audioPath){
			try {
				soundFile = new File(audioPath);
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	//==========================CARREGA IMAGENS=======================================
	
		private void carregaImagens(){
			try {
				paddleImage = new Image ("Imagens/paddle.jpg");
				paddleImage.resize(30, 6);
				bgEstagioUm = new Image("Imagens/bgEstagio1.jpg");
				bgEstagioUm.resize(Resolution.MSX.width,Resolution.MSX.height);
				bgEstagioDois = new Image("Imagens/bgEstagio2.jpg");
				bgEstagioDois.resize(Resolution.MSX.width,Resolution.MSX.height);
				bgEstagioTres = new Image("Imagens/bgEstagio3.jpg");
				bgEstagioTres.resize(Resolution.MSX.width,Resolution.MSX.height);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}//=======FINAL ARKANOID




