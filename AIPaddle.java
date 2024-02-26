package pong;
import java.awt.*;


public class AIPaddle extends Rectangle {

	int player;
	int yVelocity;
	int speed = 10;
	Ball b1;
	public AIPaddle(int xPOS, int yPOS, int PADDLE_WIDTH, int PADDLE_HEIGHT, int player, Ball b) {
		super(xPOS, yPOS, PADDLE_WIDTH, PADDLE_HEIGHT);
		this.player = player;
		b1 = b;
	}//constructor
	
	public void move() {
		y = (int)(b1.getY() - 40);
	}//move
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
	}//draw
}



