package com.jas;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;

public class Main {

	private static Server server;
	private static GUI gui;
	
	public static void main(String[] args) {
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
	
	public static Server getServer() {
		return server;
	}
	
	public static GUI getGUI() {
		return gui;
	}
}
