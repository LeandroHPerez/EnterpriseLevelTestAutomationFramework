package com.leandroperez.taf.sut.mobile.firefoxapp.pom.page;

import com.leandroperez.taf.constants.TimeConstants;
import com.leandroperez.taf.core.Session;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class BaseMobilePage {

    private Session session;

    public BaseMobilePage(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }

    public AppiumDriver getSessionAppiumDriver() {
        return this.session.getAppiumDriver();
    }

    protected void sleepSafe(long millis) {
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
            System.out.println(this.getClass().getEnclosingMethod().getDeclaringClass().getEnclosingMethod().getName() + "\n" + e + "\nElement not found.\nLocator: " + by.toString());
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
            System.out.println(this.getClass().getEnclosingMethod().getDeclaringClass().getEnclosingMethod().getName() + "\n" + e + "\nElement not found.\nLocator: " + by.toString());
        } finally {
            return webElement;
        }
    }

    public void clickInElement(WebElement element) {
        element.click();
    }

    public void clickInElementIfIsPresentSafe(By by, Duration timeout) {
        getWebElementIfIsPresentSafe(by, timeout).click();
    }

    public void clickInElementIfIsVisibleSafe(By by, Duration timeout) {
        getWebElementIfIsVisibleSafe(by, timeout).click();
    }


    public void typeAtOnceInElementSafe(By by, String text, Duration timeout) {
        getWebElementIfIsVisibleSafe(by, timeout).sendKeys(text);

            //WebElement el  = getSessionAppiumDriver().findElement(by);
            //el.getText();
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
}
