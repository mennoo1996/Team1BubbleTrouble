package levels;

import gui.MainGame;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alexandergeenen on 08/10/15.
 */

@RunWith(Parameterized.class)
public class LevelFactorySinglePlayerTest {

    @Mock private MainGame mainGame = mock(MainGame.class);
    @Mock private GameContainer gameContainer = mock(GameContainer.class);
    private int levelNum;
    private LevelFactorySinglePlayer levelFactorySinglePlayer;
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

    public LevelFactorySinglePlayerTest(int levelNum) {
        this.levelNum = levelNum;
        this.levelFactorySinglePlayer = new LevelFactorySinglePlayer();
        when(mainGame.getContainer()).thenReturn(gameContainer);
        when(gameContainer.getHeight()).thenReturn(500);
    }

    @Test
    public void createLevelTest() throws ClassNotFoundException {
        this.level = levelFactorySinglePlayer.orderLevel(levelNum, mainGame);
        assertEquals(Class.forName(classPrefix + levelNum), level.getClass());
    }
}
