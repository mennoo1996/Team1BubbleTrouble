import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import logic.BouncingCircle;
import logic.Gate;
import logic.Level;

import org.junit.Before;
import org.junit.Test;

public class LevelTest {
	ArrayList<BouncingCircle> circles;
	ArrayList<Gate> gates;
	Level l;
	BouncingCircle bc;
	Gate g;

	@Before
	public void setUp() throws Exception {
		circles = new ArrayList<BouncingCircle>();
		gates = new ArrayList<Gate>();
		bc = new BouncingCircle (10, 10, 10, 10, 10, 10);
		g = new Gate (10, 10, 10, 10);
		circles.add(bc);
		gates.add(g);
	}

	@Test
	public void testLevelTime() {
		l = new Level(5, circles, gates);
		assertEquals(5, l.getTime());
		
	}
	
	@Test
	public void testLevelCircles() {
		l = new Level(5, circles, gates);
		assertEquals(circles, l.getCircles());
		
	}
	
	@Test
	public void testLevelGates(){
		l = new Level (5, circles, gates);
		assertEquals(gates, l.getGates());
	}

	@Test
	public void testGetTime() {
		l = new Level(5, circles, gates);
		assertEquals(5, l.getTime());
	}
	
	@Test
	public void testGetTimeNoConstantValue() {
		l = new Level (10, circles, gates);
		assertEquals(10, l.getTime());
	}

	@Test
	public void testSetTime() {
		l = new Level(10, circles, gates);
		l.setTime(5);
		assertEquals(5, l.getTime());
	}
	
	@Test
	public void testSetTimeNoConstantValue() {
		l = new Level (10, circles, gates);
		l.setTime(20);
		assertEquals(20, l.getTime());
		
	}

	@Test
	public void testGetCircles() {
		l = new Level(10, circles, gates);
		assertEquals(circles, l.getCircles());
	}
	
	@Test
	public void testGetCirclesNoConstantValue() {
		circles.add(bc);
		l = new Level (10, circles, gates);
		assertEquals(circles, l.getCircles());
		
	}

	@Test
	public void testSetCircles() {
		l = new Level(10, circles, gates);
		ArrayList<BouncingCircle> circles2 = new ArrayList<BouncingCircle>();
		l.setCircles(circles2);
		assertEquals(circles2, l.getCircles());
	}
	
	@Test
	public void testSetCirclesNoConstantValue() {
		l = new Level(10, circles, gates);
		ArrayList<BouncingCircle> circles2 = new ArrayList<BouncingCircle>();
		circles2.add(bc);
		circles2.add(bc);
		l.setCircles(circles2);
		assertEquals(circles2, l.getCircles());
	}

	@Test
	public void testGetGates() {
		l = new Level(10, circles, gates);
		assertEquals(gates, l.getGates());
	}
	
	@Test
	public void testGetGatesNoConstantValue() {
		gates.add(g);
		l = new Level (10, circles, gates);
		assertEquals(gates, l.getGates());
	}

	@Test
	public void testSetGates() {
		l = new Level(10, circles, gates);
		ArrayList<Gate> gates2 = new ArrayList<Gate>();
		l.setGates(gates2);
		assertEquals(gates2, l.getGates());
	}
	
	@Test
	public void testSetGatesNoConstantValue() {
		l = new Level(10, circles, gates);
		ArrayList<Gate> gates2 = new ArrayList<Gate>();
		gates2.add(g);
		gates2.add(g);
		l.setGates(gates2);
		assertEquals(gates2, l.getGates());
	}

}
