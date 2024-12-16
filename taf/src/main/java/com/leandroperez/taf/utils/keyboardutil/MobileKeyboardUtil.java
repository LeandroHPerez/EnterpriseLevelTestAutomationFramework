package com.leandroperez.taf.utils.keyboardutil;



import com.google.common.collect.ImmutableMap;
import com.leandroperez.taf.core.session.Session;
import com.leandroperez.taf.core.enumerator.PlatformInTest;
import com.leandroperez.taf.utils.keyboardutil.constants.SpecialKey;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class MobileKeyboardUtil {

    public SpecialKey convertTextToSpecialKey(String specialKeyText) {
        switch (specialKeyText) {
            case "<ENTER>":
                return SpecialKey.ENTER;
            case "<SEARCH>":
                return SpecialKey.SEARCH;
            // Add more cases as needed
            default:
                System.out.println("convertTextToSpecialKey()Unsupported key text: " + specialKeyText);
                return null;
        }
    }

    public void pressSpecialKey(Session session, SpecialKey key, WebElement elementToType) {
        PlatformInTest platformInTest = session.getCurrentPlatformInTest();
        switch (platformInTest) {
            case ANDROID:
                pressKeyAndroid(session, key);
                break;
            case IOS:
                pressKeyIOS(session, key, elementToType);
                break;
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platformInTest);
        }
    }

    private void pressKeyAndroid(Session session, SpecialKey key) {
        AndroidDriver driver = session.getAndroidDriver();
        switch (key) {
            case ENTER:
                driver.pressKey(new KeyEvent(AndroidKey.ENTER));
                break;
            case SEARCH:
                driver.pressKey(new KeyEvent(AndroidKey.SEARCH));
                break;
            // Add more cases as needed
            default:
                throw new IllegalArgumentException("Unsupported key: " + key);
        }
    }

    private void pressKeyIOS(Session session, SpecialKey key, WebElement elementToType) {
        IOSDriver driver = session.getIosDriver();
        AppiumDriver appiumDriver2 = session.getAppiumDriver();
        switch (key) {
            case ENTER:
                String actualText = "";
                try {
                    actualText = elementToType.getText();
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e + "\nError on getText() of field.\nLocator: " + elementToType);
                }
                if (actualText == null) {
                    actualText = "";

                }
                elementToType.sendKeys(actualText + "\n");
                //elementToType.sendKeys("\n");
                // Send the enter key using executeScript
                //driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "done"));

                break;
            case SEARCH:
                elementToType.sendKeys("\u2315");
                break;
            // Add more cases as needed
            default:
                throw new IllegalArgumentException("Unsupported key: " + key);
        }
    }

}
