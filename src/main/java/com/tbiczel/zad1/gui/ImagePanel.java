package com.tbiczel.zad1.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 7279307541489873546L;
	private BufferedImage img = null;

	public ImagePanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(400, 400));
	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public void setImg(BufferedImage img) {
		this.img = img;
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
	}

	public BufferedImage getImg() {
		return img;
	}

}
