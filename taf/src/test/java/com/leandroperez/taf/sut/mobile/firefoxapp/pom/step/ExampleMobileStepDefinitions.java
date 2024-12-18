package com.leandroperez.taf.sut.mobile.firefoxapp.pom.step;

import com.leandroperez.taf.core.Session;
import com.leandroperez.taf.core.enumerator.PlatformInTest;
import com.leandroperez.taf.core.enumerator.TestExecutionStrategy;
import com.leandroperez.taf.sut.mobile.firefoxapp.pom.page.BaseMobilePage;
import com.leandroperez.taf.sut.mobile.firefoxapp.pom.page.ExampleMobilePage;
import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class ExampleMobileStepDefinitions extends BaseExampleMobileStepDefinitons {


    ExampleMobilePage exampleMobilePage;


    public void initSessionAndPages(String os) {
        AssertInitSession(os);
        exampleMobilePage = new ExampleMobilePage(session);
    }

    @After
    public  void closeSession(){
        super.closeSession();
    }

    @Given("an example scenario of mobile app")
    public void anExampleScenarioOfMobileApp() {
        System.out.println("bbbbbb aaaaaaaa aaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaaa aaaaaaaaaaaaaaaaa");
        System.out.println("bbbbbb aaaaaaaa aaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaaa aaaaaaaaaaaaaaaaa");
        System.out.println("bbbbbb aaaaaaaa aaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaaa aaaaaaaaaaaaaaaaa");
        System.out.println("bbbbbb aaaaaaaa aaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaaa aaaaaaaaaaaaaaaaa");

    }


    @Given("that the user opens the Firefox app on the operating system {string}")
    public void that_the_user_opens_the_firefox_app_on_the_operating_system(String os) {
        initSessionAndPages(os);
    }
    @When("type url {string}")
    public void type_url(String url) {
        exampleMobilePage.typeURL(url);
    }
    @When("type <ENTER>")
    public void type_enter() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("then a safe shield icon will be displayed")
    public void then_a_safe_shield_icon_will_be_displayed() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
