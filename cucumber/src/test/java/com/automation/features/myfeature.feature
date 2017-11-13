Feature: Proof of Concept that my framework works

  Scenario: My First Test
    Given I navigated to "https://www.youtube.com/"
    When I click on "searchBox"
    And I enter the search text
      | SearchText | Cucumber |
    Then I close my browser
