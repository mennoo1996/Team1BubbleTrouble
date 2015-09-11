package logic;

/**
 * This class represents a score that a player has set.
 * @author Menno
 *
 */
public class Score {
	
	private int score;
	private String name;
	/**
	 * @param score the score of the player
	 * @param name the name of the player
	 */
	public Score(int score, String name) {
		super();
		this.score = score;
		this.name = name;
	}
	
	@Override
	public String toString() {
		String res = String.format("%5d%5s%4s%s", score, "|", "", name);
		return res;
	}
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
