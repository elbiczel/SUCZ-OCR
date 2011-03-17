package com.tbiczel.zad1.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.tbiczel.zad1.gui.ImagePanel;

public abstract class DataDumper extends SwingWorker<ImagePanel, Void> {

	private File file;
	private int lineHeight;
	private String selectedClassName;

	public DataDumper(File file, int lineHeight, String selectedClassName) {
		this.file = file;
		this.lineHeight = lineHeight;
		this.selectedClassName = selectedClassName;
	}

	@Override
	protected ImagePanel doInBackground() throws Exception {
		boolean addHeader = !file.exists();
		BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
		if (addHeader) {
			file.createNewFile();
			out.write("@relation Lines\n");
			for (int i = 0; i < lineHeight + lineHeight + 1; i++) {
				out.write("@attribute line" + i + " real\n");
			}
			out.write("@attribute Class { 'not', 'line' }\n@data\n");
		}
		for (int i = 0; i < getLinesNo(); i++) {
			StringBuilder sb = new StringBuilder();
			for (int row = getSelectedRegionStart() + i - lineHeight; row < getSelectedRegionStart()
					+ i + lineHeight + 1; row++) {
				sb.append(getLineDarkness(row));
				sb.append(',');
			}
			sb.append(selectedClassName);
			sb.append('\n');
			out.write(sb.toString());
		}
		out.close();
		return null;
	}

	@Override
	protected void done() {
		JOptionPane.showMessageDialog(null, "Dumping finished", "Done",
				JOptionPane.INFORMATION_MESSAGE);
	}

	protected abstract int getSelectedRegionStart();

	protected abstract double getLineDarkness(int i);

	protected abstract int getLinesNo();

}
