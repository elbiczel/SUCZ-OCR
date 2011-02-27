package com.tbiczel.zad1.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.tbiczel.zad1.gui.ImagePanel;

public class ImageController {
	
	private ImagePanel panel;
	
	private BufferedImage image;

	public ImagePanel read(File file) {
		try {
			panel = new ImagePanel();
			image = ImageIO.read(file);
			panel.setImg(image);
			return panel;
		} catch (IOException e) {
			return null;
		}
	}

}
