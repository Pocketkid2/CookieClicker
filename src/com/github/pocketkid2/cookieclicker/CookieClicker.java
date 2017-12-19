package com.github.pocketkid2.cookieclicker;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CookieClicker extends JFrame {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -1601777113845487835L;
	
	private static Clicker c;

	public CookieClicker() {
		// Add a clicker object
		c = new Clicker();
		add(c);
		pack();

		// Set window properties
		setTitle("Cookie Clicker");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// Create menu bar
		JMenuBar bar = new JMenuBar();
		
		JMenu file = new JMenu("File");

		JMenuItem save = new JMenuItem("Save");
		save.setToolTipText("Save clicker state to file");
		save.addActionListener(new SaveFileAction());
		JMenuItem load = new JMenuItem("Load");
		load.setToolTipText("Load clicker state from file");
		load.addActionListener(new LoadFileAction());

		file.add(save);
		file.add(load);

		bar.add(file);
		
		setJMenuBar(bar);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			CookieClicker cc = new CookieClicker();
			cc.setVisible(true);
		});
	}
	
	private File chooseFile() {

		// Create the chooser class
		JFileChooser fc = new JFileChooser();
		
		// Create a filter
		FileFilter filter = new FileNameExtensionFilter("DAT files", "dat");
		fc.addChoosableFileFilter(filter);
		
		// Show the dialog, and return the file if a valid one was chosen
		if (fc.showDialog(c, "Select File") == JFileChooser.APPROVE_OPTION) return fc.getSelectedFile();

		// Return null if cancelled or no valid file was chosen
		else return null;
	}

	private class SaveFileAction extends AbstractAction {
		
		/**
		 *
		 */
		private static final long serialVersionUID = 6974981122817397012L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Find out what file we need to save to
			File saveLocation = chooseFile();

			// Do nothing if no file was chosen
			if (saveLocation == null) return;
			
			// Get a resource stream for the file
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveLocation))) {
				
				// Write the variable
				oos.writeObject(c.count);
				
				// And draw the text
				c.drawMessage("Saved game!");
				
				// And update the display
				c.repaint();
				
			} catch (FileNotFoundException e1) {
				
				// If invalid, print
				System.out.println("File not found");
				
				// And draw
				c.drawMessage("Save file not found");
				
				// And update
				c.repaint();
				
			} catch (IOException e1) {
				
				// If error, print
				System.out.println("IO error");
				
				// And draw
				c.drawMessage("IO error");
				
				// And update
				c.repaint();
			}
		}

	}

	private class LoadFileAction extends AbstractAction {
		
		/**
		 *
		 */
		private static final long serialVersionUID = -5347451710124132905L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Find out what file we need to load from
			File loadLocation = chooseFile();
			
			// Do nothing if no file was chosen
			if (loadLocation == null) return;
			
			// Get a resource stream for the file
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loadLocation))) {
				
				// Read to the variable
				c.count = (Cookie) ois.readObject();
				
				// And draw the text
				c.drawMessage("Loaded game!");
				
				// And update the display
				c.repaint();
				
			} catch (FileNotFoundException e1) {
				
				// If invalid, print
				System.out.println("File not found");
				
				// And draw
				c.drawMessage("Save file not found");
				
				// And update
				c.repaint();
				
			} catch (IOException e1) {
				
				// If error, print
				System.out.println("IO error");
				
				// And draw
				c.drawMessage("IO error");
				
				// And update
				c.repaint();
				
			} catch (ClassNotFoundException e1) {
				
				// If save file invalid or corrupt, print
				System.out.println("Cannot read Cookie: corrupted save?");
				
				// And draw
				c.drawMessage("Cannot load save game");
				
				// And update
				c.repaint();
			}
		}

	}
	
}
