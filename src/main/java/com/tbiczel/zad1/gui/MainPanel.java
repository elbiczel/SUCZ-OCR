package com.tbiczel.zad1.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -1057394803352666439L;
	
	JPanel southPanel = null;
	JPanel eastPanel = null;
	
	private ImagePanel image;

	public void removeImage() {
		remove(image);
	}

	public void setImage(ImagePanel image) {
		this.image = image;
		add(image, BorderLayout.CENTER);
	}

	public MainPanel(String title) {

		Font font = new Font("SansSerif", Font.BOLD, 24);
		JLabel titleLable = new JLabel(title);
		titleLable.setFont(font);
		titleLable.setLocation(0, 32);

		image = new ImagePanel();

		setOpaque(true);
		setLayout(new BorderLayout());
		setBackground(Color.white);

		add(titleLable, BorderLayout.NORTH);
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
