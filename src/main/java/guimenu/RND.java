package guimenu;

import logic.RenderOptions;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import powerups.Powerup;

/**
 * Final class that holds simple draw functions.
 * @author Mark
 *
 */
public final class RND {
	
	private static volatile RND instance;
	
	// Fonts used globally
	
	private AngelCodeFont dosFontN; // normal font
	private AngelCodeFont dosFontA; // additive font
	private AngelCodeFont dosFontM; // masking (button) font
	
	// Images used globally (buttons, the terminal, etc)
	
	private Image imageGameLogoN;
	private Image imageGameLogoA;
	private Image imageBackground;
	private Image imageForeground;
	private Image imageTerminal;

	private Image imageButtonHeadN;
	private Image imageButtonHeadA;
	private Image imageButtonBodyN;
	private Image imageButtonBodyA;
	private Image imageButtonTailN;
	private Image imageButtonTailA;

	
	
	// Miscellaneous variables used globally
	
	private Color color;
	private float opacity = 1.0f;

	private static final int BUTTON_BEGIN_OFFSET = 4;
	private static final int BUTTON_END_OFFSET = 22;
	private static final int BUTTON_X_OFFSET = 14;
	private static final int BUTTON_Y_OFFSET = 14;
	private static final int BUTTON_HEIGHT = 53;
	private static final float BUTTON_TEXT_OPACITY = 0.85f;
	private static final int POWERUP_IMAGE_OFFSET = 12;
	
	/**
	 * Constructor for a renderer.
	 * 
	 */
	private RND() {
		
	}
	
	/**
	 * Return instance of RND.
	 * @return	the instance
	 */
	public static RND getInstance() {
	 	if (instance == null) {
	 		synchronized (RND.class) {
	 			if (instance == null) {
	 				instance = new RND();
	 			}
	 		}
	 	}
	 	
	 	return instance;
	}
	
