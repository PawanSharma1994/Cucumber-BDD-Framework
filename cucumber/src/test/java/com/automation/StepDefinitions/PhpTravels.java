package com.automation.StepDefinitions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.commonutils.DataHandler;
import com.automation.functionLib.WebApp;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class PhpTravels {

	@FindBy(partialLinkText = "http://www.phptravels.net")
	private WebElement webLink;

	@FindBy(xpath = "(//*[@id='li_myaccount'])[2]")
	private WebElement myAccountLink;

	@FindBy(linkText = "Login")
	private WebElement loginLink;

	@FindBy(name = "username")
	private WebElement username;

	@FindBy(name = "password")
	private WebElement password;

	@FindBy(xpath = "(//*[@type='submit'])[1]")
	private WebElement loginBtn;

	public PhpTravels() {
		PageFactory.initElements(WebApp.getDriver(), this);
	}

	@When("^I click on Login icon$")
	public void click_login_icon() throws InterruptedException {
		WebApp.get().wait_ClickElement(myAccountLink);
		WebApp.get().wait_ClickElement(loginLink);

	}

	@And("^I login using credentials$")
	public void loginUser(DataTable table) throws InterruptedException {
		WebApp.get().switchToNewWindow();
		WebApp.get().sendKeys(username, DataHandler.getDataFromCucumberTable(table, 0, 0));
		WebApp.get().sendKeys(password, DataHandler.getDataFromCucumberTable(table, 0, 1));
		WebApp.get().wait_ClickElement(loginBtn);
		Thread.sleep(7000);
	}

}
