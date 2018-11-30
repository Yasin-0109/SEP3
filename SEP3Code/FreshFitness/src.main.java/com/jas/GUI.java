package com.jas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jas.utils.Console;
import com.jas.utils.Utils;

import spark.Request;

public class GUI extends JFrame { // Simple swing gui

	private static final long serialVersionUID = 6967276937972186412L;
	private JTextArea sparkRequestTA; // TextArea for our spark requestss
	
	public GUI() {
		setLayout(new BorderLayout()); // Let's use border layout
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Center
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false); // So it's cannot be edited
		Console.redirectErr(textArea); // Redirect error logger output
		Console.redirectOut(textArea); // Redirect standard logger output
		JScrollPane scrollPane = new JScrollPane(textArea); // Assign scrollpane to it so we can scroll it
		
		JPanel panel = new JPanel(); // Create new panel
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Alignment of its content
		panel.setPreferredSize(new Dimension(800, 250)); // It should have a defined size
		panel.setMinimumSize(new Dimension(800, 250));
		
		JLabel label = new JLabel(); // Label
		label.setText("Console output");
		label.setHorizontalAlignment(JLabel.LEFT);
		
		panel.add(label); // Adding them to panel
		panel.add(scrollPane); // Adding them to panel
		
		add(panel, BorderLayout.CENTER); // Add it to the center of border layout frame
		
		// Bottom
		JPanel panel2 = new JPanel(); // Create new panel
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS)); // Alignment of its content
		panel2.setPreferredSize(new Dimension(800, 350)); // Setting it's size
		panel2.setMinimumSize(new Dimension(800, 350));
		
		JLabel label2 = new JLabel(); // Label
		label2.setText("Requests debug info");
		label2.setHorizontalAlignment(JLabel.LEFT);
		
		sparkRequestTA = new JTextArea();
		sparkRequestTA.setEditable(false); // It's cannot be edited
		JScrollPane scrollPane2 = new JScrollPane(sparkRequestTA); // Scrollpane for scrolling
		
		panel2.add(label2); // Adding them to panel
		panel2.add(scrollPane2); // Adding them to panel
		
		add(panel2, BorderLayout.SOUTH); // Add it to the bottom of border layout frame
		
		setTitle("Server - Console"); // Title of the window frame
		pack(); // Make some sizing automatically
		setLocationRelativeTo(null); // Let's make it centered
		setVisible(true); // Show it
	}
	
	public void setSparkRequestInfo(Request request) { // Method for showing requests from spark
		sparkRequestTA.setText(Utils.requestToString(request));
	}
}
