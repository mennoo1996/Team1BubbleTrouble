import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class to parse the highscores from/to file.
 * @author Menno
 *
 */
public final class HighScoresParser {
	
	private HighScoresParser() {
		
	}
	
	/**
	 * Reads file into highscores.
	 * @param fileName the filename
	 * @return the highscores
	 */
	public static HighScores readHighScores(String fileName) {
		BufferedReader reader;
		HighScores hs = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			hs = new HighScores();
			String line = reader.readLine();
			while (line != null) {
				String[] splitted = line.split(",");
				int points = Integer.parseInt(splitted[0]);
				String name = splitted[1];
				Score score = new Score(points, name);
				hs.add(score);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return hs;
	}
	
	/**
	 * Writes highscores into file.
	 * @param fileName the filename
	 * @param hs the highscores
	 * @throws IOException 
	 */
	public static void writeHighScores(String fileName, HighScores hs) {
		PrintWriter writer;
		try {
			File file = new File(fileName);
			writer = new PrintWriter(file, "UTF-8");
			for (Score s: hs.getScoreList()) {
				String line = s.getScore() + "," + s.getName();
				writer.println(line);
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("exception enzo");
		}
	}
	
	public static int checkPrivateConstructor(boolean succes) {
		HighScoresParser hs = new HighScoresParser();
		if(!(hs ==  null) && succes) {
			return 1;
		}
		return 0;
	}
}