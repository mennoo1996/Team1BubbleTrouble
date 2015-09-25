package logic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import logic.HighScores;
import logic.Logger;
import logic.Score;


public class HighScoresTest {

	@Test
	public void testHighScores() {
		HighScores a = new HighScores();
		assertEquals(a.getScoreList(),new ArrayList<Score>());
	}

	@Test
	public void testToString() {
		HighScores a = new HighScores();
		a.setLogger(new Logger(true));
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
		a.setLogger(new Logger(true));
		Score score = new Score(0, "bob");
		a.add(score);
		assertTrue(a.getScoreList().contains(score));
	}

	@Test
	public void testSort() {
		HighScores a = new HighScores();
		a.setLogger(new Logger(true));
		Score score = new Score(0, "bob");
		Score score2 = new Score(500,"henk");
		a.add(score);
		a.add(score2);
		assertEquals(a.getScoreList().get(0),score);
		a.sort();
		assertEquals(a.getScoreList().get(0),score2);
	}
	
	@Test
	public void tesSortToManyInputs() {
		HighScores a = new HighScores();
		a.setLogger(new Logger(true));
		Score score = new Score(0, "bob");
		Score score2 = new Score(500,"henk");
		Score score3 = new Score(30, "bob");
		Score score4 = new Score(500,"henk");
		Score score5 = new Score(30, "bob");
		Score score6 = new Score(500,"henk");
		Score score7 = new Score(30, "bob");
		Score score8 = new Score(500,"henk");
		Score score9 = new Score(30, "bob");
		Score score10 = new Score(500,"henk");
		Score score11 = new Score(30, "bob");
		a.add(score);
		a.add(score2);
		a.add(score3);
		a.add(score4);
		a.add(score5);
		a.add(score6);
		a.add(score7);
		a.add(score8);
		a.add(score9);
		a.add(score10);
		a.add(score11);
		assertEquals(a.getScoreList().get(0),score);
		a.sort();
		assertEquals(a.getScoreList().get(0),score2);
		assertEquals(a.getScoreList().get(9),score11);
		
	}

}
