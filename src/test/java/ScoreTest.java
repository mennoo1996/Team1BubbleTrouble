import static org.junit.Assert.*;

import org.junit.Test;


public class ScoreTest {

	@Test
	public void testScore() {
		Score score = new Score(1000, "bob");
		assertEquals(score.getScore(), 1000);
		assertEquals(score.getName(), "bob");
	}

	@Test
	public void testGetScore() {
		Score score = new Score(1000, "bob");
		assertEquals(score.getScore(), 1000);
	}

	@Test
	public void testSetScore() {
		Score score = new Score(1000, "bob");
		score.setScore(4);
		assertEquals(score.getScore(), 4);
	}

	@Test
	public void testGetName() {
		Score score = new Score(1000, "bob");
		assertEquals(score.getName(), "bob");
	}

	@Test
	public void testSetName() {
		Score score = new Score(1000, "bob");
		score.setName("henk");
		assertEquals(score.getName(), "henk");
	}

}
