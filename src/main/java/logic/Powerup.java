package logic;

import java.util.concurrent.TimeUnit;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gui.GameState;
import gui.MainGame;
import gui.RND;
/**
 * Created by alexandergeenen on 09/09/15.
 */
public class Powerup {
	
	private static Image laserImageN;
	private static Image laserImageA;
	private static Image shieldImageN;
	private static Image shieldImageA;
	private static Image vineImageN;
	private static Image vineImageA;
	private static Image freezeImageN;
	private static Image freezeImageA;
	private static Image slowImageN;
	private static Image slowImageA;
	private static Image fastImageN;
	private static Image fastImageA;
	private static Image healthImageN;
	private static Image healthImageA;
	private static Image randomImageN;
	private static Image randomImageA;
    /**
     * The different powerup types.
     */
    public enum PowerupType {
    	
    	
    	SHIELD, SPIKY, INSTANT, FREEZE, SLOW, FAST, HEALTH, RANDOM;
    	
    	private Image imageN;
    	private Image imageA;
    	
    	static {
    		SHIELD.imageA = shieldImageA;
    		SHIELD.imageN = shieldImageN;
    		SPIKY.imageA = vineImageA;
    		SPIKY.imageN = vineImageN;
    		INSTANT.imageA = laserImageA;
    		INSTANT.imageN = laserImageN;
    		FREEZE.imageA = freezeImageA;
    		FREEZE.imageN = freezeImageN;
    		SLOW.imageA = slowImageA;
    		SLOW.imageN = slowImageN;
    		FAST.imageA = fastImageA;
    		FAST.imageN = fastImageN;
    		HEALTH.imageA = healthImageA;
    		HEALTH.imageN = healthImageN;
    		RANDOM.imageA = randomImageA;
    		RANDOM.imageN = randomImageN;
    	}
    	
    	/**
    	 * Get the image A.
    	 * @return the imageA
    	 */
    	public Image getImageA() {
    		return imageA;
    	}
    	
    	/**
    	 * Get the image N.
    	 * @return the imageN
    	 */
    	public Image getImageN() {
    		return imageN;
    	}
    }

    private static final float POWERUP_WIDTH = 40;
    private static final float POWERUP_HEIGHT = 40;
    private static final float POWERUP_SPEED = 200f;
    private static final int POWERUP_TIME = 5;
    private static final int SECONDS_TO_MS = 1000;
	private static final int POWERUP_IMAGE_OFFSET = 12;


    private float x, y, width, height, xId, yId;
    private long timeRemaining;
    private PowerupType type;

    /**
     * Create a new powerup element.
     * @param x the x coord
     * @param y the y coord
     * @param power the type of powerup
     */
    public Powerup(float x, float y, PowerupType power) {
        this.x = x;
        this.y = y;
        this.xId = x;
        this.yId = y;
        this.width = POWERUP_WIDTH;
        this.height = POWERUP_HEIGHT;
        this.type = power;
        this.timeRemaining = TimeUnit.SECONDS.toMillis(POWERUP_TIME);
        //System.out.println("Poooooow: " + power);
    }
    
    /**
     * Draw this powerup.
     * @param mainGame the mainGame that uses this powerup
     * @param graphics the grapihcs object used to draw things on screen.
     */
    public void draw(Graphics graphics, MainGame mainGame) {
    	RND.drawColor(graphics, type.getImageN(), type.getImageA(), x - POWERUP_IMAGE_OFFSET,
    			y - POWERUP_IMAGE_OFFSET, mainGame.getColor());
    }
    
    /**
     * Load the powerup images.
     * @throws SlickException if something goes wrong / file not found
     */
    public static void loadImages() throws SlickException {
		laserImageN = new Image("resources/images_Powerup/laserPowerup_Norm.png");
		laserImageA = new Image("resources/images_Powerup/laserPowerup_Add.png");
		shieldImageN = new Image("resources/images_Powerup/shieldPowerup_Norm.png");
		shieldImageA = new Image("resources/images_Powerup/shieldPowerup_Add.png");
		vineImageN = new Image("resources/images_Powerup/vinePowerup_Norm.png");
		vineImageA = new Image("resources/images_Powerup/vinePowerup_Add.png");
		freezeImageN = new Image("resources/images_Powerup/freezePowerup_Norm.png");
		freezeImageA = new Image("resources/images_Powerup/freezePowerup_Add.png");
		slowImageN = new Image("resources/images_Powerup/slowPowerup_Norm.png");
		slowImageA = new Image("resources/images_Powerup/slowPowerup_Add.png");
		fastImageN = new Image("resources/images_Powerup/fastPowerup_Norm.png");
		fastImageA = new Image("resources/images_Powerup/fastPowerup_Add.png");
		healthImageN = new Image("resources/images_Powerup/healthPowerup_Norm.png");
		healthImageA = new Image("resources/images_Powerup/healthPowerup_Add.png");
		randomImageN = new Image("resources/images_Powerup/randomPowerup_Norm.png");
		randomImageA = new Image("resources/images_Powerup/randomPowerup_Add.png");
    }
    
    /**
     * Create a string out of a Powerup.
     * @return Powerup as a string
     */
    @Override
    public String toString() {
    	String res = "POWERUP " + this.xId + " " + this.yId + " " + this.type + " ";
    	return res;
    }

    /**
     * Update Powerups graphical thingy.
     * @param gameState Game State
     * @param containerHeight Game Container
     * @param deltaFloat Delta
     */
    public void update(GameState gameState, float containerHeight, float deltaFloat) {
        if (!gameState.isPaused()) {
            timeRemaining -= deltaFloat * SECONDS_TO_MS;
        }
        if ((this.y + POWERUP_HEIGHT) < containerHeight - gameState.getFloor().getHeight()) {
            this.y += POWERUP_SPEED * deltaFloat;
        } else {
            this.y = containerHeight - gameState.getFloor().getHeight() - POWERUP_HEIGHT;
        }
    }

    /**
     * @return This Powerup's Rectangle
     */
    public MyRectangle getRectangle() {
        return new MyRectangle(x, y, width, height);
    }

    /**
     * @return powerup's x coord
     */
    public float getX() {
        return x;
    }

    /**
     * 
     * @return .
     */
    public float getxId() {
		return xId;
	}

    /**
     * 
     * @param xId .
     */
	public void setxId(float xId) {
		this.xId = xId;
	}

	/**
	 * 
	 * @return .
	 */
	public float getyId() {
		return yId;
	}

	/**
	 * 
	 * @param yId .
	 */
	public void setyId(float yId) {
		this.yId = yId;
	}

	/**
     * @return powerup's x coord
     */
    public float getY() {
        return y;
    }
    
    /**
     * @return powerup's x coord center
     */
    public float getCenterX() {
        return x + POWERUP_WIDTH / 2;
    }

    /**
     * @return powerup's x coord center
     */
    public float getCenterY() {
        return y + POWERUP_WIDTH / 2;
    }

    /**
     * @return powerup's type
     */
    public PowerupType getType() {
        return type;
    }

    /**
     * @return whether or not to remove item
     */
    public boolean removePowerup() {
        return timeRemaining <= 0;
    }
    
    
    /**
	 * @return the timeRemaining
	 */
	public long getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * Clone the powerup.
	 */
	@Override
	public Powerup clone() {
		Powerup res = new Powerup(x, y, type);
		res.setTimeRemaining(timeRemaining);
		res.setxId(xId);
		res.setyId(yId);
		return res;
	}

    /**
     * Set the time remaining.
     * @param time the time
     */
    public void setTimeRemaining(long time) {
    	timeRemaining = time;
    }
}
