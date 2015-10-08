package levels;

import gui.MainGame;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;

import java.util.Arrays;

import static org.mockito.Mockito.mock;

/**
 * Created by alexandergeenen on 08/10/15.
 */

@RunWith(Parameterized.class)
public class LevelFactoryMultiPlayerTest {

    @Mock private MainGame mainGame = mock(MainGame.class);
    private int levelNum;
    private LevelFactoryMultiPlayer levelFactoryMultiPlayer;
    private Level level;

    private static final String classPrefix = "levels.Level";

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {1},
                {2},
                {3},
                {4},
                {5},
                {6},
                {7},
                {8},
                {9},
                {10}
        });
    }

    public LevelFactoryMultiPlayerTest(int levelNum) {
        this.levelNum = levelNum;
        this.levelFactoryMultiPlayer = new LevelFactoryMultiPlayer();
    }

    @Test
    public void createLevelTest() throws ClassNotFoundException {
        this.level = levelFactoryMultiPlayer.orderLevel(levelNum, mainGame);
        assertEquals(Class.forName(classPrefix + levelNum), level.getClass());
        assertTrue(this.level.getMultiplayer());
    }
}
