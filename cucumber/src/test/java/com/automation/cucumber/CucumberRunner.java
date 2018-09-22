package com.automation.cucumber;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.*;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/java" }, glue = { "com.automation.functionLib" }, tags = {"@test2"},format={"pretty","html:CucumberReports"}) // separate
																											// with
																											// ,
																											// to
																											// pass
																											// multiple
																											// tags
public class CucumberRunner {

}
