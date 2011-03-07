package com.tbiczel.zad1.processing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageProcessor {

	private BufferedImage img;

	private LineSelector lineSelect;

	public ImageProcessor(BufferedImage img, int min, int max) {
		this.img = img;
		lineSelect = new LineSelector(img, min, max);
	}

	public BufferedImage process() {
		for (BufferedImage line : lineSelect.getLines()) {
			Graphics2D g = line.createGraphics();
			g.setPaint(Color.RED);
			g.drawRect(0, 0, line.getWidth() - 1, line.getHeight() - 1);
		}
		img.flush();
		return img;
	}

}
