import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyRectangleTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testHashCode() {
		MyRectangle a = new MyRectangle(1, 2, 3, 4);
		assertEquals(0, a.hashCode());
	}

	@Test
	public void testMyRectangle() {
		MyRectangle a = new MyRectangle(1, 2, 3, 4);
		assertEquals(1, a.getX(), 0);
	}

	@Test
	public void testEqualsObject() {
		MyRectangle a1 = new MyRectangle(1, 2, 3, 4);
		MyRectangle a2 = new MyRectangle(1, 2, 3, 4);
		assertEquals(a1, a2);
	}
	
	@Test
	public void testEqualsObject2() {
		MyRectangle a1 = new MyRectangle(1, 2, 3, 4);
		MyRectangle a2 = new MyRectangle(5, 6, 7, 8);
		assertFalse(a1.equals(a2));
	}
	
	@Test
	public void testEqualsObject3() {
		MyRectangle a1 = new MyRectangle(1, 2, 3, 4);
		MyRectangle a2 = new MyRectangle(1, 6, 7, 8);
		assertFalse(a1.equals(a2));
	}
	
	@Test
	public void testEqualsObject4() {
		MyRectangle a1 = new MyRectangle(1, 2, 3, 4);
		MyRectangle a2 = new MyRectangle(1, 2, 7, 8);
		assertFalse(a1.equals(a2));
	}
	
	@Test
	public void testEqualsObject5() {
		MyRectangle a1 = new MyRectangle(1, 2, 3, 4);
		MyRectangle a2 = new MyRectangle(1, 2, 3, 8);
		assertFalse(a1.equals(a2));
	}
	
	@Test
	public void testEqualsObject6() {
		MyRectangle a1 = new MyRectangle(1, 2, 3, 4);
		assertFalse(a1.equals(new Integer(7)));
	}

}
