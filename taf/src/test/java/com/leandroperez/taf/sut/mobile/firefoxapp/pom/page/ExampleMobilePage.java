package com.leandroperez.taf.sut.mobile.firefoxapp.pom.page;

import com.leandroperez.taf.constants.TimeConstants;
import com.leandroperez.taf.core.session.Session;
import com.leandroperez.taf.sut.mobile.firefoxapp.pom.element.ExampleMobileElement;
import com.leandroperez.taf.utils.keyboardutil.KeyMapper;
import com.leandroperez.taf.utils.keyboardutil.MobileKeyboardUtil;
import com.leandroperez.taf.utils.keyboardutil.constants.SpecialKey;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Leandro Henrique Perez
 */

public class ExampleMobilePage extends ExampleMobileElement {
    public ExampleMobilePage(Session session) {
        super(session);
    }

    public void clickCloseFromWelcomeScreen() {
        clickInElementIfIsVisibleSafe(getLocatorCloseButtonFromWelcomeScreen(), TimeConstants.TWO_SECONDS_DURATION);
        sleepSafe(TimeConstants.FIVE_HUNDRED_MILLIS);
    }

    public void typeURL(String url) {
        assertTrue(url != null && !url.isEmpty(), "Invalid value to url");

        MobileKeyboardUtil.handleSpecialKeyIfNeeded(url, getSessionAppiumDriver());


        if(!MobileKeyboardUtil.isSpecialKey(url)) {
            WebElement element = getWebElementIfIsVisibleSafe(getLocatorInputSearchField(), TimeConstants.FIVE_SECONDS_DURATION);
            assertNotNull(element, "Error in test: Element is null");
            /* Type the URL in the search field */
            if (getSession().isIOS()) {
                typeAtOnceInElementSafe(getLocatorInputSearchField(), url, TimeConstants.FIVE_SECONDS_DURATION);
            } else {
                typeSlowlyInElementIfIsPresentSafe(getLocatorInputSearchField(), url, TimeConstants.FIVE_SECONDS_DURATION);
            }
        }
        sleepSafe(TimeConstants.FIVE_HUNDRED_MILLIS);
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
        clickInElementIfIsVisibleSafe(getLocatorDontAllowButton(), TimeConstants.TWO_SECONDS_DURATION);
        sleepSafe(TimeConstants.ONE_SECOND_MILLIS);
    }

    public void verifyIfSafeShieldIconIsVisible() {
        WebElement element = getWebElementIfIsVisibleSafe(getLocatorSafeIconShield(), TimeConstants.THREE_SECONDS_DURATION);
        sleepSafe(TimeConstants.ONE_SECOND_MILLIS);
        assertNotNull(element, "Error in test: Safea Shield icon is not visible or is null");
    }

    public boolean tapTheButtonOfMobileDeviceKeyboard(String mobileDeviceKeyboardButtonLabel) {
        assertTrue(mobileDeviceKeyboardButtonLabel != null && !mobileDeviceKeyboardButtonLabel.isEmpty(), "Invalid value to mobileDeviceKeyboardButtonLabel");

        boolean isTappedOnButton = false;
        switch (mobileDeviceKeyboardButtonLabel.toUpperCase()) {
            case "GO":
            case "IR":
                WebElement element = getWebElementIfIsVisibleSafe(genericMobileDeviceKeyboardElement.getLocatorKeyboardGoButton(getSession()), TimeConstants.THREE_SECONDS_DURATION);
                if (clickInElementSafe(element)) {
                    isTappedOnButton = true;
                }
                break;
            default:
                System.out.println("tabTheButtonOfMobileDeciceKeyboard() = Unsupported key text: " + mobileDeviceKeyboardButtonLabel);
                break;
        }
        return isTappedOnButton;
    }

    public void clickOnNotNowForTranslatePageIfIsVisible() {
        clickInElementIfIsVisibleSafe(getLocatorNotNowForTranslatePage(), TimeConstants.TWO_SECONDS_DURATION);
        sleepSafe(TimeConstants.ONE_SECOND_MILLIS);
    }
}
