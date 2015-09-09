import org.newdawn.slick.GameContainer;

/**
 * Created by alexandergeenen on 09/09/15.
 */
public class Coin {
    private final static float COIN_WIDTH = 20;
    private final static float COIN_HEIGHT = 20;
    private final static float COIN_SPEED = 200f

    private int points;
    private float x, y, width, height;

    /**
     * Create a coin.
     * @param x x coord
     * @param y y coord
     * @param value the points value of the coin
     */
    public Coin(float x, float y, int value) {
        this.x = x;
        this.y = y;
        this.points = value;
        this.width = COIN_WIDTH;
        this.height = COIN_HEIGHT;
    }

    /**
     * Update Powerups graphical thingy.
     * @param gs Game State
     * @param container Game Container
     * @param deltaFloat Delta
     */
    public void update(GameState gs, GameContainer container, float deltaFloat) {
        if ((this.y + COIN_HEIGHT) < container.getHeight() - gs.getFloor().getHeight()) {
            this.y += COIN_SPEED * deltaFloat;
        } else {
            this.y = container.getHeight() - gs.getFloor().getHeight() - COIN_HEIGHT;
        }
    }

    /**
     * @return Points Value of the Coin
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return x coord
     */
    public float getX() {
        return this.x;
    }

    /**
     * @return y coord
     */
    public float getY() {
        return this.y;
    }

    /**
     * Return a bounding box rectangle of the coin.
     * @return a bounding box rectangle
     */
    public MyRectangle getRectangle() {
        return new MyRectangle(x, y, width, height);
    }
}
