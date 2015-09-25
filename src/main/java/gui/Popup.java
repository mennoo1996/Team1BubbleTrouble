package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
	private boolean active = false;
	private Button button;
	
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
		System.out.println(screenWidth);
		button = new Button(screenWidth / 2f - BUTTON_WIDTH / 2f, 
				screenHeight / 2f - BUTTON_HEIGHT / 2f, 
				BUTTON_WIDTH, BUTTON_HEIGHT, 
				new Image("resources/images_UI/Menu_Button_OK_Norm.png"), 
				new Image("resources/images_UI/Menu_Button_OK_Add.png"), 
				new Image("resources/images_UI/Menu_Button_OK2_Norm.png"), 
				new Image("resources/images_UI/Menu_Button_OK2_Add.png"));
	}
	
	/**
	 * Seet the popup's state.
	 * @param active whether or not it is active.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Checks if button is pressed.
	 * @param input Input context used.
	 * @return true once button is pressed.
	 */
	public boolean processButton(Input input) {
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && button.isMouseOver(input)) {
			active = false;
			return true;
		}
		return false;
	}
	
	/**
	 * Draw the popup.
	 * @param graphics context to draw in
	 * @param input the input context used.
	 * @param color to draw with
	 */
	public void drawColor(Graphics graphics, Input input, Color color) {
		if (active) {
			button.drawColor(graphics, input, color);
		}
	}
	
}
