package powerups;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import logic.CircleList;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyFloat;

public class FastPowerupTest {

	private FastPowerup fp;
	
	@Before
	public void setUp() {
		fp = new FastPowerup();
	}
	
	@Test
	public void testFreezePowerup() {
		assertNotNull(fp);
	}

	@Test
	public void testUpdateCircles() {
		CircleList cl = mock(CircleList.class);
		fp.updateCircles(cl);
		verify(cl, times(1)).setAllMultipliers(anyFloat());
	}

	@Test
	public void testUpdate1() {
		CircleList cl = mock(CircleList.class);
		fp.update(2, cl);
		verify(cl, times(0)).setAllMultipliers(anyFloat());
	}
	
	@Test
	public void testUpdate2() {
		CircleList cl = mock(CircleList.class);
		fp.update(6, cl);
		verify(cl, times(1)).setAllMultipliers(anyFloat());
	}

	@Test
	public void testGetMultiplier() {
		assertEquals(1.5, fp.getMultiplier(), 0);
	}

	@Test
	public void testSetMultiplier() {
		fp.setMultiplier(5);
		assertEquals(5, fp.getMultiplier(), 0);
	}

	@Test
	public void testGetTimeRemaining1() {
		assertEquals(5, fp.getTimeRemaining(), 0);
	}
	
	@Test
	public void testGetTimeRemaining2() {
		CircleList cl = mock(CircleList.class);
		fp.update(3, cl);
		assertEquals(2, fp.getTimeRemaining(), 0);
	}

	@Test
	public void testIsDone1() {
		assertFalse(fp.isDone());
	}
	
	@Test
	public void testIsDone2() {
		CircleList cl = mock(CircleList.class);
		fp.update(6, cl);
		assertTrue(fp.isDone());
	}

}
