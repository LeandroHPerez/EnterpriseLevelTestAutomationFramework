package com.leandroperez.taf.sut.mobile.firefoxapp.pom.page;

import com.leandroperez.taf.constants.TimeConstants;
import com.leandroperez.taf.core.Session;
import com.leandroperez.taf.sut.mobile.firefoxapp.pom.element.ExampleMobileElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleMobilePage extends ExampleMobileElement {
    public ExampleMobilePage(Session session) {
        super(session);
    }

    public void typeURL(String url) {
        assertTrue(url != null && !url.isEmpty(), "Invalid value to url");
        typeAtOnceInElementSafe(getLocatorInputSearchField(), url, TimeConstants.FIVE_SECONDS_DURATION);
        sleepSafe(TimeConstants.ONE_SECOND_MILLIS);
    }
}
