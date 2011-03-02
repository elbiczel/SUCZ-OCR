package com.tbiczel.zad1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.tbiczel.zad1.gui.ImagePanel;
import com.tbiczel.zad1.gui.MainPanel;
import com.tbiczel.zad1.io.ImageFilter;
import com.tbiczel.zad1.processing.ImageProcessor;
import com.tbiczel.zad1.processing.ImageUtils;

public class ImageController {

	private MainPanel main;

	private ImagePanel panel;

	private JFileChooser fc;
	private JButton openButton;
	private JButton processButton;

	private JPanel east = new JPanel();
	private JPanel south = new JPanel();

	private BufferedImage image;

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

		south.add(openButton);
		south.add(processButton);
		main.setSouthPanel(south);
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
		}
	}

	private void changeImage() {
		utils = new ImageUtils(image);
		main.removeImage();
		main.setImage(panel);
		main.revalidate();
		main.repaint();
	}

	public MainPanel getMain() {
		return main;
	}

}