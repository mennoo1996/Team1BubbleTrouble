import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;


public class MyShapeFillTest {

	@Test
	public void testMyShapeFill() {
		MyShapeFill myshapefill1 = new MyShapeFill(Color.blue);
		assertEquals(myshapefill1.getColor(), Color.blue);
	}

	@Test
	public void testColorAt() {
		Rectangle rectangle = new Rectangle(200,200,5,20);
		MyShapeFill myshapefill1 = new MyShapeFill(Color.blue);
		assertEquals(myshapefill1.colorAt(rectangle, 2f, 3f), Color.blue);
		
	}

	@Test
	public void testGetOffsetAt() {
		Rectangle rectangle = new Rectangle(200,200,5,20);
		MyShapeFill myshapefill1 = new MyShapeFill(Color.blue);
		assertEquals(myshapefill1.getOffsetAt(rectangle, 2f, 3f), new Vector2f());
	}

	@Test
	public void testGetColor() {
		MyShapeFill myshapefill1 = new MyShapeFill(Color.blue);
		assertEquals(myshapefill1.getColor(), Color.blue);
	}

	@Test
	public void testSetColor() {
		MyShapeFill myshapefill1 = new MyShapeFill(Color.blue);
		myshapefill1.setColor(Color.black);
		assertEquals(myshapefill1.getColor(), Color.black);
		
	}

}
