import static org.junit.Assert.*;
import logic.Logger;

import org.junit.Test;


public class LoggerTest {

	@Test
	public void testConstructor() {
		Logger logger = new Logger(true);
		assertNotNull(logger);
	}
	
	@Test
	public void testPriorityLevelsLow() {
		assertEquals(1, Logger.PriorityLevels.VERYLOW.getValue());
		assertEquals(2, Logger.PriorityLevels.LOW.getValue());
	}
	
	@Test
	public void testPriorityLevelsMedium() {
		assertEquals(3, Logger.PriorityLevels.MEDIUM.getValue());
	}
	
	@Test
	public void testPriorityLevelsHigh() {
		assertEquals(4, Logger.PriorityLevels.HIGH.getValue());
		assertEquals(4, Logger.PriorityLevels.VERYHIGH.getValue());
	}
	
	@Test
	public void testLog() {
		Logger logger = new Logger(true);
		logger.log("testLog", Logger.PriorityLevels.MEDIUM, "testing");
		logger.setFilterFile(true);
		logger.log("testLog", Logger.PriorityLevels.MEDIUM, "testing");
	}

}
