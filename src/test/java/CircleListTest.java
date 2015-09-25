import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import logic.BouncingCircle;
import logic.CircleList;

public class CircleListTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCircleList() {
		ArrayList<BouncingCircle> list = new ArrayList<BouncingCircle>();
		CircleList cl = new CircleList(list);
		
		assertEquals(1, cl.getNewID());
	}
	
	@Test
	public void testCircleList2() {
		ArrayList<BouncingCircle> list = new ArrayList<BouncingCircle>();
		CircleList cl = new CircleList(list);
		assertEquals(list, cl.getCircles());
		
	}

	@Test
	public void testAdd() {
		BouncingCircle circle = mock(BouncingCircle.class);
		when(circle.getId()).thenReturn(2);
		ArrayList<BouncingCircle> list = new ArrayList<BouncingCircle>();
		CircleList cl = new CircleList(list);
		cl.add(circle);
		assertEquals(3, cl.getNewID());
	}
	
	@Test
	public void testAdd2() {
		BouncingCircle circle = mock(BouncingCircle.class);
		when(circle.getId()).thenReturn(2);
		ArrayList<BouncingCircle> list = new ArrayList<BouncingCircle>();
		CircleList cl = new CircleList(list);
		cl.add(circle);
		assertEquals(1, cl.getCircles().size());
	}
	
	@Test
	public void testAdd3() {
		BouncingCircle circle = mock(BouncingCircle.class);
		when(circle.getId()).thenReturn(-1);
		ArrayList<BouncingCircle> list = new ArrayList<BouncingCircle>();
		CircleList cl = new CircleList(list);
		cl.add(circle);
		assertEquals(1, cl.getNewID());
	}
	
	
	


	@Test
	public void testSetCircles() {
		ArrayList<BouncingCircle> list = new ArrayList<BouncingCircle>();
		CircleList cl = new CircleList(list);
		ArrayList<BouncingCircle> list2 = new ArrayList<BouncingCircle>();
		cl.setCircles(list2);
		assertEquals(list2, cl.getCircles());
	}

}
