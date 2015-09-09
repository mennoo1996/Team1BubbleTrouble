import org.newdawn.slick.GameContainer;

/**
 * Created by alexandergeenen on 09/09/15.
 */
public class Powerup {
    public enum PowerupType {
        SHIELD, SPIKY, INSTANT
    }

    private static final float POWERUP_WIDTH = 20;
    private static final float POWERUP_HEIGHT = 20;
    private static final float POWERUP_SPEED = 200f;

    private float x, y, width, height;
    private PowerupType type;

    public Powerup(float x, float y, PowerupType power) {
        this.x = x;
        this.y = y;
        this.width = POWERUP_WIDTH;
        this.height = POWERUP_HEIGHT;
        this.type = power;
    }

    public void update(GameState gs, GameContainer container, float deltaFloat) {
        if ((this.y + POWERUP_HEIGHT) < container.getHeight() - gs.floor.getHeight()) {
            this.y += POWERUP_SPEED * deltaFloat;
        } else {
            this.y = container.getHeight() - gs.floor.getHeight() - POWERUP_HEIGHT;
        }
    }

    public MyRectangle getRectangle() {
        return new MyRectangle(x,y,width,height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public PowerupType getType() {
        return type;
    }
}
