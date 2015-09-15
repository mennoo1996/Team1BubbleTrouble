import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import logic.Button;
import logic.MyButton;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;

public class MyButtonTest {
	Image i;
	Image i2;

	@Before
	public void setUp() throws Exception {
		i = mock(Image.class);
		i2 = mock(Image.class);
	}

	@Test
	public void testHashCode() {
		MyButton b = new MyButton(1, 2, 3, 4, i, i2);
		assertEquals(0, b.hashCode());
	}

	@Test
	public void testMyButton() {
		MyButton b = new MyButton(1, 2, 3, 4, i, i2);
		assertEquals(3, b.getWidth(), 0);
	}

	@Test
	public void testEqualsObject() {
		MyButton b1 = new MyButton(1, 2, 3, 4, i, i2);
		MyButton b2 = new MyButton(1, 2, 3, 4, i, i2);
		assertEquals(b1, b2);
	}
	
	@Test
	public void testEqualsObject2() {
		MyButton b1 = new MyButton(1, 2, 3, 4, i, i2);
		Image i3 = mock(Image.class);
		MyButton b2 = new MyButton (5, 6, 7, 8, i3, i2);
		assertFalse(b1.equals(b2));
	}
	
	@Test
	public void testEqualsObject3() {
		MyButton b1 = new MyButton(1, 2, 3, 4, i, i2);
		Image i3 = mock(Image.class);
		MyButton b2 = new MyButton(1, 5, 6, 7, i3, i2);
		assertFalse(b1.equals(b2));
	}
	
	@Test
	public void testEqualsObject4() {
		MyButton b1 = new MyButton(1, 2, 3, 4, i, i2);
		Image i3 = mock(Image.class);
		MyButton b2 = new MyButton(1, 2, 6, 7, i3, i2);
		assertFalse(b1.equals(b2));
	}
	
	@Test
	public void testEqualsObject5() {
		MyButton b1 = new MyButton(1, 2, 3, 4, i, i2);
		Image i3 = mock(Image.class);
		MyButton b2 = new MyButton(1, 2, 3, 7, i3, i2);
		assertFalse(b1.equals(b2));
	}
	
	@Test
	public void testEqualsObject6() {
		MyButton b1 = new MyButton(1, 2, 3, 4, i, i2);
		Image i3 = mock(Image.class);
		MyButton b2 = new MyButton(1, 2, 3, 4, i3, i2);
		assertFalse(b1.equals(b2));
	}
	
	@Test
	public void testEqualsObject7() {
		Button b1 = new MyButton(1, 2, 3, 4, i, i2);
		assertFalse(b1.equals(new Integer(5)));
	}

	@Test
	public void testGetSerialversionuid() {
		MyButton b = new MyButton(1, 2, 3, 4, i, i2);
		assertEquals(1, b.getSerialversionuid());
	}

}
