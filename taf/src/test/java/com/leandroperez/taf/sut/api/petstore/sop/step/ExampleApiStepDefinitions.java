package com.leandroperez.taf.sut.api.petstore.sop.step;

import com.leandroperez.taf.sut.api.petstore.sop.service.ExampleApiService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author Leandro Henrique Perez
 */

public class ExampleApiStepDefinitions extends BaseExampleApiStepDefinitions {

    ExampleApiService exampleApiService;


    public void initSessionAndServices() {
        exampleApiService = new ExampleApiService();
    }


    @Given("that the user is authenticated in the Petstore API")
    public void thatTheUserIsAuthenticatedInThePetstoreAPI() {
    }

    @When("searching for pets with {string} status")
    public void searchingForPetsWithStatus(String arg0) {
        
    }

    @Then("response should contain a list of available pets")
    public void responseShouldContainAListOfAvailablePets() {
        
    }

    @And("the response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String arg0) {
    }
}
