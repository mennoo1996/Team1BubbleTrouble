import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;

public class PlayerTest {
	
	Image i;
	Player p;

	@Before
	public void setUp() throws Exception {
		i = mock(Image.class);
	}

	@Test
	public void testPlayerX() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(1, p.getX(), 0);
	}
	
	@Test
	public void testPlayerY() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(2, p.getY(), 0);
	}
	
	@Test
	public void testPlayerWidth() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(3, p.getWidth(), 0);
	}
	
	@Test
	public void testPlayerHeight() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(4, p.getHeight(), 0);
	}
	
	@Test
	public void testPlayerImage() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(i, p.getImage());
	}

	@Test
	public void testGetRectangle() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(new MyRectangle(1, 2, 3, 4), p.getRectangle());
	}

	@Test
	public void testGetCenterX() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(2.5, p.getCenterX(), 0);
	}
	
	@Test
	public void testGetCenterXNoConstantValue() {
		p = new Player (3, 6, -8, 3, i);
		assertEquals(-1, p.getCenterX(), 0);
	}

	@Test
	public void testGetCenterY() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(4, p.getCenterY(), 0);
	}
	
	@Test
	public void testGetCenterYNoConstantValue() {
		p = new Player (2, 7, 1, 5, i);
		assertEquals(9.5, p.getCenterY(), 0);
	}

	@Test
	public void testGetMaxX() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(4, p.getMaxX(), 0);
	}
	
	@Test
	public void testGetMaxXNoConstantValue() {
		p = new Player(3, -4, 6 ,2, i);
		assertEquals(9, p.getMaxX(), 0);
	}
	

	@Test
	public void testGetMaxY() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(6, p.getMaxY(), 0);
	}
	
	@Test
	public void testGetMaxYNoConstantValue() {
		p = new Player(4, -9, 3, 6, i);
		assertEquals(-3, p.getMaxY(), 0);
	}

	@Test
	public void testGetX() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(1, p.getX(), 0);
	}
	
	@Test
	public void testGetXNoConstantValue() {
		p = new Player(3, 2, 3, 4, i);
		assertEquals(3, p.getX(), 0);
	}

	@Test
	public void testSetX() {
		p = new Player(1, 2, 3, 4, i);
		p.setX(4);
		assertEquals(4, p.getX(), 0);
	}
	
	@Test
	public void testSetXNoConstantValue() {
		p = new Player(1, 2, 3, 4, i);
		p.setX(6);
		assertEquals(6, p.getX(), 0);
	}

	@Test
	public void testGetY() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(2, p.getY(), 0);
	}

	@Test
	public void testGetYNoConstantValue() {
		p = new Player(1, 6, 3, 4, i);
		assertEquals(6, p.getY(), 0);
	}
	@Test
	public void testSetY() {
		p = new Player(1, 2, 3, 4, i);
		p.setY(5);
		assertEquals(5, p.getY(), 0);
	}
	
	@Test
	public void testSetYNoConstantValue() {
		p = new Player(1, 2, 3, 4, i);
		p.setY(9);
		assertEquals(9, p.getY(), 0);
	}

	@Test
	public void testGetWidth() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(3, p.getWidth(), 0);
	}
	
	@Test
	public void testGetWidthNoConstantValue() {
		p = new Player(1, 2, 6, 4, i);
		assertEquals(6, p.getWidth(), 0);
	}

	@Test
	public void testSetWidth() {
		p = new Player(1, 2, 3, 4, i);
		p.setWidth(7);
		assertEquals(7, p.getWidth(), 0);
	}
	
	@Test
	public void testSetWidthNoConstantValue() {
		p = new Player(1, 2, 3, 4, i);
		p.setWidth(-4);
		assertEquals(-4, p.getWidth(), 0);
	}

	@Test
	public void testGetHeight() {
		p = new Player(1, 2, 3, 4, i);
		assertEquals(4,  p.getHeight(), 0);
	}
	
	@Test
	public void testGetHeightNoConstantValue() {
		p = new Player(1, 2, 3, 7, i);
		assertEquals(7, p.getHeight(), 0);
	}

	@Test
	public void testSetHeight() {
		p = new Player(1, 2, 3, 4, i);
		p.setHeight(6);
		assertEquals(6, p.getHeight(), 0);
	}
	
	@Test
	public void testSetHeightNoConstantValue() {
		p = new Player(1, 2, 3, 4, i);
		p.setHeight(18);
		assertEquals(18, p.getHeight(), 0);
	}


}
