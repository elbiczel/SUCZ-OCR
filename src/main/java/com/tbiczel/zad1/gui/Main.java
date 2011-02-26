package com.tbiczel.zad1.gui;

import javax.swing.SwingUtilities;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out
					.println("Program takes exactly one argument with file name");
		}
		final ImageViewer iv = new SwingImageViewer("TextRecognizer");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				iv.show();
			}
		});
	}

}
