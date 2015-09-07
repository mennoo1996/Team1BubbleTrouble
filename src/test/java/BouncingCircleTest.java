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
		assertEquals(-100, result.get(0).getySpeed(), 0);
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
		fail("Not yet implemented");
	}

	@Test
	public void testGetMinY() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetX() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetX() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetY() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetY() {
		fail("Not yet implemented");
	}

	

	

	

	

	


	

	@Test
	public void testIsDone() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDone() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCircle() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetxSpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetxSpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetySpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetySpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGravity() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGravity() {
		fail("Not yet implemented");
	}

}
