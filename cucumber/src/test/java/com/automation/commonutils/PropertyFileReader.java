package com.automation.commonutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

	private static InputStream input = null;
	private static Properties prop = new Properties();
	public final static String propFilePath = System.getProperty("user.dir") + "\\Test.properties";

	public final static String getProperty(String key) throws IOException {
		String propValue = "";
		try {
			input = new FileInputStream(propFilePath);
			prop.load(input);
			propValue = prop.getProperty(key);
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found Excpetion");
		}
		return propValue;
	}
}
