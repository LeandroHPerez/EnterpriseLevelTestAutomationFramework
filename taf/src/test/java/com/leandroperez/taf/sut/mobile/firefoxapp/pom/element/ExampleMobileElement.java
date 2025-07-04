package com.leandroperez.taf.sut.mobile.firefoxapp.pom.element;

import com.leandroperez.taf.core.session.Session;
import com.leandroperez.taf.sut.mobile.firefoxapp.pom.page.BaseMobilePage;
import org.openqa.selenium.By;

/**
 * @author Leandro Henrique Perez
 */

public class ExampleMobileElement extends BaseMobilePage {

    String locatorCloseButton = "//android.view.View[@content-desc='Close'] | //XCUIElementTypeButton[@name='CloseButton']";
    String locatorSearchField = "//android.widget.TextView[@resource-id='org.mozilla.firefox:id/toolbar_text']| //XCUIElementTypeTextField[@name='url']";
    String locatorDontAllowButton = "//android.widget.Button[@resource-id='com.android.permissioncontroller:id/permission_deny_button']";
    String locatorSafeIconShield = "//android.widget.ImageView[@content-desc='Site information'] | //XCUIElementTypeButton[@name=\"TabLocationView.trackingProtectionButton\"]";
    String locatorNotNowForTranslatePage = "//android.widget.TextView[@text='Not now']";

    public ExampleMobileElement(Session session) {
        super(session);
    }

    public By getLocatorCloseButtonFromWelcomeScreen() {
        return By.xpath(locatorCloseButton);
    }

    public By getLocatorInputSearchField() {
        return By.xpath(locatorSearchField);
    }

    public By getLocatorDontAllowButton() {
        return By.xpath(locatorDontAllowButton);
    }

    public By getLocatorSafeIconShield() {
        return By.xpath(locatorSafeIconShield);
    }

    public By getLocatorNotNowForTranslatePage() {
        return By.xpath(locatorNotNowForTranslatePage);
    }
}
