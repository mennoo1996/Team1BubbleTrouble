package gui;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Final class that holds simple draw functions.
 * @author Mark
 *
 */
public class RND {
	
	// Fonts used globally
	
	private static AngelCodeFont dosFontN; // normal font
	private static AngelCodeFont dosFontA; // additive font
	private static AngelCodeFont dosFontM; // masking (button) font
	
	// Images used globally (buttons, the terminal, etc)
	
	private static Image imageGameLogoN;
	private static Image imageGameLogoA;
	private static Image imageBackground;
	private static Image imageForeground;
	private static Image imageTerminal;

	private static Image imageButtonHeadN;
	private static Image imageButtonHeadA;
	private static Image imageButtonBodyN;
	private static Image imageButtonBodyA;
	private static Image imageButtonTailN;
	private static Image imageButtonTailA;

	// Miscellaneous variables used globally
	
	private static Color color;
	private static float opacity = 1.0f;

	private static final int BUTTON_BEGIN_OFFSET = 4;
	private static final int BUTTON_END_OFFSET = 22;
	private static final int BUTTON_X_OFFSET = 14;
	private static final int BUTTON_Y_OFFSET = 14;
	private static final int BUTTON_HEIGHT = 53;
	private static final float BUTTON_TEXT_OPACITY = 0.85f;
	
	/**
	 * Constructor for a renderer.
	 * 
	 */
	private RND() {
	 	// Don't use this
	}
	
