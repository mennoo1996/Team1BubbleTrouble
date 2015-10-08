package logic;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import logic.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class LoggerTest {
	
	private Logger logger;
	
	@Before
	public void setUp() {
		logger = Logger.getInstance();
		logger.setTesting(true);
	}

	@Test
	public void testConstructor() {
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
		assertEquals(5, Logger.PriorityLevels.VERYHIGH.getValue());
	}
	
	@Test
	public void testLog() {
		logger.resetLogBuffer();
		logger.log("testLog", Logger.PriorityLevels.MEDIUM, "testing");
		logger.setFilterFile(true);
		logger.log("testLog", Logger.PriorityLevels.MEDIUM, "testing");
		logger.setFilterFile(false);
		logger.log("testLog", Logger.PriorityLevels.MEDIUM, "testing");
		String expectedLogBuffer = "[testing|3]: testLog\n[testing|3]: testLog\n[testing|3]: testLog";
		assertEquals(expectedLogBuffer, logger.getLogBuffer());
	}
	
	@Test
	public void testWriteToFile() {
		logger.log("testLog", Logger.PriorityLevels.MEDIUM, "testing");
		logger.writeToFile();
		try {
			Scanner sc  = new Scanner(new File("logs/testing.txt"));
			String line = sc.nextLine();
			System.out.println(line);
			assertEquals("[testing|3]: testLog", line);
			assertEquals(0, logger.getLogBuffer().length());
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWriteToFileException() {
		ExpectedException exception = ExpectedException.none();
		exception.expect(FileNotFoundException.class);
		logger.setTestingFileName("/een/folder/haha/geenfilename");
		logger.writeToFile();
	}

	@Test
	public void testGetCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date now = new Date();
	    String strDate = sdf.format(now);
	    String loggerTimeStamp = logger.getCurrentTimeStamp();
	    loggerTimeStamp = loggerTimeStamp.substring(0, loggerTimeStamp.length() - 4);
	    assertEquals(strDate, loggerTimeStamp);
	}
	
	@Test
	public void testIsLoggingOnAndSetLoggingOn() {
		logger.setLoggingOn(true);
		assertTrue(logger.isLoggingOn());
		logger.setLoggingOn(false);
		assertFalse(logger.isLoggingOn());
	}
	
	@Test
	public void testSetAndGetConsoleLogging() {
		logger.setConsoleLoggingOn(true);
		assertTrue(logger.isConsoleLoggingOn());
		logger.setConsoleLoggingOn(false);
		assertFalse(logger.isConsoleLoggingOn());
	}
	
	@Test
	public void testSetAndGetFileLoggingOn() {
		logger.setFileLoggingOn(true);
		assertTrue(logger.isFileLoggingOn());
		logger.setFileLoggingOn(false);
		assertFalse(logger.isFileLoggingOn());		
	}
	
	@Test
	public void testSetAndGetFilterTagOn() {
		logger.setFilterTagOn(true);
		assertTrue(logger.isFilterTagOn());
		logger.setFilterTagOn(false);
		assertFalse(logger.isFilterTagOn());		
	}
	
	@Test
	public void testSetAndGetMinimumPriorityLevel() {
		assertEquals(logger.getMinimumPriorityLevel(), 0);
		logger.setMinimumPriorityLevel(4);
		assertEquals(logger.getMinimumPriorityLevel(), 4);
	}
	
	@Test
	public void testSetAndGetTagFilters() {
		assertEquals(0, logger.getTagFilters().size());
		ArrayList<String> al = new ArrayList<String>();
		al.add("hoi");
		al.add("testing");
		logger.setTagFilters(al);
		assertEquals(al, logger.getTagFilters());
	}
	
	@Test public void testSetAndGetFilterFile() {
		assertFalse(logger.isFilterFile());
		logger.setFilterFile(true);
		assertTrue(logger.isFilterFile());
	}
	
	@Test public void testSetAndGetLogBuffer() {
		assertTrue(logger.getLogBuffer().isEmpty());
		logger.setLogBuffer("dadadada");
		assertEquals("dadadada", logger.getLogBuffer());
	}
	
	@Test
	public void testSetAndGetTesting() {
		assertTrue(logger.isTesting());
		logger.setTesting(false);
		assertFalse(logger.isTesting());
	}

	@Test
	public void testSetAndGetTestingFileName() {
		assertEquals("logs/testing.txt", logger.getTestingFileName());
		logger.setTestingFileName("lalala");
		assertEquals("lalala", logger.getTestingFileName());
	}
}
