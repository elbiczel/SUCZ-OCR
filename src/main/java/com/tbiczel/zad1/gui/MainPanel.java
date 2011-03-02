package com.tbiczel.zad1.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tbiczel.zad1.controller.ImageController;
import com.tbiczel.zad1.io.ImageFilter;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -1057394803352666439L;
	private JFileChooser fc;
	JPanel southPanel = new JPanel();
	JPanel eastPanel = new JPanel();
	private JButton openButton;
	private JButton processButton;
	private ImagePanel image;
	private ImageController ic;

	public void removeImage() {
		remove(image);
	}

	public void setImage(ImagePanel image) {
		this.image = image;
		add(image, BorderLayout.CENTER);
	}

	public MainPanel(String title) {
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new ImageFilter());
		fc.setAcceptAllFileFilterUsed(false);

		Font font = new Font("SansSerif", Font.BOLD, 24);
		JLabel titleLable = new JLabel(title);
		titleLable.setFont(font);
		titleLable.setLocation(0, 32);

		image = new ImagePanel();
		ic = new ImageController();

		openButton = new JButton("Open an image...");
		openButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(MainPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					ic.cancelProcessing();
					File file = fc.getSelectedFile();
					ic.readImage(file, MainPanel.this);
				}
			}

		});
		processButton = new JButton("Process image...");
		processButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ic.cancelProcessing();
				ic.processImage(MainPanel.this);
			}

		});

		southPanel.add(openButton);
		southPanel.add(processButton);

		setOpaque(true);
		setLayout(new BorderLayout());
		setBackground(Color.white);

		add(titleLable, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		add(image, BorderLayout.CENTER);
	}
	
	public void setEastPanel(JPanel eastPanel) {
		if (this.eastPanel != null) {
			remove(eastPanel);
		}
		this.eastPanel = eastPanel;
		if (this.eastPanel != null) {
			add(eastPanel, BorderLayout.EAST);
		}
	}
	
	public void setSouthPanel(JPanel southPanel) {
		if (this.southPanel != null) {
			remove(southPanel);
		}
		this.southPanel = southPanel;
		if (this.southPanel != null) {
			add(southPanel, BorderLayout.SOUTH);
		}
	}

}
