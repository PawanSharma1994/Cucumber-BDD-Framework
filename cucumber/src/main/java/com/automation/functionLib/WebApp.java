package com.automation.functionLib;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
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
import com.automation.commonutils.Log4Interface;
import com.automation.commonutils.PDFReader;
import com.automation.commonutils.PropertyFileReader;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class for all selenium web-driver methods with added reporting and logging
 * features
 * 
 * @author pawan
 *
 */

public final class WebApp extends ExtentReport implements Log4Interface, SupportedBrowsers {

	private static WebDriver driver = null;
	private WebDriverWait wait;
	private String selectBrowser = null;
	private boolean headlessBrowserFlag = false;
	private String headlessBrowser = null;
	private static WebApp webapp;
	private Logger logger = LogManager.getLogger(getClass());

	private WebApp() {
		if (webapp != null) {
			throw new RuntimeException("use get() method");
		}
	}

	/**
	 * get the instance of web-driver
	 * 
	 * @author pawan
	 */

	public static WebDriver getDriver() {
		if (driver == null) {
			throw new WebDriverException("Webdriver is not initialized!! Check your property file");
		}
		return driver;
	}

	/**
	 * to get the instance of WebApp class which contains customized web-driver
	 * functions
	 * 
	 * @author pawan
	 */

	public static synchronized WebApp get() {
		if (webapp == null) {
			webapp = new WebApp();
		}
		return webapp;
	}

	/**
	 * to get the instance of WebdriverWait
	 * 
	 * @author pawan
	 */

	public synchronized WebDriverWait getWait() {
		return wait;
	}

	@Override
	public Logger getLogs() {
		return logger;
	}

	/**
	 * to Launch the URL in the specific browser
	 * 
	 * @author pawan
	 * @param URL
	 * @throws IOException
	 */

	public void launchBrowser(String url) throws IOException {
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
		driver.get(url);
		log.info("Launching URL: " + url + " in: " + selectBrowser);
		generateReport(LogStatus.PASS, "Launching URL: " + url + " in: " + selectBrowser);
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
		driver = new ChromeDriver(options);
	}

	private void launchFireFox() throws IOException {
		System.setProperty("webdriver.gecko.driver", PropertyFileReader.getProperty("FIREFOX_DRIVER_PATH").trim());
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary(PropertyFileReader.getProperty("FIREFOX_BINARY_PATH")).toString();
		options.setHeadless(headlessBrowserFlag);
		driver = new FirefoxDriver(options);

	}

	private void launchEdge() throws IOException {
		System.setProperty("webdriver.edge.driver", PropertyFileReader.getProperty("EDGE_DRIVER_PATH").trim());
		EdgeOptions options = new EdgeOptions();
		driver = new EdgeDriver(options);
	}

	/**
	 * waits until the element becomes click-able
	 * 
	 * @author Pawan
	 * @param clickElement
	 */

