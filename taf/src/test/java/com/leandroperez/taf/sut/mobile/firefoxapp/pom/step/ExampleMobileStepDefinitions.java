package com.leandroperez.taf.sut.mobile.firefoxapp.pom.step;

import com.leandroperez.taf.sut.mobile.firefoxapp.pom.page.ExampleMobilePage;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
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
        System.out.println("This is a very simple example scenario");
    }


    @Given("that the user opens the Firefox app on the operating system {string}")
    public void that_the_user_opens_the_firefox_app_on_the_operating_system(String os) {
        initSessionAndPages(os);
    }

    @And("close welcome screen")
    public void closeWelcomeScreen() {
        exampleMobilePage.clickCloseFromWelcomeScreen();
    }

    @When("type url {string}")
    public void type_url(String url) {
        exampleMobilePage.typeURL(url);
    }

    @And("type {string} key")
    public void typeKey(String specialKey) {
        exampleMobilePage.sendEnterkeyInURLField(specialKey);
    }

    @And("dont allow permission for notifications")
    public void dontAllowPermissionForNotifications() {
        exampleMobilePage.clickOnDontAllowSendNotificationsIfIsVisible();

    }

    @Then("then a safe shield icon will be displayed")
    public void then_a_safe_shield_icon_will_be_displayed() {
        exampleMobilePage.verifyIfSafeShieldIconIsVisible();
    }
}
