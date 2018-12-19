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
import spark.Response;

public class GUI { // Simple swing gui

	private static JFrame frame;
	private static JTextArea sparkRequestTA; // TextArea for our spark requests
	private static JTextArea sparkResponseTA; // TextArea for our spark response
	
	public static void createGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		frame = new JFrame("Fresh Fitness - Server");
		
		frame.setLayout(new BorderLayout()); // Let's use border layout
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		frame.add(panel, BorderLayout.CENTER); // Add it to the center of border layout frame
		
		// Bottom
		JPanel inPanel = new JPanel(); // Create new panel
		inPanel.setLayout(new BoxLayout(inPanel, BoxLayout.Y_AXIS)); // Alignment of its content
		inPanel.setPreferredSize(new Dimension(800, 350)); // Setting it's size
		inPanel.setMinimumSize(new Dimension(800, 350));
		
		JPanel panel2 = new JPanel(); // Create new panel
		
		JLabel label2 = new JLabel(); // Label
		label2.setText("Requests debug info");
		label2.setHorizontalAlignment(JLabel.LEFT);
		
		JLabel label3 = new JLabel();
		label3.setText("Response debug info");
		label2.setHorizontalAlignment(JLabel.RIGHT);
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS)); // Alignment of its content
		
		sparkRequestTA = new JTextArea();
		sparkRequestTA.setEditable(false); // It's cannot be edited
		JScrollPane scrollPane2 = new JScrollPane(sparkRequestTA); // Scrollpane for scrolling
		scrollPane2.setPreferredSize(new Dimension(390, 300));
		scrollPane2.setMinimumSize(new Dimension(390, 300));
		
		sparkResponseTA = new JTextArea();
		sparkResponseTA.setEditable(false); // It's cannot be edited
		JScrollPane scrollPane3 = new JScrollPane(sparkResponseTA); // Scrollpane for scrolling
		scrollPane3.setPreferredSize(new Dimension(390, 300));
		scrollPane3.setMinimumSize(new Dimension(390, 300));
		
		panel2.add(label2);
		panel2.add(label3);
		
		panel3.add(scrollPane2);
		panel3.add(scrollPane3);
		
		inPanel.add(panel2); // Adding them to panel
		inPanel.add(panel3); // Adding them to panel
		
		frame.add(inPanel, BorderLayout.SOUTH); // Add it to the bottom of border layout frame
		
		frame.pack(); // Make some sizing automatically
		//frame.setLocationRelativeTo(null); // Let's make it centered
		frame.setVisible(true); // Show it
		
		System.out.println("Starting the server...");
		Server.startServer(); // Let's start a server
		Server.isGUI(); // Make sure that we've run our code for debugging purpose
	}
	
	public static void setSparkRequestInfo(Request request) { // Method for showing requests from spark
		sparkRequestTA.setText(Utils.requestToString(request));
	}
	
	public static void setSparkResponseInfo(Response response) {
		sparkResponseTA.setText(Utils.responseToString(response));
	}
}
