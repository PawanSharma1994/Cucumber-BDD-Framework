package com.automation.functionLib;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.Scenario;

abstract public class ExtentReport {

	private final static String extentReportFile = System.getProperty("user.dir") + "\\Reports\\";
	private final static String extentReportImage = System.getProperty("user.dir") + "\\Reports\\extentImage";
	private static ExtentReports extent;
	private static ExtentTest extentTest;

	static {
		String reportName = extentReportFile + timeStamp() + "\\extentReport.html";
		System.out.println("Report name "+reportName);
		extent = new ExtentReports(reportName, true);
	}

	public static void getScenarioName(Scenario scenario) {
		System.out.println(scenario.getName());
		extentTest = extent.startTest(scenario.getName());
	}

	public static void generateReport(LogStatus status, String details) {
		try {
			File src = ((TakesScreenshot) WebApp.getDriver()).getScreenshotAs(OutputType.FILE);
			File dest = new File(extentReportImage + timeStamp() + ".png");
			FileUtils.copyFile(src, dest);
			extentTest.log(status, details, extentTest.addBase64ScreenShot(dest.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void finishReport() {
		extent.endTest(extentTest);
		extent.flush();
	}

	private static String timeStamp() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		Date dateobj = new Date();
		return df.format(dateobj);
		//return new SimpleDateFormat("yyyy-mm-dd hh-mm-ss").format(new Date());
	}

}
