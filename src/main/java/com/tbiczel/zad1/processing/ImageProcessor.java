package com.tbiczel.zad1.processing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageProcessor {

	private BufferedImage img;

	private LineSelector horizontalLineSelect;

	private LineSelector verticalLineSelect;

	private LinesData<Integer> horizontalLines;

	private LinesData<Integer> verticalLines;

	private int blackThreashold;

	private void prepareVerticalData(BufferedImage img) {
		verticalLines = new LinesData<Integer>(0);
		ImageUtils utils = new ImageUtils(img, blackThreashold);
		for (int i = 0; i < img.getWidth(); i++) {
			verticalLines.putLineData(i, utils.getColumnDarkness(i));
		}
	}

	private void prepareHorizontalData(BufferedImage img) {
		horizontalLines = new LinesData<Integer>(0);
		ImageUtils utils = new ImageUtils(img, blackThreashold);
		for (int i = 0; i < img.getHeight(); i++) {
			horizontalLines.putLineData(i, utils.getRowDarkness(i));
		}
	}

	public ImageProcessor(BufferedImage img, int blackThreashold, int lineHeight) {
		this.img = img;
		this.blackThreashold = blackThreashold;
		prepareVerticalData(img);
		prepareHorizontalData(img);
		horizontalLineSelect = new HorizontalLineSelector(img, horizontalLines,
				lineHeight);
		verticalLineSelect = new VerticalLineSelector(img, verticalLines,
				lineHeight);
	}

	public BufferedImage process(File rowsFile, File columnsFile)
			throws Exception {

		for (BufferedImage line : horizontalLineSelect.getLines(rowsFile)) {
			Graphics2D g = line.createGraphics();
			g.setPaint(Color.RED);
			g.drawRect(0, 0, line.getWidth() - 1, line.getHeight() - 1);
		}
		for (BufferedImage line : verticalLineSelect.getLines(columnsFile)) {
			Graphics2D g = line.createGraphics();
			g.setPaint(Color.CYAN);
			g.drawRect(0, 0, line.getWidth() - 1, line.getHeight() - 1);
		}
		img.flush();
		return img;
	}

	public LinesData<Integer> getHorizontalLines() {
		return horizontalLines;
	}

	public LinesData<Integer> getVerticalLines() {
		return verticalLines;
	}

}
