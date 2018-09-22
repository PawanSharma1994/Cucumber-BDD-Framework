package com.automation.functionLib;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.commonutils.DataHandler;
import com.automation.commonutils.Log4Interface;
import com.automation.commonutils.PropertyFileReader;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * @description Class for all selenium web-driver methods with added reporting
 *              and logging features
 * @author pawan
 *
 */

public final class WebApp extends ExtentReport implements Log4Interface, SupportedBrowsers {

	private static WebDriver driver = null;
	private WebDriverWait wait;
	private String selectBrowser = "";
	private boolean headlessBrowserFlag = false;
	private String headlessBrowser = null;
	private static WebApp webapp;
	private Logger logger = LogManager.getLogger(getClass());

	private WebApp() {
		log.info("Private Constructor!!");
	}

	/**
	 * @author pawan
	 * @description get the instance of web-driver
	 */

	public static WebDriver getDriver() {
		if (driver == null) {
			throw new WebDriverException("Webdriver is not initialized!! Check your property file");
		}
		return driver;
	}

	/**
	 * @author pawan
	 * @description to get the instance of WebApp class which contains
	 *              customized web-driver functions
	 */

	public static synchronized WebApp get() {
		if (webapp == null) {
			webapp = new WebApp();
		}
		return webapp;
	}

	/**
	 * @author pawan
	 * @description to get the instance of WebdriverWait
	 */

	public synchronized WebDriverWait getWait() {
		return wait;
	}

	@Override
	public Logger getLogs() {
		return logger;
	}

	/**
	 * @author pawan
	 * @param URL
	 * @throws IOException
	 * @description to Launch the URL in browser
	 */

	public void launchBrowser(String URL) throws IOException {
		selectBrowser = PropertyFileReader.getProperty("TEST_BROWSER").trim();
		headlessBrowser = PropertyFileReader.getProperty("HEADLESS_MODE").trim();
		log.info("Browser Selected is " + selectBrowser);
		if (headlessBrowser.equalsIgnoreCase("Yes")) {
			headlessBrowserFlag = true;
			log.info("running in headless mode");
		}
		if (selectBrowser.equalsIgnoreCase(SupportedBrowsers.CHROME)) {
			launchChrome();
		} else if (selectBrowser.equalsIgnoreCase(SupportedBrowsers.FIREFOX)) {
			launchFireFox();
		} else if (selectBrowser.equalsIgnoreCase(SupportedBrowsers.MSEdge)) {
			launchEdge();
		}
		driver.manage().deleteAllCookies();
		log.info("--deleting all cookies--");
		driver.manage().window().maximize();
		log.info("--maximizing window--");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 15);
		driver.get(URL);
		log.info("Launching URL: " + URL + " in: " + selectBrowser);
		generateReport(LogStatus.PASS, "Launching URL: " + URL + " in: " + selectBrowser);
	}

	private void launchChrome() throws IOException {
		System.setProperty("webdriver.chrome.driver", PropertyFileReader.getProperty("CHROME_DRIVER_PATH").trim());
		ChromeOptions options = new ChromeOptions();
		options.setBinary(PropertyFileReader.getProperty("CHROME_BINARY_PATH")).toString().trim();
		options.addArguments("--disable-extensions");
		log.info("--disable extentions--");
		options.addArguments("--disable-popup-blocking");
		log.info("--disable pop-up blocking--");
		options.setHeadless(headlessBrowserFlag);
		chromeLogging(true);
		driver = new ChromeDriver(options);
	}

	private void launchFireFox() throws IOException {
		System.setProperty("webdriver.gecko.driver", PropertyFileReader.getProperty("FIREFOX_DRIVER_PATH").trim());
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary(PropertyFileReader.getProperty("FIREFOX_BINARY_PATH")).toString().trim();
		options.setHeadless(headlessBrowserFlag);
		driver = new FirefoxDriver(options);

	}

	private void launchEdge() throws IOException {
		System.setProperty("webdriver.edge.driver", PropertyFileReader.getProperty("EDGE_DRIVER_PATH").trim());
		EdgeOptions options = new EdgeOptions();
		driver = new EdgeDriver(options);
	}

	/**
	 * @author pawan
	 * @param clickElement
	 * @description to click on Web-element
	 */

	public void elementClick(By clickElement) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(clickElement));
			driver.findElement(clickElement).click();
			generateReport(LogStatus.PASS, "Clicked on " + clickElement.toString());
			log.info("Clicked on the Element: " + clickElement);
		} catch (StaleElementReferenceException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not found");
			log.error("StaleElementReferenceException");
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not found");
			log.error("NoSuchElementException");
		} catch (ElementNotVisibleException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not Visible");
			log.error("ElementNotVisibleException");
		} catch (TimeoutException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not found");
			log.error("TimeoutException");
		}
	}

	public void elementClick(By clickElement, String text) {
		try {
			driver.findElement(clickElement).click();
			generateReport(LogStatus.PASS, "Clicked on " + text);
			Log4Interface.log.info("Clicked on the Element: " + text);
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
			log.info("Clicked on Submit button");
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, element.toString() + " Not found");
			log.error("NoSuchElementException");
		}
	}

	public void sendKeys(By element, String text) {
		try {
			driver.findElement(element).sendKeys(text);
			generateReport(LogStatus.PASS, "Text is entered in " + element.toString());
			log.info("Entered the text: " + text);
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, element.toString() + " Not found");
		}
	}

	public void verifyElementPresent(By element) {
		int width = 0;
		try {
			width = driver.findElement(element).getSize().getWidth();
		} catch (NoSuchElementException e) {
			System.out.println(width);
		}
		if (width != 0) {
			generateReport(LogStatus.PASS, element.toString() + " is present");
			log.info(element.toString() + " is present");
		} else {
			generateReport(LogStatus.FAIL, element.toString() + " not found");
			log.info("Element is not found");
		}

	}

	public void verifyElementNotPresent(By element) throws NoSuchElementException {
		int width = driver.findElement(element).getSize().getWidth();
		if (width != 0) {
			generateReport(LogStatus.FAIL, element.toString() + " is present");
			log.info("Element is present");
		} else {
			generateReport(LogStatus.PASS, element.toString() + " not found");
			log.info("Element is not found");
		}
	}

	public void clickByLinkText(String linkText) throws NoSuchElementException, StaleElementReferenceException {
		driver.findElement(By.linkText(linkText)).click();
		generateReport(LogStatus.PASS, linkText + " is Clicked");
		log.info("Clicked on: " + linkText);
	}

	public void endSession() {
		generateReport(LogStatus.INFO, "Browser is closed");
		log.info("--Closing Sesssion--");
		driver.quit();
		log.info("--generating report--");
		finishReport();
	}

	public void navigateForward() {
		driver.navigate().forward();
		generateReport(LogStatus.INFO, "Navigated forward");
		log.info("Navigating forward");
	}

	public void navigateBack() {
		driver.navigate().back();
		generateReport(LogStatus.INFO, "Navigated to last page");
		log.info("Navigating backwards");

	}

	public void refreshPage() {
		driver.navigate().refresh();
		generateReport(LogStatus.INFO, "Page is refreshed");
		log.info("Refreshing page");
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

}
