package com.leandroperez.taf.core.mobile.locators;

import com.leandroperez.taf.core.enumerator.PlatformInTest;
import com.leandroperez.taf.utils.testutil.FrameworkTestUtil;
import org.openqa.selenium.By;

public class MobileDeviceKeyboardElement {
    FrameworkTestUtil frameworkTestUtil = new FrameworkTestUtil();
    String locatorImplementMe = "Hey, i am an invalid locator. Implement me!";
    String locatorkeyboardGoButtonForIOS = "//XCUIElementTypeButton[@name='Go']";
    String locatorKeyboardGoButtonForAndroid = locatorImplementMe;



    public By getLocatolocatorKeyboardGoButton(PlatformInTest platformInTest) {
        if (!frameworkTestUtil.isPlatformInTestMobile(platformInTest)) return null;

        if (platformInTest == PlatformInTest.IOS) {
            return By.xpath(locatorkeyboardGoButtonForIOS);
        } else if (platformInTest == PlatformInTest.ANDROID) {
            return By.xpath(locatorKeyboardGoButtonForAndroid);
        } else {
            System.out.println("Error: " + "getLocatolocatorKeyboardGoButton error: platformInTest is not iOS or Android");
            return null;
        }
    }

}