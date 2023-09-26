package com.testing.api.stepDefinitions;

import com.testing.api.models.Client;
import com.testing.api.requests.ClientRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ClientSteps extends BaseSteps{
    private final ClientRequest clientRequest = new ClientRequest();
    private Client   client;


    /**
     * Verify if there are registered clients in the system.
     */
    @Given("there are registered clients in the system")
    public void thereAreRegisteredClientsInTheSystem() {
        logger.info("there are registered clients in the system");
        List<Client> clientList = clientRequest.getClientsEntity(clientRequest.getClients());
        logger.info(clientList.isEmpty() + " " + clientList.size());
        Assert.assertFalse(clientList.isEmpty());
    }

    /**
     * Creates a Client object based on the clientData provided
     * @param clientData DataTable
     */
    @Given("I have a client with the following details:")
    public void iHaveAClientWithTheFollowingDetails(DataTable clientData) {
        logger.info("I have a client with the following details:" + clientData);
        Map<String, String> clientDataInfo = clientData.asMaps().get(0);
        client = Client.builder()
                .name(clientDataInfo.get("Name"))
                .lastName(clientDataInfo.get("LastName"))
                .country(clientDataInfo.get("Country"))
                .city(clientDataInfo.get("City"))
                .email(clientDataInfo.get("Email"))
                .phone(clientDataInfo.get("Phone")).build();

    }

    /**
     * Makes a GET request to view all the clients.
     */
    @When("I send a GET request to view all the clients")
    public void iSendAGETRequestToViewAllTheClient() {
        logger.info("I send a GET request to view all the clients");
        response = clientRequest.getClients();
    }

    /**
     * Makes a POST request to create a new client with the params provided on the iHaveAClientWithTheFollowingDetails function.
     */
    @When("I send a POST request to create a client")
    public void iSendAPOSTRequestToCreateAClient() {
        logger.info("I send a POST request to create a client");
        response = clientRequest.createClient(client);
    }

    /**
     * Compares the client created to verify that has the right attributes.
     */
    @Then("the response should include the details of the created client")
    public void theResponseShouldIncludeTheDetailsOfTheCreatedClient() {
        logger.info("the response should include the details of the created client");
        Client responseClient = clientRequest.getClientEntity(response);
        responseClient.setId(null);
        Assert.assertEquals(client, responseClient);
    }

    /**
     * Validates the response with the client JSON schema.
     */
    @Then("validates the response with client JSON schema")
    public void userValidatesResponseWithClientJSONSchema() {
        logger.info("validates the response with client JSON schema");
        Assert.assertTrue(clientRequest.validateSchema(response, "schemas/clientSchema.json"));
    }


    /**
     * Validates the response with the client List JSON schema.
     */
    @Then("validates the response with client list JSON schema")
    public void userValidatesResponseWithClientListJSONSchema() {
        logger.info("validates the response with client list JSON schema");
        Assert.assertTrue(clientRequest.validateSchema(response, "schemas/clientListSchema.json"));
    }


}
