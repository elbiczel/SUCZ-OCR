package com.tbiczel.zad1.controller;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingWorker;

import com.tbiczel.zad1.gui.ImagePanel;
import com.tbiczel.zad1.gui.MainPanel;
import com.tbiczel.zad1.gui.Selector;
import com.tbiczel.zad1.io.DataDumper;
import com.tbiczel.zad1.io.ImageFilter;
import com.tbiczel.zad1.processing.ImageProcessor;
import com.tbiczel.zad1.processing.ImageUtils;

public class ImageController {

	private static final double BLACK_THREASHOLD = 0.3;

	private MainPanel main;

	private ImagePanel panel;

	private JFileChooser fc;
	private JFileChooser outFileChooser;
	private JButton openButton;
	private JButton processButton;
	private JButton dumpRowsButton;
	private JButton dumpColumnsButton;
	private JSlider lineHeightSlider;

	private Selector selector;

	private JPanel east = new JPanel();
	private JPanel south = new JPanel();

	private BufferedImage image;
	private ImageProcessor proc;

	private Rectangle selectedRegion = null;

	private File safeCopy;
	private File rowsFile = null;
	private File columnsFile = null;

	private SwingWorker<ImagePanel, Void> worker = null;

	private ImageUtils utils;

	public ImageController(String title) {
		this.main = new MainPanel(title);
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new ImageFilter());
		fc.setAcceptAllFileFilterUsed(true);
		outFileChooser = new JFileChooser();
		openButton = new JButton("Open an image...");
		openButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(ImageController.this.main);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					ImageController.this.cancelProcessing();
					File file = fc.getSelectedFile();
					ImageController.this.readImage(file);
				}
			}

		});
		processButton = new JButton("Process image...");
		processButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ImageController.this.cancelProcessing();
				ImageController.this.processImage();
			}

		});
		dumpRowsButton = new JButton("Dump selected rows data...");
		dumpRowsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ImageController.this.cancelProcessing();
				String selectedClassName = JOptionPane.showInputDialog(main,
						"class name");
				if (rowsFile == null) {
					int returnVal = outFileChooser.showSaveDialog(main);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						rowsFile = outFileChooser.getSelectedFile();
					}
				}
				if (selectedRegion == null || rowsFile == null) {
					return;
				}
				worker = new DataDumper(rowsFile, lineHeightSlider.getValue(),
						selectedClassName) {

					@Override
					protected double getLineDarkness(int i) {
						return proc.getHorizontalLines().getLine(i);
					}

					@Override
					protected int getSelectedRegionStart() {
						return selectedRegion.y;
					}

					@Override
					protected int getLinesNo() {
						return selectedRegion.height;
					}

				};
				worker.execute();
			}

		});
		dumpColumnsButton = new JButton("Dump selected columns data...");
		dumpColumnsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ImageController.this.cancelProcessing();
				String selectedClassName = JOptionPane.showInputDialog(main,
						"class name");
				if (columnsFile == null) {
					int returnVal = outFileChooser.showSaveDialog(main);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						columnsFile = outFileChooser.getSelectedFile();
					}
				}
				if (selectedRegion == null || columnsFile == null) {
					return;
				}
				worker = new DataDumper(columnsFile, lineHeightSlider
						.getValue(), selectedClassName) {

					@Override
					protected double getLineDarkness(int i) {
						return proc.getVerticalLines().getLine(i);
					}

					@Override
					protected int getSelectedRegionStart() {
						return selectedRegion.x;
					}

					@Override
					protected int getLinesNo() {
						return selectedRegion.width;
					}

				};
				worker.execute();
			}
		}

		);
		east.setLayout(new GridLayout(0, 1));
		east.add(openButton);
		east.add(processButton);
		east.add(dumpRowsButton);
		east.add(dumpColumnsButton);
		main.setEastPanel(east);

		lineHeightSlider = createSlider(0, 25, 10, 5, 1);

		south.setLayout(new GridLayout(0, 1));
		south.add(lineHeightSlider);
		main.setSouthPanel(south);
	}

	private JSlider createSlider(int minVal, int maxVal, int val,
			int majTickSpacing, int minTickSpacing) {
		JSlider result = new JSlider(minVal, maxVal, val);
		result.setMajorTickSpacing(majTickSpacing);
		result.setMinorTickSpacing(minTickSpacing);
		result.setPaintTicks(true);
		result.setPaintLabels(true);
		return result;
	}

	public ImagePanel readImage(File file) {
		try {
			panel = new ImagePanel();
			image = ImageIO.read(file);
			utils = new ImageUtils(image, BLACK_THREASHOLD);
			proc = new ImageProcessor(image, BLACK_THREASHOLD,
					lineHeightSlider.getValue());
			safeCopy = file;
			panel.setImg(image);
			changeImage();
			return panel;
		} catch (IOException e) {
			return null;
		}
	}

	public void processImage() {
		worker = new SwingWorker<ImagePanel, Void>() {

			@Override
			protected ImagePanel doInBackground() throws Exception {
				BufferedImage img = ImageIO.read(safeCopy);
				img = proc.process(rowsFile, columnsFile);
				ImagePanel panel = new ImagePanel();
				panel.setImg(img);
				return panel;
			}

			@Override
			protected void done() {
				try {
					ImagePanel panel = get();
					ImageController.this.panel = panel;
					ImageController.this.image = panel.getImg();
					ImageController.this.changeImage();
				} catch (Exception ignore) {
					System.err.println(ignore.getLocalizedMessage());
				}
			}

		};
		worker.execute();
	}

	public void cancelProcessing() {
		if (worker != null) {
			worker.cancel(true);
			worker = null;
		}
	}

	private void changeImage() {
		selector = new Selector(this, panel, utils);
		panel.addMouseListener(selector);
		panel.addMouseMotionListener(selector);
		main.removeImage();
		main.setImage(panel);
		main.revalidate();
		main.repaint();
	}

	public MainPanel getMain() {
		return main;
	}

	public void setSelectedRegion(int x, int y, int w, int h) {
		selectedRegion = new Rectangle(new Point(x, y), new Dimension(w, h));
	}

}
