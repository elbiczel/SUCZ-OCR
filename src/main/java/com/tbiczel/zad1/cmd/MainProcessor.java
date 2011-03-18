package com.tbiczel.zad1.cmd;

import java.io.File;

import javax.imageio.ImageIO;

import com.tbiczel.zad1.Constants;

public class MainProcessor extends Main {

	private static Main main;

	public MainProcessor(String[] fileNames) throws Exception {
		super(fileNames);
	}

	public static void main(String[] args) throws Exception {
		main = new MainProcessor(args);
		main.process();
	}

	@Override
	protected void processSingleImage(File imgFile) throws Exception {
		processor.drawRectangles(img, processor.process());
		imgFile.delete();
		imgFile.createNewFile();
		ImageIO.write(img, Constants.EXTENSION, imgFile);
	}

}
