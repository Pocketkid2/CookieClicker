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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CookieClicker extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -1601777113845487835L;
	
	public CookieClicker() {
		File saveLocation = new File("cookie.dat");

		// Add a clicker object
		Clicker c = new Clicker();
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
		save.addActionListener((ActionEvent e) -> {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveLocation))) {
				oos.writeObject(c.count);
				c.drawMessage("Saved game!");
				c.repaint();
			} catch (FileNotFoundException e1) {
				System.out.println("File not found");
				c.drawMessage("Save file not found");
				c.repaint();
			} catch (IOException e1) {
				System.out.println("IO error");
				c.drawMessage("IO error");
				c.repaint();
			}
		});
		JMenuItem load = new JMenuItem("Load");
		load.setToolTipText("Load clicker state from file");
		load.addActionListener((ActionEvent e) -> {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveLocation))) {
				c.count = (Cookie) ois.readObject();
				c.drawMessage("Loaded game!");
				c.repaint();
			} catch (FileNotFoundException e1) {
				System.out.println("File not found");
				c.drawMessage("Save file not found");
				c.repaint();
			} catch (IOException e1) {
				System.out.println("IO error");
				c.drawMessage("IO error");
				c.repaint();
			} catch (ClassNotFoundException e1) {
				System.out.println("Cannot read Cookie: corrupted save?");
				c.drawMessage("Cannot load save game");
				c.repaint();
			}
		});
		
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

}
