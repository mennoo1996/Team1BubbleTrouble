package gui;
import logic.MyRectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
	private Image imageN;
	private Image imageA;
	private Image imageMouseoverN;
	private Image imageMouseoverA;

	private static final float HALF = 0.5f;
	
	/**
	 * Button constructor class.
	 * @param x coordinate
	 * @param y coordinate
	 * @param width of the button
	 * @param height of the button
	 * @param imageN normal image
	 * @param imageA additive image
	 */
	public Button(float x, float y, float width, float height, Image imageN, Image imageA) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.imageN = imageN;
		this.imageA = imageA;
	}
	
	/**
	 * Button constructor class.
	 * @param x coordinate
	 * @param y coordinate
	 * @param width of button
	 * @param height of button
	 * @param imageN normal image
	 * @param imageA additive image
	 * @param imageMouseoverN normal image
	 * @param imageMouseroverA additive image
	 */
	public Button(float x, float y, float width, float height, Image imageN, Image imageA,
			Image imageMouseoverN, Image imageMouseroverA) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.imageN = imageN;
		this.imageA = imageA;
		this.imageMouseoverN = imageMouseoverN;
		this.imageMouseoverA = imageMouseroverA;
	}
	
	/**
	 * returns boolean if the mouse is hovering over button right now.
	 * @param input input used to find mouse.
	 * @return boolean whether or not the mouse is hovering over the button.
	 */
	public boolean isMouseOver(Input input) {
		if (getRectangle().contains(input.getMouseX(), input.getMouseY())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Draw function that draws a button, using graphics/input to determine its state and draw.
	 * @param graphics context the drawing is done in.
	 * @param input used to determine the button's state (such as mouse-over)
	 * @param color the color used to draw this button
	 */
	public void drawColor(Graphics graphics, Input input, Color color) {
		if (isMouseOver(input)) {
			RND.drawColor(graphics, getImageMouseOverN(), 
					getImageMouseOverA(), getX(), getY(), color);
		} else {
			RND.drawColor(graphics, getImageN(), getImageA(), getX(), getY(), color);
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
			RND.drawColor(graphics, getImageMouseOverN(), 
					getImageMouseOverA(), getX(), getY(), color);
		} else {
			RND.drawColor(graphics, getImageN(), getImageA(), getX(), getY(), color);
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
	 * @return image_norm for this button
	 */
	public Image getImageN() {
		return imageN;
	}
	
	/**
	 * @return image_add for this button
	 */
	public Image getImageA() {
		return imageA;
	}
	
	/**
	 * @return image_norm for this button
	 */
	public Image getImageMouseOverN() {
		return imageMouseoverN;
	}
	 
	/**
	 * @return image_add for this button
	 */
	public Image getImageMouseOverA() {
		return imageMouseoverA;
	}
	
	/**
	 * @param imageN the image_norm to set
	 * @param imageA the image_add to set
	 */
	public void setImage(Image imageN, Image imageA) {
		this.imageN = imageN;
		this.imageA = imageA;
	}
	
	/**
	 * @param imageN the image_norm to set
	 * @param imageA the image_add to set
	 */
	public void setImageMouseOver(Image imageN, Image imageA) {
		this.imageMouseoverN = imageN;
		this.imageMouseoverA = imageA;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Button) {
			Button that = (Button) other;
			if (this.getX() == that.getX()
					&& this.getY() == that.getY()
					&& this.getWidth() == that.getWidth()
					&& this.getHeight() == that.getHeight()
					&& this.getImageN() == that.getImageN()
					&& this.getImageA() == that.getImageA()) {
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
