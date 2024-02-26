package pong;
import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle{
	
	int player;
	int yVelocity;
	int speed = 10;
	
	Paddle(int xPOS, int yPOS, int PADDLE_WIDTH, int PADDLE_HEIGHT, int player) {
		super(xPOS, yPOS, PADDLE_WIDTH, PADDLE_HEIGHT);
		this.player = player;
	}//constructor
	
	public void keyPressed(KeyEvent e) {
		switch(player) {
		case 1:
			if(e.getKeyCode()==KeyEvent.VK_W) {
				setYDirection(-speed);
				move();
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				setYDirection(speed);
				move();
			}
			break;
		case 2:
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				setYDirection(-speed);
				move();
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				setYDirection(speed);
				move();
			}
			break;
		}
	}//keyPressed "e"
	
	public void keyRelease(KeyEvent e) {
		switch(player) {
		case 1:
			if(e.getKeyCode()==KeyEvent.VK_W) {
				setYDirection(0);
				move();
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				setYDirection(0);
				move();
			}
			break;
		case 2:
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				setYDirection(0);
				move();
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				setYDirection(0);
				move();
			}
			break;
		}
	}//keyReleased "e"
	
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}//setYDirection
	
	public void move() {
		y = y + yVelocity;
	}//move
	
	public void draw(Graphics g) {
		if(player == 1)
			g.setColor(Color.blue);
		else
			g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}//draw
}
