package pong;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{
	
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (5.0/9));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 20;
	static final int PADDLE_HEIGHT = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	AIPaddle paddle3; 
	Ball ball;
	Score score;
	int gameMode;
	
	GamePanel(int x) {
		gameMode = x;
		newBall();
		newPaddles();
		score = new Score(GAME_WIDTH,GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new ActionListener());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this);
		gameThread.start();
	}//Constructor
	
	/**
	 * Creates a new ball with a random y value at start
	 */
	public void newBall() {
		random = new Random();
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2), random.nextInt(GAME_HEIGHT-BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
	}//newBall
	
	/**
	 * Creates two paddles at start position
	 * if gameMode==1, generates AI paddle else creates player 1 paddle (left)
	 */
	public void newPaddles() {
		if (gameMode == 1)
			paddle3 = new AIPaddle(0, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1, ball);
		else
			paddle1 = new Paddle(0, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
	}//NewPaddles
	
	/**
	 * Adds the game windows onto the screen
	 */
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}//paint
	
	/**
	 * Calls the draw method for the paddles, ball, and score
	 */
	public void draw(Graphics g) {
		if (gameMode == 1)
			paddle3.draw(g);
		else 
			paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
	}//draw
	
	/**
	 * Calls the move method for the paddles and ball
	 */
	public void move() {
		if (gameMode == 1)
			paddle3.move();
		else 
			paddle1.move();
		paddle2.move();
		ball.move();
	}//move
	
	/**
	 * Checks for ball to paddle collision and ball to edge of screen collision
	 */
	public void checkCollision() {
		//bounce ball off top and bottom edges
		if(ball.y <= 0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}
		
		if (gameMode == 1) {       //bounces ball off paddle3 ("AI")
			if(ball.intersects(paddle3)) {
				ball.xVelocity = Math.abs(ball.xVelocity);
				ball.xVelocity++;
				if(ball.yVelocity > 0)
					ball.yVelocity++;
				else
					ball.yVelocity--;
				ball.setXDirection(ball.xVelocity);
				ball.setYDirection(ball.yVelocity);
			}
		}
		else {           //bounces ball off paddle1
			if(ball.intersects(paddle1)) {
				ball.xVelocity = Math.abs(ball.xVelocity);
				ball.xVelocity++;
				if(ball.yVelocity > 0)
					ball.yVelocity++;
				else
					ball.yVelocity--;
				ball.setXDirection(ball.xVelocity);
				ball.setYDirection(ball.yVelocity);
			}
		}

		//bounces ball off paddle2
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			if(ball.yVelocity > 0)
				ball.yVelocity++;
			else
				ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		//prevents paddles from moving off-screen
		if (gameMode == 1) {
			if(paddle3.y <= 0)
				paddle3.y = 0;
			if(paddle3.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
				paddle3.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}
		else {
			if(paddle1.y <= 0)
				paddle1.y = 0;
			if(paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
				paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}

		if(paddle2.y <= 0)
			paddle2.y = 0;
		if(paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
			paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
		
		//Checks if player scores
		if(ball.x <= 0) {
			score.player2++;
			newBall();
			newPaddles();
		}
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			score.player1++;
			newBall();
			newPaddles();
		}
	}//checkCollision
	
	/**
	 * Contains the game loop to run at 60 fps
	 */
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}//run
	
	
	public class ActionListener extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			if (gameMode == 2)
				paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}//keyPressed "e"
		
		public void keyReleased(KeyEvent e) {
			if (gameMode == 2)
				paddle1.keyRelease(e);
			paddle2.keyRelease(e);
		}//keyRelease "e"
		
	}//ActionListener
}
