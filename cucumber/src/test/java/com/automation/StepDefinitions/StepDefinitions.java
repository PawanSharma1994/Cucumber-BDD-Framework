package com.automation.StepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.automation.functionLib.DataHandler;
import com.automation.functionLib.ExtentReport;
import com.automation.functionLib.WebApp;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	
	private By searchBox = By.id("search");
	private By loginLink = By.xpath("//*[@class='login']");
	private By userName = By.name("email");
	private By password = By.name("pass");
	private By searchIcon = By.id("search-icon-legacy");
	private By ratingHover = By.xpath("//*[starts-with(@class,'wh-rating rating_')]"); //*[starts-with(@class,'wh-rating rating_')]
	private By starClick = By.xpath("//*[@class='wh-rating-choices-holder']/*[5]");
	private By reviewDropdown = By.xpath("//*[@class='dropdown-title']");
	private By reviewTextBox = By.name("review");
	private By submitBtn 	 = By.xpath("//*[@type='submit']");
	private By health		= By.linkText("Health");
	private By overallRating = By.xpath("//*[@id='overallRating']/*[5]");
	private By loginBtn 	 = By.id("loginbutton");
	private By captchaEnter  = By.xpath("//*[@class='rc-anchor-logo-portrait']");
	private By statusBox     = By.name("xhpc_message");
/////////////////////////////////////////////////////////////////////////////////////////	
	@Before
	public void setUp(Scenario s){
		WebApp.getScenarioName(s);
	}
	
	@After
	public void endSession(){
		WebApp.endSession();
	}
//////////////////////////////////////////////////////////////////////////////////////////
	
	@Given("^I navigated to \"([^\"]*)\"$")
	public void openURL(String URL) throws Throwable {
	  WebApp.open(URL);
	}

	@And("^I log into my Account$")
	public void login(DataTable table) throws InterruptedException{
		WebApp.sendKeys(userName,DataHandler.getDataByIndex(table,1,0));
		WebApp.sendKeys(password,DataHandler.getDataByIndex(table,1,1));
		WebApp.elementClick(loginBtn);
		/// Handling captcha manually
		Thread.sleep(10000);
	}
	
	@And("^I post my status$")
	public void enterStatus(){
		WebApp.elementClick(statusBox);
		WebApp.sendKeys(statusBox,"Hello World");
	}
	
	@When("^I click on element$")
	public void clickSearchbox() throws Throwable {
	  WebApp.elementClick(searchBox);
	}

	@Then("^I verify the element$")
	public void verifyElement(){
		WebApp.verifyElementPresent(searchBox);
	}
	
	@And("^I enter the text$")
	public void enterSearchText(DataTable table){
		WebApp.sendKeys(searchBox,DataHandler.getDataByIndex(table, 0,1));
	}
	
	@And("^I clicked on searchicon$")
	public void clickSearchIcon(){
		WebApp.elementClick(searchIcon);
	}
	
	@When("^I hover over rating and select 5 stars$")
	public void clickRating() throws InterruptedException{
		WebApp.moveToElement(ratingHover, starClick);
	}
	
	@Then("^I navigated to Reviews Page$")
	public void verifyReviewsPageTitle(){
		WebApp.verifyElementPresent(reviewDropdown);
	}
	
	@And("^I selected \"([^\"]*)\"$")
	public void selectPolicyDropdown(String text) throws InterruptedException{
	
		WebApp.elementClick(reviewDropdown);
		WebApp.elementClick(health,text);
		Thread.sleep(5000);
		WebApp.elementClick(overallRating);
	}
	
	@And("^I Entered Review in the review text box$")
	public void addReview(){
		for (int i=0;i<52;i++){
		WebApp.getDriver().findElement(reviewTextBox).sendKeys("and ");
		}
	}
	
	@And("^submitted the review$")
	public void submitReview(){
		WebApp.submit(submitBtn);
	}
	
}
