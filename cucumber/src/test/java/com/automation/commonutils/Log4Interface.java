package com.automation.commonutils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

public interface Log4Interface {
	
	public Logger log = LogManager.getRootLogger();
	
	/**
	 * @Description Initialize logger if class name also needed in logs, else use directly e.g log.info()
	 * @return Logger Object 
	 */
	
	public Logger getLogs();
	
}
