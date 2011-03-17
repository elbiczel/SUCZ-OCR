package com.tbiczel.zad1.processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomTree;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public abstract class LineSelector {

	protected BufferedImage img;
	protected LinesData<Integer> lines;
	protected int lineHeight;

	protected Instances instances;

	public LineSelector(BufferedImage img, LinesData<Integer> lines,
			int lineHeight) {
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
	}

	public Instance buildInstance(int row, String className) {
		double[] instanceValues = new double[instances.numAttributes()];
		for (int i = 0; i < lineHeight + lineHeight + 1; i++) {
			instanceValues[i] = 1.0 * lines.getLine(i + row - lineHeight);
		}
		double classVal = 0.;
		if (className.equals("?")) {
			classVal = Instance.missingValue();
		} else if (className.equals("line")) {
			classVal = 1.;
		}
		instanceValues[instances.numAttributes() - 1] = classVal;
		Instance inst = new Instance(1.0, instanceValues);
		inst.setDataset(instances);
		return inst;
	}

	protected int begin = -1;
	protected int height = 0;

	public Vector<BufferedImage> getLines(File lineAttributes) throws Exception {
		instances = new Instances(new FileReader(lineAttributes));
		instances.setClassIndex(instances.numAttributes() - 1);
		RandomTree classifier = new RandomTree();
		classifier.buildClassifier(instances);
		System.err.println(classifier.toString());
		Vector<BufferedImage> result = new Vector<BufferedImage>();
		resetLine();
		for (int i = 0; i < getLinesNo(); i++) {
			if (isLine(classifier, i)) {
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

	protected abstract BufferedImage createLine();

	protected abstract int getLinesNo();

	private boolean isLine(Classifier cl, int i) throws Exception {
		return cl.classifyInstance(buildInstance(i, "?")) > 0.7;
	}
}
