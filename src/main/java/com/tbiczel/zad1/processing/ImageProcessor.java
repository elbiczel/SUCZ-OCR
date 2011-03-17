package com.tbiczel.zad1.processing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

public class ImageProcessor {

	private BufferedImage img;

	private LineSelector horizontalLineSelect;

	private LineSelector verticalLineSelect;

	private LinesData<Double> horizontalLines;

	private LinesData<Double> verticalLines;

	private double blackThreashold;

	private void prepareVerticalData(BufferedImage img) {
		verticalLines = new LinesData<Double>(0.);
		ImageUtils utils = new ImageUtils(img, blackThreashold);
		for (int i = 0; i < img.getWidth(); i++) {
			verticalLines.putLineData(i, utils.getColumnDarkness(i));
		}
	}

	private void prepareHorizontalData(BufferedImage img) {
		horizontalLines = new LinesData<Double>(0.);
		ImageUtils utils = new ImageUtils(img, blackThreashold);
		for (int i = 0; i < img.getHeight(); i++) {
			horizontalLines.putLineData(i, utils.getRowDarkness(i));
		}
	}

	public ImageProcessor(BufferedImage img, double blackThreashold,
			int lineHeight) {
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
		Vector<Rectangle> rows = horizontalLineSelect.getLines(rowsFile);
		Vector<Rectangle> columns = verticalLineSelect.getLines(columnsFile);
		Vector<Rectangle> textAreas = new Vector<Rectangle>(rows.size()
				* columns.size());
		for (Rectangle row : rows) {
			for (Rectangle column : columns) {
				textAreas.add(row.intersection(column));
			}
		}
		Graphics2D g = img.createGraphics();
		g.setPaint(Color.GREEN);
		for (Rectangle textArea : textAreas) {
			g.draw(textArea);
		}
		img.flush();
		return img;
	}

	public LinesData<Double> getHorizontalLines() {
		return horizontalLines;
	}

	public LinesData<Double> getVerticalLines() {
		return verticalLines;
	}

}
