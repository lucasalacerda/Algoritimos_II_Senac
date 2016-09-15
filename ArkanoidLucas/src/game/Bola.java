package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bola extends Sprite {
	private double dy = 1.0;
	private double dx = 1.0;
	
	public Bola() {
		super(5,5,Color.BLACK);
			setPosition(
					Resolution.MSX.width/2-5,
					Resolution.MSX.height-10
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
	
}
