package com.tbiczel.zad1.controller;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.tbiczel.zad1.gui.ImagePanel;

public class ImageController {

	public ImagePanel read(File file) {
		try {
			ImagePanel image = new ImagePanel();
			image.setImg(ImageIO.read(file));
			return image;
		} catch (IOException e) {
			return null;
		}
	}

}
