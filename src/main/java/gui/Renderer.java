package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Final class that holds simple draw functions.
 * @author Mark
 *
 */
public final class Renderer {
	
	/**
	 * Constructor for a renderer.
	 * @param graphics context to use
	 */
	private Renderer() {
		// Don't use this
	}
	
	
	/**
	 * Example draw function that just renders an object to the screen.
	 * @param graphics the graphics context used
	 * @param imageNorm normal grayscale transparent image
	 * @param imageAdd additive grayscale non-transparent image
	 * @param x x-location of top-left
	 * @param y y-location of top-left
	 * @param color the color to draw with
	 */
	public static void drawWithColour(Graphics graphics, Image imageNorm, Image imageAdd,
			float x, float y, Color color) {
		
		graphics.drawImage(imageNorm, x, y, color);
		graphics.setDrawMode(Graphics.MODE_ADD);
		graphics.drawImage(imageAdd, x, y);
		graphics.setDrawMode(Graphics.MODE_NORMAL);
		
	}
	
	public static void drawWithColourStretched(Graphics graphics, Image imageNorm, Image imageAdd,
			int x, int y, int x2, int y2, Color color) {
	
	}
	
	public static void draw(Graphics graphics, Image imageNorm,
			int x, int y) {
		
		graphics.drawImage(imageNorm, x, y);
		
	}
	
	public static void drawStretched(Graphics graphics, Image imageNorm,
			int x, int y, int x2, int y2) {
		
		graphics.drawImage(imageNorm, x, y);
		
	}
	
}
