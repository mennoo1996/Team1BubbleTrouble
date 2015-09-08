import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class HighScoresParser {
	
	public static HighScores readHighScores(String fileName) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		HighScores hs = new HighScores();
		
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] words = line.split(" ");
			int points = Integer.parseInt(words[0]);
			String name = words[1];
			String player = words[2];
			
			Score score = new Score(points, name, player);
			hs.add(score);
		}
		
		sc.close();
		return hs;
	}
	
	public static void writeHighScores(String fileName, HighScores hs) {
		
		try {
			FileWriter fw = new FileWriter(fileName);
			for(Score score : hs.getScoreList()) {
				fw.write(String.format("%d %s %s", score.getScore(), score.getName(), score.getPlayer()));
				if(hs.getScoreList().get(hs.getScoreList().size()-1) != score) {
					fw.write("\n");
				}
			}
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Element writeScore(Score score, Document doc) {
		Element scoreElement = doc.createElement("score");
		
		Element pointsElement = doc.createElement("points");
		scoreElement.appendChild(pointsElement);
		//pointsElement.appendChild(doc.createTextNode(String.format("%d", score.getScore())));
		pointsElement.appendChild(doc.createTextNode("Score here"));
		
		
//		Element nameElement = doc.createElement("name");
//		nameElement.appendChild(pointsElement);
//		pointsElement.appendChild(doc.createTextNode(score.getName()));
//		
//		Element playerElement = doc.createElement("player");
//		playerElement.appendChild(pointsElement);
//		pointsElement.appendChild(doc.createTextNode(score.getPlayer()));
//		
		return scoreElement;
	}
	
	public static void main(String[] args) {
		HighScores hs = readHighScores("resources/highscores.txt");
		//writeHighScores("resources/highscores.txt", hs);
		System.out.println(hs.printHighScores());
	}

}
