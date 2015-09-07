import static org.junit.Assert.*;

import org.junit.Test;
import org.newdawn.slick.geom.Rectangle;


public class GateTest {

	@Test
	public void testGateX() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getX(), 500);
	}
	
	@Test
	public void testGateY() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getY(), 550);
	}

	@Test
	public void testGateWidth() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getWidth(), 550);
	}
	
	@Test
	public void testGateHeight() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getHeight(), 550);
	}
	
	@Test
	public void testGateDefaultDone() {
		Gate gate1 = new Gate(500,550,50,200);
		assertFalse(gate1.isDone());
	}
	
	@Test
	public void testGateDefaultFading() {
		Gate gate1 = new Gate(500,550,50,200);
		assertFalse(gate1.isFading());
	}
	
	@Test
	public void testGateDefaultHeightPercentage() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getHeightPercentage(), 1,0);
	}
	
	@Test
	public void testGateDefaultFadingSpeed() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getFadingSpeed(), 1000, 0);
	}
	
	@Test
	public void testUpdateIfNotFading() {
		Gate gate1 = new Gate(500,550,50,200);
		Gate gate2 = gate1;
		gate1.update(0.1f);
		Gate gate3 = gate1;
		assertEquals(gate2, gate3);
	}
	
	@Test
	public void testUpdateIfFading1() {
		Gate gate1 = new Gate(500,550,50,200);
		gate1.setFading(true);
		gate1.update(0.1f);
		
		System.out.println(gate1.getHeightPercentage());
		assertEquals(gate1.getHeightPercentage(), 0.5, 0);
	}
	
	@Test
	public void testUpdateIfFading2() {
		Gate gate1 = new Gate(500,550,50,200);
		gate1.setFading(true);
		gate1.setHeightPercentage(0);
		gate1.update(0.1f);
		
		assertTrue(gate1.isDone());
		assertFalse(gate1.isFading());
	}

	@Test
	public void testIsFading() {
		Gate gate1 = new Gate(500,550,50,200);
		assertFalse(gate1.isFading());
	}

	@Test
	public void testSetFading() {
		Gate gate1 = new Gate(500,550,50,200);
		gate1.setFading(true);
		assertTrue(gate1.isFading());
	}

	@Test
	public void testGetHeightPercentage() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(1,gate1.getHeightPercentage(),0);
	}

	@Test
	public void testSetHeightPercentage() {
		Gate gate1 = new Gate(500,550,50,200);
		gate1.setHeightPercentage(4);
		assertEquals(4,gate1.getHeightPercentage(),0);
	}

	@Test
	public void testIsDone() {
		Gate gate1 = new Gate(500,550,50,200);
		assertFalse(gate1.isDone());
	}

	@Test
	public void testSetDone() {
		Gate gate1 = new Gate(500,550,50,200);
		gate1.setDone(true);
		assertTrue(gate1.isDone());
	}

	@Test
	public void testGetRectangle() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getRectangle(), new Rectangle(500,550,50,200));
	}

	@Test
	public void testAddToRequirementsBouncingCircle() {
		Gate gate1 = new Gate(500,550,50,200);
		BouncingCircle circle1 = new BouncingCircle(100,100,10,1,2,0.01f);
		gate1.addToRequirements(circle1);
		assertTrue(gate1.contains(circle1));
	}

	@Test
	public void testRemoveFromRequirements() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRequired() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRequired() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddToRequirementsArrayListOfBouncingCircle() {
		fail("Not yet implemented");
	}

}
