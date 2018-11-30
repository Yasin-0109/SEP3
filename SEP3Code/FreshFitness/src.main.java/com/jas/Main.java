package com.jas;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Main {

	private static Properties config;
	private static Server server;
	private static GUI gui;
	
	public static void main(String[] args) {
		try {
			InputStream is = new FileInputStream("config.properties"); // Create input stream for config file
			config = new Properties();
			config.load(is); // Load config from input stream of a file
			
			is.close();
		} catch (IOException ignored) { // Catch some problems
			try {
				Files.copy(
						Thread.currentThread().getContextClassLoader().getResourceAsStream("config.sample.properties"),
						Paths.get("config.sample.properties"),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (NullPointerException e) {
				System.out.println("Couldn't copy configuration!");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (!isHeadless()) { // Check if there's a screen
				JOptionPane.showMessageDialog(null,
						"You don't have Server configured yet!\nPlease configure it first.",
						"Configuration", JOptionPane.INFORMATION_MESSAGE); // Show a message box
				return;
			}
			
			System.out.println("You don't have Server configured yet!");
			System.out.println("Please configure it first.");
			return;
		}
		
		// No JavaFX in Eclipse by default? Why...
		
		server = new Server();
		server.start(); // Let's start a server
		
		// Check if there's a screen
		if (!isHeadless()) { // There is a screen
			// Show GUI
			gui = new GUI(); // Create a GUI
			server.isGUI(); // Make sure that we've run our code for debugging purpose
		} else { // No screen
			// Debug in console?
		}
	}
	
	private static boolean isHeadless() { // Returns true value if we don't have a screen
		if (GraphicsEnvironment.isHeadless()) { // If it supports display, keyboard and mouse
			return true;
		}
		
		try { // Let's check that we have the screen anyways
			GraphicsDevice[] sD = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices(); // Get list of displays
			return sD == null || sD.length == 0; // Check if there is any
		} catch (HeadlessException e) { // Just in case it doesn't support display, keyboard or mouse
			e.printStackTrace();
			return true;
		}
	}
	
	public static Properties getConfig() {
		return config;
	}
	
	public static Server getServer() {
		return server;
	}
	
	public static GUI getGUI() {
		return gui;
	}
}
