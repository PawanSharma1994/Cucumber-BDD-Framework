package com.automation.StepDefinitions;

import org.openqa.selenium.By;
import com.automation.commonutils.DataHandler;
import com.automation.functionLib.WebApp;
import com.codoid.products.exception.FilloException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class Sapphire_StepDefs {

	/////////////////////////////////////////////////////////////////////////////////////////

	@Before(order = 0)
	public void setUp(Scenario s) {
		WebApp.getScenarioName(s);
		DataHandler.getScenarioName(s);
	}

	@After
	public void endSession() {
		WebApp.get().endSession();
	}

	//////////////////////////////////////////////////////////////////////////////////////////

	private By searchBox = By.name("search_query");
	private By loginLink = By.xpath("//*[@class='login']");
	private By userName = By.name("email");
	private By password = By.name("pass");
	private By searchIcon = By.id("search-icon-legacy");
	private By ratingHover = By.xpath("//*[starts-with(@class,'wh-rating rating_')]");
	private By starClick = By.xpath("//*[@class='wh-rating-choices-holder']/*[5]");
	private By reviewDropdown = By.xpath("//*[@class='dropdown-title']");
	private By reviewTextBox = By.name("review");
	private By submitBtn = By.xpath("//*[@type='submit']");
	private By health = By.linkText("Health");
	private By overallRating = By.xpath("//*[@id='overallRating']/*[5]");
	private By loginBtn = By.id("loginbutton");
	private By captchaEnter = By.xpath("//*[@class='rc-anchor-logo-portrait']");
	private By statusBox = By.name("xhpc_message");
	
	
	@Given("^I navigated to \"([^\"]*)\"$")
	public void openURL(String URL) throws Throwable {
		WebApp.get().launchBrowser(URL);
	}

	@And("^I log into my Account$")
	public void login(DataTable table) throws InterruptedException {
		WebApp.get().sendKeys(userName, DataHandler.getDataFromCucumberTable(table,""));
		WebApp.get().sendKeys(password, DataHandler.getDataFromCucumberTable(table,""));
		WebApp.get().elementClick(loginBtn);
	}

	@And("^I post my status$")
	public void enterStatus() {
		WebApp.get().elementClick(statusBox);
		WebApp.get().sendKeys(statusBox, "Hello World");
	}

	@When("^I click on element$")
	public void clickSearchbox() throws Throwable {
		WebApp.get().elementClick(searchBox);
	}

	@Then("^I verify the element$")
	public void verifyElement() {
		WebApp.get().verifyElementPresent(searchBox);
	}

	@And("^I enter the text$")
	public void enterSearchText(DataTable table) {
		WebApp.get().sendKeys(searchBox, DataHandler.getDataFromCucumberTable(table,"SearchText"));
		// id not working for edge browser use name or other attrb
	}

	@And("^I enter the text as \"([^\"]*)\"$")
	public void enterText(String searchText) {
		WebApp.get().sendKeys(searchBox, searchText);
	}

	@And("^I fetch the text$")
	public void enterText() throws FilloException {
		// WebApp.get().sendKeys(searchBox, DataHandler.getXLData("DataSheet1",
		// "SearchText"));
		WebApp.get().sendKeys(searchBox, DataHandler.getDataFromXL("DataSheet1", "SearchText"));
	}

	@And("^I clicked on searchicon$")
	public void clickSearchIcon() {
		WebApp.get().elementClick(searchIcon);
	}

	@When("^I hover over rating and select 5 stars$")
	public void clickRating() throws InterruptedException {
		WebApp.get().moveToElement(ratingHover, starClick);
	}

	@Then("^I navigated to Reviews Page$")
	public void verifyReviewsPageTitle() {
		WebApp.get().verifyElementPresent(reviewDropdown);
	}

	@And("^I selected \"([^\"]*)\"$")
	public void selectPolicyDropdown(String text) throws InterruptedException {
		WebApp.get().elementClick(reviewDropdown);
		WebApp.get().elementClick(health, text);
		WebApp.get().elementClick(overallRating);
	}

	@And("^I Entered Review in the review text box$")
	public void addReview() {
		for (int i = 0; i < 52; i++) {
			WebApp.getDriver().findElement(reviewTextBox).sendKeys("and ");
		}
	}

	@And("^submitted the review$")
	public void submitReview() {
		WebApp.get().submit(submitBtn);
	}

}
