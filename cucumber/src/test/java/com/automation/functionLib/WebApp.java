package com.automation.functionLib;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.relevantcodes.extentreports.LogStatus;

public class WebApp extends ExtentReport{

	private static WebDriver driver = null;
	
	public static WebDriver getDriver() {
	return driver;
	}

	static {
		System.setProperty("webdriver.chrome.driver","G:/chromedriver_win32/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-popup-blocking");
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
	}
	
	public static void open(String URL){
		System.out.println("------Opening------");
		driver.get(URL);
		generateReport(LogStatus.INFO,"Opening Browser");
	}
	
	public static void elementClick(By webElement){
		driver.findElement(webElement).click();
		generateReport(LogStatus.PASS,"Element is Clicked");
	}
	
	public static void sendKeys(By webElement,String text){
		driver.findElement(webElement).sendKeys(text);
		generateReport(LogStatus.PASS,"Text is entered");
	}
	
	public static void verifyElementPresent(By webElement){
		int width = driver.findElement(webElement).getSize().getWidth();
		if(width!=0){
			System.out.println("Element is present");
		}else{
			System.out.println("Element is not found");
		}
		
	}
	
	public static void endSession(){
		driver.close();
		driver.quit();
		generateReport(LogStatus.INFO,"Browser is closed");
		finishReport();
	}
}
