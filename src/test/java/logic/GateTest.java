package logic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import logic.BouncingCircle;
import logic.Gate;
import logic.MyRectangle;

import org.junit.Test;


public class GateTest {

	@Test
	public void testGateX() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getX(), 500,0);
	}
	
	@Test
	public void testGateY() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getY(), 550,0);
	}

	@Test
	public void testGateWidth() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getWidth(), 50,0);
	}
	
	@Test
	public void testGateHeight() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getHeight(), 200,0);
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
		assertEquals(gate1.getRectangle(), new MyRectangle(500,550,50,200));
	}

	@Test
	public void testAddToRequirementsBouncingCircle() {
		Gate gate1 = new Gate(500,550,50,200);
		BouncingCircle circle1 = new BouncingCircle(100,100,10,1,2,0.01f, 0);
		gate1.addToRequirements(circle1);
		assertTrue(gate1.getUnlockCircles().contains(circle1));
	}

	@Test
	public void testRemoveFromRequirements() {
		Gate gate1 = new Gate(500,550,50,200);
		BouncingCircle circle1 = new BouncingCircle(100,100,10,1,2,0.01f, 0);
		gate1.addToRequirements(circle1);
		gate1.removeFromRequirements(circle1);
		assertFalse(gate1.contains(circle1));
	}

	@Test
	public void testGetRequired() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getUnlockCircles(),new ArrayList<BouncingCircle>());
	}

	@Test
	public void testSetRequired() {
		Gate gate1 = new Gate(500,550,50,200);
		ArrayList<BouncingCircle> thing = new ArrayList<BouncingCircle>();
		BouncingCircle circle1 = new BouncingCircle(100,100,10,1,2,0.01f, 0);
		thing.add(circle1);
		gate1.setRequired(thing);
		assertEquals(gate1.getUnlockCircles(), thing);
	}

	@Test
	public void testAddToRequirementsArrayListOfBouncingCircle() {
		Gate gate1 = new Gate(500,550,50,200);
		ArrayList<BouncingCircle> thing = new ArrayList<BouncingCircle>();
		ArrayList<BouncingCircle> thing2 = new ArrayList<BouncingCircle>();
		ArrayList<BouncingCircle> thing3 = new ArrayList<BouncingCircle>();
		BouncingCircle circle1 = new BouncingCircle(100,100,10,1,2,0.01f, 0);
		BouncingCircle circle2 = new BouncingCircle(200,200,10,1,2,0.01f, 0);
		thing.add(circle1);
		thing2.add(circle2);
		
		thing3.add(circle1);
		thing3.add(circle2);
		
		gate1.setRequired(thing);
		gate1.addToRequirements(thing2);
		assertEquals(gate1.getUnlockCircles(), thing3);
	}

	//Add tests for get and set fading speed
	
	@Test
	public void testGetFadingSpeed() {
		Gate gate1 = new Gate(500,550,50,200);
		assertEquals(gate1.getFadingSpeed(),1000,0);
	}
	
	@Test
	public void testSetFadingSpeed() {
		Gate gate1 = new Gate(500,550,50,200);
		gate1.setFadingSpeed(500);
		assertEquals(gate1.getFadingSpeed(),500,0);
	}
}
