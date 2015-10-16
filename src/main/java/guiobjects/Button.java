package guiobjects;
import logic.MyRectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Class that respresents a button - you can click on it and something might happen.
 * @author Menno
 *
 */
public class Button {
	private float x;
	private float y;
	private float width;
	private float height;
	private String text;
	
	private static final float HALF = 0.5f;
	
	/**
	 * Button constructor class.
	 * @param x coordinate
	 * @param y coordinate
	 * @param width of the button
	 * @param height of the button
	 * @param text the text to show in the button
	 */
	public Button(float x, float y, float width, float height, String text) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	/**
	 * returns boolean if the mouse is hovering over button right now.
	 * @param input input used to find mouse.
	 * @return boolean whether or not the mouse is hovering over the button.
	 */
	public boolean isMouseOver(Input input) {
		return getRectangle().contains(input.getMouseX(), input.getMouseY());
	}
	
	/**
	 * Draw function that draws a button, using graphics/input to determine its state and draw.
	 * @param graphics context the drawing is done in.
	 * @param input used to determine the button's state (such as mouse-over)
	 * @param color the color used to draw this button
	 */
	public void drawColor(Graphics graphics, Input input, Color color) {
		if (isMouseOver(input)) {
			RND.getInstance().drawButtonHighlight(graphics, this);
		} else {
			RND.getInstance().text(graphics, x, y, text);
		}
	}
	
	/**
	 * Draw function that draws a button, using graphics/input to determine its state and draw.
	 * This method has a boolean to disable/enable mouse-over capabilities. 
	 * @param graphics context the drawing is done in.
	 * @param input used to determine the button's state (such as mouse-over)
	 * @param color the color used to draw this button
	 * @param enable whether mouse-over highlighting is enabled
	 */
	public void drawColor(Graphics graphics, Input input, Color color, boolean enable) {
		if (isMouseOver(input) && enable) {
			RND.getInstance().drawButtonHighlight(graphics, this);
		} else {
			RND.getInstance().text(graphics, x, y, text);
		}
	}
	
	/**
	 * get bounding box rectangle of the button.
	 * @return the Rectangle object of this button.
	 */
	public MyRectangle getRectangle() {
		return new MyRectangle(x, y, width, height);
	}
	
	/**
	 * Get the center x value of the button.
	 * @return the x value of the center of the button.
	 */
	public float getCenterX() {
		return x + (HALF * width);
	}
	
	/**
	 * Get the center y value of the button.
	 * @return the y value of the center of the button.
	 */
	public float getCenterY() {
		return y + (HALF * height);
	}
	
	/**
	 * Get the maximum x value of the button.
	 * @return the maximum x value of this button.
	 */
	public float getMaxX() {
		return x + width;
	}
	
	/**
	 * Get the maximum y value of the button.
	 * @return the maximum y value of this button.
	 */
	public float getMaxY() {
		return y + height;
	}
	
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Set the text this button displays.
	 * @param text the text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return the text in this button.
	 */
	public String getText() {
		return text;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Button) {
			Button that = (Button) other;
			if (this.getX() == that.getX()
					&& this.getY() == that.getY()
					&& this.getWidth() == that.getWidth()
					&& this.getHeight() == that.getHeight()
					&& this.text.equals(that.getText())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}
}
