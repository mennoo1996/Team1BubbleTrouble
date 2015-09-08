import java.util.ArrayList;


public class HighScores {
	
	private ArrayList<Score> scoreList;
	
	public HighScores() {
		scoreList = new ArrayList<Score>();
	}
	
	public String printHighScores() {
		String res = "";
		
		for(Score score : scoreList) {
			String line = String.format("%5d%5s%10s\n", score.getScore(), "|", score.getName());
			res += line;
		}
		
		res = res.substring(0, res.length()-2);
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
	
	public void add(Score score) {
		scoreList.add(score);
	}
	
	
	
}
