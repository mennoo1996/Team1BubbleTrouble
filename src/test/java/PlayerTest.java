import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import logic.MyRectangle;
import logic.Player;
import gui.GameState;
import gui.MainGame;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class PlayerTest {
	
	MainGame mg;
	Image i;
	Image i2;
	Player p;
	SpriteSheet s;
	GameState gs;

	@Before
	public void setUp() throws Exception {
		i = mock(Image.class);
		i2 = mock(Image.class);
		mg = new MainGame("maingame");
		s = mock(SpriteSheet.class);
		gs = mock(GameState.class);
	}

	@Test
	public void testPlayerX() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(1, p.getX(), 0);
	}
	
	@Test
	public void testPlayerY() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(2, p.getY(), 0);
	}
	
	@Test
	public void testPlayerWidth() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(3, p.getWidth(), 0);
	}
	
	@Test
	public void testPlayerHeight() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(4, p.getHeight(), 0);
	}
	
	@Test
	public void testPlayerGetImage() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(i, p.getImage());
	}
	
	@Test
	public void testPlayerGetShieldImage() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(i2, p.getShieldImage());
	}
	
	@Test
	public void testPlayerSetImage() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		Image j = mock(Image.class);
		p.setImage(j);
		assertEquals(j, p.getImage());
	}

	@Test
	public void testGetRectangle() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(new MyRectangle(1, 2, 3, 4), p.getRectangle());
	}

	@Test
	public void testGetCenterX() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(2.5, p.getCenterX(), 0);
	}
	
	@Test
	public void testGetCenterXNoConstantValue() {
		p = new Player (3, 6, -8, 3, i, i2, mg);
		assertEquals(-1, p.getCenterX(), 0);
	}

	@Test
	public void testGetCenterY() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(4, p.getCenterY(), 0);
	}
	
	@Test
	public void testGetCenterYNoConstantValue() {
		p = new Player (2, 7, 1, 5, i, i2, mg);
		assertEquals(9.5, p.getCenterY(), 0);
	}

	@Test
	public void testGetMaxX() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(4, p.getMaxX(), 0);
	}
	
	@Test
	public void testGetMaxXNoConstantValue() {
		p = new Player(3, -4, 6 ,2, i, i2, mg);
		assertEquals(9, p.getMaxX(), 0);
	}
	

	@Test
	public void testGetMaxY() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(6, p.getMaxY(), 0);
	}
	
	@Test
	public void testGetMaxYNoConstantValue() {
		p = new Player(4, -9, 3, 6, i, i2, mg);
		assertEquals(-3, p.getMaxY(), 0);
	}

	@Test
	public void testGetX() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(1, p.getX(), 0);
	}
	
	@Test
	public void testGetXNoConstantValue() {
		p = new Player(3, 2, 3, 4, i, i2, mg);
		assertEquals(3, p.getX(), 0);
	}

	@Test
	public void testSetX() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setX(4);
		assertEquals(4, p.getX(), 0);
	}
	
	@Test
	public void testSetXNoConstantValue() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setX(6);
		assertEquals(6, p.getX(), 0);
	}

	@Test
	public void testGetY() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(2, p.getY(), 0);
	}

	@Test
	public void testGetYNoConstantValue() {
		p = new Player(1, 6, 3, 4, i, i2, mg);
		assertEquals(6, p.getY(), 0);
	}
	@Test
	public void testSetY() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setY(5);
		assertEquals(5, p.getY(), 0);
	}
	
	@Test
	public void testSetYNoConstantValue() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setY(9);
		assertEquals(9, p.getY(), 0);
	}

	@Test
	public void testGetWidth() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(3, p.getWidth(), 0);
	}
	
	@Test
	public void testGetWidthNoConstantValue() {
		p = new Player(1, 2, 6, 4, i, i2, mg);
		assertEquals(6, p.getWidth(), 0);
	}

	@Test
	public void testSetWidth() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setWidth(7);
		assertEquals(7, p.getWidth(), 0);
	}
	
	@Test
	public void testSetWidthNoConstantValue() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setWidth(-4);
		assertEquals(-4, p.getWidth(), 0);
	}

	@Test
	public void testGetHeight() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		assertEquals(4,  p.getHeight(), 0);
	}
	
	@Test
	public void testGetHeightNoConstantValue() {
		p = new Player(1, 2, 3, 7, i, i2, mg);
		assertEquals(7, p.getHeight(), 0);
	}

	@Test
	public void testSetHeight() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setHeight(6);
		assertEquals(6, p.getHeight(), 0);
	}
	
	@Test
	public void testSetHeightNoConstantValue() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setHeight(18);
		assertEquals(18, p.getHeight(), 0);
	}
	
	@Test
	public void testSpriteSheet() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setSpritesheet(s);
		assertEquals(s, p.getSpritesheet());
		
	}
	
	@Test
	public void testMovement() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setMovement(8);
		assertEquals(8, p.getMovement());
	}
	
	@Test
	public void testMovementCounter() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.resetMovementCounter();
		p.incrementMovementCounter();
		assertEquals(1, p.getMovementCounter());
	}
	
	@Test
	public void testMovementCounter2() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setMovementCounter_Max(0);
		p.resetMovementCounter();
		p.incrementMovementCounter();
		assertEquals(0, p.getMovementCounter());
	}
	
	@Test
	public void testMovementCounterMax() {
		p = new Player(1, 2, 3, 4, i, i2, mg);
		p.setMovementCounter_Max(5);
		assertEquals(5, p.getMovementCounter_Max());
	}
	
	
	
	


}
