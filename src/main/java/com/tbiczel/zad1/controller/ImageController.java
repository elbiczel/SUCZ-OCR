package com.tbiczel.zad1.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

import com.tbiczel.zad1.gui.ImagePanel;
import com.tbiczel.zad1.gui.MainPanel;
import com.tbiczel.zad1.processing.ImageProcessor;
import com.tbiczel.zad1.processing.ImageUtils;

public class ImageController {

	private ImagePanel panel;

	private BufferedImage image;

	private File safeCopy;

	private SwingWorker<ImagePanel, Void> worker = null;

	private ImageUtils utils;

	private int min = 370;

	private int max = 255 + 255 + 140;

	public ImagePanel readImage(File file, MainPanel resultContainer) {
		try {
			panel = new ImagePanel();
			image = ImageIO.read(file);
			safeCopy = file;
			panel.setImg(image);
			changeImage(resultContainer);
			return panel;
		} catch (IOException e) {
			return null;
		}
	}

	public void processImage(final MainPanel resultContainer) {
		worker = new SwingWorker<ImagePanel, Void>() {

			private ImageProcessor proc;

			@Override
			protected ImagePanel doInBackground() throws Exception {
				BufferedImage img = ImageIO.read(safeCopy);
				proc = new ImageProcessor(img, min, max);
				img = proc.process();
				ImagePanel panel = new ImagePanel();
				panel.setImg(img);
				return panel;
			}

			@Override
			protected void done() {
				try {
					ImagePanel panel = get();
					ImageController.this.panel = panel;
					ImageController.this.image = panel.getImg();
					ImageController.this.changeImage(resultContainer);
				} catch (Exception ignore) {
					System.err.println(ignore.getLocalizedMessage());
				}
			}

		};
		worker.execute();
	}

	public void cancelProcessing() {
		if (worker != null) {
			worker.cancel(true);
		}
	}

	public int getLineDarkness(int row) {
		return utils.getLineDarkness(row);
	}

	public int getPixelDarkness(int row, int col) {
		return utils.getPixelDarkness(row, col);
	}

	private void changeImage(MainPanel resultContainer) {
		utils = new ImageUtils(image);
		resultContainer.removeImage();
		resultContainer.setImage(panel);
		resultContainer.revalidate();
		resultContainer.repaint();
	}

}
