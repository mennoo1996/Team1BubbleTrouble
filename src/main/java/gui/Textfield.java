package gui;

import logic.MyRectangle;
import logic.RenderOptions;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

/**
 * Textfield class, representing a textfield in our GUI.
 * @author Mark
 */
public class Textfield {

	private float x;
	private float y;
	private TextField textfield;
	
	private static final int TEXT_FIELD_WIDTH = 800;
	private static final int TEXT_FIELD_HEIGHT = 60;
	private static final int TF_BACKGROUND_DEVIATION = 27;
	private static final int TC_X_DEVIATION = 15;
	private static final int TC_Y_DEVIATION = 7;
	private static final int TF_MAX_LENGTH = 40;
	
	private String text;
	
	private Image fieldNorm;
	private Image fieldAdd;
	private Image fieldOnNorm;
	private Image fieldOnAdd;
	private Image cursorNorm;
	private Image cursorAdd;
	
	private int stringlength;
	private int cursor;
	private boolean focus;
	
	/**
	 * Textfield constructor. Creates a drawable textfield element for our GUI.
	 * @param x location of textfield
	 * @param y location of textfield
	 * @param text run-time text in textfield
	 * @param container necessary appgamegontainer for it to live in
	 * @throws SlickException probably the images cant be found
	 */
	public Textfield(float x, float y, String text, GameContainer container) throws SlickException {
		textfield = new TextField(container, null, (int) x, (int) y,
				TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		textfield.setBackgroundColor(null);
		textfield.setBorderColor(null);
		fieldNorm = new Image("resources/images_UI/textfield_Norm.png");
		fieldAdd = new Image("resources/images_UI/textfield_Add.png");
		fieldOnNorm = new Image("resources/images_UI/textfield2_Norm.png");
		fieldOnAdd = new Image("resources/images_UI/textfield2_Add.png");
		cursorNorm = new Image("resources/images_UI/textcursor_normal.png");
		cursorAdd = new Image("resources/images_UI/textcursor_Add.png");
		this.text = text;
		this.stringlength = this.text.length();
		this.x = x;
		this.y = y;
		
		textfield.setText(text);
		textfield.setCursorPos(text.length());
		textfield.setMaxLength(TF_MAX_LENGTH);
		textfield.setFocus(false);
	}
	
	/**
	 * Provides updates to textfield, so it can properly run button inputs etc.
	 * @param input the Input object to use
	 */
	public void update(Input input) {
		// process mouse input
		if (getRectangle().contains(input.getMouseX(), input.getMouseY())
				&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.setFocus(true);
			textfield.setText("");
			cursor = 0;
		}
		// process the bloody textfield input edgecase
		if (textfield.hasFocus()) {
			if (!focus) {
				textfield.setText("");
				cursor = 0;
				focus = true;
			}
		} else {
			focus = false;
		}
		// process keyboard input
		if (textfield.hasFocus()) {
			processTextInput(input);
		} else {
			focus = false;
		}
	}
	
	/**
	 * Provides updates to keyboard functions, so the cursor etc can work properly.
	 * @param input the Input object to use.
	 */
	private void processTextInput(Input input) {
		// process typing in field
		if (stringlength < text.length()) {
			cursor += (text.length() - stringlength);
			stringlength = text.length();
		} else if (stringlength > text.length()) {
			cursor -= (stringlength - text.length());
			stringlength = text.length();
		}
		// safety check for empty field
		if (text.length() == 0) {
			stringlength = 0;
			cursor = 0;
		}
		// process arrow keys
		if (input.isKeyPressed(Input.KEY_RIGHT) && cursor < text.length()) {
			cursor++;
		} else if (input.isKeyPressed(Input.KEY_LEFT) && cursor > 0) {
			cursor--;
		}
		textfield.setCursorPos(cursor);
	}
	
	/**
	 * get bounding box rectangle of the button.
	 * @return the Rectangle object of this button.
	 */
	public MyRectangle getRectangle() {
		return new MyRectangle(x, y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
	}
	
	/**
	 * Set focus on this textfield on or off.
	 * @param focus to set
	 */
	public void setFocus(boolean focus) {
		this.textfield.setFocus(focus);
		this.focus = focus;
	}
	
	/**
	 * @return whether or not the text field has focus.
	 */
	public boolean hasFocus() {
		return textfield.hasFocus();
	}
	
	/**
	 * @return the text inside this textfield.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Set text inside the textfield to a string.
	 * @param text string to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Draws the textfield.
	 * @param graphics context to draw in.
	 * @param color to draw with.
	 */
	public void drawColor(Graphics graphics, Color color) {
		text = textfield.getText();
		if (textfield.hasFocus()) {
			RND.getInstance().drawColor(new RenderOptions(graphics, fieldOnNorm, fieldOnAdd, 
					x - TF_BACKGROUND_DEVIATION, y - TF_BACKGROUND_DEVIATION, color));
			if (cursor > 0 && cursor <= text.length()) {
				String s = text.substring(0, cursor);
				float length = RND.getInstance().getStringPixelWidth(s);
				RND.getInstance().drawColor(new RenderOptions(graphics, cursorNorm, cursorAdd, 
						x - TF_BACKGROUND_DEVIATION + length, y - TC_Y_DEVIATION, color));
			} else if (cursor == 0) {
				RND.getInstance().drawColor(new RenderOptions(graphics, cursorNorm, cursorAdd, 
						x - TF_BACKGROUND_DEVIATION + TC_X_DEVIATION, y - TC_Y_DEVIATION, color));
			}
		} else {
			RND.getInstance().drawColor(new RenderOptions(graphics, fieldNorm, fieldAdd, 
					x - TF_BACKGROUND_DEVIATION, y - TF_BACKGROUND_DEVIATION, color));
		}
		RND.getInstance().textSpecifiedColor(graphics, x, y, text, color);
	}
	
}
