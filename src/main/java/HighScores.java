import java.util.ArrayList;

/**
 * Class for the highscores in the game.
 * @author Menno
 *
 */
public class HighScores {
	
	private ArrayList<Score> scoreList;
	
	/**
	 * Construct a new HighScores object. 
	 */
	public HighScores() {
		scoreList = new ArrayList<Score>();
	}
	
	/**
	 * Print the current highscores.
	 * @return a String representation of the highscores.
	 */
	public String printHighScores() {
		String res = "";
		
		for (Score score : scoreList) {
			String line = String.format("%5d%5s%10s\n", score.getScore(), "|", score.getName());
			res += line;
		}
		
		res = res.substring(0, res.length() - 2);
		return res;
	}

	/**
	 * @return the scoreList
	 */
	public ArrayList<Score> getScoreList() {
		return scoreList;
	}

	/**
	 * 
	 * @param scoreList the scoreList to set
	 */
	public void setScoreList(ArrayList<Score> scoreList) {
		this.scoreList = scoreList;
	}
	
	/**
	 * Add a score to the highscores.
	 * @param score the score to add
	 */
	public void add(Score score) {
		scoreList.add(score);
	}
	
	
	
}
