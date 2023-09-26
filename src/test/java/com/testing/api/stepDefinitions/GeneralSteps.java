package com.testing.api.stepDefinitions;

import io.cucumber.java.en.Then;
import org.junit.Assert;

public class GeneralSteps extends BaseSteps{

    /**
     * Verifies if the status code is the right one.
     * @param statusCode int
     */
    @Then("the response should have a status code of {int}")
    public void theResponseShouldHaveAStatusCodeOf(int statusCode) {
        logger.info("the response should have a status code of " + statusCode);
        Assert.assertEquals(statusCode, response.getStatusCode());
    }
}
