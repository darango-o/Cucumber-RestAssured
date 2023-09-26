@active
Feature: Client testing CRUD

  Scenario:  Get the List of Clients
    Given there are registered clients in the system
    When I send a GET request to view all the clients
    Then the response should have a status code of 200
    And validates the response with client list JSON schema

  Scenario: Create a new client
    Given I have a client with the following details:
      | Name | LastName | Country | City     | Email | Phone      |
      | John | Doe      | USA     | New York | email | 1234567890 |
    When I send a POST request to create a client
    Then the response should have a status code of 201
    And the response should include the details of the created client
    And validates the response with client JSON schema



