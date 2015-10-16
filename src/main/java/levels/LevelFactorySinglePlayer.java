package levels;

import guimenu.MainGame;

import java.lang.reflect.Constructor;

/**
 * The basic implementation of the LevelFactory.
 * @author Stefan
 *
 */
public class LevelFactorySinglePlayer extends LevelFactory {

	@Override
	Level createLevel(int levelNumber, MainGame mainGame) {
		Level level = null;
		
		String classString = "levels.Level" + levelNumber;

		try {
			@SuppressWarnings("rawtypes")
			Constructor constructor = Class.forName(classString).getConstructor(MainGame.class,
					boolean.class);
			level = (Level) constructor.newInstance(mainGame, false);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return level;
	}

}
