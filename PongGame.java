package pong;

import javax.swing.*;


public class PongGame {

	public static void main(String[] args) {
		
		int gameMode = 1;
		try {
			gameMode = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter a number: (1 - solo, 2 - 2 player)(Default is solo"));
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Not a number!");
		}
		
		GameFrame frame = new GameFrame(gameMode);

	}//main

}
