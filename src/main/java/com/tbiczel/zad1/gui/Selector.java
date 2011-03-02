package com.tbiczel.zad1.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.tbiczel.zad1.controller.ImageController;
import com.tbiczel.zad1.processing.ImageUtils;

public class Selector implements MouseListener, MouseMotionListener {

	private int x1, y1, x2, y2;
	private boolean pressed = false;

	private ImageController ic;
	private ImagePanel panel;
	private ImageUtils utils;

	public Selector(ImageController imageController, ImagePanel panel,
			ImageUtils utils) {
		ic = imageController;
		this.panel = panel;
		this.utils = utils;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (pressed) {
			changeSelection(e, false);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (pressed) {
			changeSelection(e, false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		panel.earseSelection();
		System.out.println("Mouse: " + e.getY() + ", " + e.getX());
		System.out.println("LineDarkness: " + utils.getLineDarkness(e.getY()));
		System.out.println("PixelDarkness: "
				+ utils.getPixelDarkness(e.getY(), e.getX()));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		panel.earseSelection();
		pressed = true;
		x1 = e.getX();
		y1 = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		changeSelection(e, true);
		pressed = false;
	}

	private void changeSelection(MouseEvent e, boolean selectRegion) {
		panel.earseSelection();
		x2 = e.getX();
		y2 = e.getY();
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		int w = Math.max(x1, x2) - Math.min(x1, x2);
		int h = Math.max(y1, y2) - Math.min(y1, y2);
		if (selectRegion && (w != 0) && (h != 0)) {
			ic.setSelectedRegion(x, y, w, h);
		}
		panel.showSelected(x, y, w, h);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

}
