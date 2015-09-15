package logic;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class used for logging actions.
 * @author Bart
 *
 */
public class Logger {

	private String outputFileName;
	private boolean loggingOn;
	private boolean consoleLoggingOn;
	private boolean fileLoggingOn;
	private int minimumPriorityLevel;
	private String[] tagFilters;
	private boolean filterTagOn;
	
	
	/**
	 * Constructor of the logger.
	 * @param outputFileName	- the filename to log to
	 * @param loggingOn			- logging on or of
	 */
	public Logger(String outputFileName, boolean loggingOn) {
		super();
		System.out.println("\n\nLOGGER INITIALIZED\nLogging On: " + loggingOn + "\n\n");
		this.outputFileName = outputFileName;
		this.loggingOn = loggingOn;
	}
	
	/**
	 * Log a given string with a given priority level and a given tag.
	 * @param logString		- the string to log
	 * @param priorityLevel	- the priority level of the log
	 * @param tag			- the tag of the log
	 */
	public void log(String logString, int priorityLevel, String tag) {
		String timeStamp = getCurrentTimeStamp();
		String newLogString = timeStamp + " - [" + tag + "|" + priorityLevel + "]: " + logString;
		System.out.println(newLogString);
	}
	
	private String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date now = new Date();
	    String strDate = sdf.format(now);
	    return strDate;
	}
	
	
	/**
	 * @return the outputFileName
	 */
	public String getOutputFileName() {
		return outputFileName;
	}
	/**
	 * @param outputFileName the outputFileName to set
	 */
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
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
	public String[] getTagFilters() {
		return tagFilters;
	}
	/**
	 * @param tagFilters the tagFilters to set
	 */
	public void setTagFilters(String[] tagFilters) {
		this.tagFilters = tagFilters;
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
	
	
	
	
}
