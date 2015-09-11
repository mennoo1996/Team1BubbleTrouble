package logic;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for the highscores in the game.
 * @author Menno
 *
 */
public class HighScores {
	
	private ArrayList<Score> scoreList;
	private static final int MAX_HIGHSCORES = 10;
	
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
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		for (Score score : scoreList) {
			buf.append(score.toString());
			buf.append("\n");
		}
		
		String res = buf.toString();
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
	
	/**
	 * Sort the scoreList with the custom scoresComaprator.
	 */
	public void sort() {
		Collections.sort(scoreList, new ScoresComparator());
		
		if (scoreList.size() > MAX_HIGHSCORES) {
			for (int i = MAX_HIGHSCORES; i < scoreList.size(); i++) {
				scoreList.remove(i);
			}
		}
	}
	
}

