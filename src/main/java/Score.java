
public class Score {
	
	private int score;
	private String name;
	/**
	 * @param score
	 * @param name
	 */
	public Score(int score, String name) {
		super();
		this.score = score;
		this.name = name;
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
	
//	public boolean equals(Object other) {
//		if (other instanceof Score) {
//			Score that = (Score) other;
//			if (this.getName() == that.getName() && this.getScore() == that.getScore()) {
//				return true;
//			}
//		}
//		return false;
//	}

}
