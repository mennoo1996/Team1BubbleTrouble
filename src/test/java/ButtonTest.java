import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;

import gui.Button;
import logic.MyRectangle;

public class ButtonTest {
	
	Image i;
	Button p;
	Image i2;
	Image i3;
	Image i4;

	@Before
	public void setUp() throws Exception {
		i = mock(Image.class);
		i2 = mock(Image.class);
		i3 = mock(Image.class);
		i4 = mock(Image.class);
	}

	@Test
	public void testButtonX() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(1, p.getX(), 0);
	}
	
	@Test
	public void testButtonY() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(2, p.getY(), 0);
	}
	
	@Test
	public void testButtonWidth() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(3, p.getWidth(), 0);
	}
	
	@Test
	public void testButtonHeight() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(4, p.getHeight(), 0);
	}
	
	@Test
	public void testButtonImage() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(i, p.getImageN());
	}

	@Test
	public void testGetRectangle() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(new MyRectangle(1, 2, 3, 4), p.getRectangle());
	}

	@Test
	public void testGetCenterX() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(2.5, p.getCenterX(), 0);
	}
	
	@Test
	public void testGetCenterXNoConstantValue() {
		p = new Button (3, 6, -8, 3, i, i3);
		assertEquals(-1, p.getCenterX(), 0);
	}

	@Test
	public void testGetCenterY() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(4, p.getCenterY(), 0);
	}
	
	@Test
	public void testGetCenterYNoConstantValue() {
		p = new Button (2, 7, 1, 5, i, i3);
		assertEquals(9.5, p.getCenterY(), 0);
	}

	@Test
	public void testGetMaxX() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(4, p.getMaxX(), 0);
	}
	
	@Test
	public void testGetMaxXNoConstantValue() {
		p = new Button(3, -4, 6 ,2, i, i3);
		assertEquals(9, p.getMaxX(), 0);
	}
	

	@Test
	public void testGetMaxY() {
		p = new Button(1, 2, 3, 4, i,i3);
		assertEquals(6, p.getMaxY(), 0);
	}
	
	@Test
	public void testGetMaxYNoConstantValue() {
		p = new Button(4, -9, 3, 6, i, i3);
		assertEquals(-3, p.getMaxY(), 0);
	}

	@Test
	public void testGetX() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(1, p.getX(), 0);
	}
	
	@Test
	public void testGetXNoConstantValue() {
		p = new Button(3, 2, 3, 4, i, i3);
		assertEquals(3, p.getX(), 0);
	}

	@Test
	public void testSetX() {
		p = new Button(1, 2, 3, 4, i, i3);
		p.setX(4);
		assertEquals(4, p.getX(), 0);
	}
	
	@Test
	public void testSetXNoConstantValue() {
		p = new Button(1, 2, 3, 4, i, i3);
		p.setX(6);
		assertEquals(6, p.getX(), 0);
	}

	@Test
	public void testGetY() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(2, p.getY(), 0);
	}

	@Test
	public void testGetYNoConstantValue() {
		p = new Button(1, 6, 3, 4, i, i3);
		assertEquals(6, p.getY(), 0);
	}
	@Test
	public void testSetY() {
		p = new Button(1, 2, 3, 4, i, i3);
		p.setY(5);
		assertEquals(5, p.getY(), 0);
	}
	
	@Test
	public void testSetYNoConstantValue() {
		p = new Button(1, 2, 3, 4, i, i3);
		p.setY(9);
		assertEquals(9, p.getY(), 0);
	}

	@Test
	public void testGetWidth() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(3, p.getWidth(), 0);
	}
	
	@Test
	public void testGetWidthNoConstantValue() {
		p = new Button(1, 2, 6, 4, i, i3);
		assertEquals(6, p.getWidth(), 0);
	}

	@Test
	public void testSetWidth() {
		p = new Button(1, 2, 3, 4, i, i3);
		p.setWidth(7);
		assertEquals(7, p.getWidth(), 0);
	}
	
	@Test
	public void testSetWidthNoConstantValue() {
		p = new Button(1, 2, 3, 4, i, i3);
		p.setWidth(-4);
		assertEquals(-4, p.getWidth(), 0);
	}

	@Test
	public void testGetHeight() {
		p = new Button(1, 2, 3, 4, i, i3);
		assertEquals(4,  p.getHeight(), 0);
	}
	
	@Test
	public void testGetHeightNoConstantValue() {
		p = new Button(1, 2, 3, 7, i, i3);
		assertEquals(7, p.getHeight(), 0);
	}

	@Test
	public void testSetHeight() {
		p = new Button(1, 2, 3, 4, i, i3);
		p.setHeight(6);
		assertEquals(6, p.getHeight(), 0);
	}
	
	@Test
	public void testSetHeightNoConstantValue() {
		p = new Button(1, 2, 3, 4, i, i3);
		p.setHeight(18);
		assertEquals(18, p.getHeight(), 0);
	}
	
	@Test
	public void testSecondConstructorX() {
		p = new Button(1, 2, 3, 4, i, i2);
		assertEquals(1, p.getX(), 0);
	}
	
	@Test
	public void testSecondConstructorY() {
		p = new Button(1, 2, 3, 4, i, i2);
		assertEquals(2, p.getY(), 0);
		
	}
	
	@Test
	public void testSecondConstructurWidth() {
		p = new Button(1, 2, 3, 4, i, i2);
		assertEquals(3, p.getWidth(), 0);
		
	}
	
	@Test
	public void testSecondConstructorHeight() {
		p = new Button(1, 2, 3, 4, i, i2);
		assertEquals(4, p.getHeight(), 0);
	}
	
	@Test
	public void testSecondConstructorImage() {
		p = new Button(1, 2, 3, 4, i, i2);
		assertEquals(i, p.getImageN());
	}
	
	@Test
	public void testSecondConstructurSecondImage() {
		p = new Button(1, 2, 3, 4, i, i3, i2, i3);
		assertEquals(i2, p.getImageMouseOverN());
	}
	
	@Test
	public void testSetImage() {
		p = new Button(1, 2, 3, 4, i, i2);
		p.setImage(i2, i3);
		assertEquals(i2, p.getImageN());
	}
	
	@Test
	public void testSetImageMouseOver() {
		p = new Button(1, 2, 3, 4, i, i2);
		p.setImageMouseOver(i, i3);
		assertEquals(i, p.getImageMouseOverN());
	}
	
	@Test
	public void testGetImageA() {
		p = new Button(1, 2, 3, 4, i3, i4);
		p.setImage(i,i2);
		assertEquals(i2, p.getImageA());
	}
	
	@Test
	public void testGetImageMouseOverA() {
		p = new Button(1, 2, 3, 4, i3, i4);
		p.setImageMouseOver(i, i2);
		assertEquals(i2, p.getImageMouseOverA());
	}
	
	@Test
	public void testEquals() {
		Button a = new Button(1, 2, 3, 4, i, i2);
		Button b = new Button(1, 2, 3, 4, i, i2);
		assertTrue(a.equals(b));
	}
	
	@Test
	public void testEquals2() {
		Button a = new Button(2, 2, 3, 4, i, i2);
		Button b = new Button(1, 2, 3, 4, i, i2);
		assertFalse(a.equals(b));
	}

	@Test
	public void testEquals3() {
		Button a = new Button(1, 3, 3, 4, i, i2);
		Button b = new Button(1, 2, 3, 4, i, i2);
		assertFalse(a.equals(b));
	}
	
	@Test
	public void testEquals4() {
		Button a = new Button(1, 2, 5, 4, i, i2);
		Button b = new Button(1, 2, 3, 4, i, i2);
		assertFalse(a.equals(b));
	}
	
	@Test
	public void testEquals5() {
		Button a = new Button(1, 2, 3, 6, i, i2);
		Button b = new Button(1, 2, 3, 4, i, i2);
		assertFalse(a.equals(b));
	}
	
	@Test
	public void testEquals6() {
		Button a = new Button(1, 2, 3, 4, i3, i2);
		Button b = new Button(1, 2, 3, 4, i, i2);
		assertFalse(a.equals(b));
	}
	
	@Test
	public void testEquals7() {
		Button a = new Button(1, 2, 3, 4, i, i3);
		Button b = new Button(1, 2, 3, 4, i, i2);
		assertFalse(a.equals(b));
	}
	
	@Test
	public void testEquals8() {
		Button a = new Button(1, 2, 3, 4, i, i2);
		assertFalse(a.equals(1));
	}
	
	@Test
	public void testHashCode() {
		Button a = new Button(1, 2, 3, 4, i, i2);
		assertEquals(a.hashCode(), 0);
	}
	
	


}
