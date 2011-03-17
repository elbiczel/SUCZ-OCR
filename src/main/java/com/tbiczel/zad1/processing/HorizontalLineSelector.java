package com.tbiczel.zad1.processing;

import java.awt.image.BufferedImage;

public class HorizontalLineSelector extends LineSelector {

	public HorizontalLineSelector(BufferedImage img, LinesData<Double> lines,
			int lineHeight) {
		super(img, lines, lineHeight);
	}

	@Override
	protected BufferedImage createLine() {

		return img.getSubimage(0, begin, img.getWidth(), height + 1);
	}

	@Override
	protected int getLinesNo() {
		return img.getHeight();
	}

}
