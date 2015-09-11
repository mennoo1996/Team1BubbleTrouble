import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.HighScores;
import logic.Score;

import org.junit.Test;


public class HighScoresTest {

	@Test
	public void testHighScores() {
		HighScores a = new HighScores();
		assertEquals(a.getScoreList(),new ArrayList<Score>());
	}

	@Test
	public void testToString() {
		HighScores a = new HighScores();
		Score score = new Score(0, "bob");
		a.add(score);
		assertEquals("    0    |    bob\n",a.toString());
	}

	@Test
	public void testGetScoreList() {
		HighScores a = new HighScores();
		assertEquals(a.getScoreList(),new ArrayList<Score>());
	}

	@Test
	public void testSetScoreList() {
		HighScores a = new HighScores();
		Score score = new Score(0, "bob");
		ArrayList<Score> banana = new ArrayList<Score>();
		banana.add(score);
		a.setScoreList(banana);
		assertEquals(a.getScoreList(),banana);
	}

	@Test
	public void testAdd() {
		HighScores a = new HighScores();
		Score score = new Score(0, "bob");
		a.add(score);
		assertTrue(a.getScoreList().contains(score));
	}

	@Test
	public void testSort() {
		HighScores a = new HighScores();
		Score score = new Score(0, "bob");
		Score score2 = new Score(500,"henk");
		a.add(score);
		a.add(score2);
		assertEquals(a.getScoreList().get(0),score);
		a.sort();
		assertEquals(a.getScoreList().get(0),score2);
	}

}
