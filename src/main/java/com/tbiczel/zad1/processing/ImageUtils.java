package com.tbiczel.zad1.processing;

import java.awt.image.BufferedImage;

public class ImageUtils {

	private BufferedImage img;
	
	private int threshold;

	public ImageUtils(BufferedImage img, int threshold) {
		this.img = img;
		this.threshold = threshold;
	}

	// number of black pixels
	public int getLineDarkness(int i) {
		int darkness = 0;
		for (int j = 0; j < img.getWidth(); j++) {
			darkness += blackOrWhite(getPixelDarkness(i, j));
		}
		return darkness;
	}

	public int getPixelDarkness(int row, int col) {
		int colour = img.getRGB(col, row);
		return (colour & (0x000000ff)) + ((colour & (0x0000ff00)) >> 8)
				+ ((colour & (0x00ff0000)) >> 16);
	}
	
	// black is 1
	public int blackOrWhite(int darkness) {
		if (darkness < threshold) {
			return 1;
		}
		return 0;
	}

}
