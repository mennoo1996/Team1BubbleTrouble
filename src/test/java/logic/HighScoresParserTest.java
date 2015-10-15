package logic;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.rules.ExpectedException;


public class HighScoresParserTest {

	private static final String readFileLocation = "resources/testfile.txt";
	private static final String writeFileLocation = "resources/testfile_empty.txt";

	@Test
	public void readHighScoresTest() {
		HighScores hs = new HighScores();
		hs.add(new Score(300, "jopie krekel"));
		hs.add(new Score(0, "freek"));
		
		HighScores hs2 = HighScoresParser.readHighScores(readFileLocation);
		
		ArrayList<Score> hsl = hs.getScoreList();
		ArrayList<Score> hsl2 = hs2.getScoreList();
		
		assertEquals(hsl.size(), hsl2.size());
	}

	
	@Test
	public void writeHighScoresTest() {
		
		HighScores hs = new HighScores();
		hs.add(new Score(300, "jopie krekel"));
		hs.add(new Score(0, "freek"));
		
		HighScoresParser.writeHighScores(writeFileLocation, hs);
		HighScores hs2 = HighScoresParser.readHighScores(writeFileLocation);

		ArrayList<Score> hsl = hs.getScoreList();
		ArrayList<Score> hsl2 = hs2.getScoreList();
		
		assertEquals(hsl.size(), hsl2.size());
		
	}
	
	@Test
	public void readWrongFileNameTest() {
		ExpectedException exception = ExpectedException.none();
		exception.expect(FileNotFoundException.class);
		HighScoresParser.readHighScores("no valid filename");
	}
	
	
}
