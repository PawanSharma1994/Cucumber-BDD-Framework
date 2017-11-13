package com.automation.functionLib;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReport {

	private final static String extentReportFile = System.getProperty("user.dir")+"\\Reports\\extentReport.html";
	private final static String extentReportImage = System.getProperty("user.dir")+"\\Reports\\extentImage.html";
	private static ExtentReports extent;
	private static ExtentTest extentTest;
	
	public ExtentTest getExtentTest() {
		return extentTest;
	}

	static{
		extent = new ExtentReports(extentReportFile,false);
		extentTest = extent.startTest("###");
	}
	
	public static void generateReport(LogStatus status,String details){
		extentTest.log(status,details);
	}
	
	public static void finishReport(){
		extent.endTest(extentTest);
		extent.flush();
	}
}
