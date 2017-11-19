Feature: Proof of Concept that my framework works

  # Scenario: My First Test
  #   Given I navigated to "https://www.youtube.in"
  #   When I click on element
  #   Then I verify the element
  #   And I enter the text
  #     | SearchText | Cucumber |
  #   And I clicked on searchicon
  #  Scenario: My Second Test
  #    Given I navigated to "https://www.youtube.in"
  #    When I click on element
  #    Then I verify the element
  #    And I enter the text
  #      | SearchText | Birds |
  #    And I clicked on searchicon
  Scenario: Assignment 1
    Given I navigated to "https://www.facebook.com"
    When I log into my Account
      | userName              | Password  |
      | sharmapawan@gmail.com | ********* |
    And I post my status

  Scenario: Assignment 2
    Given I navigated to "http://wallethub.com/profile/test_insurance_company"
    When I hover over rating and select 5 stars
    Then I navigated to Reviews Page
    And I selected "Health"
    And I Entered Review in the review text box
    And submitted the review
