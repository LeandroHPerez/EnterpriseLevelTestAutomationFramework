package com.leandroperez.taf.utils.keyboardutil;


import com.leandroperez.taf.core.enumerator.PlatformInTest;
import com.leandroperez.taf.core.session.Session;
import com.leandroperez.taf.utils.keyboardutil.constants.SpecialKey;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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
            case "<NEW_LINE>":
                return SpecialKey.NEW_LINE;
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
                pressKeyAndroid(session, key, elementToType);
                break;
            case IOS:
                pressKeyIOS(session, key, elementToType);
                break;
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platformInTest);
        }
    }

    private void pressKeyAndroid(Session session, SpecialKey key, WebElement elementToType) {
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


    /* Utility methods to identify and handle special keys in input strings that starts with < and ends with > */
    // E.g.: <ENTER>, <NEW_LINE>
    public static boolean isSpecialKey(String input) {
        return input != null && input.startsWith("<") && input.endsWith(">");
    }

    public static SpecialKey extractSpecialKey(String input) {
        return SpecialKey.fromString(input);
    }

    public static void handleSpecialKeyIfNeeded(String input, AppiumDriver driver) {
        if (isSpecialKey(input)) {
            SpecialKey key = extractSpecialKey(input);

            /* Special handling for NEW_LINE key */
            if (key != null && key.isNewLine()) {
                new Actions(driver)
                        .sendKeys("\n")
                        .perform();
            }
        }
    }


}
