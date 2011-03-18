package com.tbiczel.zad1.cmd;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.tbiczel.zad1.Constants;

public class MainSplitter extends Main {
	private static Main main;

	public MainSplitter(String[] fileNames) throws Exception {
		super(fileNames);
	}

	public static void main(String[] args) throws Exception {
		main = new MainProcessor(args);
		main.process();
	}

	@Override
	protected void processSingleImage(File imgFile) throws Exception {
		ArrayList<Rectangle> parts = processor.process();
		for (Rectangle part : parts) {
			BufferedImage subImage = img.getSubimage(part.x, part.y,
					part.width, part.height);
			ImageIO.write(subImage, Constants.EXTENSION, new File(
					getOutputName(part.x, part.y, imgFile.getPath())));
		}
	}

	private String getOutputName(int x, int y, String oldPath) {
		int length = oldPath.lastIndexOf(".");
		StringBuilder sb = new StringBuilder(oldPath.substring(0, length));
		return sb.append('_').append(x).append('_').append(y).append('.')
				.append(Constants.EXTENSION).toString();
	}
}
