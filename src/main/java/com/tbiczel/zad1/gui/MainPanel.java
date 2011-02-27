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
	private JButton openButton;
	private ImagePanel image;
	private ImageController ic;

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
					File file = fc.getSelectedFile();
					MainPanel.this.remove(image);
					MainPanel.this.image = ic.read(file);
					MainPanel.this.add(image, BorderLayout.CENTER);
					MainPanel.this.revalidate();
					MainPanel.this.repaint();
				}
			}

		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(openButton);

		setOpaque(true);
		setLayout(new BorderLayout());
		setBackground(Color.white);

		add(titleLable, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
		add(image, BorderLayout.CENTER);
	}

}