	/**
	 * Initialize the RND - this function makes sure all the images/fonts
	 * used globally are properly loaded.
	 */
	public static void init() {
		try {
			imageBackground = new Image("resources/terminal/Screen_Underlayer.png");
			imageForeground = new Image("resources/terminal/Screen_Overlayer.png");
			imageTerminal = new Image("resources/terminal/Terminal_Base.png");
			imageGameLogoN = new Image("resources/images_UI/Menu_Logo_Norm.png");
			imageGameLogoA = new Image("resources/images_UI/Menu_Logo_Add.png");
			initButtonImages(); initFonts();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Make sure all the fonts are properly loaded.
	 * @throws SlickException this means some font assets are missing!
	 */
	private static void initFonts() throws SlickException {
		dosFontN = new AngelCodeFont("resources/images_Font/dosfont.fnt",
				"resources/images_Font/dosfont_Norm.png");
		dosFontA = new AngelCodeFont("resources/images_Font/dosfont.fnt",
				"resources/images_Font/dosfont_Add.png");
		dosFontM = new AngelCodeFont("resources/images_Font/dosfont.fnt",
				"resources/images_Font/dosfont_Mask.png");
	}
	
	/**
	 * Make sure all the images for buttons are properly loaded.
	 * @throws SlickException this means some button images are missing!
	 */
	private static void initButtonImages() throws SlickException {
		imageButtonHeadN = 
				new Image("resources/images_UI/images_Buttons/Menu_Button_Head_Norm.png");
		imageButtonHeadA = 
				new Image("resources/images_UI/images_Buttons/Menu_Button_Head_Add.png");
		imageButtonBodyN = 
				new Image("resources/images_UI/images_Buttons/Menu_Button_Body_Norm.png");
		imageButtonBodyA = 
				new Image("resources/images_UI/images_Buttons/Menu_Button_Body_Add.png");
		imageButtonTailN = 
				new Image("resources/images_UI/images_Buttons/Menu_Button_Tail_Norm.png");
		imageButtonTailA = 
				new Image("resources/images_UI/images_Buttons/Menu_Button_Tail_Add.png");
	}
	
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
		if (!a.getTexture().equals(n.getTexture())) {
			g.setDrawMode(Graphics.MODE_ADD);
			g.drawImage(a, x, y, new Color(color.r * opacity, color.g * opacity, 
					color.b * opacity));
			g.setDrawMode(Graphics.MODE_NORMAL);
		}
	}
	
	/**
	 * Draw function rendering objects to screen with color filter.
	 * @param g graphics context
	 * @param n normal image
	 * @param a additive image
	 * @param x x-location
	 * @param y y-location
	 * @param width width to draw the image with
	 * @param height height to draw the image with
	 * @param color color filter
	 */
	public static void drawColor(Graphics g, Image n, Image a,
			float x, float y, float width, float height,
			Color color) {
		n.draw(x, y, width, height, new Color(color.r * opacity, color.g * opacity, 
				color.b * opacity));
		if (!a.getTexture().equals(n.getTexture())) {
			g.setDrawMode(Graphics.MODE_ADD);
			a.draw(x, y, width, height, new Color(color.r * opacity, color.g * opacity, 
					color.b * opacity));
			g.setDrawMode(Graphics.MODE_NORMAL);
		}
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
		
		if (!a.getTexture().equals(n.getTexture())) {
			g.setDrawMode(Graphics.MODE_ADD);
			g.drawImage(a, x, y, x2, y2, srcx, srcy, srcx2, srcy2, 
					new Color(color.r * opacity, color.g * opacity, color.b * opacity)); 
			g.setDrawMode(Graphics.MODE_NORMAL);
		}
	}
	
	/**
	 * draws colored text to screen.
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
	 * @param color color to set
	 */
	public static void textSpecifiedColor(Graphics g, 
			float x, float y, String text, Color color) {
		Color newColor;
		
		if (color.a < 1.0f) {
			newColor = new Color(color.r, color.g, color.b, color.a);
		} else {
			newColor = new Color(color.r, color.g, color.b, opacity);
		}
		
		dosFontN.drawString(x, y, text, newColor);
		if (color.a == 1.0f) {
			g.setDrawMode(Graphics.MODE_ADD);
			dosFontA.drawString(x, y, text, 
					new Color(color.r * opacity, color.g * opacity, color.b * opacity));
			g.setDrawMode(Graphics.MODE_NORMAL);
		}
	}
	
	/**
	 * draws uncolored text to screen.
	 * @param x location
	 * @param y location
	 * @param text to draw
	 */
	public static void textNoColor(float x, float y, String text) {
		dosFontN.drawString(x, y, text);
	}
	
	/**
	 * Draws a button's highlight (when you mouse-over) to the screen.
	 * @param g the context to draw in.
	 * @param button the button to draw.
	 */
	public static void drawButtonHighlight(Graphics g, Button button) {
		// draw head
		RND.drawColor(g, imageButtonHeadN, imageButtonHeadA, 
				button.getX() - BUTTON_X_OFFSET, 
				button.getY() - BUTTON_Y_OFFSET, color);
		// draw body
		RND.drawColor(g, imageButtonBodyN, imageButtonBodyA,
				button.getX() + BUTTON_BEGIN_OFFSET, button.getY() - BUTTON_Y_OFFSET,
				dosFontM.getWidth(button.getText()) - BUTTON_END_OFFSET, BUTTON_HEIGHT, color);
		// draw tail
		RND.drawColor(g, imageButtonTailN, imageButtonTailA, 
				button.getX() 
				+ BUTTON_BEGIN_OFFSET + dosFontM.getWidth(button.getText()) - BUTTON_END_OFFSET, 
				button.getY() - BUTTON_Y_OFFSET, color);
		
		// draw text
		dosFontM.drawString(button.getX(), button.getY(), button.getText(), 
				new Color(0, 0, 0, BUTTON_TEXT_OPACITY));
		RND.textSpecifiedColor(g, button.getX(), button.getY(), button.getText(), 
				new Color(color.r, color.g, color.b, 1f - BUTTON_TEXT_OPACITY));
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
	
	/**
	 * Returns the pixel width of rendered text.
	 * @param text to examine
	 * @return pixel width of text in int
	 */
	public static int getStringPixelWidth(String text) {
		return dosFontN.getWidth(text);
	}
	
	/**
	 * Draws the background image onto the screen.
	 * @param g the context to draw in.
	 */
	public static void drawBackground(Graphics g) {
		g.drawImage(imageBackground, 0, 0);
	}
	
	/**
	 * Draws the foreground and terminal images onto the screen.
	 * @param g the context to draw in.
	 */
	public static void drawForeGround(Graphics g) {
		g.drawImage(imageForeground, 0, 0);
		g.drawImage(imageTerminal, 0, 0);
	}
	
	/**
	 * Draws the game's logo at given location.
	 * @param g context to draw in.
	 * @param x coordinate x.
	 * @param y coordinate y.
	 */
	public static void drawLogo(Graphics g, int x, int y) {
		drawColor(g, imageGameLogoN, imageGameLogoA, x, y, color);
	}
	
}
