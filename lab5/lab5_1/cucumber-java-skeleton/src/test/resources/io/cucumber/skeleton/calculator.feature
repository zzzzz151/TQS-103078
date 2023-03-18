Feature: Calculator

  Scenario: Testing addition
    Given a calculator I just turned on
    When I add 3 to 7
    Then the result is 10.0

  Scenario: Testing  subtraction
    Given a calculator I just turned on
    When I subtract 2 from 5
    Then the result is 3.0