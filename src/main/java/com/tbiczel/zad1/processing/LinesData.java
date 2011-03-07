package com.tbiczel.zad1.processing;

import java.util.Vector;

public class LinesData<T> {
	
	private Vector<T> data = new Vector<T>();
	
	private T empty;
	
	public LinesData(T maxValue) {
		empty = maxValue;
	}
	
	public T getLine(int lineNo) {
		if (lineNo < 0 || lineNo >= data.size()) {
			return empty;
		}
		return data.get(lineNo);
	}
	
	public void putLineData(int lineNo, T value) {
		data.add(lineNo, value);
	}

}
