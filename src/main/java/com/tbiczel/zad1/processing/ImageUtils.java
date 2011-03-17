package com.tbiczel.zad1.processing;

import java.awt.image.BufferedImage;

public class ImageUtils {

	private BufferedImage img;

	private double threshold;

	public ImageUtils(BufferedImage img, double threshold) {
		this.img = img;
		this.threshold = threshold;
	}

	// number of black pixels
	public double getRowDarkness(int i) {
		int darkness = 0;
		for (int j = 0; j < img.getWidth(); j++) {
			darkness += blackOrWhite(getPixelDarkness(i, j));
		}
		return (1.0 * darkness) / img.getWidth();
	}

	// number of black pixels
	public double getColumnDarkness(int i) {
		int darkness = 0;
		for (int j = 0; j < img.getHeight(); j++) {
			darkness += blackOrWhite(getPixelDarkness(j, i));
		}
		return (1.0 * darkness) / img.getWidth();
	}

	public double getPixelDarkness(int row, int col) {
		int colour = img.getRGB(col, row);
		return ((colour & (0x000000ff)) + ((colour & (0x0000ff00)) >> 8) + ((colour & (0x00ff0000)) >> 16))
				/ (1.0 * 765);
	}

	// black is 1
	public int blackOrWhite(double darkness) {
		if (darkness < threshold) {
			return 1;
		}
		return 0;
	}

}
