package com.tbiczel.zad1.processing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.tbiczel.zad1.io.ClassifierBuilder;

public class VerticalLineSelector extends LineSelector {

	public VerticalLineSelector(BufferedImage img, LinesData<Double> lines,
			int lineHeight, ClassifierBuilder columnsClassifier) throws Exception {
		super(img, lines, lineHeight, columnsClassifier);
	}

	@Override
	protected Rectangle createLine() {
		return new Rectangle(new Point(begin, 0), new Dimension(height + 1,
				img.getHeight()));
	}

	@Override
	protected int getLinesNo() {
		return img.getWidth();
	}
}
