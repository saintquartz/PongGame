package pong;
import java.awt.*;

import javax.swing.*;

public class GameFrame extends JFrame {
	
	GamePanel panel;
	GameFrame(int gameMode) {
		
		panel = new GamePanel(gameMode);
		this.add(panel);
		this.setTitle("Pong");
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}//Constructor
}
