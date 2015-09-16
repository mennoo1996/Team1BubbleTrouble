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
public class Logger {

	private boolean loggingOn;
	private boolean consoleLoggingOn;
	private boolean fileLoggingOn;
	private int minimumPriorityLevel;
	private ArrayList<String> tagFilters;
	private boolean filterTagOn;
	private String logBuffer;
	private boolean filterFile;
	
	/**
	 * Constructor of the logger.
	 * @param loggingOn			- logging on or of
	 */
	public Logger(boolean loggingOn) {
		super();
		System.out.println("\n\nLOGGER INITIALIZED\nLogging On: " + loggingOn + "\n\n");
		this.loggingOn = loggingOn;
		logBuffer = "";
		tagFilters = new ArrayList<String>();
		fileLoggingOn = true;
		minimumPriorityLevel = 0;
		filterTagOn = false;
		filterFile = false;
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
		String timeStamp = getCurrentTimeStamp();
		String newLogString = timeStamp + " - [" + tag + "|" 
			+ priorityLevel.value + "]: " + logString;
		
		if (priorityLevel.value >= minimumPriorityLevel || (!filterTagOn || tagFilters.contains(tag))) {
			if (consoleLoggingOn) {
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
			Date now = new Date();
			String strDate = "logs/" + sdf.format(now);
			File file = new File(strDate);
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
	
	private String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date now = new Date();
	    String strDate = sdf.format(now);
	    return strDate;
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
	
	
	
	
}
