package guiobjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * THis class holds the information needed to render something.
 * @author Bart
 *
 */
public class RenderOptions {
	private final Graphics g;
	private final Image n, a;
	private final float x, y;
	private final Color color;
	
	
	/**
	 * @param g		- the graphics used to render.
	 * @param n		- the n image used to render.
	 * @param a		- the a image used to render.
	 * @param x		- the upperleft corner to render from.
	 * @param y		- the upperleft corner to render from.
	 * @param color	- the color to render with.
	 */
	public RenderOptions(Graphics g, Image n, Image a, float x, 
			float y, Color color) {
		super();
		this.g = g;
		this.n = n;
		this.a = a;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	
	/**
	 * @return the g
	 */
	public Graphics getG() {
		return g;
	}
	/**
	 * @return the n
	 */
	public Image getN() {
		return n;
	}
	/**
	 * @return the a
	 */
	public Image getA() {
		return a;
	}
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}
	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	
}
