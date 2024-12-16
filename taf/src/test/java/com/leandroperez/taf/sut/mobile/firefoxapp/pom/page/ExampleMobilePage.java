package com.leandroperez.taf.sut.mobile.firefoxapp.pom.page;

import com.leandroperez.taf.constants.TimeConstants;
import com.leandroperez.taf.core.session.Session;
import com.leandroperez.taf.sut.mobile.firefoxapp.pom.element.ExampleMobileElement;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleMobilePage extends ExampleMobileElement {
    public ExampleMobilePage(Session session) {
        super(session);
    }

    public void clickCloseFromWelcomeScreen() {
        clickInElementIfIsVisibleSafe(getLocatorCloseButtonFromWelcomeScreen(), TimeConstants.FIVE_SECONDS_DURATION);
        sleepSafe(TimeConstants.ONE_SECOND_MILLIS);
    }

    public void typeURL(String url) {
        assertTrue(url != null && !url.isEmpty(), "Invalid value to url");
        typeAtOnceInElementSafe(getLocatorInputSearchField(), url, TimeConstants.FIVE_SECONDS_DURATION);
        sleepSafe(TimeConstants.ONE_SECOND_MILLIS);
    }


    public void sendEnterkeyInURLField(String specialKey) {
        assertTrue(specialKey != null && !specialKey.isEmpty(), "Invalid value to specialKey");
        WebElement element = getWebElementIfIsVisibleSafe(getLocatorInputSearchField(), TimeConstants.FIVE_SECONDS_DURATION);
        sleepSafe(TimeConstants.ONE_SECOND_MILLIS);
        assertNotNull(element, "Error in test: Element is null");
        typeSpecialKeySafe(element, specialKey);
        sleepSafe(TimeConstants.THREE_SECONDS_MILLIS);
    }


    public void clickOnDontAllowSendNotificationsIfIsVisible() {
        clickInElementIfIsVisibleSafe(getLocatorDontAllowButton(), TimeConstants.THREE_SECONDS_DURATION);
        sleepSafe(TimeConstants.ONE_SECOND_MILLIS);
    }

    public void verifyIfSafeShieldIconIsVisible() {
        WebElement element = getWebElementIfIsVisibleSafe(getLocatorSafeIconShield(), TimeConstants.THREE_SECONDS_DURATION);
        sleepSafe(TimeConstants.ONE_SECOND_MILLIS);
        assertNotNull(element, "Error in test: Safea Shield icon is not visible or is null");
    }
}
