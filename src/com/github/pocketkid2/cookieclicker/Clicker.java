package com.github.pocketkid2.cookieclicker;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Clicker extends JPanel implements MouseListener {
	
	// This is here so eclipse doesn't give me a warning :/
	private static final long serialVersionUID = 4440492073192870794L;

	private static final int FONT_SIZE = 40;

	private static final int WINDOW_WIDTH = 640;
	private static final int WINDOW_HEIGHT = 480;

	// The cookie image gets scaled by this much, i.e. when you hold down the button it scales to 90%
	private static final double SCALE_FACTOR = 0.9;

	// The regular sized image
	private BufferedImage image;

	// The counter object
	private Cookie count;
	
	// Whether we have the mouse pressed down or not
	private boolean isPressed;
	
	public Clicker() {
		try {
			// Read a buffered image from src/resources/PerfectCookie.png
			image = ImageIO.read(CookieClicker.class.getResource("/resources/PerfectCookie.png"));
		} catch (IOException e) {
			System.out.println("Error reading image");
			e.printStackTrace();
		}
		// A cookie increment option (it seems pointless but it allows for future possibilities
		count = new Cookie();
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		addMouseListener(this);
		// Initialize to false
		isPressed = false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// Paint parent and prepare graphics
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Set some options
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);

		// Pick a nice-ish font
		g2d.setFont(new Font("Tahoma", Font.BOLD, FONT_SIZE));
		
		// Draw the cookie and the counter separately
		drawCookie(g2d);
		drawCounter(g2d);
	}
	
	public void drawCookie(Graphics2D g) {
		// Get the center
		int x = WINDOW_WIDTH / 2;
		int y = WINDOW_HEIGHT / 2;

		int xOffset;
		int yOffset;

		// Set the offset based on the future image size
		if (isPressed) {
			xOffset = (int) (image.getWidth() * SCALE_FACTOR / 2);
			yOffset = (int) (image.getHeight() * SCALE_FACTOR / 2);
		} else {
			xOffset = image.getWidth() / 2;
			yOffset = image.getHeight() / 2;
		}

		// Create a transform
		AffineTransform at = new AffineTransform();

		// Move it to the place
		at.translate(x - xOffset, y - yOffset);
		
		// Scale it if needed
		if (isPressed) {
			at.scale(SCALE_FACTOR, SCALE_FACTOR);
		}

		// Draw the image
		g.drawRenderedImage(image, at);
	}
	
	public void drawCounter(Graphics2D g) {
		// Basically try and find the middle of the text string to set the X and Y
		String s = count.toString();
		int length = s.length();
		length *= FONT_SIZE;
		length /= 2;
		int x = getWidth() / 2 - length;
		int y = (int) (getHeight() * 0.9);

		// And draw it
		g.drawString(s, x, y);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		count.increment();
		repaint();
		// System.out.println("Mouse clicked!");
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// System.out.println("Mouse pressed!");
		isPressed = true;
		repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// System.out.println("Mouse released!");
		isPressed = false;
		repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// System.out.println("Mouse entered!");
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// System.out.println("Mouse exited!");
	}
}
