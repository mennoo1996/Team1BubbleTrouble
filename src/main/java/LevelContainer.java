import java.util.ArrayList;

/**
 * This class is a container for all the levels in the game.
 * @author Menno
 *
 */
public class LevelContainer {
	
	private ArrayList<Level> levels;
	
	/**
	 * Initialize the container
	 */
	public LevelContainer() {
		levels = new ArrayList<Level>();
	}
	
	public ArrayList<Level> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
	}

	/**
	 * Add a level to the container
	 * @param level the level to add
	 */
	public void add (Level level) {
		levels.add(level);
	}
	
	/**
	 * Get a level from the container
	 * @param ID the ID from the level to get
	 * @return the level with ID specified
	 */
	public Level getLevel(int ID) {
		return levels.get(ID);
	}
	
	/**
	 * The size of the container
	 * @return the size (amount of levels)
	 */
	public int size() {
		return levels.size();
	}

}

