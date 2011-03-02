package com.tbiczel.zad1.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 7279307541489873546L;
	private BufferedImage img = null;
	private Rectangle rect = null;

	public ImagePanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(400, 400));
	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
		if (rect != null) {
			Graphics2D graph = (Graphics2D) g;
			graph.setPaint(Color.BLUE);
			graph.draw(rect);
		}
	}

	public void setImg(BufferedImage img) {
		this.img = img;
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
	}

	public BufferedImage getImg() {
		return img;
	}

	public void earseSelection() {
		rect = null;
		repaint();
	}

	public void showSelected(int x, int y, int w, int h) {
		rect = new Rectangle(new Point(x, y), new Dimension(w, h));
		repaint();
	}

}
