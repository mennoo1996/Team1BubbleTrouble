package logic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import logic.Logger.PriorityLevels;

/**
 * Class to parse the highscores from/to file.
 * @author Menno
 *
 */
public final class HighScoresParser {
	private static Logger logger;
	
	/**
	 * Set the logger of this class.
	 * @param loggerIn	- the logger to set
	 */
	public static void setLogger(Logger loggerIn) {
		logger = loggerIn;
	}
	 
	/**
	 * Unused constructor.
	 */
	private HighScoresParser() {
		// do not even call this
	}
	
	/**
	 * Reads file into highscores.
	 * @param fileName the filename
	 * @return the highscores
	 */
	public static HighScores readHighScores(String fileName) {
		logger.log("Highscores read from file, filename=" + fileName, 
				PriorityLevels.LOW, "Highscores");
		BufferedReader reader;
		HighScores hs = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			hs = new HighScores();
			hs.setLogger(logger);
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
			System.out.println("exception");
		}
		
		return hs;
	}
	
	/**
	 * Writes highscores into file.
	 * @param fileName the filename
	 * @param hs the highscores
	 */
	public static void writeHighScores(String fileName, HighScores hs) {
		logger.log("Higscores written to file, filename=" + fileName, 
				PriorityLevels.LOW, "Highscores");
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
			e.printStackTrace();
		}
	}
}
