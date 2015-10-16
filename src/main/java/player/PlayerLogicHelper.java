package player;

import logic.MyRectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import guigame.GameState;
import guimenu.MainGame;

/**
 * Class which helps player with processing most basic logic crunching and data.
 * Should only be used by the Player class!
 * @author Mark
 *
 */
public class PlayerLogicHelper {

	private Player player;
	private MainGame mainGame;
	private GameState gameState;
	
	private float x;
	private float y;
	private final float startX;
	private final float startY;
	private float width;
	private float height;
	private Image imageN;
	private Image imageA;
	private Image shieldImageN;
	private Image shieldImageA;
	private SpriteSheet spritesheetN;
	private SpriteSheet spritesheetA;
	
	private static final int SPRITESHEET_VALUE = 120;
	private static final float HALF = 0.5f;
	
	/**
	 * Constructor class for a PlayerLogicHelper object.
	 * @param player	- the player this helper belongs to.
	 * @param mainGame	- the gamestate the player plays in.
	 * @param gameState	- the game the player plays in.
	 * @param x - the starting x.
	 * @param y - the starting y.
	 */
	public PlayerLogicHelper(Player player, MainGame mainGame, GameState gameState, 
			float x, float y) {
		this.player = player;
		this.mainGame = mainGame;
		this.gameState = gameState;
		this.startX = x;
		this.startY = y;
	}
	
	/**
	 * Called by respawn() in player on a player's respawn. Resets location.
	 */
	public void respawn() {
		this.x = startX;
		this.y = startY;
	}
	
	/**
	 * Return a bounding box rectangle of the player.
	 * @return a bounding box rectangle
	 */
	public MyRectangle getRectangle() {
		return new MyRectangle(x, y, width, height);
	}
	
	/**
	 * Get the center X coordinate.
	 * @return the center x
	 */
	public float getCenterX() {
		return x + (HALF * width);
	}
	
	/**
	 * Get the center Y coordinate.
	 * @return the center Y
	 */
	public float getCenterY() {
		return y + (HALF * height);
	}
	
	/**
	 * Get the maximum x value.
	 * @return the maximum x.
	 */
	public float getMaxX() {
		return x + width;
	}
	
	/**
	 * Get the maximum Y value.
	 * @return the maximum Y.
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
	 * @return the starting x
	 */
	public float getStartX() {
		return startX;
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
	 * @return the starting y
	 */
	public float getStartY() {
		return startY;
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
	 * @return the player image_norm
	 */
	public Image getImageN() {
		return imageN;
	}
	
	/**
	 * @return the player image_add
	 */
	public Image getImageA() {
		return imageA;
	}
	
	/**
	 * sets player images.
	 * @param imageN normal image
	 * @param imageA additive image
	 */
	public void setImage(Image imageN, Image imageA) {
		this.imageN = imageN;
		this.imageA = imageA;
		this.spritesheetN = new SpriteSheet(imageN, SPRITESHEET_VALUE, SPRITESHEET_VALUE);
		this.spritesheetA = new SpriteSheet(imageA, SPRITESHEET_VALUE, SPRITESHEET_VALUE);
	}
	
	/**
	 * sets player shield images.
	 * @param imageN normal image
	 * @param imageA additive image
	 */
	public void setShieldImage(Image imageN, Image imageA) {
		this.shieldImageN = imageN;
		this.shieldImageA = imageA;
	}
	
	/**
	 * @return the shield image_norm
	 */
	public Image getShieldImageN() {
		return shieldImageN;
	}
	
	/**
	 * @return the shield image_add
	 */
	public Image getShieldImageA() {
		return shieldImageA;
	}
	
	/**
	 * @return the player spritesheet_norm
	 */
	public SpriteSheet getSpritesheetN() {
		return spritesheetN;
	}
	
	/**
	 * @return the player spritesheet_add
	 */
	public SpriteSheet getSpritesheetA() {
		return spritesheetA;
	}
	
	/**
	 * @param spritesheetN the spritesheet_norm to set.
	 * @param spritesheetA the spritesheet_add to set.
	 */
	public void setSpritesheet(SpriteSheet spritesheetN, SpriteSheet spritesheetA) {
		this.spritesheetN = spritesheetN;
		this.spritesheetA = spritesheetA;
	}
	
}
