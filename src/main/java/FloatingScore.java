/**
 * Class that represents a floating score element.
 * @author Mark
 */
public class FloatingScore {
	
	private static final float MAX_SPEED = 100f;
	private static final float MAX_LIFE = 1000;
	private static final float Y_OFFSET = 120;
	
	/**
	 * Variables.
	 */
	private String score;
	private float x;
	private float y;
	private float speed;
	private float life;
	private float opacity = 1.0f;
	
	/**
	 * Constructor class for a floating score.
	 * @param circle the circle this score draws its information from
	 */
	public FloatingScore(BouncingCircle circle) {
		this.score = Integer.toString(circle.getScore());
		this.x = circle.getCenterX();
		this.y = circle.getCenterY();
		this.speed = MAX_SPEED;
		this.life = MAX_LIFE;
	}
	
	/**
	 * Constructor class for a floating score.
	 * @param coin the coin this score draws its information from
	 */
	public FloatingScore(Coin coin) {
		this.score = Integer.toString(coin.getPoints());
		this.x = coin.getCenterX();
		this.y = coin.getCenterY() - Y_OFFSET;
		this.speed = MAX_SPEED;
		this.life = MAX_LIFE;
	}
	
	/**
	 * Constructor class for a floating score.
	 * @param powerup the powerup this score draws its information from
	 */
	public FloatingScore(Powerup powerup) {
		this.score = "> " + powerup.getType().toString() + "();";
		this.x = powerup.getCenterX();
		this.y = powerup.getCenterY() - Y_OFFSET;
		this.speed = MAX_SPEED;
		this.life = MAX_LIFE;
	}

	/**
	 * Update function for floating score.
	 * @param deltaFloat time since last frame
	 * @param timeDelta time passed in counter
	 */
	public void update(float deltaFloat, long timeDelta) {
		y -= speed * deltaFloat;
		life -= timeDelta;
		opacity = life / MAX_LIFE;
		speed = MAX_SPEED * opacity;
	}

	/**
	 * @return whether this floating score is still alive
	 */
	public boolean isDead() {
		return life <= 0;
	}
	
	/**
	 * @return The score this floating score displays
	 */
	public String getScore() {
		return score;
	}
	
	/**
	 * @return Opacity of this floating score
	 */
	public float getOpacity() {
		return opacity;
	}
	
	/**
	 * @return X-position of this floating score
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * @return Y-position of this floating score
	 */
	public float getY() {
		return y;
	}
	
}
