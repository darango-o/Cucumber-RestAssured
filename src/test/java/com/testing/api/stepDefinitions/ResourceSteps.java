package com.testing.api.stepDefinitions;

import com.testing.api.models.Resource;
import com.testing.api.requests.ResourceRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ResourceSteps extends BaseSteps{
    private final ResourceRequest resourceRequest = new ResourceRequest();

    private List<Resource> resources;
    private Resource resource;

    /**
     * Verify if there are registered resources in the system.
     */
    @Given("there are registered resources in the system")
    public void thereAreRegisteredResourcesInTheSystem() {
        logger.info("there are registered resources in the system");
        resources = resourceRequest.getResourcesEntity(resourceRequest.getResources());
        Assert.assertFalse(resources.isEmpty());
    }

    /**
     * Retrieves the information of the latest resource on the resources List.
     */
    @Given("I retrieve the details of the latest resource")
    public void iRetrieveTheDetailsOfTheLatestResource() {
        logger.info("I retrieve the details of the latest resource");
        resource = resources.get(resources.size()-1);

    }

    /**
     * Makes a GET request to view all the resources.
     */
    @When("I send a GET request to view all the resources")
    public void iSendAGETRequestToViewAllTheResource() {
        logger.info("I send a GET request to view all the resources");
        response = resourceRequest.getResources();
    }

    /**
     * Makes a PUT request sending as the body the info provided on the requestBody.
     * @param requestBody String
     */
    @When("I send a PUT request to update the latest resource")
    public void iSendAPUTRequestToUpdateTheResourceWithID(String requestBody) {
        logger.info("I send a PUT request to update the latest resource " + requestBody);
        response = resourceRequest.updateResource(resourceRequest.getResourceEntity(requestBody), resource.getId());
    }

    /**
     * Compares a created Resource object, based on the clientData provided, with the actual Response Resource Object.
     * @param expectedData DataTable
     */
    @Then("the response should have the following details:")
    public void theResponseShouldHaveTheFollowingDetails(DataTable expectedData) {
        logger.info("the response should have the following details:" + expectedData);
        Map<String, String> expectedDataInfo = expectedData.asMaps().get(0);
        resource = Resource.builder()
                .name(expectedDataInfo.get("Name"))
                .trademark(expectedDataInfo.get("Trademark"))
                .stock(Integer.parseInt(expectedDataInfo.get("Stock")))
                .price(Double.parseDouble(expectedDataInfo.get("Price")))
                .description(expectedDataInfo.get("Description"))
                .tags(expectedDataInfo.get("Tags"))
                .is_active(Boolean.parseBoolean(expectedDataInfo.get("Is_Active"))).build();
        Resource responseResource = resourceRequest.getResourceEntity(response);
        responseResource.setId(null);
        Assert.assertEquals(resource, responseResource);
    }

    /**
     * Validates the response with the resource JSON schema.
     */
    @Then("validates the response with resource JSON schema")
    public void userValidatesResponseWithResourceJSONSchema() {
        logger.info("validates the response with resource JSON schema");
        Assert.assertTrue(resourceRequest.validateSchema(response, "schemas/resourceSchema.json"));
    }

    /**
     * Validates the response with the resource List JSON schema.
     */
    @Then("validates the response with resource list JSON schema")
    public void userValidatesResponseWithResourceListJSONSchema() {
        logger.info("validates the response with resource list JSON schema");
        Assert.assertTrue(resourceRequest.validateSchema(response, "schemas/resourceListSchema.json"));
    }
}
