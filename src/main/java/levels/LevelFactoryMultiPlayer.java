package levels;

import guimenu.MainGame;

import java.lang.reflect.Constructor;

/**
 * Multiplayer implementation of LevelFactory.
 * @author alexandergeenen
 */
public class LevelFactoryMultiPlayer extends LevelFactory {

    @Override
    Level createLevel(int levelNumber, MainGame mainGame) {
        Level level = null;

        try {
            String classString = "levels.Level" + levelNumber;
            @SuppressWarnings("rawtypes")
            Constructor constructor = Class.forName(classString).getConstructor(MainGame.class,
                    boolean.class);
            level = (Level) constructor.newInstance(mainGame, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return level;
    }
}
