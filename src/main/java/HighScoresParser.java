import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class to parse the highscores from/to file.
 * @author Menno
 *
 */
public final class HighScoresParser {
	
	private HighScoresParser() {
		// do not even call this
	}
	
	public static HighScores readHighScores(String fileName) {
		BufferedReader reader;
		HighScores hs = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			hs = new HighScores();
			String line = reader.readLine();
			while (line!=null) {
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
	
	public static void writeHighScores(String fileName, HighScores hs) {
		System.out.println("HIER OOK");
		PrintWriter writer;
		try {
			writer = new PrintWriter(new FileWriter(fileName));
			for (Score s: hs.getScoreList()) {
				System.out.println(s);
				String line = s.getScore() + "," + s.getName();
				writer.println(line);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	
//	/**
//	 * Read the highscores from file.
//	 * @param fileName the location of the file
//	 * @return a HighScores object with the highscores from the file.
//	 */
//	public static HighScores readHighScores(String fileName) {
//		Scanner sc = null;
//		try {
//			sc = new Scanner(new File(fileName));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		HighScores hs = new HighScores();
//		
//		while (sc.hasNextLine()) {
//			String line = sc.nextLine();
//			String[] words = line.split(" ");
//			int points = Integer.parseInt(words[0]);
//			String name = words[1];
//			
//			Score score = new Score(points, name);
//			hs.add(score);
//		}
//		
//		sc.close();
//		return hs;
//	}
//	
//	/**
//	 * Write the highscores to file.
//	 * @param fileName the location of the file
//	 * @param hs the highscores to write.
//	 */
//	public static void writeHighScores(String fileName, HighScores hs) {
//		
//		try {
//			FileWriter fw = new FileWriter(fileName);
//			for (Score score : hs.getScoreList()) {
//				fw.write(String.format("%d %s", score.getScore(), score.getName()));
//				if (hs.getScoreList().get(hs.getScoreList().size() - 1) != score) {
//					fw.write("\n");
//				}
//			}
//			
//			fw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * To test the parser?
	 * @param args for command line arguments - not used
	 */
	public static void main(String[] args) {
		HighScores hs = readHighScores("resources/highscores.txt");
		//writeHighScores("resources/highscores.txt", hs);
		System.out.println(hs.toString());
	}

}
