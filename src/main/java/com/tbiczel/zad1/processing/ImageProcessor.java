package com.tbiczel.zad1.processing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ImageProcessor {

	private BufferedImage img;

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
		this.img = img;
		this.blackThreashold = blackThreashold;
		prepareVerticalData(img);
		prepareHorizontalData(img);
		horizontalLineSelect = new HorizontalLineSelector(img, horizontalLines,
				lineHeight);
		verticalLineSelect = new VerticalLineSelector(img, verticalLines,
				lineHeight);
		charSelect = new CharSelector(blackThreashold);
	}

	public BufferedImage process(File rowsFile, File columnsFile)
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
		Graphics2D g = img.createGraphics();
		for (Rectangle textArea : textAreas) {
			g.setPaint(Color.GREEN);
			BufferedImage textAreaImg = img.getSubimage(textArea.x, textArea.y,
					textArea.width, textArea.height);
			for (Rectangle character : charSelect.process(textAreaImg)) {
				g.draw(character);
			}
			g.setPaint(Color.RED);
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
