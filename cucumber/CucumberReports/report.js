$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("com/automation/features/PHPTravels.feature");
formatter.feature({
  "line": 1,
  "name": "Sample test scenarios",
  "description": "",
  "id": "sample-test-scenarios",
  "keyword": "Feature"
});
formatter.before({
  "duration": 480854186,
  "status": "passed"
});
formatter.scenario({
  "line": 4,
  "name": "Login in PHPTravels",
  "description": "",
  "id": "sample-test-scenarios;login-in-phptravels",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 3,
      "name": "@php"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "I navigated to \"https://www.phptravels.net\"",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I click on Login icon",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "I login using credentials",
  "rows": [
    {
      "cells": [
        "user@phptravels.com",
        "demouser"
      ],
      "line": 8
    }
  ],
  "keyword": "And "
});
formatter.match({
  "arguments": [
    {
      "val": "https://www.phptravels.net",
      "offset": 16
    }
  ],
  "location": "Sapphire_StepDefs.openURL(String)"
});
formatter.result({
  "duration": 20394017280,
  "status": "passed"
});
formatter.match({
  "location": "PhpTravels.click_login_icon()"
});
formatter.result({
  "duration": 26244263680,
  "status": "passed"
});
formatter.match({
  "location": "PhpTravels.loginUser(DataTable)"
});
formatter.result({
  "duration": 8783152213,
  "status": "passed"
});
formatter.after({
  "duration": 6248606294,
  "status": "passed"
});
});