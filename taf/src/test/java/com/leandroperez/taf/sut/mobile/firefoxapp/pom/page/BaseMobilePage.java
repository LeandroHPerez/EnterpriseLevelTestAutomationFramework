package com.leandroperez.taf.sut.mobile.firefoxapp.pom.page;

import com.leandroperez.taf.constants.TimeConstants;
import com.leandroperez.taf.core.mobile.locators.GenericMobileDeviceKeyboardElement;
import com.leandroperez.taf.core.session.Session;
import com.leandroperez.taf.utils.keyboardutil.MobileKeyboardUtil;
import com.leandroperez.taf.utils.keyboardutil.constants.SpecialKey;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @author Leandro Henrique Perez
 */

public class BaseMobilePage {

    private Session session;
    protected GenericMobileDeviceKeyboardElement genericMobileDeviceKeyboardElement = new GenericMobileDeviceKeyboardElement();

    public BaseMobilePage(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }

    public AppiumDriver getSessionAppiumDriver() {
        return this.session.getAppiumDriver();
    }

    protected void sleepSafe(long millis) { //TODO: verificar troca pelo método de mesmo nome na classe ThreadUtils
        if (millis <= 0) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(this.getClass().getEnclosingMethod().getDeclaringClass().getEnclosingMethod().getName()
                    + "\n" + e + "\nError during wait/sleep. Waiting time: " + millis);
        }
    }


    protected WebElement getWebElementIfIsVisibleSafe(By by, Duration timeout) {
        WebElement webElement = null;
        try {
            WebDriverWait wait = new WebDriverWait(getSessionAppiumDriver(), timeout);
            webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return webElement;
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e + "\nElement not found.\nLocator: " + by.toString());
        } finally {
            return webElement;
        }
    }

    protected WebElement getWebElementIfIsPresentSafe(By by, Duration timeout) {
        WebElement webElement = null;
        try {
            WebDriverWait wait = new WebDriverWait(getSessionAppiumDriver(), timeout);
            webElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return webElement;
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e + "\nElement not found.\nLocator: " + by.toString());
        } finally {
            return webElement;
        }
    }

    public void clickInElement(WebElement element) {
        element.click();
    }

    public boolean clickInElementSafe(WebElement element) {
        if (element == null) {
            return false;
        }
        element.click();
        return true;
    }

    public void clickInElementIfIsPresentSafe(By by, Duration timeout) {
        getWebElementIfIsPresentSafe(by, timeout).click();
    }

    public WebElement clickInElementIfIsVisibleSafe(By by, Duration timeout) {
        WebElement element = getWebElementIfIsVisibleSafe(by, timeout);
        if (element != null) {
            element.click();
        }
        return element;
    }


    public WebElement typeAtOnceInElementSafe(By by, String text, Duration timeout) {
        WebElement element = getWebElementIfIsVisibleSafe(by, timeout);
        if (element == null || text == null) {
            System.out.println("typeAtOnceInElementSafe(): Error: Element or text is null.\nLocator: " + by + "\nText: " + text);
            return null;
        }
        element.sendKeys(text);
        return element;
    }

    public WebElement typeSlowlyInElementIfIsPresentSafe(By by, String text, Duration timeout) {
        WebElement elementToClickAndType = getWebElementIfIsPresentSafe(by, timeout);
        if (elementToClickAndType == null || text == null) {
            return null;
        }

        clickInElement(elementToClickAndType);

        if (text.length() > 1) {
            for (char ch : text.toCharArray()) {
                sleepSafe(TimeConstants.DEFAULT_TIME_FOR_SLOWLY_TYPE);
                new Actions(getSessionAppiumDriver())
                        .sendKeys(Character.toString(ch))
                        .perform();
                sleepSafe(TimeConstants.DEFAULT_TIME_FOR_SLOWLY_TYPE);
            }
        }
        return elementToClickAndType;
    }


    public WebElement typeSpecialKeySafe(WebElement elementToType, String specialKeyText) {
        if (elementToType == null || specialKeyText == null || specialKeyText.isEmpty()) {
            return null;
        }
        try {
            MobileKeyboardUtil mobileKeyboardUtil = new MobileKeyboardUtil();
            SpecialKey key = mobileKeyboardUtil.convertTextToSpecialKey(specialKeyText);
            if (key == null) {
                return null;
            }
            mobileKeyboardUtil.pressSpecialKey(session, key, elementToType);
            return elementToType;
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e + "\nError on send/type Special key.\nLocator: " + elementToType);
            return null;
        }
    }

}
