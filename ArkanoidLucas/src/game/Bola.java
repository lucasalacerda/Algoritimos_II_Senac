package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bola extends Sprite {
	private double dy = 0.8;
	private double dx = 0.8;

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	
	public Bola() {
		super(5,5,Color.WHITE);
			setPosition(
					Resolution.MSX.width/2-5,
					Resolution.MSX.height-11
					);
		
	}

	public void move() {
		super.move(dx, dy);
	}

	public void invertHorizontal() {
		dx *= -1;
	}

	public void invertVertical() {
		dy *= -1;
	}
	
	//==============VERIFICA SE A BOLA BATEU NA PAREDE==================
	public void colidiuParede(Bola bola) {
		Point posicao = bola.getPosition();
		if (posicao.x < 0 || posicao.x >= Resolution.MSX.width-5){
			bola.invertHorizontal();
		}
		if (posicao.y < 0 || posicao.y >= Resolution.MSX.height-5)
			bola.invertVertical();
		
		}

	public void posicionaBola() {
		super.setPosition(
				Resolution.MSX.width/2-5,
				Resolution.MSX.height-11
				);
		dx = 0.8;
		dy = 0.8;
	}
	
}
