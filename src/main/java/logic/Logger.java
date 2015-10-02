package logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A class used for logging actions.
 * @author Bart
 *
 */
public final class Logger {

	private boolean loggingOn;
	private boolean consoleLoggingOn;
	private boolean fileLoggingOn;
	private int minimumPriorityLevel;
	private ArrayList<String> tagFilters;
	private boolean filterTagOn;
	private String logBuffer;
	private boolean filterFile;
	private boolean testing;
	private String testingFileName;
	
	private static Logger instance = new Logger(true);
	
	/**
	 * Constructor of the logger.
	 * @param loggingOn			- logging on or of
	 */
	private Logger(boolean loggingOn) {
		super();
		System.out.println("\nLOGGER INITIALIZED\nLogging On: " + loggingOn + "\n");
		this.loggingOn = loggingOn;
		logBuffer = "";
		tagFilters = new ArrayList<String>();
		fileLoggingOn = true;
		minimumPriorityLevel = 0;
		filterTagOn = false;
		filterFile = false;
		consoleLoggingOn = true;
		testing = false;
		testingFileName = "logs/testing.txt";
	}
	
	/**
	 * Reset the log buffer.
	 */
	public void resetLogBuffer() {
		logBuffer = "";
	}
	
	/**
	 * Return the Logger instance.
	 * @return the logger
	 */
	public static Logger getInstance() {
		return instance;
	}
	
	/**
	 * Enum of priority levels.
	 * @author Stefan
	 *
	 */
	public enum PriorityLevels {
		VERYHIGH(5),
		HIGH(4),
		MEDIUM(3),
		LOW(2),
		VERYLOW(1);
		
		private final int value;
		
		/**
		 * PriorityLevel constructor.
		 * @param x level of priority
		 */
		PriorityLevels(final int x) {
			value = x;
		}
		
		/**
		 * 
		 * @return the value of a prioritylevel
		 */
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * Log a given string with a given priority level and a given tag.
	 * @param logString		- the string to log
	 * @param priorityLevel	- the priority level of the log
	 * @param tag			- the tag of the log
	 */
	public void log(String logString, Logger.PriorityLevels priorityLevel, String tag) {
		String newLogString;
		if (!testing) {
			String timeStamp = getCurrentTimeStamp();
			newLogString = timeStamp + " - [" + tag + "|" 
					+ priorityLevel.value + "]: " + logString;	
		} else {
			newLogString = "[" + tag + "|" 
					+ priorityLevel.value + "]: " + logString;	
		}
		
		boolean tagFilter = !filterTagOn || tagFilters.contains(tag);
		if (priorityLevel.value >= minimumPriorityLevel || tagFilter) {
			if (consoleLoggingOn && !testing) {
				System.out.println(newLogString);
			}
			if (filterFile) {
				if (logBuffer.length() != 0) {
					logBuffer += "\n";
				}
				logBuffer += newLogString;	
			}
		}
		if (!filterFile) {
			if (logBuffer.length() != 0) {
				logBuffer += "\n";
			}
			logBuffer += newLogString;
		}
	}
	
	/**
	 * Write the logBuffer to a file.
	 */
	public void writeToFile() {
		if (fileLoggingOn) {
			File file = new File(getFileName());
			try {
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(logBuffer);
				fileWriter.close();
				this.log("Succesfully wrote log to file", Logger.PriorityLevels.MEDIUM, "log I/O");
			} catch (IOException e) {
				this.log("Could not write logfile", Logger.PriorityLevels.VERYHIGH, "Error");
			}
		}
		logBuffer = "";
	}
	
	/**
	 * Get the current time stamp en a nice format.
	 * @return	the formatted timestamp
	 */
	public String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date now = new Date();
	    String strDate = sdf.format(now);
	    return strDate;
	}
	
	/**
	 * Get the file name.
	 * @return the filename
	 */
	private String getFileName() {
		new File("logs").mkdir();
		if (testing) {
			return testingFileName;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
			return "logs/" + sdf.format(new Date()) + ".txt";
		}
	}
	
	/**
	 * @return the loggingOn
	 */
	public boolean isLoggingOn() {
		return loggingOn;
	}
	/**
	 * @param loggingOn the loggingOn to set
	 */
	public void setLoggingOn(boolean loggingOn) {
		this.loggingOn = loggingOn;
	}
	/**
	 * @return the consoleLoggingOn
	 */
	public boolean isConsoleLoggingOn() {
		return consoleLoggingOn;
	}
	/**
	 * @param consoleLoggingOn the consoleLoggingOn to set
	 */
	public void setConsoleLoggingOn(boolean consoleLoggingOn) {
		this.consoleLoggingOn = consoleLoggingOn;
	}
	/**
	 * @return the fileLoggingOn
	 */
	public boolean isFileLoggingOn() {
		return fileLoggingOn;
	}
	/**
	 * @param fileLoggingOn the fileLoggingOn to set
	 */
	public void setFileLoggingOn(boolean fileLoggingOn) {
		this.fileLoggingOn = fileLoggingOn;
	}
	/**
	 * @return the minimumPriorityLevel
	 */
	public int getMinimumPriorityLevel() {
		return minimumPriorityLevel;
	}
	/**
	 * @param minimumPriorityLevel the minimumPriorityLevel to set
	 */
	public void setMinimumPriorityLevel(int minimumPriorityLevel) {
		this.minimumPriorityLevel = minimumPriorityLevel;
	}
	
	/**
	 * @return the tagFilters
	 */
	public ArrayList<String> getTagFilters() {
		return tagFilters;
	}

	/**
	 * @param tagFilters the tagFilters to set
	 */
	public void setTagFilters(ArrayList<String> tagFilters) {
		this.tagFilters = tagFilters;
	}

	/**
	 * @return the filterFile
	 */
	public boolean isFilterFile() {
		return filterFile;
	}

	/**
	 * @param filterFile the filterFile to set
	 */
	public void setFilterFile(boolean filterFile) {
		this.filterFile = filterFile;
	}

	/**
	 * @return the filterTagOn
	 */
	public boolean isFilterTagOn() {
		return filterTagOn;
	}
	/**
	 * @param filterTagOn the filterTagOn to set
	 */
	public void setFilterTagOn(boolean filterTagOn) {
		this.filterTagOn = filterTagOn;
	}

	/**
	 * @return the logBuffer
	 */
	public String getLogBuffer() {
		return logBuffer;
	}

	/**
	 * @param logBuffer the logBuffer to set
	 */
	public void setLogBuffer(String logBuffer) {
		this.logBuffer = logBuffer;
	}

	/**
	 * @return the testing
	 */
	public boolean isTesting() {
		return testing;
	}

	/**
	 * @param testing the testing to set
	 */
	public void setTesting(boolean testing) {
		this.testing = testing;
	}

	/**
	 * @return the testingFileName
	 */
	public String getTestingFileName() {
		return testingFileName;
	}

	/**
	 * @param testingFileName the testingFileName to set
	 */
	public void setTestingFileName(String testingFileName) {
		this.testingFileName = testingFileName;
	}
	
	
	
	
}
