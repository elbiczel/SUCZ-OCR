package com.tbiczel.zad1.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {

	private static JFrame frame;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndDisplayGUI();
			}
		});
	}

	protected static void createAndDisplayGUI() {
		// Create and set up the window.
		frame = new JFrame("jTextRecognizer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add the ubiquitous "Hello World" label.
		JPanel mainPanel = new MainPanel("jTextRecognizer");
		frame.getContentPane().add(mainPanel);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

}
