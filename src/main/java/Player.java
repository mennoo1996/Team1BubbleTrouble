import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Player {
	//Method 1 code
	//int lifeCount;

	private boolean laser;
	private boolean shield;
	private float x;
	private float y;
	private float width;
	private float height;
	private Image image;
	private boolean freeToRoam;
	private MainGame mg;
	private GameState gs;
	private Gate intersectingGate;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param image
	 */
	public Player(float x, float y, float width, float height, Image image, MainGame mg) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.laser = false;
		this.shield = false;
		//Method 1 code
		//this.lifeCount = lifeCount;
		this.mg = mg;
		this.gs = (GameState) mg.getState(mg.GAME_STATE);
	}
	
	public void update(float deltaFloat) {
		processGates();
		processWeapon(mg.getContainer(), deltaFloat);
		processPlayerMovement(mg.getContainer(), deltaFloat);
	}
	
	private void processGates() {
		// Check the intersection of a player with a gate
		freeToRoam = true;
		for(Gate someGate :gs.gateList) {
			if(this.getRectangle().intersects(someGate.getRectangle())) {
				freeToRoam = false;
				intersectingGate = someGate;
			}
		}
		// Reset intersecting gate to none if there is no intersection
		if(freeToRoam) {
			intersectingGate = null;
		}
	}

	private void processWeapon(GameContainer container, float deltaFloat) {
		// Shoot laser when spacebar is pressed and no laser is active
		if (gs.input.isKeyPressed(Input.KEY_SPACE) && !gs.shot) {
            gs.shot = true;
            float x = this.getCenterX();
            gs.laser = new Laser(x, container.getHeight() - gs.floor.getHeight(), mg.laserSpeed, mg.laserWidth);
        }

		// Update laser
		if (gs.shot) {
            gs.laser.update(gs, deltaFloat);
            // Disable laser when it has reached the ceiling
            if (!gs.laser.visible) {
                gs.shot = false;
            }
        }
	}
	
	private void processPlayerMovement(GameContainer container, float deltaFloat) {
		// Walk left when left key pressed and not at left wall OR a gate
		if (gs.input.isKeyDown(Input.KEY_LEFT) && this.getX() > gs.leftWall.getWidth()) {
            if(freeToRoam || (this.getCenterX() < intersectingGate.getRectangle().getCenterX())) {
            	this.setX(this.getX() - mg.playerSpeed * deltaFloat);
            }
        }

		// Walk right when right key pressed and not at right wall OR a gate
		if (gs.input.isKeyDown(Input.KEY_RIGHT) && this.getMaxX() < (container.getWidth() - gs.rightWall.getWidth())) {
           if(freeToRoam || (this.getCenterX() > intersectingGate.getRectangle().getCenterX())) {
        	   this.setX(this.getX() + mg.playerSpeed * deltaFloat);
           }
        }
	}

	/**
	 * Return a bounding box rectangle of the player
	 * @return
	 */
	public MyRectangle getRectangle() {
		return new MyRectangle(x,y,width,height);
	}
	
	/**
	 * Get the center X coordinate
	 * @return
	 */
	public float getCenterX() {
		return x + (0.5f * width);
	}
	
	/**
	 * Get the center Y coordinate
	 * @return
	 */
	public float getCenterY() {
		return y + (0.5f * height);
	}
	
	/**
	 * Get the maximum x value
	 * @return
	 */
	public float getMaxX() {
		return x + width;
	}
	
	/**
	 * Get the maximum Y value
	 * @return
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

	public Image getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	public boolean hasShield() {
		return shield;
	}

	public void addShield() {
		shield = true;
		Executors.newScheduledThreadPool(1).schedule(() -> shield = false, 10, TimeUnit.SECONDS);
	}

	public boolean hasLaser() {
		return laser;
	}

	public void addLaser() {
		laser = true;
		Executors.newScheduledThreadPool(1).schedule(() -> laser = false, 10, TimeUnit.SECONDS);
	}
	
	//Method 1 code
//	public void decreaselifeCount() {
//		lifeCount = lifeCount -1;
//	}
//	
//	public int getLifeCount() {
//		return lifeCount;
//	}
}
