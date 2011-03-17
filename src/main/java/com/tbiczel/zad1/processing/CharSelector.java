package com.tbiczel.zad1.processing;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CharSelector {

	private double blackThreashold;
	private ImageUtils utils;
	int[][] consistent;
	BufferedImage img;
	int last;

	public CharSelector(double blackThreashold) {
		this.blackThreashold = blackThreashold;
	}

	public ArrayList<Rectangle> process(BufferedImage textArea) {
		img = textArea;
		utils = new ImageUtils(textArea, blackThreashold);
		last = 1;
		ArrayList<Rectangle> chars = new ArrayList<Rectangle>();
		consistent = new int[img.getWidth()][img.getHeight()];
		for (int i = 0; i < textArea.getWidth(); i++) {
			for (int j = 0; j < textArea.getHeight(); j++) {
				consistent[i][j] = -1;
			}
		}
		for (int i = 0; i < textArea.getWidth(); i++) {
			for (int j = 0; j < textArea.getHeight(); j++) {
				if (visit(i, j) > 5) {
					last++;
				} else {
					visit_and_earse(i, j);
				}
			}
		}
		System.err.println(last);
		return chars;
	}

	private int visit(int i, int j) {
		if (i < 0 || i >= img.getWidth() || j < 0 || j >= img.getHeight()) {
			return 0;
		}
		if (consistent[i][j] != -1) {
			return 0;
		}
		if (utils.blackOrWhite(utils.getPixelDarkness(j, i)) == 0) {
			consistent[i][j] = 0;
			return 0;
		}
		int sum = 1;
		consistent[i][j] = last;
		sum += visit(i - 1, j - 1);
		sum += visit(i, j - 1);
		sum += visit(i + 1, j - 1);
		sum += visit(i - 1, j);
		sum += visit(i + 1, j);
		sum += visit(i - 1, j + 1);
		sum += visit(i, j + 1);
		sum += visit(i, j + 1);
		return sum;
	}

	private void visit_and_earse(int i, int j) {
		if (i < 0 || i >= img.getWidth() || j < 0 || j >= img.getHeight()) {
			return;
		}
		if (consistent[i][j] != last) {
			return;
		}
		consistent[i][j] = 0;
		visit_and_earse(i - 1, j - 1);
		visit_and_earse(i, j - 1);
		visit_and_earse(i + 1, j - 1);
		visit_and_earse(i - 1, j);
		visit_and_earse(i + 1, j);
		visit_and_earse(i - 1, j + 1);
		visit_and_earse(i, j + 1);
		visit_and_earse(i, j + 1);
	}

}
