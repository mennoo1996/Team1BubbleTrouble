import java.util.ArrayList;

public class LevelContainer {
	
	private ArrayList<Level> levels;
	
	public LevelContainer() {
		levels = new ArrayList<Level>();
	}
	
	public void add (Level level) {
		
		levels.add(level);
	}
	
	public Level getLevel(int ID) {
		return levels.get(ID);
	}
	
	public Level getNextLevel(int ID) {
		return levels.get(ID+1);
	}
	
	public int size() {
		return levels.size();
	}

}

//public HashMap<Integer, ArrayList<BouncingCircle>> map;
//public int levelCounter;
//
//public Level () {
//	map = new HashMap<Integer, ArrayList<BouncingCircle>>();
//	levelCounter = 0;
//}
//
//public void add(ArrayList<BouncingCircle> circles) {
//	map.put(levelCounter, circles);
//	levelCounter++;
//}
//
//public ArrayList<BouncingCircle> getCirclesForId(int levelId) {
//	return map.get(levelId);
//}