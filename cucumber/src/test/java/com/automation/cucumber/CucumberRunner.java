package com.automation.cucumber;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.*;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/java" }, glue = { "com.automation.StepDefinitions" })
public class CucumberRunner {
	
}
