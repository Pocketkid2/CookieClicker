package com.github.pocketkid2.cookieclicker;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class CookieClicker extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -1601777113845487835L;
	
	public CookieClicker() {
		// Add a clicker object
		add(new Clicker());
		pack();
		
		// Set window properties
		setTitle("Cookie Clicker");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			CookieClicker cc = new CookieClicker();
			cc.setVisible(true);
		});
	}

}
