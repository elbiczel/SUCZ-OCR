package com.tbiczel.zad1.processing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.tbiczel.zad1.io.ClassifierBuilder;

public class HorizontalLineSelector extends LineSelector {

	public HorizontalLineSelector(BufferedImage img, LinesData<Double> lines,
			int lineHeight, ClassifierBuilder rowsClassifier) {
		super(img, lines, lineHeight, rowsClassifier);
	}

	@Override
	protected Rectangle createLine() {
		return new Rectangle(new Point(0, begin), new Dimension(img.getWidth(),
				height + 1));
	}

	@Override
	protected int getLinesNo() {
		return img.getHeight();
	}

}
