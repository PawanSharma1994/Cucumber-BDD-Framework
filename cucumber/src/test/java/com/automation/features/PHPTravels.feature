Feature: Sample test scenarios 

@php 
Scenario: Login in PHPTravels 
	Given I navigated to "https://www.phptravels.net" 
	When I click on Login icon
	When I click on Login icon
	* I click on Login icon
	And I login using credentials 
		|UserName|user@phptravels.com|
		|Password|demouser|
