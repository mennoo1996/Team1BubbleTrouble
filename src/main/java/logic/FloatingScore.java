package logic;

import powerups.Powerup;

/**
 * Class that represents a floating score element.
 * @author Mark
 */
public class FloatingScore implements Cloneable {
	
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
	 * Constructor for a floating score.
	 * @param score	the score of the flaoting score
	 * @param x the x of the flaoting score
	 * @param y the y of the flaoting score
	 * @param speed the speed of the flaoting score
	 * @param life the life of the flaoting score
	 */
	public FloatingScore(String score, float x, float y, float speed, float life) {
		this.score = score;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.life = life;
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
	 * Constructor class for a floating score.
	 * @param score the score displayed by floating score
	 * @param x the x location of where the floatingscore appears
	 * @param y the y location of where the FloatingScore appears
	 */
	public FloatingScore(String score, float x, float y) {
		this.score = score;
		this.x = x;
		this.y = y;
		this.speed = MAX_SPEED;
		this.life = MAX_LIFE;
	}
	
	/**
	 * Give the FloatingScore object back as a string.
	 * @return the FloatingScore object as a string.
	 */
	@Override
	public String toString() {
		String res = "FLOATINGSCORE " + this.x + " " + this.y + " " + this.score + " ";
		return res;
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
	
	/**
	 * @return Clone the floating score and return it.
	 */
	@Override
	public FloatingScore clone() throws CloneNotSupportedException {
		super.clone();
		return new FloatingScore(score, x, y, speed, life);
	}
	
}