	/**
	 * Initialize the RND - this function makes sure all the images/fonts
	 * used globally are properly loaded.
	 */
	public void init() {
		try {
			imageBackground = new Image("resources/terminal/Screen_Underlayer.png");
			imageForeground = new Image("resources/terminal/Screen_Overlayer.png");
			imageTerminal = new Image("resources/terminal/Terminal_Base.png");
			imageGameLogoN = new Image("resources/images_UI/Menu_Logo_Norm.png");
			imageGameLogoA = new Image("resources/images_UI/Menu_Logo_Add.png");
			initButtonImages();
			initFonts(); 
			Powerup.loadImages();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Make sure all the fonts are properly loaded.
	 * @throws SlickException this means some font assets are missing!
	 */
	public void initFonts() throws SlickException {
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
	private void initButtonImages() throws SlickException {
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
	public void setOpacity(float newOpacity) {
		opacity = newOpacity;
	}
	
	/**
	 * @return the opacity of on-screen elements.
	 */
	public float getOpacity() {
		return opacity;
	}
	
	/**
	 * set screen color to new color.
	 * @param newColor to set
	 */
	public void setColor(Color newColor) {
		color = newColor;
	}
	
	/**
	 * Draw function rendering objects to screen with color filter.
	 * @param ro - the renderoptions to render with.
	 */
	public void drawColor(RenderOptions ro) {
		ro.getG().drawImage(ro.getN(), ro.getX(), ro.getY(), 
				new Color(color.r, color.g, color.b, opacity));
		if (!ro.getA().getTexture().equals(ro.getN().getTexture())) {
			ro.getG().setDrawMode(Graphics.MODE_ADD);
			ro.getG().drawImage(ro.getA(), ro.getX(), ro.getY(), 
					new Color(color.r * opacity, color.g * opacity, 
					color.b * opacity));
			ro.getG().setDrawMode(Graphics.MODE_NORMAL);
		}
	}
	
	/**
	 * Draw function rendering objects to screen with color filter.
	 * @param ro the renderoptions to render with
	 * @param width width to draw the image with
	 * @param height height to draw the image with
	 */
	public void drawColor(RenderOptions ro, float width, float height) {
		ro.getN().draw(ro.getX(), ro.getY(), width, height, new Color(color.r * opacity, 
				color.g * opacity, color.b * opacity));
		if (!ro.getA().getTexture().equals(ro.getN().getTexture())) {
			ro.getG().setDrawMode(Graphics.MODE_ADD);
			ro.getA().draw(ro.getX(), ro.getY(), width, height, new Color(color.r * opacity, 
					color.g * opacity, color.b * opacity));
			ro.getG().setDrawMode(Graphics.MODE_NORMAL);
		}
	}

	
	/**
	 * Draw stretched function, with color settings.
	 * @param ro the renderoptions to render with
	 * @param x2 x-right
	 * @param y2 y-bottom
	 * @param srcx resx
	 * @param srcy resy
	 * @param srcx2 stretchx
	 * @param srcy2 stretchy
	 */
	public void drawColor(RenderOptions ro, float x2, float y2, 
			float srcx, float srcy, float srcx2, float srcy2) {
		ro.getG().drawImage(ro.getN(), ro.getX(), ro.getY(), x2, y2, srcx, srcy, srcx2, srcy2,
				new Color(color.r, color.g, color.b, opacity)); 
		
		if (!ro.getA().getTexture().equals(ro.getN().getTexture())) {
			ro.getG().setDrawMode(Graphics.MODE_ADD);
			ro.getG().drawImage(ro.getA(), ro.getX(), ro.getY(), x2, y2, srcx, srcy, srcx2, srcy2, 
					new Color(color.r * opacity, color.g * opacity, color.b * opacity)); 
			ro.getG().setDrawMode(Graphics.MODE_NORMAL);
		}
	}
	
	/**
	 * draws colored text to screen.
	 * @param g graphics context
	 * @param x location
	 * @param y location
	 * @param text to draw
	 */
	public void text(Graphics g, 
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
	public void textSpecifiedColor(Graphics g, 
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
	public void textNoColor(float x, float y, String text) {
		dosFontN.drawString(x, y, text);
	}
	
	/**
	 * Draws a button's highlight (when you mouse-over) to the screen.
	 * @param g the context to draw in.
	 * @param button the button to draw.
	 */
	public void drawButtonHighlight(Graphics g, Button button) {
		// draw head
		RenderOptions ro1 = new RenderOptions(g, imageButtonHeadN, imageButtonHeadA, 
				button.getX() - BUTTON_X_OFFSET, 
				button.getY() - BUTTON_Y_OFFSET, color);
		RND.getInstance().drawColor(ro1);
		// draw body
		RND.getInstance().drawColor(new RenderOptions(g, imageButtonBodyN, imageButtonBodyA,
				button.getX() + BUTTON_BEGIN_OFFSET, button.getY() - BUTTON_Y_OFFSET, color),
				dosFontM.getWidth(button.getText()) - BUTTON_END_OFFSET, BUTTON_HEIGHT);
		// draw tail
		RenderOptions ro3 = new RenderOptions(g, imageButtonTailN, imageButtonTailA, 
				button.getX() + BUTTON_BEGIN_OFFSET + dosFontM.getWidth(button.getText()) 
				- BUTTON_END_OFFSET, button.getY() - BUTTON_Y_OFFSET, color);
		RND.getInstance().drawColor(ro3);
		
		// draw text
		dosFontM.drawString(button.getX(), button.getY(), button.getText(), 
				new Color(0, 0, 0, BUTTON_TEXT_OPACITY));
		RND.getInstance().textSpecifiedColor(g, button.getX(), button.getY(), button.getText(), 
				new Color(color.r, color.g, color.b, 1f - BUTTON_TEXT_OPACITY));
	}
	
	/**
	 * draws simple image to screen.
	 * @param graphics context
	 * @param imageNorm to draw
	 * @param x location
	 * @param y location
	 */
	public void draw(Graphics graphics, Image imageNorm,
			int x, int y) {
		graphics.drawImage(imageNorm, x, y);
	}
	
	/**
	 * Draws a given powerup to the screen.
	 * @param g the graphics context to draw in
	 * @param powerup the powerup to draw
	 */
	public void drawPowerup(Graphics g, Powerup powerup) {
		Image imageN = powerup.getType().getImageN();
		Image imageA = powerup.getType().getImageA();
		float x = powerup.getX() - POWERUP_IMAGE_OFFSET, 
			  y = powerup.getY() - POWERUP_IMAGE_OFFSET;
		
		RND.getInstance().drawColor(new RenderOptions(g, imageN, imageA, x, y, color));
	}
	
	/**
	 * Returns the pixel width of rendered text.
	 * @param text to examine
	 * @return pixel width of text in int
	 */
	public int getStringPixelWidth(String text) {
		return dosFontN.getWidth(text);
	}
	
	/**
	 * Draws the background image onto the screen.
	 * @param g the context to draw in.
	 */
	public void drawBackground(Graphics g) {
		g.drawImage(imageBackground, 0, 0, color);
	}
	
	/**
	 * Draws the foreground and terminal images onto the screen.
	 * @param g the context to draw in.
	 */
	public void drawForeGround(Graphics g) {
		g.drawImage(imageForeground, 0, 0);
		g.drawImage(imageTerminal, 0, 0);
	}
	
	/**
	 * Draws the game's logo at given location.
	 * @param g context to draw in.
	 * @param x coordinate x.
	 * @param y coordinate y.
	 */
	public void drawLogo(Graphics g, int x, int y) {
		drawColor(new RenderOptions(g, imageGameLogoN, imageGameLogoA, x, y, color));
	}
	
}
