package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
	private static final int TF_MAX_LENGTH = 40;
	
	private String text;
	
	private Image fieldNorm;
	private Image fieldAdd;
	private Image fieldOnNorm;
	private Image fieldOnAdd;
	
	/**
	 * Textfield constructor. Creates a drawable textfield element for our GUI.
	 * @param x location of textfield
	 * @param y location of textfield
	 * @param text run-time text in textfield
	 * @param container necessary appgamegontainer for it to live in
	 * @throws SlickException probably the images cant be found
	 */
	public Textfield(float x, float y, String text, GameContainer container) throws SlickException {
		textfield = new TextField(container, RND.getFont_Normal(), (int) x, (int) y,
				TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		textfield.setBackgroundColor(null);
		textfield.setBorderColor(null);
		fieldNorm = new Image("resources/images_UI/textfield_Norm.png");
		fieldAdd = new Image("resources/images_UI/textfield_Add.png");
		fieldOnNorm = new Image("resources/images_UI/textfield2_Norm.png");
		fieldOnAdd = new Image("resources/images_UI/textfield2_Add.png");
		
		this.text = text;
		this.x = x;
		this.y = y;
		
		textfield.setText(text);
		textfield.setCursorPos(text.length());
		textfield.setMaxLength(TF_MAX_LENGTH);
		textfield.setFocus(false);
	}
	
	/**
	 * Set focus on this textfield on or off.
	 * @param focus to set
	 */
	public void setFocus(boolean focus) {
		textfield.setFocus(focus);
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
			RND.drawColor(graphics, fieldOnNorm, fieldOnAdd, 
					x - TF_BACKGROUND_DEVIATION, y - TF_BACKGROUND_DEVIATION, color);
		} else {
			RND.drawColor(graphics, fieldNorm, fieldAdd, 
					x - TF_BACKGROUND_DEVIATION, y - TF_BACKGROUND_DEVIATION, color);
		}
		RND.text(graphics, x, y, text, color);
	}
	
	
	
}
