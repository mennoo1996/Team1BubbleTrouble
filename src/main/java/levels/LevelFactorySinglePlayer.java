package levels;

import gui.MainGame;

/**
 * The basic implementation of the LevelFactory.
 * @author Stefan
 *
 */
public class LevelFactorySinglePlayer extends LevelFactory {

	private static final int LEVEL_3_ID = 3;
	private static final int LEVEL_4_ID = 4;
	private static final int LEVEL_5_ID = 5;
	private static final int LEVEL_6_ID = 6;
	private static final int LEVEL_7_ID = 7;
	private static final int LEVEL_8_ID = 8;
	private static final int LEVEL_9_ID = 9;
	private static final int LEVEL_10_ID = 10;
	
	@Override
	Level createLevel(int i, MainGame mainGame) {
		Level level = null;
		
		@SuppressWarnings("rawtypes")
		Class[] types = {Double.TYPE, this.getClass()};
		System.out.println("Hello: " + types);
		
		if (i == 1) {
			level = new Level1(mainGame);
		} else if (i == 2) {
			level = new Level2(mainGame);
		} else if (i == LEVEL_3_ID) {
			level = new Level3(mainGame);
		} else if (i == LEVEL_4_ID) {
			level = new Level4(mainGame);
		} else if (i == LEVEL_5_ID) {
			level = new Level5(mainGame);
		} else if (i == LEVEL_6_ID) {
			level = new Level6(mainGame);
		} else if (i == LEVEL_7_ID) {
			level = new Level7(mainGame);
		} else if (i == LEVEL_8_ID) {
			level = new Level8(mainGame);
		} else if (i == LEVEL_9_ID) {
			level = new Level9(mainGame);
		} else if (i == LEVEL_10_ID) {
			level = new Level10(mainGame);
		}
		return level;
	}

}
