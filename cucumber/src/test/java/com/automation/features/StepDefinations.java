package com.automation.features;

import org.openqa.selenium.By;
import com.automation.functionLib.DataHandler;
import com.automation.functionLib.WebApp;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinations {
	
	private By searchBox = By.xpath("//*[@id='search']");
	
	@Given("^I navigated to \"([^\"]*)\"$")
	public void openURL(String URL) throws Throwable {
	  WebApp.open(URL);
	}

	@When("^I click on \"([^\"]*)\"$")
	public void clickSearchbox(String element) throws Throwable {
	  WebApp.verifyElementPresent(searchBox);
	}

	@Then("^I close my browser$")
	public void close() throws Throwable {
	    WebApp.endSession();
	}
	
	@And("^I enter the search text$")
	public void enterSearchText(DataTable table){
		WebApp.sendKeys(searchBox,DataHandler.getDataByIndex(table, 0,1));
	}
}
