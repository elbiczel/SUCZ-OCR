package com.tbiczel.zad1.processing;

import java.awt.image.BufferedImage;

public class ImageUtils {

	private BufferedImage img;

	public ImageUtils(BufferedImage img) {
		this.img = img;
	}

	public int getLineDarkness(int i) {
		int darkness = 0;
		for (int j = 0; j < img.getWidth(); j++) {
			darkness += getPixelDarkness(i, j);
		}
		return darkness / img.getWidth();
	}

	public int getPixelDarkness(int row, int col) {
		int colour = img.getRGB(col, row);
		return (colour & (0x000000ff)) + ((colour & (0x0000ff00)) >> 8)
				+ ((colour & (0x00ff0000)) >> 16);
	}

}
