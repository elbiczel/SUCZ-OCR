package com.tbiczel.zad1.processing;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;

import com.tbiczel.zad1.io.ClassifierBuilder;

public abstract class LineSelector {

	protected BufferedImage img;
	protected LinesData<Double> lines;
	protected int lineHeight;

	private ClassifierBuilder classifierBuilder;

	public LineSelector(BufferedImage img, LinesData<Double> lines,
			int lineHeight, ClassifierBuilder columnsClassifier)
			throws Exception {
		this.img = img;
		this.lines = lines;
		this.lineHeight = lineHeight;
		FastVector attributes = new FastVector();
		for (int i = 0; i < lineHeight; i++) {
			attributes.addElement(new Attribute("A" + i, attributes.size()));
		}
		attributes.addElement(new Attribute("line", attributes.size()));
		for (int i = 0; i < lineHeight; i++) {
			attributes.addElement(new Attribute("B" + i, attributes.size()));
		}
		Attribute classAttr = new Attribute("class", attributes.size());
		attributes.addElement(classAttr);
		this.classifierBuilder = columnsClassifier;
	}

	protected Instance buildInstance(int row, String className) {
		double[] instanceValues = new double[lineHeight + lineHeight + 2];
		for (int i = 0; i < lineHeight + lineHeight + 1; i++) {
			instanceValues[i] = lines.getLine(i + row - lineHeight);
		}
		double classVal = 0.;
		if (className.equals("?")) {
			classVal = Instance.missingValue();
		} else if (className.equals("line")) {
			classVal = 1.;
		}
		instanceValues[lineHeight + lineHeight + 1] = classVal;
		Instance inst = new Instance(1.0, instanceValues);
		inst.setDataset(classifierBuilder.getInstances());
		return inst;
	}

	protected int begin = -1;
	protected int height = 0;

	public ArrayList<Rectangle> getLines() throws Exception {
		ArrayList<Rectangle> result = new ArrayList<Rectangle>();
		resetLine();
		for (int i = 0; i < getLinesNo(); i++) {
			if (isLine(classifierBuilder.getClassifier(), i)) {
				expandLine(i);
			} else if (insideLine()) {
				result.add(createLine());
				resetLine();
			}
		}
		if (insideLine()) {
			result.add(createLine());
		}
		return result;
	}

	private void expandLine(int line) {
		if (!insideLine()) {
			begin = line;
		} else {
			height++;
		}
	}

	private boolean insideLine() {
		return begin != -1;
	}

	private void resetLine() {
		begin = -1;
		height = 0;
	}

	protected abstract Rectangle createLine();

	protected abstract int getLinesNo();

	private boolean isLine(Classifier cl, int i) throws Exception {
		return cl.classifyInstance(buildInstance(i, "?")) > 0.7;
	}
}
