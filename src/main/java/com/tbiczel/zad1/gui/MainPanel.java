package com.tbiczel.zad1.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -1057394803352666439L;

	JPanel southPanel = null;
	JPanel eastPanel = null;

	private JScrollPane imageScroll = null;
	private ImagePanel image = null;

	public void removeImage() {
		remove(image);
	}

	public void setImage(ImagePanel image) {
		if (this.image == image) {
			return;
		}
		if (this.image != null) {
			remove(imageScroll);
		}
		this.image = image;
		imageScroll = new JScrollPane(image);
		add(imageScroll, BorderLayout.CENTER);
	}

	public MainPanel(String title) {

		Font font = new Font("SansSerif", Font.BOLD, 24);
		JLabel titleLable = new JLabel(title);
		titleLable.setFont(font);
		titleLable.setLocation(0, 32);

		setOpaque(true);
		setLayout(new BorderLayout());
		setBackground(Color.white);

		add(titleLable, BorderLayout.NORTH);
		setImage(new ImagePanel());
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
