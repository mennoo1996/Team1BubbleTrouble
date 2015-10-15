package guimenu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Class used to make a popup appear on-screen..
 * @author Mark
 *
 */
public class Popup {

	private String warning;
	private float screenWidth;
	private float screenHeight;
	private static final float BUTTON_WIDTH = 129;
	private static final float BUTTON_HEIGHT = 53;
	private static final float PAUSE_OVERLAY_COLOR_FACTOR = 0.75f;
	private static final int PAUSED_RECT_Y_DEVIATION = 150;
	private static final int BUTTON_OFFSET_Y = 40;
	private static final int SEPARATOR_OFFSET_X = -200;
	private static final int TEXT_OFFSET_X = 10;
	private static final int TEXT_OFFSET_Y = -40;
	private boolean active = false;
	private Button button;
	private Separator separator;
	
	/**
	 * Popup constructor method.
	 * @param warning string player gets to read.
	 * @param screenWidth width of the screen.
	 * @param screenHeight height of the screen.
	 * @throws SlickException means button images can't be found.
	 */
	public Popup(String warning, float screenWidth, float screenHeight) throws SlickException {
		this.warning = warning;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		button = new Button(screenWidth / 2f - RND.getInstance().getStringPixelWidth("> OK <") 
				/ 2f, screenHeight / 2f + BUTTON_OFFSET_Y, 
				BUTTON_WIDTH, BUTTON_HEIGHT, "> OK <");
		separator = new Separator(screenWidth / 2 + SEPARATOR_OFFSET_X,
				screenHeight / 2, false, "", screenWidth);
	}
	
	/**
	 * @return whether or not the popup is active.
	 */
	public boolean getActive() {
		return this.active;
	}
	
	/**
	 * Set the popup's state.
	 * @param active whether or not it is active.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Change the warning text in the popup.
	 * @param text to set as warning.
	 */
	public void setText(String text) {
		warning = text;
	}
	
	/**
	 * Checks if button is pressed.
	 * @param input Input context used.
	 */
	public void processButton(Input input) {
		if (button.isMouseOver(input)) {
			this.active = false;
		}
	}
	
	/**
	 * Draw the popup.
	 * @param graphics context to draw in
	 * @param input the input context used.
	 * @param color to draw with
	 */
	public void drawColor(Graphics graphics, Input input, Color color) {
		if (active) {
			Color overLay = new Color(0f, 0f, 0f, PAUSE_OVERLAY_COLOR_FACTOR);
			graphics.setColor(overLay);
			graphics.fillRect(0, 0, screenWidth, screenHeight
					- PAUSED_RECT_Y_DEVIATION);
			button.drawColor(graphics, input, color);
			RND.getInstance().textSpecifiedColor(graphics, 
					screenWidth / 2f - RND.getInstance().getStringPixelWidth(warning) / 2f 
					+ TEXT_OFFSET_X, screenHeight / 2f + TEXT_OFFSET_Y,
					warning, color);
			separator.drawColor(graphics, color);
		}
	}
	
}
