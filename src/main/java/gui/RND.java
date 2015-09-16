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
	private static float opacity = 1.0f;
	
	/**
	 * Set opacity of all on-screen elements to a certain value.
	 * @param newOpacity the new opacity of on-screen elements.
	 */
	public static void setOpacity(float newOpacity) {
		opacity = newOpacity;
	}
	
	/**
	 * @return the opacity of on-screen elements.
	 */
	public static float getOpacity() {
		return opacity;
	}
	
	/**
	 * set font to new font, normal.
	 * @param dosFont to set.
	 */
	public static void setFont_Normal(AngelCodeFont dosFont) {
		dosFontN = dosFont;
	}
	
	/**
	 * set font to new font, additive.
	 * @param dosFont to set.
	 */
	public static void setFont_Additive(AngelCodeFont dosFont) {
		dosFontA = dosFont;
	}
	
	/**
	 * @return returns normal font.
	 */
	public static AngelCodeFont getFont_Normal() {
		return dosFontN;
	}
	
	/**
	 * @return returns additive font.
	 */
	public static AngelCodeFont getFont_Additive() {
		return dosFontA;
	}
	
	/**
	 * set screen color to new color.
	 * @param newColor to set
	 */
	public static void setColor(Color newColor) {
		color = newColor;
	}
	
	/**
	 * Draw function rendering objects to screen with color filter.
	 * @param g graphics context
	 * @param n normal image
	 * @param a additive image
	 * @param x x-location
	 * @param y y-location
	 * @param color color filter
	 */
	public static void drawColor(Graphics g, Image n, Image a,
			float x, float y, Color color) {
		g.drawImage(n, x, y, new Color(color.r, color.g, color.b, opacity));
		g.setDrawMode(Graphics.MODE_ADD);
		g.drawImage(a, x, y, new Color(color.r * opacity, color.g * opacity, color.b * opacity));
		g.setDrawMode(Graphics.MODE_NORMAL);
		
	}

	/**
	 * Draw stretched function, with color settings.
	 * @param g graphics context
	 * @param n normal image
	 * @param a additive image
	 * @param x x-left
	 * @param y y-top
	 * @param x2 x-right
	 * @param y2 y-bottom
	 * @param srcx resx
	 * @param srcy resy
	 * @param srcx2 stretchx
	 * @param srcy2 stretchy
	 * @param color color filter
	 */
	public static void drawColor(Graphics g, Image n, Image a, 
			float x, float y, float x2, float y2, 
			float srcx, float srcy, float srcx2, float srcy2,
			Color color) {
		
		g.drawImage(n, x, y, x2, y2, srcx, srcy, srcx2, srcy2,
				new Color(color.r, color.g, color.b, opacity)); 
		g.setDrawMode(Graphics.MODE_ADD);
		
		g.drawImage(a, x, y, x2, y2, srcx, srcy, srcx2, srcy2, 
				new Color(color.r * opacity, color.g * opacity, color.b * opacity)); 
		g.setDrawMode(Graphics.MODE_NORMAL);
		
	}
	
	/**
	 * draws simple text to screen.
	 * @param x location
	 * @param y location
	 * @param text to draw
	 */
	public static void text(float x, float y, String text) {
		dosFontN.drawString(x, y, text);
	}
	
	/**
	 * draws simple colored text to screen.
	 * @param g graphics context
	 * @param x location
	 * @param y location
	 * @param text to draw
	 */
	public static void text(Graphics g, 
			float x, float y, String text) {
		
		dosFontN.drawString(x, y, text, new Color(color.r, color.g, color.b, opacity));
		g.setDrawMode(Graphics.MODE_ADD);
		dosFontA.drawString(x, y, text, 
				new Color(color.r * opacity, color.g * opacity, color.b * opacity));
		g.setDrawMode(Graphics.MODE_NORMAL);
		
	}
	
	/**
	 * draws set-colored text to screen.
	 * @param g graphics context
	 * @param x location
	 * @param y location
	 * @param text to draw
	 * @param newColor color to set
	 */
	public static void text(Graphics g, 
			float x, float y, String text, Color newColor) {
		
		dosFontN.drawString(x, y, text, new Color(newColor.r, newColor.g, newColor.b, opacity));
		if (newColor.a == 1.0f) {
			g.setDrawMode(Graphics.MODE_ADD);
			dosFontA.drawString(x, y, text, 
					new Color(newColor.r * opacity, newColor.g * opacity, newColor.b * opacity));
			g.setDrawMode(Graphics.MODE_NORMAL);
		}
		
	}
	
	/**
	 * draws simple image to screen.
	 * @param graphics context
	 * @param imageNorm to draw
	 * @param x location
	 * @param y location
	 */
	public static void draw(Graphics graphics, Image imageNorm,
			int x, int y) {
		
		graphics.drawImage(imageNorm, x, y);
		
	}
	
//	/**
//	 * draws simple stretched image to screen.
//	 * @param graphics
//	 * @param imageNorm
//	 * @param x
//	 * @param y
//	 * @param x2
//	 * @param y2
//	 */
//	public static void drawStretched(Graphics graphics, Image imageNorm,
//			int x, int y, int x2, int y2) {
//		
//		graphics.drawImage(imageNorm, x, y);
//		
//	}
	
}
