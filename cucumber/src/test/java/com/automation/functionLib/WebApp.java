package com.automation.functionLib;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.commonutils.PropertyFileReader;
import com.relevantcodes.extentreports.LogStatus;

public final class WebApp extends ExtentReport {

	private static WebDriver driver = null;
	private WebDriverWait wait;
	private String selectBrowser = "";
	private boolean headlessBrowserFlag = false;
	private String headlessBrowser = null;
	private static WebApp webapp;

	private WebApp() {
		System.out.println("Private Constructor!!");
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static synchronized WebApp get() {
		if (webapp == null) {
			webapp = new WebApp();
		}
		return webapp;
	}

	public synchronized WebDriverWait getWait() {
		return wait;
	}

	static {
		System.setProperty("webdriver.chrome.driver", "G:/SeleniumDrivers/chromedriver_win32/chromedriver.exe");
		System.setProperty("webdriver.edge.driver",
				"G:/SeleniumDrivers/MicrosoftWebDriver_win32/MicrosoftWebDriver.exe");
		System.setProperty("webdriver.gecko.driver", "G:/SeleniumDrivers/geckodriver-v0.19.1-win64/geckodriver.exe");
	}

	public void open(String URL) throws Exception {
		selectBrowser = PropertyFileReader.getProperty("Browser").trim();
		headlessBrowser = PropertyFileReader.getProperty("HeadlessBrowser").trim();

		System.out.println("Browser Selected is " + selectBrowser);
		if (headlessBrowser == null) {
			throw new Exception("headlessBrowser value is not Set!!");
		}
		if (headlessBrowser.equalsIgnoreCase("Yes")) {
			headlessBrowserFlag = true;
		}
		if (selectBrowser.equalsIgnoreCase("Chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-popup-blocking");
			options.setHeadless(headlessBrowserFlag);
			driver = new ChromeDriver(options);
		} else if (selectBrowser.equalsIgnoreCase("Firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(headlessBrowserFlag);
			driver = new FirefoxDriver(options);
		} else if (selectBrowser.equalsIgnoreCase("MSEdge")) {
			EdgeOptions options = new EdgeOptions();
			driver = new EdgeDriver(options);
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 15);
		driver.get(URL);
		generateReport(LogStatus.PASS, "Opening Browser: " + selectBrowser);
	}

	public void elementClick(By clickElement) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(clickElement));
			driver.findElement(clickElement).click();
			generateReport(LogStatus.PASS, "Clicked on " + clickElement.toString());
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not found");
		} catch (ElementNotVisibleException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not Visible");
		}
	}

	public void elementClick(By clickElement, String text) {
		try {
			driver.findElement(clickElement).click();
			generateReport(LogStatus.PASS, "Clicked on " + text);
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not found");
		} catch (ElementNotVisibleException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not visible");
		}
	}

	public void submit(By element) {
		try {
			driver.findElement(element).submit();
			generateReport(LogStatus.PASS, "Submit button is clicked");
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, element.toString() + " Not found");
		}
	}

	public void sendKeys(By element, String text) {
		try {
			driver.findElement(element).sendKeys(text);
			generateReport(LogStatus.PASS, "Text is entered in " + element.toString());
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, element.toString() + " Not found");
		}
	}

	public void verifyElementPresent(By element) throws NoSuchElementException {
		int width = driver.findElement(element).getSize().getWidth();
		if (width != 0) {
			generateReport(LogStatus.PASS, element.toString() + " is present");
			System.out.println("Element is present");
		} else {
			generateReport(LogStatus.FAIL, element.toString() + " not found");
			System.out.println("Element is not found");
		}

	}

	public void verifyElementNotPresent(By element) throws NoSuchElementException {
		int width = driver.findElement(element).getSize().getWidth();
		if (width != 0) {
			generateReport(LogStatus.FAIL, element.toString() + " is present");
			System.out.println("Element is present");
		} else {
			generateReport(LogStatus.PASS, element.toString() + " not found");
			System.out.println("Element is not found");
		}
	}

	public void clickByLinkText(String linkText) throws NoSuchElementException, StaleElementReferenceException {
		driver.findElement(By.linkText(linkText)).click();
		generateReport(LogStatus.PASS, linkText + " is Clicked");
	}

	public void endSession() {
		generateReport(LogStatus.INFO, "Browser is closed");
		driver.quit();
		finishReport();
	}

	public void navigateForward() {
		driver.navigate().forward();
		generateReport(LogStatus.INFO, "Navigated forward");
	}

	public void navigateBack() {
		driver.navigate().back();
		generateReport(LogStatus.INFO, "Navigated to last page");
	}

	public void refreshPage() {
		driver.navigate().refresh();
		generateReport(LogStatus.INFO, "Page is refreshed");
	}

	public void moveToElement(By moveToElement, By clickElement) {
		WebElement moveToElementTemp = driver.findElement(moveToElement);
		WebElement clickElementTemp = driver.findElement(clickElement);
		new Actions(driver).moveToElement(moveToElementTemp).click(clickElementTemp).build().perform();
		generateReport(LogStatus.PASS, "Clicked on " + clickElement.toString());
	}

	public void selectByVisibleText(By element, String text) {
		new Select(driver.findElement(element)).selectByVisibleText(text);
		generateReport(LogStatus.PASS, "Value " + text + " is selected");
	}

	public void selectByIndex(By element, int index) {
		new Select(driver.findElement(element)).selectByIndex(index);
		generateReport(LogStatus.PASS, "Index " + index + " is Selected");
	}

	public void jseClick(By clickElement) throws NoSuchElementException {
		WebElement element = driver.findElement(clickElement);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void alertHandle(String action, String text) throws NoAlertPresentException {
		try {
			Alert alert = driver.switchTo().alert();

			if (action.equalsIgnoreCase("Accept")) {
				alert.accept();
				generateReport(LogStatus.PASS, "Accept Alert");
			} else if (action.equalsIgnoreCase("Dismiss")) {
				alert.dismiss();
				generateReport(LogStatus.PASS, "Dismiss Alert");
			} else if (action.equalsIgnoreCase("SendKeys")) {
				alert.sendKeys(text);
				generateReport(LogStatus.PASS, "Entered the text successfully");
			}
		} catch (NoAlertPresentException e) {
			generateReport(LogStatus.FAIL, "Alert Window is not present");
		}
	}

	public void nonstatic() {

	}

}
