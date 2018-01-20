Feature: Proof of Concept that my framework works

 Background: 

     Scenario: My First Test
     Given I navigated to "https://www.youtube.in"
       When I click on element
       Then I verify the element
       And I enter the text
         | SearchText | Cucumber |
       And I clicked on searchicon
  
#  Scenario: My Second Test
#    Given I navigated to "https://www.youtube.in"
#    When I click on element
#    Then I verify the element
#    And I fetch the text
#    And I clicked on searchicon

#  Scenario: My Fourth Test
#    Given I navigated to "https://www.youtube.in"
#    When I click on element
#    Then I verify the element
#    And I fetch the text
#    And I clicked on searchicon
