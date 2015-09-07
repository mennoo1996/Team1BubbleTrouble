import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Circle;

public class BouncingCircleTest {
	
	BouncingCircle c;
	MainGame mg;
	GameState gs;
	GameContainer gc;

	@Before
	public void setUp() throws Exception {
		mg = new MainGame("TestGame");
		gs = new GameState(mg);
		
		gc = mock(GameContainer.class);
		
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
	public void testUpdateYDeltaChange() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		gs.setFloor(new MyRectangle(0, 0, 0, 100));
		gs.setLeftWall(new MyRectangle(0, 0, 0, 0));
		gs.setRightWall(new MyRectangle(0, 0, 0, 0));
		when(gc.getHeight()).thenReturn(400);
		when(gc.getWidth()).thenReturn(400);
		c.update(gs, gc, 2);
		assertEquals(9, c.getY(), 0);
		
	}
	
	@Test
	public void testUpdateYReverseSpeed() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		gs.setFloor(new MyRectangle(0, 0, 0, 400));
		gs.setLeftWall(new MyRectangle(0, 0, 0, 0));
		gs.setRightWall(new MyRectangle(0, 0, 0, 0));
		when(gc.getHeight()).thenReturn(400);
		when(gc.getWidth()).thenReturn(400);
		c.update(gs, gc, 2);
		assertEquals(-400, c.getySpeed(), 0);
	}
	
	@Test
	public void testUpdateIncreaseYSpeed() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		gs.setFloor(new MyRectangle(0, 0, 0, 100));
		gs.setLeftWall(new MyRectangle(0, 0, 0, 0));
		gs.setRightWall(new MyRectangle(0, 0, 0, 0));
		when(gc.getHeight()).thenReturn(400);
		when(gc.getWidth()).thenReturn(400);
		c.update(gs, gc, 2);
		assertEquals(17, c.getySpeed(), 0);
	}
	
	@Test
	public void testUpdateXDeltaChange() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		gs.setFloor(new MyRectangle(0, 0, 0, 100));
		gs.setLeftWall(new MyRectangle(0, 0, 0, 0));
		gs.setRightWall(new MyRectangle(0, 0, 0, 0));
		when(gc.getHeight()).thenReturn(400);
		when(gc.getWidth()).thenReturn(400);
		c.update(gs, gc, 2);
		assertEquals(9, c.getCenterX(), 0);
	}
	
	@Test
	public void testUpdateXReverseSpeedLeftWall() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		gs.setFloor(new MyRectangle(0, 0, 0, 0));
		gs.setLeftWall(new MyRectangle(0, 0, 100, 0));
		gs.setRightWall(new MyRectangle(0, 0, 0, 0));
		when(gc.getHeight()).thenReturn(400);
		when(gc.getWidth()).thenReturn(400);
		c.update(gs, gc, 2);
		assertEquals(-4, c.getxSpeed(), 0);
	}
	
	@Test
	public void testUpdateXReverseSpeedRightWall() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		gs.setFloor(new MyRectangle(0, 0, 0, 0));
		gs.setLeftWall(new MyRectangle(0, 0, 0, 0));
		gs.setRightWall(new MyRectangle(0, 0, 400, 0));
		when(gc.getHeight()).thenReturn(400);
		when(gc.getWidth()).thenReturn(400);
		c.update(gs, gc, 2);
		assertEquals(-4, c.getxSpeed(), 0);	
	}
	
	@Test
	public void testUpdateXReverseSpeedGate() {
		ArrayList<Gate> gates = new ArrayList<Gate>();
		gates.add(new Gate(0, 0, 1000, 1000));
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		gs.setGateList(gates);
		gs.setFloor(new MyRectangle(0, 0, 0, 0));
		gs.setLeftWall(new MyRectangle(0, 0, 0, 0));
		gs.setRightWall(new MyRectangle(0, 0, 0, 0));
		when(gc.getHeight()).thenReturn(400);
		when(gc.getWidth()).thenReturn(400);
		c.update(gs, gc, 2);
		assertEquals(-4, c.getxSpeed(), 0);
	}
	
	@Test
	public void testUpdateXNoReverse() {
		c = new BouncingCircle(50, 2, 10, 4, 5, 6);
		gs.setFloor(new MyRectangle(0, 0, 0, 0));
		gs.setLeftWall(new MyRectangle(0, 0, 0, 0));
		gs.setRightWall(new MyRectangle(0, 0, 0, 0));
		when(gc.getHeight()).thenReturn(400);
		when(gc.getWidth()).thenReturn(400);
		c.update(gs,gc,2);
		assertEquals(4, c.getxSpeed(), 0);
	}
	

	@Test
	public void testGetSplittedCirclesSmallestRadius() {
		c = new BouncingCircle(1, 2, 10, 4, 5, 6);
		assertNull(c.getSplittedCircles(mg));
	}
	
	@Test
	public void testGetSplittedCirclesUseMinimumSpeed() {
		c = new BouncingCircle(1, 2, 20, 4, 5, 6);
		ArrayList<BouncingCircle> result = c.getSplittedCircles(mg);
		assertEquals(-200, result.get(0).getySpeed(), 0);
	}
	
	@Test
	public void testGetSplittedCirclesUseBonusSpeed() {
		c = new BouncingCircle(1, 2, 20, 4, -700, 0);
		ArrayList<BouncingCircle> result = c.getSplittedCircles(mg);
		assertEquals(-800, result.get(0).getySpeed(), 0);
	}
	
	@Test
	public void testGetSplittedCirclesNothingSpecial() {
		c = new BouncingCircle(1, 2, 20, 4, -400, 0);
		ArrayList<BouncingCircle> result = c.getSplittedCircles(mg);
		assertEquals(-400, result.get(1).getySpeed(), 0);
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
	public void testGetScore140() {
		c = new BouncingCircle(1, 2, 140, 4, 5, 6);
		assertEquals(1, c.getScore());
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
	

	

	

	

	

	


	

	

	

	


}
