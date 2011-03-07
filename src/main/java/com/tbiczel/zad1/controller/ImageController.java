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
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tbiczel.zad1.gui.ImagePanel;
import com.tbiczel.zad1.gui.MainPanel;
import com.tbiczel.zad1.gui.Selector;
import com.tbiczel.zad1.io.ImageFilter;
import com.tbiczel.zad1.processing.ImageProcessor;
import com.tbiczel.zad1.processing.ImageUtils;
import com.tbiczel.zad1.processing.LinesData;

public class ImageController {

	private MainPanel main;

	private ImagePanel panel;

	private JFileChooser fc;
	private JButton openButton;
	private JButton processButton;
	private JButton dumpButton;
	private JSlider minSlider;
	private JSlider maxSlider;
	private JSlider lineHeightSlider;
	private static final int MIN_SLIDER_VALUE = 0;
	private static final int MAX_SLIDER_VALUE = 255 + 255 + 255;

	private Selector selector;

	private JPanel east = new JPanel();
	private JPanel south = new JPanel();

	private BufferedImage image;

	private Rectangle selectedRegion = null;

	private File safeCopy;

	private SwingWorker<ImagePanel, Void> worker = null;

	private ImageUtils utils;

	private int min = 370;

	private int max = 255 + 255 + 140;

	public ImageController(String title) {
		this.main = new MainPanel(title);
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new ImageFilter());
		fc.setAcceptAllFileFilterUsed(true);
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
		dumpButton = new JButton("Dump selected lines data...");
		dumpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ImageController.this.cancelProcessing();
				String selectedClassName = null;
				String fileName = null;
				ImageController.this.dumpData(lineHeightSlider.getValue(),
						selectedClassName, fileName);
			}

		});
		east.setLayout(new GridLayout(0, 1));
		east.add(openButton);
		east.add(processButton);
		east.add(dumpButton);
		main.setEastPanel(east);

		minSlider = createSlider(370);
		maxSlider = createSlider(650);
		lineHeightSlider = createSlider(0, 25, 10, 5, 1);
		minSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					min = (int) source.getValue();
				}
			}

		});
		maxSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					max = (int) source.getValue();
				}
			}

		});

		south.setLayout(new GridLayout(0, 1));
		south.add(minSlider);
		south.add(maxSlider);
		south.add(lineHeightSlider);
		main.setSouthPanel(south);
	}

	private JSlider createSlider(int val) {
		return createSlider(MIN_SLIDER_VALUE, MAX_SLIDER_VALUE, val, 50, 5);
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
			safeCopy = file;
			panel.setImg(image);
			changeImage();
			return panel;
		} catch (IOException e) {
			return null;
		}
	}

	protected void dumpData(final int lineHeight,
			final String selectedClassName, final String fileName) {
		if (selectedRegion == null) {
			return;
		}
		worker = new SwingWorker<ImagePanel, Void>() {
			private LinesData<Integer> lines = new LinesData<Integer>(765);

			// private File file = new File(fileName);

			@Override
			protected ImagePanel doInBackground() throws Exception {
				for (int i = 0; i < image.getHeight(); i++) {
					lines.putLineData(i, utils.getLineDarkness(i));
				}
				// FileOutputStream out = new FileOutputStream(file);
				System.out.println("DUMP DATA");
				for (int i = 0; i < selectedRegion.height; i++) {
					StringBuilder sb = new StringBuilder();
					for (int row = selectedRegion.y - lineHeight; row < selectedRegion.y
							+ lineHeight + 1; row++) {
						sb.append(lines.getLine(row));
						sb.append(',');
					}
					sb.append(selectedClassName);
					sb.append('\n');
					System.out.print(sb.toString());
					// ByteArrayInputStream ba = new ByteArrayInputStream(new
					// byte[sb.length()]);
					// out.write();
				}
				// out.close();
				return null;
			}

			@Override
			protected void done() {
				// TODO show done dialog
			}
		};
		worker.execute();
	}

	public void processImage() {
		worker = new SwingWorker<ImagePanel, Void>() {

			private ImageProcessor proc;

			@Override
			protected ImagePanel doInBackground() throws Exception {
				BufferedImage img = ImageIO.read(safeCopy);
				proc = new ImageProcessor(img, min, max);
				img = proc.process();
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
		utils = new ImageUtils(image);
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
