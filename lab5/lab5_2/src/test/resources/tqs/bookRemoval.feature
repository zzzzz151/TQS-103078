Feature: Remove books
  The library should allow removal of books by the customer

  Scenario: Remove a book
    Given the following books:
      | title | author | published |
      | Game in the South | Sean Flint | 30 August 2020 |
      | Red and Green | Alice Scott | 28 February 2023 |
      | Daylight ran out | Jill Clinton | 15 March 2016 |
    When the customer removes the book titled 'Red and Green'
    And the customer searches all books
    Then 2 book should have been found
    And Book 1 should have the title 'Game in the South'
    And Book 2 should have the title 'Daylight ran out'