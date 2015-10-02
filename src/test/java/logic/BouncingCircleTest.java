package logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
//import edu.umd.cs.findbugs.gui.Logger;
import gui.GameState;
import gui.MainGame;

import java.util.ArrayList;

import logic.BouncingCircle;
import logic.CircleList;
import logic.Gate;
import logic.Logger;
import logic.MyRectangle;
import logic.Logger.PriorityLevels;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Circle;
@RunWith(MockitoJUnitRunner.class)
public class BouncingCircleTest {
	
	BouncingCircle c;
	MainGame mg;
	GameState gs;
	GameContainer gc;
	Logger logger;

	@Before
	public void setUp() throws Exception {
		mg = new MainGame("TestGame");
		gs = new GameState(mg);
		gs.setCircleList(new CircleList(new ArrayList<BouncingCircle>()));
		
		mg = mock(MainGame.class);
	}
	
	@Test
	public void testUpdate1() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		GameState gs = mock(GameState.class);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.isPaused()).thenReturn(false);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		c.update(gs, 1, 1, 1);
		
		assertEquals(4.0, c.getxSpeed(), 0);
		assertEquals(-360.0, c.getySpeed(), 0);
	}
	
	@Test
	public void testUpdate2() {
		c = new BouncingCircle(30, 2, 10, 4, 5, 6);
		GameState gs = mock(GameState.class);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.isPaused()).thenReturn(false);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		c.update(gs, 100, 1, 1);
		
		assertEquals(-4.0, c.getxSpeed(), 0);
		assertEquals(11.0, c.getySpeed(), 0);
	}
	
	@Test
	public void testUpdate3() {
		c = new BouncingCircle(30, 2, 10, 4, 5, 6);
		GameState gs = mock(GameState.class);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.isPaused()).thenReturn(false);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		Gate gate = new Gate(30,2, 1, 1);
		ArrayList<Gate> gateList = new ArrayList<Gate>();
		ArrayList<BouncingCircle> circleList = new ArrayList<BouncingCircle>();
		circleList.add(c);
		gate.setRequired(circleList);
		gateList.add(gate);
		when(gs.getGateList()).thenReturn(gateList);
		c.update(gs, 100, 100, 1);
		
		assertEquals(-4.0, c.getxSpeed(), 0);
		assertEquals(11.0, c.getySpeed(), 0);
	}
	
	@Test
	public void testUpdate4() {
		c = new BouncingCircle(30, 2, 10, 4, 5, 6);
		GameState gs = mock(GameState.class);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.isPaused()).thenReturn(false);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		Gate gate = new Gate(30,2, 1, 1);
		ArrayList<Gate> gateList = new ArrayList<Gate>();
		ArrayList<BouncingCircle> circleList = new ArrayList<BouncingCircle>();
		gate.setRequired(circleList);
		gateList.add(gate);
		when(gs.getGateList()).thenReturn(gateList);
		c.update(gs, 100, 100, 1);
		
		assertEquals(4.0, c.getxSpeed(), 0);
		assertEquals(11.0, c.getySpeed(), 0);
	}
	
	@Test
	public void testBouncingCircleCenterX() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(1, c.getCenterX(), 0);
	}
	
	@Test
	public void testBouncingCircleCenterY() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(2, c.getCenterY(), 0);
	}
	
	@Test
	public void testBouncingCircleRadius() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(3, c.getRadius(), 0);
	}
	
	@Test
	public void testBouncingCircleXSpeed() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(4, c.getxSpeed(), 0);
	}
	
	@Test
	public void testBouncingCircleYSpeed() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(5, c.getySpeed(), 0);
	}
	
	@Test
	public void testBouncingCircleGravity() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(6, c.getGravity(), 0);
	}
	
	@Test
	public void testBouncingCircleDone() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertFalse(c.isDone());
	}
	
	@Test
	public void testGetSplittedCirclesBonusSpeedBranch() {
		c = new BouncingCircle(1,2,51,4,-15,6);
		ArrayList<BouncingCircle> result = c.getSplittedCircles(mg, gs);
		assertEquals(-250.0, result.get(0).getySpeed(), 0);
	}
	

	@Test
	public void testGetSplittedCirclesSmallestRadius() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		assertNull(c.getSplittedCircles(mg, gs));
	}
	
	@Test
	public void testGetSplittedCirclesUseMinimumSpeed() {
		c = new BouncingCircle(1, 2, 20, 4, 5, 6);
		ArrayList<BouncingCircle> result = c.getSplittedCircles(mg, gs);
		assertEquals(-250, result.get(0).getySpeed(), 0);
	}
	
	@Test
	public void testGetSplittedCirclesUseBonusSpeed() {
		
		c = new BouncingCircle(1, 2, 20, 4, -700, 0);
		ArrayList<BouncingCircle> result = c.getSplittedCircles(mg, gs);
		assertEquals(-750, result.get(0).getySpeed(), 0);
	}
	
	@Test
	public void testGetSplittedCirclesNothingSpecial() {
		c = new BouncingCircle(1, 2, 20, 4, -400, 0);
		ArrayList<BouncingCircle> result = c.getSplittedCircles(mg, gs);
		assertEquals(-450, result.get(1).getySpeed(), 0);
	}
	
	@Test
	public void testGetScore10() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		assertEquals(100, c.getScore());
	}
	
	@Test
	public void testGetScore20() {
		c = new BouncingCircle(1, 2, 20, 4, 5, 6);
		assertEquals(50, c.getScore());
	}
	
	@Test
	public void testGetScore30() {
		c = new BouncingCircle(1, 2, 30, 4, 5, 6);
		assertEquals(25, c.getScore());
	}
	
	@Test
	public void testGetScore45() {
		c = new BouncingCircle(1, 2, 45, 4, 5, 6);
		assertEquals(10, c.getScore());
	}
	
	@Test
	public void testGetScore65() {
		c = new BouncingCircle(1, 2, 65, 4, 5, 6);
		assertEquals(5, c.getScore());
	}
	
	@Test
	public void testGetScore90() {
		c = new BouncingCircle(1, 2, 90, 4, 5, 6);
		assertEquals(2, c.getScore());
	}
	
	
	@Test
	public void testGetScoreNoUsualRadius() {
		c = new BouncingCircle(1, 2, 19, 4, 5, 6);
		assertEquals(0, c.getScore());
	}
	
	@Test
	public void testGetMaxX() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(4, c.getMaxX(), 0);
	}
	
	@Test
	public void testGetMaxXNoConstantValue() {
		c = new BouncingCircle(3, 9, 8, 1, 0, 2);
		assertEquals(11, c.getMaxX(), 0);
	}
	
	@Test
	public void testGetMaxY() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(5, c.getMaxY(), 0);
	}
	
	@Test
	public void testGetMaxYNoConstantValue() {
		c = new BouncingCircle(5, 2, 8, 4, 6, 7);
		assertEquals(10, c.getMaxY(), 0);
	}
	
	@Test
	public void testGetMinX() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(-2, c.getMinX(), 0);
	}
	
	@Test
	public void testGetMinXNoConstantValue() {
		c = new BouncingCircle(2, 5, 1, 9, 8, 6);
		assertEquals(1, c.getMinX(), 0);
	}

	@Test
	public void testGetMinY() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(-1, c.getMinY(), 0);
	}
	
	@Test
	public void testGetMinYNoConstantValue() {
		c = new BouncingCircle(5, 1, 8, 3, 6, 3);
		assertEquals(-7, c.getMinY(), 0);
	}
	
	@Test
	public void testIsDone() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertFalse(c.isDone());
	}
	
	@Test
	public void testIsDoneNoConstantValue() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setDone(true);
		assertTrue(c.isDone());
	}

	@Test
	public void testSetDone() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setDone(true);
		assertTrue(c.isDone());
	}
	
	@Test
	public void testSetDoneNoConstantValue() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setDone(false);
		assertFalse(c.isDone());
	}
	
	@Test
	public void testGetCircleCenterX() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		Circle res = c.getCircle();
		assertEquals(1, res.getCenterX(), 0);
	}
	
	@Test
	public void testGetCircleCenterY() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		Circle res = c.getCircle();
		assertEquals(2, res.getCenterY(), 0);
	}
	
	@Test
	public void testGetCircleRadius() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		Circle res = c.getCircle();
		assertEquals(3, res.getRadius(), 0);
	}
	
	@Test
	public void testGetCircleCenterXNoConstantValue() {
		c = new BouncingCircle(6, 5, 4, 3, 2, 1);
		Circle res = c.getCircle();
		assertEquals(6, res.getCenterX(), 0);
	}
	
	@Test
	public void testGetCircleCenterYNoConstantValue() {
		c = new BouncingCircle(6, 5, 4, 3, 2, 1);
		Circle res = c.getCircle();
		assertEquals(5, res.getCenterY(), 0);
	}
	
	@Test
	public void testGetCircleRadiusNoConstantValue() {
		c = new BouncingCircle(6, 5, 4, 3, 2, 1);
		Circle res = c.getCircle();
		assertEquals(4, res.getRadius(), 0);
	}
	
	@Test
	public void testGetxSpeed() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(4, c.getxSpeed(), 0);
	}
	
	@Test
	public void testGetxSpeedNoConstantValue() {
		c = new BouncingCircle(1, 2, 3, 5, 5, 6);
		assertEquals(5, c.getxSpeed(), 0);
	}

	@Test
	public void testSetxSpeed() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setxSpeed(8);
		assertEquals(8, c.getxSpeed(), 0);
	}
	
	@Test
	public void testSetxSpeedNoConstantValue() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setxSpeed(80);
		assertEquals(80, c.getxSpeed(), 0);
	}

	@Test
	public void testGetySpeed() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(5, c.getySpeed(), 0);
	}
	
	@Test
	public void testGetySpeedNoConstantValue() {
		c = new BouncingCircle(1, 2, 3, 4, 7, 6);
		assertEquals(7, c.getySpeed(), 0);
	}

	@Test
	public void testSetySpeed() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setySpeed(50);
		assertEquals(50, c.getySpeed(), 0);
	}
	
	@Test
	public void testSetySpeedNoConstantValue() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setySpeed(20);
		assertEquals(20, c.getySpeed(), 0);
	}

	@Test
	public void testGetX() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(-2, c.getX(), 0);
	}
	
	@Test
	public void testGetXNoConstantValue() {
		c = new BouncingCircle(2, 2, 5, 4, 5, 6);
		assertEquals(-3, c.getX(), 0);
	}

	@Test
	public void testSetX() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setX(8);
		assertEquals(8, c.getX(), 0);
	}
	
	@Test
	public void testSetXNoConstantValue() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setX(10);
		assertEquals(10, c.getX(), 0);
	}
	
	@Test
	public void testGetY() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(-1, c.getY(), 0);
	}
	
	@Test
	public void testGetYNoConstantValue() {
		c = new BouncingCircle(1, 3, 3, 4, 5, 6);
		assertEquals(0, c.getY(), 0);
	}

	@Test
	public void testSetY() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setY(10);
		assertEquals(10, c.getY(), 0);
	}
	
	@Test
	public void testSetYNoConstantValue() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setY(20);
		assertEquals(20, c.getY(), 0);
	}

	@Test
	public void testGetGravity() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(6, c.getGravity(), 0);
	}
	
	@Test
	public void testGetGravityNoConstantValue() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 8);
		assertEquals(8, c.getGravity(), 0);
	}

	@Test
	public void testSetGravity() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setGravity(10);
		assertEquals(10, c.getGravity(), 0);
	}
	
	@Test
	public void testSetGravityNoConstantValue() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setGravity(50);
		assertEquals(50, c.getGravity(), 0);
	}
	
	@Test
	public void testIsHitCeiling() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertFalse(c.isHitCeiling());
	}
	
	@Test
	public void testSetHitCeiling() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		c.setHitCeiling(true);
		assertTrue(c.isHitCeiling());
	}
	
	@Test
	public void testGetSpeedForRadius20() {
		c = new BouncingCircle(1, 2, 20, 4, 5, 6);
		assertEquals(470, c.getSpeedForRadius(), 0);
	}
	
	@Test
	public void testGetSpeedForRadius30() {
		c = new BouncingCircle(1, 2, 30, 4, 5, 6);
		assertEquals(530, c.getSpeedForRadius(), 0);
	}
	
	@Test
	public void testGetSpeedForRadius45() {
		c = new BouncingCircle(1, 2, 45, 4, 5, 6);
		assertEquals(570, c.getSpeedForRadius(), 0);
	}
	
	@Test
	public void testGetSpeedForRadius65() {
		c = new BouncingCircle(1, 2, 65, 4, 5, 6);
		assertEquals(610, c.getSpeedForRadius(), 0);
	}
	
	@Test
	public void testGetSpeedForRadius90() {
		c = new BouncingCircle(1, 2, 90, 4, 5, 6);
		assertEquals(650, c.getSpeedForRadius(), 0);
	}
	
	@Test
	public void testGetSpeedForRadius10() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		assertEquals(360, c.getSpeedForRadius(), 0);
	}
	
	@Test
	public void testGetSpeedForRadiusWrongRadius() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(0, c.getSpeedForRadius(), 0);
	}
	
	@Test
	public void testGetNewRadius10() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		assertEquals(10, c.getNewRadius(), 0);
	}
	
	@Test
	public void testGetNewRadius20() {
		c = new BouncingCircle(1, 2, 20, 4, 5, 6);
		assertEquals(10, c.getNewRadius(), 0);
	}
	
	@Test
	public void testGetNewRadius30() {
		c = new BouncingCircle(1, 2, 30, 4, 5, 6);
		assertEquals(20, c.getNewRadius(), 0);
	}
	
	@Test
	public void testGetNewRadius45() {
		c = new BouncingCircle(1, 2, 45, 4, 5, 6);
		assertEquals(30, c.getNewRadius(), 0);
	}
	
	@Test
	public void testGetNewRadius65() {
		c = new BouncingCircle(1, 2, 65, 4, 5, 6);
		assertEquals(45, c.getNewRadius(), 0);
	}
	
	@Test
	public void testGetNewRadius90() {
		c = new BouncingCircle(1, 2, 90, 4, 5, 6);
		assertEquals(65, c.getNewRadius(), 0);
	}
	
	@Test
	public void testGetNewRadiusWrongRadius() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(0, c.getNewRadius(), 0);
	}
	

	

	

	

	


	

	

	

	


}
