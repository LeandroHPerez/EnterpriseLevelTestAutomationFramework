package com.leandroperez.taf.sut.mobile.firefoxapp.pom.element;

import com.leandroperez.taf.core.Session;
import com.leandroperez.taf.sut.mobile.firefoxapp.pom.page.BaseMobilePage;
import org.openqa.selenium.By;

public class ExampleMobileElement extends BaseMobilePage {

    String locatorCloseButton = "//android.view.View[@content-desc='Close']";
    String locatorSearchField = "//android.widget.EditText[@resource-id='org.mozilla.klar:id/mozac_browser_toolbar_edit_url_view']";
    String locatorDontAllowButton = "//android.widget.Button[@resource-id='com.android.permissioncontroller:id/permission_deny_button']";
    String locatorProtectionIconShield = "//android.widget.ImageView[@content-desc='Tracking Protection is on']";

    public ExampleMobileElement(Session session) {
        super(session);
    }

    public By getLocatorInputSearchField() {
        return By.xpath(locatorSearchField);
    }
}
