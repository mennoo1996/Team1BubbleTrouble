package levels;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import gui.MainGame;

import java.util.ArrayList;

import levels.Level;
import logic.BouncingCircle;
import logic.Gate;

import org.junit.Before;
import org.junit.Test;

public class LevelTest {
	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BALL_Y = 200;
	private static final float RADIUS_3 = 30;
	private static final int DEFAULT_YSPEED = -50;
	private static final int LEVEL_1_TIME = 40;
	
	private MainGame mg = mock(MainGame.class);
	ArrayList<BouncingCircle> circles;
	ArrayList<Gate> gates;
	Level l;
	BouncingCircle bc;
	Gate g;
	int time;
	
	@Before
	public void setUp() throws Exception {
		circles = new ArrayList<BouncingCircle>();
		BouncingCircle circle11 = new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y,
				RADIUS_3, mg.getStartingSpeed(),
				DEFAULT_YSPEED, mg.getGravity(), 0);
		circles.add(circle11);
		gates = new ArrayList<Gate>();
		time = LEVEL_1_TIME;
		g = new Gate(10, 10, 10, 10);
	}

	@Test
	public void testLevelTime() {
		l = new Level1(mg);
		l.constructLevel();
		assertEquals(time, l.getTime());
		
	}
	
	@Test
	public void testLevelCircles() {
		l = new Level1(mg);
		l.constructLevel();
		assertEquals(circles, l.getCircles());
		
	}
	
	@Test
	public void testLevelGates(){
		l = new Level1 (mg);
		l.constructLevel();
		assertEquals(gates, l.getGates());
	}

	@Test
	public void testGetTime() {
		l = new Level1(mg);
		l.constructLevel();
		assertEquals(LEVEL_1_TIME, l.getTime());
	}
	
	@Test
	public void testGetTimeNoConstantValue() {
		l = new Level1(mg);
		l.constructLevel();
		l.setTime(10);
		assertEquals(10, l.getTime());
	}

	@Test
	public void testSetTime() {
		l = new Level1(mg);
		l.constructLevel();
		l.setTime(5);
		assertEquals(5, l.getTime());
	}
	
	@Test
	public void testSetTimeNoConstantValue() {
		l = new Level1(mg);
		l.constructLevel();
		l.setTime(20);
		assertEquals(20, l.getTime());
		
	}

	@Test
	public void testGetCircles() {
		l = new Level1(mg);
		l.constructLevel();
		assertEquals(circles, l.getCircles());
	}
	
	@Test
	public void testGetCirclesNoConstantValue() {
		circles.add(bc);
		l = new Level1(mg);
		l.constructLevel();
		l.setCircles(circles);
		assertEquals(circles, l.getCircles());
		
	}

	@Test
	public void testSetCircles() {
		l = new Level1(mg);
		l.constructLevel();
		ArrayList<BouncingCircle> circles2 = new ArrayList<BouncingCircle>();
		l.setCircles(circles2);
		assertEquals(circles2, l.getCircles());
	}
	
	@Test
	public void testSetCirclesNoConstantValue() {
		l = new Level1(mg);
		l.constructLevel();
		ArrayList<BouncingCircle> circles2 = new ArrayList<BouncingCircle>();
		circles2.add(bc);
		circles2.add(bc);
		l.setCircles(circles2);
		assertEquals(circles2, l.getCircles());
	}

	@Test
	public void testGetGates() {
		l = new Level1(mg);
		l.constructLevel();
		assertEquals(gates, l.getGates());
	}
	
	@Test
	public void testGetGatesNoConstantValue() {
		gates.add(g);
		l = new Level1(mg);
		l.constructLevel();
		l.setGates(gates);
		assertEquals(gates, l.getGates());
	}

	@Test
	public void testSetGates() {
		l = new Level1(mg);
		l.constructLevel();
		ArrayList<Gate> gates2 = new ArrayList<Gate>();
		l.setGates(gates2);
		assertEquals(gates2, l.getGates());
	}
	
	@Test
	public void testSetGatesNoConstantValue() {
		l = new Level1(mg);
		l.constructLevel();
		ArrayList<Gate> gates2 = new ArrayList<Gate>();
		gates2.add(g);
		gates2.add(g);
		l.setGates(gates2);
		assertEquals(gates2, l.getGates());
	}

}
