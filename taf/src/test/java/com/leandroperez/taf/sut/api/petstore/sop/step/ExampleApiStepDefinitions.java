package com.leandroperez.taf.sut.api.petstore.sop.step;

import com.leandroperez.taf.sut.api.petstore.sop.service.ExampleApiService;
import graphql.Assert;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Leandro Henrique Perez
 */

public class ExampleApiStepDefinitions extends BaseExampleApiStepDefinitions {

    ExampleApiService examplePetStoreExampleApiService;


    public void initSessionAndServices() {
        examplePetStoreExampleApiService = new ExampleApiService();
    }


    @Given("that the user is authenticated in the Petstore API")
    public void thatTheUserIsAuthenticatedInThePetstoreAPI() {
        initSessionAndServices();
        examplePetStoreExampleApiService.authenticateUserAndStoreRequestExample01();
    }

    @When("searching for pets with {string} status")
    public void searchingForPetsWithStatus(String status) {
        examplePetStoreExampleApiService.getPetsByStatus(status);
    }

    @Then("response should contain a list of available pets")
    public void responseShouldContainAListOfAvailablePets() {
        Assert.assertTrue(examplePetStoreExampleApiService.isPetByStatusValidList());
    }

    @And("the response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
        Assert.assertTrue(examplePetStoreExampleApiService.isInStatusCodeSafe(statusCode), "Status code is not as expected");

    }
}
