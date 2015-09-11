package logic;

import gui.GameState;

import java.util.concurrent.TimeUnit;
/**
 * Created by alexandergeenen on 09/09/15.
 */
public class Powerup {
    /**
     * The different powerup types.
     */
    public enum PowerupType {
        SHIELD, SPIKY, INSTANT
    }

    private static final float POWERUP_WIDTH = 40;
    private static final float POWERUP_HEIGHT = 40;
    private static final float POWERUP_SPEED = 200f;
    private static final int POWERUP_TIME = 5;
    private static final int SECONDS_TO_MS = 1000;

    private float x, y, width, height;
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
        this.width = POWERUP_WIDTH;
        this.height = POWERUP_HEIGHT;
        this.type = power;
        this.timeRemaining = TimeUnit.SECONDS.toMillis(POWERUP_TIME);
    }

    /**
     * Update Powerups graphical thingy.
     * @param gs Game State
     * @param container Game Container
     * @param deltaFloat Delta
     */
    public void update(GameState gs, float containerHeight, float deltaFloat) {
        if (!gs.isPaused()) {
            timeRemaining -= deltaFloat * SECONDS_TO_MS;
        }
        if ((this.y + POWERUP_HEIGHT) < containerHeight - gs.getFloor().getHeight()) {
            this.y += POWERUP_SPEED * deltaFloat;
        } else {
            this.y = containerHeight - gs.getFloor().getHeight() - POWERUP_HEIGHT;
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
}
