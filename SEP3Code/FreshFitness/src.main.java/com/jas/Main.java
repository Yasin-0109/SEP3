package com.jas;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.File;
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
	
	public static void main(String[] args) {
		try {
			InputStream is = new FileInputStream("config.properties"); // Create input stream for config file
			config = new Properties();
			config.load(is); // Load config from input stream of a file
			
			is.close();
			
			File file = new File("keystore.jks");
			if (!file.exists()) {
				Files.copy(
						Thread.currentThread().getContextClassLoader().getResourceAsStream("keystore.jks"),
						Paths.get("keystore.jks"),
						StandardCopyOption.REPLACE_EXISTING);
			}
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
		
		
		// Check if there's a screen
		if (!isHeadless()) { // There is a screen
			System.out.println("Starting GUI...");
			// Show GUI
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					GUI.createGUI(); // Create a GUI
				}
			});
		} else { // No screen
			System.out.println("There's no screen! Running in console.");
			// Debug in console?
			System.out.println("Starting the server...");
			Server.startServer(); // Let's start a server
		}
		System.out.println("Finished initialize sequence.");
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
	
}
