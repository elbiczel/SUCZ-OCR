package com.tbiczel.zad1.io;

import java.io.File;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;

public class ClassifierBuilder {

	private Instances instances;

	private Classifier classifier;

	public ClassifierBuilder(File lineAttributes) throws Exception {
		instances = new Instances(new FileReader(lineAttributes));
		instances.setClassIndex(instances.numAttributes() - 1);
		classifier = new RandomTree();
		classifier.buildClassifier(instances);
		System.err.println(classifier.toString());
	}

	public Instances getInstances() {
		return instances;
	}

	public Classifier getClassifier() {
		return classifier;
	}

}
