$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("com/automation/features/myfeature.feature");
formatter.feature({
  "line": 1,
  "name": "Proof of Concept that my framework works",
  "description": "",
  "id": "proof-of-concept-that-my-framework-works",
  "keyword": "Feature"
});
formatter.background({
  "line": 3,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.scenario({
  "line": 15,
  "name": "My Second Test",
  "description": "",
  "id": "proof-of-concept-that-my-framework-works;my-second-test",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 14,
      "name": "@test2"
    }
  ]
});
formatter.step({
  "line": 16,
  "name": "I navigated to \"https://www.youtube.in\"",
  "keyword": "Given "
});
formatter.step({
  "line": 17,
  "name": "I click on element",
  "keyword": "When "
});
formatter.step({
  "line": 18,
  "name": "I verify the element",
  "keyword": "Then "
});
formatter.step({
  "line": 19,
  "name": "I fetch the text",
  "keyword": "And "
});
formatter.step({
  "line": 20,
  "name": "I clicked on searchicon",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});