package com.tbiczel.zad1.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SwingImageViewer implements ImageViewer {

	private JFrame frame;

	public SwingImageViewer(String title) {
		// Create and set up the window.
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add the ubiquitous "Hello World" label.
		JLabel label = new JLabel("Hello World");
		frame.getContentPane().add(label);

		// Display the window.
		frame.pack();
	}

	public void show() {
		frame.setVisible(true);
	}

	public void setImage(String path) {
		// TODO Auto-generated method stub

	}

}
