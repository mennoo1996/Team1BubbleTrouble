package gui;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import edu.umd.cs.findbugs.graph.Graph;

/**
 * Final class that holds simple draw functions.
 * @author Mark
 *
 */
public final class RND {
	
	/**
	 * Constructor for a renderer.
	 * @param graphics context to use
	 */
	private RND() {
		// Don't use this
	}
	
	private static AngelCodeFont dosFontN;
	private static AngelCodeFont dosFontA;
	private static Color color;
	
	public static void setFont_Normal(AngelCodeFont dosFont) {
		dosFontN = dosFont;
	}
	
	public static void setFont_Additive(AngelCodeFont dosFont) {
		dosFontA = dosFont;
	}
	
	public static AngelCodeFont getFont_Normal() {
		return dosFontN;
	}
	
	public static AngelCodeFont getFont_Additive() {
		return dosFontA;
	}
	
	public static void setColor(Color newColor){
		color = newColor;
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
	public static void drawColor (Graphics g, Image n, Image a,
			float x, float y, Color color) {
		g.drawImage(n, x, y, color);
		g.setDrawMode(Graphics.MODE_ADD);
		g.drawImage(a, x, y, color);
		g.setDrawMode(Graphics.MODE_NORMAL);
		
	}

	// STUFF IT WHERE THE SUN DONT SHINE, CHECKSTYLE. I DONT GIVE HALF A SHILLING ABOUT THE PARAMETERS OR THE LENGTH OF THIS SENTENCE
	public static void drawColor (Graphics g, Image n, Image a, 
			float x, float y, float x2, float y2, 
			float srcx, float srcy, float srcx2, float srcy2,
			Color color) {
		
		g.drawImage(n, x, y, x2, y2, srcx, srcy, srcx2, srcy2, color); 
		g.setDrawMode(Graphics.MODE_ADD);
		
		g.drawImage(a, x, y, x2, y2, srcx, srcy, srcx2, srcy2, color); 
		g.setDrawMode(Graphics.MODE_NORMAL);
		
	}
	
	public static void text(float x, float y, String text) {
		dosFontN.drawString(x, y, text);
	}
	
//	public static void textColor(Graphics g, 
//			float x, float y, String text, Color color) {
//		
//		dosFontN.drawString(x, y, text, color);
//		g.setDrawMode(Graphics.MODE_ADD);
//		dosFontA.drawString(x, y, text, color);
//		g.setDrawMode(Graphics.MODE_NORMAL);
//		
//	}
	
	public static void text(Graphics g, 
			float x, float y, String text) {
		
		dosFontN.drawString(x, y, text, color);
		g.setDrawMode(Graphics.MODE_ADD);
		dosFontA.drawString(x, y, text, color);
		g.setDrawMode(Graphics.MODE_NORMAL);
		
	}
	
	public static void text(Graphics g, 
			float x, float y, String text, Color newColor) {
		
		dosFontN.drawString(x, y, text, newColor);
		if (newColor.a == 1.0f) {
			g.setDrawMode(Graphics.MODE_ADD);
			dosFontA.drawString(x, y, text, newColor);
			g.setDrawMode(Graphics.MODE_NORMAL);
		}
		
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
