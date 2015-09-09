import org.newdawn.slick.GameContainer;

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

    private static final float POWERUP_WIDTH = 20;
    private static final float POWERUP_HEIGHT = 20;
    private static final float POWERUP_SPEED = 200f;

    private float x, y, width, height;
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
    }

    /**
     * Update Powerups graphical thingy.
     * @param gs Game State
     * @param container Game Container
     * @param deltaFloat Delta
     */
    public void update(GameState gs, GameContainer container, float deltaFloat) {
        if ((this.y + POWERUP_HEIGHT) < container.getHeight() - gs.getFloor().getHeight()) {
            this.y += POWERUP_SPEED * deltaFloat;
        } else {
            this.y = container.getHeight() - gs.getFloor().getHeight() - POWERUP_HEIGHT;
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
     * @return powerup's type
     */
    public PowerupType getType() {
        return type;
    }
}
