package com.tbiczel.zad1.processing;

import java.awt.image.BufferedImage;

public class VerticalLineSelector extends LineSelector {

	public VerticalLineSelector(BufferedImage img, LinesData<Integer> lines,
			int lineHeight) {
		super(img, lines, lineHeight);
	}

	@Override
	protected BufferedImage createLine() {
		return img.getSubimage(begin, 0, height + 1, img.getHeight());
	}

	@Override
	protected int getLinesNo() {
		return img.getWidth();
	}
}
