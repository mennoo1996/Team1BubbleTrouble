package levels;

import gui.MainGame;

/**
 * The abstract factory which can create levels.
 * @author Stefan
 *
 */
public abstract class LevelFactory {

	/**
	 * Construct a level.
	 * @param i indicates the level to construct
	 * @param mainGame the game in which the level is build
	 * @return the correctly constructed level
	 */
	public Level orderLevel(int i, MainGame mainGame) {
		Level level = createLevel(i, mainGame);
		level.constructLevel();
		return level;
	}

	/**
	 * Initialize the correct level.
	 * @param i indicates the level to create
	 * @param mainGame the game in which the level is build
	 * @return the level created
	 */
	abstract Level createLevel(int i, MainGame mainGame);
}
