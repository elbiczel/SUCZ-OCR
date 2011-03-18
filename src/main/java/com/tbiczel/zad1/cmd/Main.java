package com.tbiczel.zad1.cmd;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.tbiczel.zad1.Constants;
import com.tbiczel.zad1.io.ClassifierBuilder;
import com.tbiczel.zad1.processing.ImageProcessor;

public abstract class Main {

	private ClassifierBuilder rowsClassifier = null;
	private ClassifierBuilder columnsClassifier = null;
	private File[] files;
	protected ImageProcessor processor;
	protected BufferedImage img;

	public Main(String[] fileNames) throws Exception {
		rowsClassifier = new ClassifierBuilder(new File(
				Constants.ROWS_ATTRIBUTES_FILE));
		columnsClassifier = new ClassifierBuilder(new File(
				Constants.COLUMNS_ATTRIBUTES_FILE));
		files = new File[fileNames.length];
		int i = 0;
		for (String fileName : fileNames) {
			files[i++] = new File(fileName);
		}
	}

	public void process() throws IOException, Exception {
		for (File imgFile : files) {
			img = ImageIO.read(imgFile);
			processor = new ImageProcessor(img, Constants.BLACK_THREASHOLD,
					Constants.LINES_AROUND, rowsClassifier, columnsClassifier);
			processSingleImage(imgFile);
		}
	}

	protected abstract void processSingleImage(File imgFile) throws Exception;
}
