package logic;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator class to sort highscores based on the value in the score attribute.
 * @author Bart
 *
 */
public class ScoresComparator implements Comparator<Score>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5401083012573568914L;

	/**
	 * Compare the 2 scores and return the highest.
	 * @param s1	- the first score
	 * @param s2	- the second score
	 * @return 	int	1 if hs1 is the highest, 0 if equal, -1 if hs2 is the highest
	 */
	public int compare(Score s1, Score s2) {
		if (s1.getScore() > s2.getScore()) {
			return -1;
		} else if (s1.getScore() == s2.getScore()) {
			return 0;
		} else {
			return 1;
		}
	}
	
}
