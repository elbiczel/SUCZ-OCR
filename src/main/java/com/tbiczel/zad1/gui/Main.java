package com.tbiczel.zad1.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.tbiczel.zad1.controller.ImageController;

public class Main {

	private static JFrame frame;
	private static ImageController ic;

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
		ic = new ImageController("jTextRecognizer");
		frame.getContentPane().add(ic.getMain());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

}
