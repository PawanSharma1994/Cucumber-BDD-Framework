Feature: Proof of Concept that my framework works

  Background: 

  @test1
  Scenario: My First Test
    Given I navigated to "https://www.youtube.in"
    When I click on element
    Then I verify the element
    And I enter the text
      | SearchText | Cucumber |
    And I clicked on searchicon

  @test2
  Scenario: My Second Test
    Given I navigated to "https://www.youtube.in"
    When I click on element
    Then I verify the element
    And I fetch the text
    And I clicked on searchicon

  @test3
  Scenario Outline: Youtube
    Given I navigated to "https://www.youtube.in"
    When I click on element
    Then I verify the element
    And I enter the text as "<SearchText>"
    And I clicked on searchicon

    Examples: 
      | SearchText |
      | Cucumber   |
      | Gherking   |
      | Selenium   |
#  Scenario: My Fourth Test
#    Given I navigated to "https://www.youtube.in"
#    When I click on element
#    Then I verify the element
#    And I fetch the text
#    And I clicked on searchicon
