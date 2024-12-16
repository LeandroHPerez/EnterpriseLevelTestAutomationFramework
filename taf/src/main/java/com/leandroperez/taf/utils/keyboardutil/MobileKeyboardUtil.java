package com.leandroperez.taf.utils.keyboardutil;


import com.leandroperez.taf.core.enumerator.PlatformInTest;
import com.leandroperez.taf.core.session.Session;
import com.leandroperez.taf.utils.keyboardutil.constants.SpecialKey;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.WebElement;

/**
 * @author Leandro Henrique Perez
 */

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
        String actualText = "";
        switch (key) {
            case ENTER: //TODO review ENTER by keyboard on iOS
                try {
                    actualText = elementToType.getText();
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e + "\nError on getText() of field.\nLocator: " + elementToType);
                }
                if (actualText == null) {
                    actualText = "";

                }
                elementToType.sendKeys(actualText + "\n");
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
