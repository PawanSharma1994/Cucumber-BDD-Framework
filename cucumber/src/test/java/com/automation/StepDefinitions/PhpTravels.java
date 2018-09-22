package com.automation.StepDefinitions;

import org.openqa.selenium.By;

import com.automation.commonutils.DataHandler;
import com.automation.functionLib.ExtentReport;
import com.automation.functionLib.WebApp;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class PhpTravels {

	By loginlink = By.xpath("");

	
	
	
	
	@When("^I click on Login icon$")
	public void login() {
		WebApp.get().elementClick(loginlink);

	}

}
