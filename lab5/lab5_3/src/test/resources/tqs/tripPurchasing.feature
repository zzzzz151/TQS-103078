Feature: Trip purchasing
  The main feature of Blazedemo app is to allow the customer to select and buy a trip

  Scenario: Select and buy a trip
    Given I am in the initial page
    When I select the trip from "Boston" to "Dublin"
    And I click 'Find Flights'
    And I select flight number 3
    And I fill my passenger details with my name "Ricardo"
    And I hit 'Confirm'
    Then I am on the confirmation page
    Then I should see the price

