/**
 * Class that represents a floating score element.
 * @author Mark
 */
public class floatingScore {
	
	private static final float MAX_SPEED = 100f;
	private static final float MAX_LIFE = 1000;
	
	/**
	 * Variables.
	 */
	private int SCORE;
	private float X;
	private float Y;
	private float SPEED;
	private float LIFE;
	private float OPACITY = 1.0f;
	
	/**
	 * Constructor class for a floating score
	 * @param circle the circle this score draws its information from
	 */
	public floatingScore(BouncingCircle circle) {
		this.SCORE = circle.getScore();
		this.X = circle.getCenterX();
		this.Y = circle.getCenterY();
		this.SPEED = MAX_SPEED;
		this.LIFE = MAX_LIFE;
	}
	
	/**
	 * Update function for floating score
	 * @param deltaFloat time since last frame
	 * @param timeDelta time passed in counter
	 */
	public void update(float deltaFloat, long timeDelta) {
		Y -= SPEED*deltaFloat;
		LIFE -= timeDelta;
		OPACITY = LIFE/MAX_LIFE;
		SPEED = MAX_SPEED * OPACITY;
	}

	/**
	 * @return whether this floating score is still alive
	 */
	public boolean isDead() {
		return LIFE <= 0;
	}
	
	/**
	 * @return The score this floating score displays
	 */
	public int getScore() {
		return SCORE;
	}
	
	/**
	 * @return Opacity of this floating score
	 */
	public float getOpacity() {
		return OPACITY;
	}
	
	/**
	 * @return X-position of this floating score
	 */
	public float getX() {
		return X;
	}
	
	/**
	 * @return Y-position of this floating score
	 */
	public float getY() {
		return Y;
	}
	
}
