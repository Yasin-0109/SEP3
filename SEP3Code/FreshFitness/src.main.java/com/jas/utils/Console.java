package com.jas.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

public class Console implements Runnable {

	JTextArea displayPane; // Pane where all text will be written to
	PipedOutputStream stream; // Piped stream
	BufferedReader reader; // Buffered reader for reading output
	
	private Console(JTextArea displayPane, PipedOutputStream stream) {
		this.displayPane = displayPane; // Assign display pane
		this.stream = stream;
		
		createReader();
	}
	
	private void createReader() {
		try {
			PipedInputStream iS = new PipedInputStream(stream); // For reading content as a stream of bytes
			reader = new BufferedReader(new InputStreamReader(iS)); // So we can read them on buffer
		} catch (IOException ignored) {
			
		}	
	}
	
	public void run() {
		String line = null;
		try {
			while ((line = reader.readLine()) != null) { // While we have something to read from
				displayPane.append(line + "\n"); // Let's write out the output
				displayPane.setCaretPosition(displayPane.getDocument().getLength()); // We have to set the caret position to the end
			}
		} catch (IOException e) {
			displayPane.append("\nError redirecting output: " + e.getMessage() + "\n"); // Show a message when we can't redirect output
			displayPane.setCaretPosition(displayPane.getDocument().getLength()); // We have to set the caret position to the end
			createReader();
		}
	}
	
	public static void redirectOut(JTextArea displayPane) {
		PipedOutputStream oS = new PipedOutputStream(); // Let's create output stream
		System.setOut(new PrintStream(oS, true)); // set out from default logger
	
		Console console = new Console(displayPane, oS); // Make a new Console object with output stream from logger
		new Thread(console).start(); // It should be in it's own thread
	}
	
	public static void redirectErr(JTextArea displayPane) {
		PipedOutputStream oS = new PipedOutputStream(); // Let's create output stream
		System.setErr(new PrintStream(oS, true)); // set standard error output from logger
		
		Console console = new Console(displayPane, oS); // Make a new Console object with that output
		new Thread(console).start(); // It should be in it's own thread
	}
}
