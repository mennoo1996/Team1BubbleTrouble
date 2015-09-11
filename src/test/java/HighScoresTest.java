import static org.junit.Assert.*;

import java.util.ArrayList;

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
	
	@Test
	public void testSortTooMuchEntries() {
		HighScores a = new HighScores();
		Score score = new Score(0, "bob");
		Score score2 = new Score(100,"henk");
		Score score3 = new Score(200, "bob2");
		Score score4 = new Score(300,"henk2");
		Score score5 = new Score(400, "bob3");
		Score score6 = new Score(500,"henk3");
		Score score7 = new Score(600, "bob4");
		Score score8 = new Score(700,"henk4");
		Score score9 = new Score(800, "bob5");
		Score score10 = new Score(800,"henk5");
		Score score11 = new Score(1000, "bob6");
		Score score12 = new Score(900,"henk6");
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
		a.add(score12);
		assertEquals(a.getScoreList().get(11),score12);
		a.sort();
		assertEquals(a.getScoreList().get(1),score12);
	}

}
