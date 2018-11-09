package com.automation.functionLib;

/**
 * 
 * @author pawan
 * @description All browsers supported by this framework (use Chrome , Firefox
 *              or Edge in properties file)
 *
 */
public interface SupportedBrowsers {

	String CHROME = "Chrome";
	String FIREFOX = "Firefox";
	String MSEdge = "MSEdge";
	String GRID_CHROME = "Grid_Chrome";
	String GRID_FIREFOX = "Grid_Firefox";

	/**
	 * @author pawan
	 * @param enableVerbose
	 * @description method to enable chrome logs
	 */

	public default void chromeLogging(boolean enableVerbose) {
		System.setProperty("webdriver.chrome.logfile", System.getProperty("user.dir") + "\\chrome.log");
		if (enableVerbose)
			System.setProperty("webdriver.chrome.verboseLogging", "true");
	}

}
