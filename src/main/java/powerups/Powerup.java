package powerups;

import java.util.concurrent.TimeUnit;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import guigame.GameState;
import logic.MyRectangle;
/**
 * Created by alexandergeenen on 09/09/15.
 */
public class Powerup implements Cloneable {

    /**
     * The different powerup types.
     */
    public enum PowerupType {
    	SHIELD, SPIKY, INSTANT, FREEZE, SLOW, FAST, HEALTH, RANDOM;
    	
    	private Image imageN;
    	private Image imageA;
    	
    	static {
    		SHIELD.imageA = imageShieldImageA;
    		SHIELD.imageN = imageShieldImageN;
    		SPIKY.imageA = imageVineImageA;
    		SPIKY.imageN = imageVineImageN;
    		INSTANT.imageA = imageLaserImageA;
    		INSTANT.imageN = imageLaserImageN;
    		FREEZE.imageA = imageFreezeImageA;
    		FREEZE.imageN = imageFreezeImageN;
    		SLOW.imageA = imageSlowImageA;
    		SLOW.imageN = imageSlowImageN;
    		FAST.imageA = imageFastImageA;
    		FAST.imageN = imageFastImageN;
    		HEALTH.imageA = imageHealthImageA;
    		HEALTH.imageN = imageHealthImageN;
    		RANDOM.imageA = imageRandomImageA;
    		RANDOM.imageN = imageRandomImageN;
    	}
    	
    	/**
    	 * Get the image A.
    	 * @return the imagea.
    	 */
    	public Image getImageA() {
    		return imageA;
    	}
    	
    	/**
    	 * Get the imageN.
    	 * @return the imagen.
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


    private float x, y, width, height, xId, yId;
    private long timeRemaining;
    private PowerupType type;
    
    private static Image imageLaserImageN;
	private static Image imageLaserImageA;
	private static Image imageShieldImageN;
	private static Image imageShieldImageA;
	private static Image imageVineImageN;
	private static Image imageVineImageA;
	private static Image imageFreezeImageN;
	private static Image imageFreezeImageA;
	private static Image imageSlowImageN;
	private static Image imageSlowImageA;
	private static Image imageFastImageN;
	private static Image imageFastImageA;
	private static Image imageHealthImageN;
	private static Image imageHealthImageA;
	private static Image imageRandomImageN;
	private static Image imageRandomImageA;

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
    }
    
    /**
     * Load the images for powerups.
     * @throws SlickException if something goes wrong / file not found
     */
    public static void loadImages() throws SlickException {
    	String location = "resources/images_Powerup/";
		imageLaserImageN = new Image(location + "laserPowerup_Norm.png");
		imageLaserImageA = new Image(location + "laserPowerup_Add.png");
		imageShieldImageN = new Image(location + "shieldPowerup_Norm.png");
		imageShieldImageA = new Image(location + "shieldPowerup_Add.png");
		imageVineImageN = new Image(location + "vinePowerup_Norm.png");
		imageVineImageA = new Image(location + "vinePowerup_Add.png");
		imageFreezeImageN = new Image(location + "freezePowerup_Norm.png");
		imageFreezeImageA = new Image(location + "freezePowerup_Add.png");
		imageSlowImageN = new Image(location + "slowPowerup_Norm.png");
		imageSlowImageA = new Image(location + "slowPowerup_Add.png");
		imageFastImageN = new Image(location + "fastPowerup_Norm.png");
		imageFastImageA = new Image(location + "fastPowerup_Add.png");
		imageHealthImageN = new Image(location + "healthPowerup_Norm.png");
		imageHealthImageA = new Image(location + "healthPowerup_Add.png");
		imageRandomImageN = new Image(location + "randomPowerup_Norm.png");
		imageRandomImageA = new Image(location + "randomPowerup_Add.png");
   
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
     * Update this powerup.
     * @param gameState the gameState that uses this powerup.
     * @param containerHeight the GameContainer that we are playing in
     * @param deltaFloat the time in seconds since last update
     */
    public void update(GameState gameState, float containerHeight, float deltaFloat) {
        if (!gameState.getLogicHelper().isPaused()) {
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
     * @return the xId.
     */
    public float getxId() {
		return xId;
	}

    /**
     * 
     * @param xId the xId to set.
     */
	public void setxId(float xId) {
		this.xId = xId;
	}

	/**
	 * 
	 * @return the yId.
	 */
	public float getyId() {
		return yId;
	}

	/**
	 * 
	 * @param yId the yId to set.
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
     * @return powerup's y coord center
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
	 * @throws CloneNotSupportedException if the clone is not supported.
	 */
	@Override
	public Powerup clone() throws CloneNotSupportedException {
		super.clone();
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
