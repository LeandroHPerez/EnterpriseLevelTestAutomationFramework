@SISTEMA
@API
Feature: Petstore API - Search pets by status

  Scenario Outline: Search for available pets
    Given that the user is authenticated in the Petstore API
    When searching for pets with "available" status
    Then response should contain a list of available pets
    And the response status code should be "<statusCode>"

    Examples:
      | statusCode  |
      | 200         |
