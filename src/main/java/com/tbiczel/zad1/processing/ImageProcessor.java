package com.tbiczel.zad1.processing;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ImageProcessor {

	private LineSelector horizontalLineSelect;

	private LineSelector verticalLineSelect;

	private CharSelector charSelect;

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
		this.blackThreashold = blackThreashold;
		prepareVerticalData(img);
		prepareHorizontalData(img);
		horizontalLineSelect = new HorizontalLineSelector(img, horizontalLines,
				lineHeight);
		verticalLineSelect = new VerticalLineSelector(img, verticalLines,
				lineHeight);
		charSelect = new CharSelector(blackThreashold, img);
	}

	public ArrayList<Rectangle> process(File rowsFile, File columnsFile)
			throws Exception {
		ArrayList<Rectangle> rows = horizontalLineSelect.getLines(rowsFile);
		ArrayList<Rectangle> columns = verticalLineSelect.getLines(columnsFile);
		ArrayList<Rectangle> textAreas = new ArrayList<Rectangle>(rows.size()
				* columns.size());
		for (Rectangle row : rows) {
			for (Rectangle column : columns) {
				textAreas.add(row.intersection(column));
			}
		}
		ArrayList<Rectangle> result = new ArrayList<Rectangle>(rows.size()
				* columns.size() * 40);
		for (Rectangle textArea : textAreas) {
			result.addAll(charSelect.process(textArea));
		}
		return result;
	}

	public LinesData<Double> getHorizontalLines() {
		return horizontalLines;
	}

	public LinesData<Double> getVerticalLines() {
		return verticalLines;
	}

}
