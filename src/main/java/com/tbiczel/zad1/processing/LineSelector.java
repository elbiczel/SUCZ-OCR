package com.tbiczel.zad1.processing;

import java.awt.image.BufferedImage;
import java.util.Vector;

public class LineSelector {

	private BufferedImage img;
	private ImageUtils utils;

	public LineSelector(BufferedImage img, int min, int max, ImageUtils utils) {
		this.img = img;
		this.utils = utils;
		this.min = min;
		this.max = max;
	}

	private int begin = -1;
	private int height = 0;

	private int min;
	private int max;

	public Vector<BufferedImage> getLines() {
		Vector<BufferedImage> result = new Vector<BufferedImage>();
		resetLine();
		for (int i = 0; i < img.getHeight(); i++) {
			if (isLine(i)) {
				expandLine(i);
			} else if (insideLine()) {
				result.add(createLine());
				resetLine();
			}
		}
		if (insideLine()) {
			result.add(createLine());
		}
		return result;
	}

	private void expandLine(int line) {
		if (!insideLine()) {
			begin = line;
		} else {
			height++;
		}
	}

	private boolean insideLine() {
		return begin != -1;
	}

	private void resetLine() {
		begin = -1;
		height = 0;
	}

	private BufferedImage createLine() {
		return img.getSubimage(0, begin, img.getWidth(), height + 1);
	}

	private boolean isLine(int i) {
		int darkness = utils.getLineDarkness(i);
		return min < darkness && darkness < max;
	}
}
