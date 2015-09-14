package gui;

import org.newdawn.slick.AngelCodeFont;
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
	public static void drawColor(Graphics g, Image n, Image a,
			float x, float y, Color color) {
		
		g.drawImage(n, x, y, color);
		g.setDrawMode(Graphics.MODE_ADD);
		g.drawImage(a, x, y, color);
		g.setDrawMode(Graphics.MODE_NORMAL);
		
	}

	// STUFF IT WHERE THE SUN DONT SHINE, CHECKSTYLE
	public static void drawColor(Graphics g, Image n, Image a, 
			float x, float y, float x2, float y2, 
			float srcx, float srcy, float srcx2, float srcy2,
			Color color) {
		
		g.drawImage(n, x, y, x2, y2, srcx, srcy, srcx2, srcy2, color); 
		g.setDrawMode(Graphics.MODE_ADD);
		g.drawImage(a, x, y, x2, y2, srcx, srcy, srcx2, srcy2, color); 
		g.setDrawMode(Graphics.MODE_NORMAL);
		
	}
	
	public static void text(AngelCodeFont f, float x, float y, String text) {
		f.drawString(x, y, text);
	}
	
	public static void textColor(Graphics g, AngelCodeFont n, AngelCodeFont a, 
			float x, float y, String text, Color color) {
		
		n.drawString(x, y, text, color);
		g.setDrawMode(Graphics.MODE_ADD);
		n.drawString(x, y, text, color);
		g.setDrawMode(Graphics.MODE_NORMAL);
		
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
