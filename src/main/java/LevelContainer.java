import java.util.ArrayList;

import org.newdawn.slick.GameContainer;

/**
 * This class is a container for all the levels in the game.
 * @author Menno
 *
 */
public class LevelContainer {
	
	private ArrayList<Level> levels;
	private MainGame mg;
	private GameContainer container;
	
	
	/**
	 * Initialize the container
	 */
	public LevelContainer(MainGame mg) {
		this.mg = mg;
		this.container = mg.getContainer();
		this.levels = new ArrayList<Level>();
	}
	
	public void initialize() {
		this.levels = initializeLevels();
	}
	
	public ArrayList<Level> getLevels() {
		return levels;
	}

	private ArrayList<Level> initializeLevels() {
		ArrayList<Level> res = new ArrayList<Level>();
		
		//First level, test with gate
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		BouncingCircle circle11 = new BouncingCircle(200, 200, 30, mg.startingSpeed, -50, mg.gravity);
		circles.add(new BouncingCircle(1200, 200, 30, mg.startingSpeed, -50, mg.gravity));
		circles.add(circle11);
		
		ArrayList<Gate> gates = new ArrayList<Gate>();
		Gate gate11 = new Gate(container.getWidth()/2+50f,0,45,container.getHeight());
		gate11.addToRequirements(circle11);
		gates.add(gate11);
		//Height to height-150?
		Level level = new Level(40, circles, gates);
		res.add(level);
		
		
		ArrayList<BouncingCircle> circles2 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates2 = new ArrayList<Gate>();
		circles2.add(new BouncingCircle(200, 200, 45, mg.startingSpeed, -50, mg.gravity));
		level = new Level(40, circles2, gates2);
		res.add(level);
		
		
		ArrayList<BouncingCircle> circles3 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates3 = new ArrayList<Gate>();
		circles3.add(new BouncingCircle(200, 200, 65, mg.startingSpeed, -50, mg.gravity));
		level = new Level(100, circles3, gates3);
		res.add(level);
		
		ArrayList<BouncingCircle> circles4 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates4 = new ArrayList<Gate>();
		circles4.add(new BouncingCircle(200, 200, 45, mg.startingSpeed, -50, mg.gravity));
		circles4.add(new BouncingCircle(500, 300, 65, -mg.startingSpeed, -50, mg.gravity));
		level = new Level(125, circles4, gates4);
		res.add(level);
		
		ArrayList<BouncingCircle> circles5 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates5 = new ArrayList<Gate>();
		for (int i=0;i<10;i++) {
			circles5.add(new BouncingCircle(50*i+200, 300, 10, mg.startingSpeed, -50, mg.gravity));
		}
		level = new Level(60, circles5, gates5);
		res.add(level);
		
		ArrayList<BouncingCircle> circles6 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates6 = new ArrayList<Gate>();
		circles6.add(new BouncingCircle(900, 300, 90, -mg.startingSpeed, -50, mg.gravity));
		level = new Level(120, circles6, gates6);
		res.add(level);
		
		ArrayList<BouncingCircle> circles7 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates7 = new ArrayList<Gate>();
		circles7.add(new BouncingCircle(200, 200, 30, mg.startingSpeed, -50, mg.gravity));
		circles7.add(new BouncingCircle(500, 500, 65, 0, -50, mg.gravity));
		circles7.add(new BouncingCircle(900, 300, 90, -mg.startingSpeed, -50, mg.gravity));
		level =  new Level(150, circles7, gates7);
		res.add(level);
		
		ArrayList<BouncingCircle> circles8 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates8 = new ArrayList<Gate>();
		circles8.add(new BouncingCircle(200, 200, 20, 0, -50, mg.gravity));
		circles8.add(new BouncingCircle(300, 200, 30, 0, -50, mg.gravity));
		circles8.add(new BouncingCircle(500, 200, 45, 0, -50, mg.gravity));
		circles8.add(new BouncingCircle(700, 200, 65, 0, -50, mg.gravity));
		level = new Level (120, circles8, gates8);
		res.add(level);
		
		ArrayList<BouncingCircle> circles9 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates9 = new ArrayList<Gate>();
		circles9.add(new BouncingCircle(200, 300, 90, mg.startingSpeed, -50, mg.gravity));
		circles9.add(new BouncingCircle(900, 300, 90, -mg.startingSpeed, -50, mg.gravity));
		level = new Level(180, circles9, gates9);
		res.add(level);
		
		ArrayList<BouncingCircle> circles10 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates10 = new ArrayList<Gate>();
		for (int i=0;i<20;i++) {
			circles10.add(new BouncingCircle(200, 10*i+ 200, 10, 50, -50, mg.gravity));
		}
		level = new Level(40, circles10, gates10);
		res.add(level);		
		
		return res;
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

