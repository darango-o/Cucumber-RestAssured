@active
Feature: Resource testing CRUD

  Scenario:  Get the List of Resources
    Given there are registered resources in the system
    When I send a GET request to view all the resources
    Then the response should have a status code of 200
    And validates the response with resource list JSON schema

  Scenario: Update the Last Resource
    Given there are registered resources in the system
    And I retrieve the details of the latest resource
    When I send a PUT request to update the latest resource
    """
    {
      "name": "Barcelona",
      "trademark": "Ni-ho",
      "stock": 1000,
      "price": 99.99,
      "description": "Brand New",
      "tags": "NewTag",
      "is_active": True
    }
    """
    Then the response should have a status code of 200
    And the response should have the following details:
      | Name      | Trademark | Stock | Price | Description | Tags   | Is_Active |
      | Barcelona | Ni-ho     | 1000  | 99.99 | Brand New   | NewTag | True      |
    And validates the response with resource JSON schema

