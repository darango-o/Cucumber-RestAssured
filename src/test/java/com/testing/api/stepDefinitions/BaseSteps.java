package com.testing.api.stepDefinitions;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

public class BaseSteps {
    protected static final Logger logger = LogManager.getLogger(BaseSteps.class);
    protected static Response response;
}