	public void waitClickElement(By clickElement) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(clickElement));
			driver.findElement(clickElement).click();
			generateReport(LogStatus.PASS, "Clicked on " + clickElement.toString());
			log.info("Clicked on the Element: " + clickElement);
		} catch (TimeoutException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not found");
			log.error("TimeoutException");
		}
	}

	/**
	 * waits until the element becomes click-able
	 * 
	 * @author pawan
	 * @param clickElement
	 */

	public void waitClickElement(WebElement clickElement) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(clickElement));
			clickElement.click();
			generateReport(LogStatus.PASS, "Clicked on " + clickElement.toString());
			log.info("Clicked on the Element: " + clickElement);
		} catch (TimeoutException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not found");
			log.error("TimeoutException");
		}
	}

	/**
	 * to click on element
	 * 
	 * @author pawan
	 * @param clickElement
	 */

	public void elementClick(By clickElement) {
		try {
			driver.findElement(clickElement).click();
			generateReport(LogStatus.PASS, "Clicked on " + clickElement.toString());
		} catch (StaleElementReferenceException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not found");
			log.error("StaleElementReferenceException");
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not found");
			log.error("NoSuchElementException");
		} catch (ElementNotVisibleException e) {
			generateReport(LogStatus.FAIL, clickElement.toString() + " Not Visible");
			log.error("ElementNotVisibleException");
		}
	}

	/**
	 * To click on Element and print the custom name in report
	 * 
	 * @param clickElement
	 * @param text
	 */

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

	/**
	 * To Submit the form (element should be present in between 'form' tags
	 * 
	 * @author pawan
	 * @param element
	 */

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

	/**
	 * To Submit the form (element should be present in between 'form' tags
	 * 
	 * @author pawan
	 * @param element
	 */

	public void submit(WebElement element) {
		try {
			element.submit();
			generateReport(LogStatus.PASS, "Submit button is clicked");
			log.info("Clicked on Submit button");
		} catch (NoSuchElementException e) {
			generateReport(LogStatus.FAIL, element.toString() + " Not found");
			log.error("NoSuchElementException");
		}
	}

	/**
	 * To enter the text in the text-box
	 * 
	 * @author pawan
	 * @param element
	 * @param text
	 */

	public void sendKeys(By element, String text) {
		try {
			driver.findElement(element).sendKeys(text);
			generateReport(LogStatus.PASS, "Text is entered in " + element.toString());
			log.info("Entered the text: " + text);
		} catch (NoSuchElementException e) {
			log.error("NoSuchElementException");
			generateReport(LogStatus.FAIL, element.toString() + " Not found");
		}
	}

	/**
	 * To enter the text in the text-box
	 * 
	 * @author pawan
	 * @param element
	 * @param text
	 */

	public void sendKeys(WebElement element, String text) {
		try {
			element.sendKeys(text);
			generateReport(LogStatus.PASS, "Text is entered in " + element.toString());
			log.info("Entered the text: " + text);
		} catch (NoSuchElementException e) {
			log.error("NoSuchElementException");
			generateReport(LogStatus.FAIL, element.toString() + " Not found");
		} catch (IllegalArgumentException e) {
			log.error("IllegalArgumentException");
		}
	}

	/**
	 * To verify if element is present on the page
	 * 
	 * @author pawan
	 * @param element
	 */

	public void verifyElementPresent(By element) {
		int width = 0;
		try {
			width = driver.findElement(element).getSize().getWidth();
		} catch (NoSuchElementException e) {
			log.info("Element not found!!");
		}
		if (width != 0) {
			generateReport(LogStatus.PASS, element.toString() + " is present");
			log.info(element.toString() + " is present");
		} else {
			generateReport(LogStatus.FAIL, element.toString() + " not found");
			log.info("Element is not found");
		}

	}

	/**
	 * To verify if element is present on the page
	 * 
	 * @author pawan
	 * @param element
	 */

	public void verifyElementNotPresent(By element) {
		int width = driver.findElement(element).getSize().getWidth();
		if (width != 0) {
			generateReport(LogStatus.FAIL, element.toString() + " is present");
			log.info("Element is present");
		} else {
			generateReport(LogStatus.PASS, element.toString() + " not found");
			log.info("Element is not found");
		}
	}

	/**
	 * To click the link by using link-text property
	 * 
	 * @author pawan
	 * @param linkText
	 * @throws NoSuchElementException
	 * @throws StaleElementReferenceException
	 */

	public void clickByLinkText(String linkText) {
		driver.findElement(By.linkText(linkText)).click();
		generateReport(LogStatus.PASS, linkText + " is Clicked");
		log.info("Clicked on: " + linkText);
	}

	/**
	 * To end the Webdriver Session
	 * 
	 * @author pawan
	 */

	public void endSession() {
		generateReport(LogStatus.INFO, "Browser is closed");
		log.info("--Closing Sesssion--");
		driver.quit();
		log.info("--generating report--");
		finishReport();
	}

	/**
	 * To navigate to the next page if it opened earlier
	 */

	public void navigateForward() {
		driver.navigate().forward();
		generateReport(LogStatus.INFO, "Navigated forward");
		log.info("Navigating forward");
	}

	/**
	 * To move backward to previous page
	 */

	public void navigateBack() {
		driver.navigate().back();
		generateReport(LogStatus.INFO, "Navigated to last page");
		log.info("Navigating backwards");

	}

	/**
	 * To refresh the current page
	 */

	public void refreshPage() {
		driver.navigate().refresh();
		generateReport(LogStatus.INFO, "Page is refreshed");
		log.info("Refreshing page");
	}

	/**
	 * Get URL of current opened tab
	 * 
	 * @author pawan
	 * @return
	 */

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	/**
	 * close the current opened window
	 * 
	 * @author pawan
	 */

	public void closeWindow() {
		driver.close();
		generateReport(LogStatus.INFO, "Closing the current window!");
		log.info("Closing the current window");
	}

	/**
	 * Switch to the newly opened window
	 * 
	 * @author pawan
	 */

	public void switchToNewWindow() {
		Set<String> windowHandle = driver.getWindowHandles();
		try {
			for (String handles : windowHandle) {
				log.info("window handles are :\n" + handles);
				driver.switchTo().window(handles);
			}
		} catch (NoSuchWindowException e) {
			generateReport(LogStatus.FAIL, "Switiching Window not found");
			log.error("NoSuchWindowException");
		}
	}

	/**
	 * Move mouse on element and click on it
	 * 
	 * @author pawan
	 * @param moveToElement
	 * @param clickElement
	 */

	public void moveToElement(By moveToElement, By clickElement) {
		WebElement moveToElementTemp = driver.findElement(moveToElement);
		WebElement clickElementTemp = driver.findElement(clickElement);
		new Actions(driver).moveToElement(moveToElementTemp).click(clickElementTemp).build().perform();
		generateReport(LogStatus.PASS, "Clicked on " + clickElement.toString());
	}

	/**
	 * Select from Drop-down : Visible text
	 * 
	 * @author pawan
	 * @param element
	 * @param text
	 */

	public void selectByVisibleText(By element, String text) {
		new Select(driver.findElement(element)).selectByVisibleText(text);
		generateReport(LogStatus.PASS, "Value " + text + " is selected");
	}

	/**
	 * Select from drop-down : index
	 * 
	 * @author pawan
	 * @param element
	 * @param index
	 */

	public void selectByIndex(By element, int index) {
		new Select(driver.findElement(element)).selectByIndex(index);
		generateReport(LogStatus.PASS, "Index " + index + " is Selected");
	}

	/**
	 * To Click on Element using javaScriptExecutor
	 * 
	 * @author pawan
	 * @param clickElement
	 * @throws NoSuchElementException
	 */

	public void jseClick(By clickElement) {
		WebElement element = driver.findElement(clickElement);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void alertHandle(String action, String text) {
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

	/**
	 * Get the text from pdf if it is opened in the current tab
	 * 
	 * @author pawan
	 * @return
	 * @throws IOException
	 */

	public String getTextFromPDFCurrentURL() throws IOException {
		return PDFReader.getPDFReader().getTextFromPDF(getCurrentURL());
	}

	/**
	 * Verify text if it is present in the pdf file
	 * 
	 * @author pawan
	 * @param matchers
	 * @throws IOException
	 */

	public void verifyPDFContainsText(String matchers) throws IOException {
		String pdftext = PDFReader.getPDFReader().getTextFromPDF(getCurrentURL());
		if (pdftext.contains(matchers)) {
			generateReport(LogStatus.PASS, "Text found in pdf file!!");
		} else {
			generateReport(LogStatus.FAIL, "Text not found in pdf file!!");
		}
	}

}
