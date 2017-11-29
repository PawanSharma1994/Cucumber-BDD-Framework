package com.automation.functionLib;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.Scenario;

public final class WebApp extends ExtentReport {

	private static WebDriver driver = null;

	public static synchronized WebDriver getDriver() {
		return driver;
	}

	private WebApp() {
		System.out.println("------");
	}

	static {
		System.setProperty("webdriver.chrome.driver", "G:/chromedriver_win32/chromedriver.exe");
	}

	public static void open(String URL) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-popup-blocking");
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(URL);
		generateReport(LogStatus.INFO, "Opening Browser");
	}

	public static void elementClick(By webElement) {
		try {
			driver.findElement(webElement).click();
			generateReport(LogStatus.PASS, "Clicked on " + webElement.toString());
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, webElement.toString() + " Not found");
		} catch (ElementNotVisibleException e) {
			generateReport(LogStatus.FAIL, webElement.toString() + " Not Visible");
		}
	}

	public static void elementClick(By webElement, String text) {
		try {
			driver.findElement(webElement).click();
			generateReport(LogStatus.PASS, "Clicked on " + text);
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, webElement.toString() + " Not found");
		} catch (ElementNotVisibleException e) {
			generateReport(LogStatus.FAIL, webElement.toString() + " Not visible");
		}
	}

	public static void submit(By webElement) {
		try {
			driver.findElement(webElement).submit();
			generateReport(LogStatus.PASS, "Submit button is clicked");
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, webElement.toString() + " Not found");
		}
	}

	public static void sendKeys(By webElement, String text) {
		try {
			driver.findElement(webElement).sendKeys(text);
			generateReport(LogStatus.PASS, "Text is entered in " + webElement.toString());
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, webElement.toString() + " Not found");
		}
	}

	public static void verifyElementPresent(By webElement) {
		int width = driver.findElement(webElement).getSize().getWidth();
		if (width != 0) {
			generateReport(LogStatus.PASS, webElement.toString() + " is present");
			System.out.println("Element is present");
		} else {
			generateReport(LogStatus.FAIL, webElement.toString() + " not found");
			System.out.println("Element is not found");
		}

	}

	public static void verifyElementNotPresent(By webElement) {
		int width = driver.findElement(webElement).getSize().getWidth();
		if (width != 0) {
			generateReport(LogStatus.FAIL, webElement.toString() + " is present");
			System.out.println("Element is present");
		} else {
			generateReport(LogStatus.PASS, webElement.toString() + " not found");
			System.out.println("Element is not found");
		}
	}

	public static void clickByLinkText(String linkText) {
		driver.findElement(By.linkText(linkText)).click();
		generateReport(LogStatus.PASS, linkText + " is Clicked");
	}

	public static void endSession() {
		generateReport(LogStatus.INFO, "Browser is closed");
		driver.close();
		driver.quit();
		finishReport();
	}

	////////// Action Class Methods //////////////

	public static void moveToElement(By moveToElement, By clickElement) {
		WebElement moveToElementTemp = driver.findElement(moveToElement);
		WebElement clickElementTemp = driver.findElement(clickElement);
		new Actions(driver).moveToElement(moveToElementTemp).click(clickElementTemp).build().perform();
		generateReport(LogStatus.PASS, "Clicked on " + clickElement.toString());
	}

	public static void selectByVisibleText(By element, String text) {
		new Select(driver.findElement(element)).selectByVisibleText(text);
		generateReport(LogStatus.PASS, "Value " + text + " is selected");
	}

	public static void jseClick(By clickElement) {
		WebElement element = driver.findElement(clickElement);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

}
