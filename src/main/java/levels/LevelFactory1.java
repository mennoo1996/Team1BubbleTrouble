package levels;

import gui.MainGame;

/**
 * The basic implementation of the LevelFactory.
 * @author Stefan
 *
 */
public class LevelFactory1 extends LevelFactory {

	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;
	private static final int NINE = 9;
	private static final int TEN = 10;
	
	@Override
	Level createLevel(int i, MainGame mainGame) {
		Level level = null;
		if (i == 1) {
			level = new Level1(mainGame);
		} else if (i == 2) {
			level = new Level1(mainGame);
		} else if (i == THREE) {
			level = new Level1(mainGame);
		} else if (i == FOUR) {
			level = new Level1(mainGame);
		} else if (i == FIVE) {
			level = new Level1(mainGame);
		} else if (i == SIX) {
			level = new Level1(mainGame);
		} else if (i == SEVEN) {
			level = new Level1(mainGame);
		} else if (i == EIGHT) {
			level = new Level1(mainGame);
		} else if (i == NINE) {
			level = new Level1(mainGame);
		} else if (i == TEN) {
			level = new Level1(mainGame);
		}
		return level;
	}

}
