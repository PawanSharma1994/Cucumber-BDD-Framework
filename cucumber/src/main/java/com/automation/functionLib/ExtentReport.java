package com.automation.functionLib;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.automation.commonutils.Log4Interface;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.Scenario;

public class ExtentReport implements Log4Interface{

	private static final String EXTENTREPORTPATH = System.getProperty("user.dir") + "\\Reports\\";
	private static String reportName;
	private static ExtentReports extent;
	private static ExtentTest extentTest;

	static {
		reportName = EXTENTREPORTPATH + timeStamp();
		log.info("Report name " + reportName);
		extent = new ExtentReports(reportName + "\\extentReport.html", true);
	}

	public static void getScenarioName(Scenario scenario) {
		log.info(scenario.getName());
		extentTest = extent.startTest(scenario.getName());
	}

	public static void generateReport(LogStatus status, String details) {
		try {
			File src = ((TakesScreenshot) WebApp.getDriver()).getScreenshotAs(OutputType.FILE);
			File dest = new File(reportName + "\\extentImage_" + timeStamp() + ".png");
			FileUtils.copyFile(src, dest);
			extentTest.log(status, details, extentTest.addBase64ScreenShot(dest.toString()));
		} catch (IOException e) {
			log.info("IOException found!!");
		}
	}

	public static void finishReport() {
		extent.endTest(extentTest);
		extent.flush();
	}

	private static String timeStamp() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		Date dateobj = new Date();
		return df.format(dateobj);
	}

	@Override
	public Logger getLogs() {
		return null;
	}

}
