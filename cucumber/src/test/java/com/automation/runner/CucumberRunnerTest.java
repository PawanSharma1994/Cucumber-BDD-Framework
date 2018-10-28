package com.automation.runner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/java" }, glue = { "com.automation.StepDefinitions" }, tags = {"@php,@test1"},format={"pretty","html:CucumberReports"}) // separate
																											// with
																											// ,
																											// to
																											// pass
																											// multiple
																											// tags
public class CucumberRunnerTest {

}
