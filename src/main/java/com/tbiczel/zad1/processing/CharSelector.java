package com.tbiczel.zad1.processing;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CharSelector {

	private double blackThreashold;
	private ImageUtils utils;
	private boolean[][] visited;
	private BufferedImage root;
	private Rectangle textArea;
	private int last;
	private int min_x;
	private int min_y;
	private int max_x;
	private int max_y;

	private void reset_rectangle() {
		min_x = Integer.MAX_VALUE;
		min_y = Integer.MAX_VALUE;
		max_x = Integer.MIN_VALUE;
		max_y = Integer.MIN_VALUE;
	}

	private void update_rectangle(int i, int j) {
		min_x = Math.min(min_x, i);
		max_x = Math.max(max_x, i);
		min_y = Math.min(min_y, j);
		max_y = Math.max(max_y, j);
	}

	private void addRectangle(ArrayList<Rectangle> chars) {
		chars.add(new Rectangle(min_x + textArea.x, min_y + textArea.y, max_x
				- min_x, max_y - min_y));
	}

	public CharSelector(double blackThreashold, BufferedImage root) {
		this.blackThreashold = blackThreashold;
		this.root = root;
	}

	public ArrayList<Rectangle> process(Rectangle textArea) {
		reset_rectangle();
		this.textArea = textArea;
		utils = new ImageUtils(root.getSubimage(textArea.x, textArea.y,
				textArea.width, textArea.height), blackThreashold);
		last = 1;
		ArrayList<Rectangle> chars = new ArrayList<Rectangle>();
		visited = new boolean[textArea.width][textArea.height];
		for (int i = 0; i < textArea.getWidth(); i++) {
			for (int j = 0; j < textArea.getHeight(); j++) {
				visited[i][j] = false;
			}
		}
		for (int i = 0; i < textArea.getWidth(); i++) {
			for (int j = 0; j < textArea.getHeight(); j++) {
				if (visit(i, j) > 5) {
					last++;
					addRectangle(chars);
				}
				reset_rectangle();
			}
		}
		System.err.println(last);
		return chars;
	}

	private int visit(int i, int j) {
		if (i < 0 || i >= textArea.width || j < 0 || j >= textArea.height) {
			return 0;
		}
		if (visited[i][j]) {
			return 0;
		}
		visited[i][j] = true;
		double darkness = utils.getPixelDarkness(j, i);
		if (utils.blackOrWhite(darkness) == 0) {
			return 0;
		}
		int sum = 1;
		update_rectangle(i, j);
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

}
